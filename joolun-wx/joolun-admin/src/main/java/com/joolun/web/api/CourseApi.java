package com.joolun.web.api;

import com.aliyun.oss.OSSClient;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.mall.config.CommonConstants;
import com.joolun.mall.entity.*;
import com.joolun.mall.service.*;
import com.joolun.mall.util.OSSClientUtil;
import com.joolun.system.service.ISysDictDataService;
import com.joolun.mall.dto.CourseVO;
import com.joolun.web.util.FileUtils;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 课程API
 *
 * @author Owen
 * @date 2021/12/14 8:34 PM
 **/
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/weixin/api/course/")
public class CourseApi {

    /**
     * 课程Service
     */
    private final ICourseService courseService;

    /**
     * 课程视频Service
     */
    private final ICourseVideoService courseVideoService;

    /**
     * 课程闯关问题Service
     */
    private final ICourseQuestionService courseQuestionService;

    /**
     * 课程闯关问题答案Service
     */
    private final ICourseQuestionChoiceService courseQuestionChoiceService;

    /**
     * 书籍Service
     */
    private final IBookService bookService;

    private final IBookCategoryService bookCategoryService;

    /**
     * 系统数据字典Service
     */
    private final ISysDictDataService sysDictDataService;

    /**
     * 用户课程Service
     */
    private final IUserCourseService userCourseService;

    /**
     * 课程问题Service
     */
    private final ICourseQuestionChoiceService choiceService;

    /**
     * 用户答案Service
     */
    private final IUserAnswerService userAnswerService;

    /**
     * 用户录音Service
     */
    private final IUserAudioService userAudioService;

    /**
     * 课程故事线Service
     */
    private final ICourseStoryService iCourseStoryService;

    /**
     * 查询奖学金计划课程
     *
     * @return 参与奖学金计划的课程
     */
    @GetMapping("/plan")
    public AjaxResult getPlan() {
        return AjaxResult.success(courseService.selectPlan());
    }

    /**
     * 查询推荐列表
     *
     * @param page 分页参数
     * @return 课程列表
     */
    @GetMapping("/recommend")
    public AjaxResult getRecommendPlan(Page<Course> page) {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("recommend", "1");
        wrapper.eq("plan", "0");
        wrapper.orderByDesc("create_time");
        wrapper.select("id,title,price,cover_url,age_start,age_end,rates,(price*rates) as realPrice");
        return AjaxResult.success(courseService.page(page, wrapper));
    }

    /**
     * 查询课程详情
     *
     * @param id 课程ID
     * @return 课程详情
     */
    @GetMapping("/detail/{id}")
    public AjaxResult getCourseDetail(@PathVariable Long id) {
        Course course = courseService.getById(id);
        if (null != course.getRates() && course.getRates().compareTo(BigDecimal.ZERO) > 0) {
            course.setRealPrice(course.getPrice().multiply(course.getRates()));
        } else {
            course.setRealPrice(course.getPrice());
        }
        QueryWrapper<CourseVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", id).orderByAsc("sort");
        List<CourseVideo> courseVideoList = courseVideoService.list(wrapper);
        Map<String, Object> result = new HashMap<>();
        result.put("course", course);
        result.put("video", courseVideoList);
        return AjaxResult.success(result);
    }

    /**
     * 查询课程闯关问题及答案 与用户答案
     *
     * @param id 课程ID
     * @return 课程音频问题
     */
    @GetMapping("/question/{id}/{userId}")
    public AjaxResult getCourseQuestion(@PathVariable Long id, @PathVariable String userId) {
        List<CourseQuestion> questions = getCourseQuestions(id, userId);
        return AjaxResult.success(questions);
    }

    private List<CourseQuestion> getCourseQuestions(Long id, String userId) {
        QueryWrapper<CourseQuestion> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", id).orderByAsc("sort");
        List<CourseQuestion> questions = courseQuestionService.list(wrapper);
        questions.forEach(t -> {
            QueryWrapper<CourseQuestionChoice> choiceQueryWrapper = new QueryWrapper<>();
            choiceQueryWrapper.eq("question_id", t.getId());
            List<CourseQuestionChoice> choices = choiceService.list(choiceQueryWrapper);
            t.setChoices(choices);
            QueryWrapper<UserAnswer> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("question_id", t.getId()).eq("user_id", userId);
            UserAnswer answer = userAnswerService.getOne(queryWrapper);
            if (null != answer) {
                t.setChoiceId(answer.getAnswerId());
            }
        });
        return questions;
    }


    /**
     * 保存用户答案
     *
     * @param userAnswer 用户答案
     * @return 成功
     */
    @PostMapping("/choice")
    public AjaxResult updateUserChoice(@RequestBody UserAnswer userAnswer) {
        return AjaxResult.success(userAnswerService.saveOrUpdate(userAnswer));
    }


    /**
     * 查询用户录音信息
     *
     * @param courseId 课程ID
     * @param userId   用户Id
     * @return 用户录音对象
     */
    @GetMapping("/audio/{courseId}/{userId}")
    public AjaxResult getAudio(@PathVariable Long courseId, @PathVariable String userId) {
        QueryWrapper<UserAudio> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).eq("course_id", courseId);
        return AjaxResult.success(userAudioService.getOne(wrapper));
    }

    /**
     * 保存用户录音文件
     *
     * @param request 请求参数
     * @param file    文件
     * @return 录音文件地址
     */
    @PostMapping("/audio")
    public AjaxResult uploadAudio(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        String audioUrl;
        File fileLocal = null;
        try {
            fileLocal = FileUtils.mutipartFileToFile(file);
            OSSClient client = OSSClientUtil.getOSSClient();
            String bucketName = OSSClientUtil.getBucketName();
            String endPoint = OSSClientUtil.getEndpoint();
            String userId = request.getParameter("userId");
            String courseId = request.getParameter("courseId");
            System.out.println(userId);
            System.out.println(courseId);
            UserAudio userAudio;

            QueryWrapper<UserAudio> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", userId).eq("course_id", courseId);
            userAudio = userAudioService.getOne(wrapper);
            if (null == userAudio) {
                userAudio = new UserAudio();
            }
            userAudio.setUserId(userId);
            userAudio.setCourseId(Long.parseLong(courseId));
            userAudio.setCreateTime(LocalDateTime.now());
            audioUrl = "https://" + bucketName + "." + endPoint + "/audio/" + LocalDate.now() + "/" + file.getOriginalFilename();
            userAudio.setAudioUrl(audioUrl);
            userAudioService.saveOrUpdate(userAudio);

            //上传文件放后
            OSSClientUtil.uploadObject2OSS(client, fileLocal, bucketName, "audio/" + LocalDate.now());


        } catch (Exception e) {
            log.error("CourseApi.uploadAudio", e);
            return AjaxResult.error("文件处理异常，请联系管理员");
        } finally {
            if (null != fileLocal) {
                FileUtils.deleteTempFile(fileLocal);
            }
        }

        return AjaxResult.success(audioUrl);
    }

    /**
     * 返回书籍树形集合
     */
    @ApiOperation(value = "返回树形集合")
    @GetMapping("/tree")
    public AjaxResult goodsCategoryTree(BookCategory bookCategory) {
        bookCategory.setEnable(CommonConstants.YES);
        return AjaxResult.success(bookCategoryService.selectTree(bookCategory));
    }

    /**
     * 获取读书报告
     *
     * @param id     课程ID
     * @param userId 用户ID
     * @return 读书报告
     */
    @GetMapping("/report/{id}/{userId}")
    public AjaxResult getReport(@PathVariable Long id, @PathVariable String userId) {

        //1.查询用户课程问题及答案
        List<CourseQuestion> courseQuestions = getCourseQuestions(id, userId);

        courseQuestions.forEach(question -> {
            Long choosed = question.getChoiceId();
            List<CourseQuestionChoice> choices = question.getChoices();
            choices.forEach(choice -> {
                if (null != choosed && choice.getChoosed() == 1 && choosed.equals(choice.getId())) {
                    question.setCorrect(true);
                }
            });

        });

        //2.课程故事线
        QueryWrapper<CourseStory> storyWrapper = new QueryWrapper<>();
        storyWrapper.eq("course_id", id);
        List<CourseStory> stories = iCourseStoryService.list(storyWrapper);

        //3.用户录音
        QueryWrapper<UserAudio> audioWrapper = new QueryWrapper<>();
        audioWrapper.eq("course_id", id);
        UserAudio userAudio = userAudioService.getOne(audioWrapper);

        //4.课程本身信息
        Course course = courseService.getById(id);

        Map<String, Object> result = new HashMap<>();
        result.put("courseQuestion", courseQuestions);
        result.put("story", stories);
        result.put("userAudio", userAudio);
        result.put("course", course);

        return AjaxResult.success(result);
    }

    /**
     * 用户课程信息
     */
    @GetMapping("/usercourse/{id}/{userId}")
    public AjaxResult isMoneyGet(@PathVariable Long id, @PathVariable Long userId) {
        QueryWrapper<UserCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", id).eq("user_id", userId);
        UserCourse userCourse = userCourseService.getOne(wrapper);
        return AjaxResult.success(userCourse);
    }

    /**
     * 分页查询课程
     *
     * @param page   分页对象
     * @param course 查询对象
     * @return 书籍列表
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/list")
    public AjaxResult getGoodsSpuPage(Page page, CourseVO course) {
        return AjaxResult.success(courseService.selectDataPage(page, course));
    }
}
