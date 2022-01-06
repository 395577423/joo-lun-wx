package com.joolun.web.controller.course;

import com.aliyun.oss.OSSClient;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.mall.entity.*;
import com.joolun.mall.service.*;
import com.joolun.mall.util.OSSClientUtil;
import com.joolun.weixin.entity.WxUser;
import com.joolun.weixin.service.WxUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Owen
 * @date 2022/1/2 9:47 PM
 */
@Controller
@RequestMapping("/report")
@AllArgsConstructor
@Slf4j
public class ReportController {

    private final ICourseStoryService iCourseStoryService;

    private final IUserAudioService userAudioService;

    private final ICourseService courseService;

    private final ICourseQuestionService courseQuestionService;

    private final IUserAnswerService userAnswerService;

    private final ICourseQuestionChoiceService choiceService;

    private final WxUserService wxUserService;

    private final ICourseVideoService courseVideoService;

    private final IUserCourseService userCourseService;

    private final RestTemplate restTemplate;

    @GetMapping("/test")
    @ResponseBody
    public AjaxResult test(HttpServletRequest request, HttpServletResponse response, Long id, String userId, String userName) throws IOException {
        log.info(request.getPathInfo());
        response.sendRedirect("/report/mobile?id=" + id + "&userId=" + userId + "&userName=" + userName);
        return AjaxResult.success();
    }

    @GetMapping("/mobile")
    public ModelAndView mobileTemplate(Model model, Long id, String userId, String userName) {
        log.info("调用了图片生成");
        //4.课程本身信息
        Course course = courseService.getById(id);

        QueryWrapper<CourseVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", id);
        List<CourseVideo> videos = courseVideoService.list(videoQueryWrapper);

        AtomicReference<Integer> videoLength = new AtomicReference<>(0);
        videos.forEach(t -> {
            videoLength.updateAndGet(v -> v + t.getDuration());
        });

        String videoDuration = videoLength.get() / 60 + "分钟";

        QueryWrapper<UserAnswer> answerQueryWrapper = new QueryWrapper<>();
        answerQueryWrapper.eq("course_id", id);
        answerQueryWrapper.eq("user_id", userId);
        List<UserAnswer> userAnswers = userAnswerService.list(answerQueryWrapper);

        AtomicReference<Integer> correctCount = new AtomicReference<>(0);
        userAnswers.forEach(t -> {
            if (t.getCorrect() == 1) {
                correctCount.updateAndGet(v -> v++);
            }
        });

        int star = 20 - (userAnswers.size() - correctCount.get());


        model.addAttribute("userName", userName);
        model.addAttribute("star", star);
        model.addAttribute("course", course);
        model.addAttribute("length", videoDuration);
        model.addAttribute("userId", userId);
        return new ModelAndView("mobile");
    }

    @ResponseBody
    @PostMapping("/image")
    public AjaxResult saveImage(String img,Long courseId,String userId) throws Exception {
        log.info("调用了图片上传");
        OSSClient client = OSSClientUtil.getOSSClient();
        String bucketName = OSSClientUtil.getBucketName();
        String endPoint = OSSClientUtil.getEndpoint();
        File file = new File("/Users/ben/Downloads/");
        if (!file.exists()) {
            file.createNewFile();
        }
        img = URLDecoder.decode(img, "UTF-8");
        img = img.replaceAll("data:image/png;base64,", "");
        String filepath = generateFile(img, "/Users/ben/Downloads/");
        File uploadFile = new File(filepath);
        OSSClientUtil.uploadObject2OSS(client, uploadFile, bucketName, "share/" + LocalDate.now());
        String url = "https://" + bucketName + "." + endPoint + "/share/" + LocalDate.now() + "/" + uploadFile.getName();
        QueryWrapper<UserCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        queryWrapper.eq("user_id", userId);
        UserCourse userCourse = userCourseService.getOne(queryWrapper);
        userCourse.setReport(url);
        userCourseService.saveOrUpdate(userCourse);
        //删除文件
        uploadFile.deleteOnExit();
        return AjaxResult.success();
    }

    public String generateFile(String base64FileStr, String filePath) throws Exception {
        if (StringUtils.isAnyBlank(filePath, base64FileStr)) {
            return null;
        }
        // 截取图片文件后缀


        // 截取图片数据
        String base64ImgData = StringUtils.substringAfter(base64FileStr, ";base64,");

        // 文件名称
        String fileName = DateFormatUtils.format(new Date(), "yyyyMMddHHmmsssSSS") + "." + "png";

        // 文件相对路径
        String relativePath = DateFormatUtils.format(new Date(), "yyyyMMdd");
        // 文件目录：NFS路径/日期
        filePath = filePath + "/" + relativePath;

        // 判断目录是否存在，不存在则创建
        File file = new File(filePath);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }

        String imgPath = filePath + "/" + fileName;

        BASE64Decoder decoder = new BASE64Decoder();
        byte[] bs = decoder.decodeBuffer(base64ImgData);
        FileOutputStream fos = null;
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(base64FileStr);
            for (int i = 0; i < b.length; ++i) {
                //调整异常数据
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            //生成jpeg图片
            OutputStream out = new FileOutputStream(imgPath);
            out.write(b);
            out.flush();
            out.close();

        } catch (Exception e) {
            System.out.println("保存图片到本地异常");
        }
        return imgPath;

    }

    @GetMapping
    public ModelAndView toTemplate(Long id, String userId, Model result) {

        System.out.println("================================report================================");
        //1.查询用户课程问题及答案
        List<CourseQuestion> courseQuestions = getCourseQuestions(id, userId);

        handleUserAnswer(courseQuestions);

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

        //5.小程序用户信息
        WxUser wxUser = wxUserService.getById(userId);

        result.addAttribute("courseQuestion", courseQuestions);
        result.addAttribute("story", stories);
        result.addAttribute("userAudio", userAudio);
        result.addAttribute("course", course);
        result.addAttribute("wxUser", wxUser);


        return new ModelAndView("report");

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

    private void handleUserAnswer(List<CourseQuestion> courseQuestions) {
        courseQuestions.forEach(question -> {
            Long choosed = question.getChoiceId();
            List<CourseQuestionChoice> choices = question.getChoices();
            question.setCorrect(false);
            choices.forEach(choice -> {
                if (null != choosed && choice.getChoosed() == 1 && choosed.equals(choice.getId())) {
                    question.setCorrect(true);
                }
            });

        });
    }
}
