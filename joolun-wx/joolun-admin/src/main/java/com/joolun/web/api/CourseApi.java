package com.joolun.web.api;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.aliyun.oss.OSSClient;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.mall.config.CommonConstants;
import com.joolun.mall.config.MallConfigProperties;
import com.joolun.mall.dto.CoursePayVO;
import com.joolun.mall.dto.CourseVO;
import com.joolun.mall.entity.*;
import com.joolun.mall.service.*;
import com.joolun.mall.util.OSSClientUtil;
import com.joolun.web.util.FileUtils;
import com.joolun.weixin.config.WxPayConfiguration;
import com.joolun.weixin.constant.MyReturnCode;
import com.joolun.weixin.entity.WxUser;
import com.joolun.weixin.service.WxUserService;
import com.joolun.weixin.utils.WxMaUtil;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

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


    private final ICourseClazzService iCourseClazzService;

    /**
     * 书籍Service
     */
    private final IBookService bookService;

    private final IBookCategoryService bookCategoryService;

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
     * 小程序用户
     */
    private final WxUserService wxUserService;

    /**
     * 课程视频服务
     */
    private final ICourseAudioService iCourseAudioService;

    private final MallConfigProperties mallConfigProperties;

    /**
     * 赋能中心视频服务
     */
    private final IEmpowerVideoService iEmpowerVideoService;

    private final IUserEmpowerService iUserEmpowerService;


    private final ICourseGuideService iCourseGuideService;

    private final ICourseReplyService iCourseReplyService;

    /**
     * 查询奖学金计划课程
     *
     * @return 参与奖学金计划的课程
     */
    @GetMapping("/plan")
    public AjaxResult getPlan() {
        List<Course> courses = courseService.selectPlan();
        courses.forEach(t -> {
            List<Book> listByCourse = bookService.getListByCourse(t.getId());
            t.setBookList(listByCourse);
        });
        return AjaxResult.success(courses);
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
        wrapper.select("id,title,price,cover_url,age_start,age_end,rates,participant");
        return AjaxResult.success(courseService.page(page, wrapper));
    }


    /**
     * 课程音频列表
     *
     * @param id 课程ID
     * @return
     */
    @GetMapping("/audios/{id}")
    public AjaxResult getCourseAudios(@PathVariable Long id) {
        QueryWrapper<CourseAudio> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", id);
        List<CourseAudio> audios = iCourseAudioService.list(wrapper);
        return AjaxResult.success(audios);
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
        QueryWrapper<CourseVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", id).orderByAsc("sort");
        List<CourseVideo> courseVideoList = courseVideoService.list(wrapper);

        QueryWrapper<CourseClazz> clazzQueryWrapper = new QueryWrapper<>();
        clazzQueryWrapper.eq("course_id", id).orderByAsc("sort");
        List<CourseClazz> clazzList = iCourseClazzService.list(clazzQueryWrapper);


        List<CourseGuide> guide = iCourseGuideService.list(Wrappers.<CourseGuide>lambdaQuery().eq(CourseGuide::getCourseId, id));

        Map<String, Object> result = new HashMap<>();
        result.put("course", course);
        result.put("video", courseVideoList);
        result.put("clazz", clazzList);
        result.put("guide", guide);
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

        handleUserAnswer(questions);

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
    @GetMapping("/audio/{courseId}/{userId}/{audioId}")
    public AjaxResult getAudio(@PathVariable Long courseId, @PathVariable String userId, @PathVariable Long audioId) {
        QueryWrapper<UserAudio> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).eq("course_id", courseId).eq("audio_id", audioId);
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
            String audioId = request.getParameter("audioId");
            String replyId = request.getParameter("replyId");
            CourseReply courseReply = iCourseReplyService.getById(replyId);

            UserAudio userAudio;

            QueryWrapper<UserAudio> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", userId).eq("course_id", courseId).eq("audio_id", audioId);
            userAudio = userAudioService.getOne(wrapper);
            if (null == userAudio) {
                userAudio = new UserAudio();
            }
            userAudio.setReplyId(Integer.parseInt(replyId));
            userAudio.setContent(courseReply.getContent());


            userAudio.setUserId(userId);
            userAudio.setCourseId(Long.parseLong(courseId));
            userAudio.setAudioId(Long.parseLong(audioId));
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

        handleUserAnswer(courseQuestions);

        //2.课程故事线
        QueryWrapper<CourseStory> storyWrapper = new QueryWrapper<>();
        storyWrapper.eq("course_id", id);
        List<CourseStory> stories = iCourseStoryService.list(storyWrapper);

        //3.用户录音
        List<UserAudio> userAudio = userAudioService.listUserAudio(userId, id);

        //4.课程本身信息
        Course course = courseService.getById(id);

        List<Book> listByCourse = bookService.getListByCourse(course.getId());

        //5.用户课程信息
        QueryWrapper<UserCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", id);
        queryWrapper.eq("user_id", userId);
        UserCourse userCourse = userCourseService.getOne(queryWrapper);


        Map<String, Object> result = new HashMap<>();
        result.put("courseQuestion", courseQuestions);
        result.put("story", stories);
        result.put("userAudio", userAudio);
        result.put("course", course);
        result.put("userCourse", userCourse);
        result.put("books", listByCourse);

        return AjaxResult.success(result);
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

    /**
     * 购买课程
     *
     * @param vo 课程购买请求VO
     * @return
     */
    @PostMapping("/usercourse")
    public AjaxResult updateUserCourse(@RequestBody CoursePayVO vo) {
        try {
            //课程信息
            Course course = courseService.getById(vo.getId());
            UserCourse userCourse = new UserCourse();
            userCourse.setUserId(vo.getUserId());
            userCourse.setCourseId(vo.getId());

            BigDecimal realPrice;
            if (null != course.getRates() && course.getRates().compareTo(BigDecimal.ZERO) > 0 && course.getRates().compareTo(course.getPrice()) < 0) {
                realPrice = course.getRates();
            } else {
                realPrice = course.getPrice();
            }

            if (course.getPlan().equals(CommonConstants.YES)) {
                userCourse.setReturnable(1L);
                userCourse.setIsPlanCourse("1");
                userCourse.setOriginalMoney(course.getCashReturn());
            } else {
                userCourse.setIsPlanCourse("0");
            }

            userCourse.setPrice(realPrice);
            userCourseService.save(userCourse);
            return AjaxResult.success();

        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("购买失败，请联系客服");
        }
    }

    /**
     * 购买课程
     *
     * @param vo 课程购买请求VO
     * @return
     */
    @PostMapping("/userempower")
    public AjaxResult updateUserEmpower(@RequestBody CoursePayVO vo) {
        try {
            UserEmpower empower = new UserEmpower();
            empower.setEmpowerId(vo.getId());
            empower.setUserId(vo.getUserId());
            iUserEmpowerService.save(empower);
            return AjaxResult.success();

        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("购买失败，请联系客服");
        }
    }

    /**
     * 调用统一下单接口，并组装生成支付所需参数对象.
     *
     * @param vo 统一下单请求参数
     * @return 返回 {@link com.github.binarywang.wxpay.bean.order}包下的类对象
     */
    @ApiOperation(value = "调用统一下单接口")
    @PostMapping("/unifiedOrder")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult unifiedOrder(HttpServletRequest request, @RequestBody CoursePayVO vo) throws WxPayException {
        //检验用户session登录
        WxUser wxUser = wxUserService.getById(vo.getUserId());

        Course course = courseService.getById(vo.getId());

        QueryWrapper<UserCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", vo.getId()).eq("user_id", vo.getUserId());
        UserCourse userCourse = userCourseService.getOne(wrapper);

        if (null == course.getId()) {
            return AjaxResult.error(MyReturnCode.ERR_50000.getCode(), MyReturnCode.ERR_50000.getMsg());
        }
        //防止重复购买
        if (null != userCourse) {
            return AjaxResult.error(MyReturnCode.ERR_50001.getCode(), MyReturnCode.ERR_50001.getMsg());
        }

        BigDecimal realPrice;
        if (null != course.getRates() && course.getRates().compareTo(course.getPrice()) < 0 && course.getRates().compareTo(BigDecimal.ZERO) > 0) {
            realPrice = course.getRates();
        } else {
            realPrice = course.getPrice();
        }

        String appId = WxMaUtil.getAppId(request);
        WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = new WxPayUnifiedOrderRequest();
        wxPayUnifiedOrderRequest.setAppid(appId);
        String body = course.getTitle();
        body = body.length() > 40 ? body.substring(0, 39) : body;
        wxPayUnifiedOrderRequest.setBody(body);
        wxPayUnifiedOrderRequest.setOutTradeNo(IdUtil.getSnowflake(0, 0).nextIdStr());
        wxPayUnifiedOrderRequest.setTotalFee(realPrice.multiply(new BigDecimal(100)).intValue());
        wxPayUnifiedOrderRequest.setTradeType("JSAPI");
        wxPayUnifiedOrderRequest.setNotifyUrl(mallConfigProperties.getNotifyHost() + "/weixin/api/ma/orderinfo/notify-order");
        wxPayUnifiedOrderRequest.setSpbillCreateIp("127.0.0.1");
        wxPayUnifiedOrderRequest.setOpenid(wxUser.getOpenId());
        WxPayService wxPayService = WxPayConfiguration.getPayService();
        return AjaxResult.success(JSONUtil.parse(wxPayService.createOrder(wxPayUnifiedOrderRequest)));
    }

    /**
     * 调用统一下单接口，并组装生成支付所需参数对象.
     *
     * @param vo 统一下单请求参数
     * @return 返回 {@link com.github.binarywang.wxpay.bean.order}包下的类对象
     */
    @ApiOperation(value = "调用统一下单接口")
    @PostMapping("/unifiedEmpower")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult unifiedEmpower(HttpServletRequest request, @RequestBody CoursePayVO vo) throws WxPayException {
        //检验用户session登录
        WxUser wxUser = wxUserService.getById(vo.getUserId());

        EmpowerVideo empowerVideo = iEmpowerVideoService.getById(vo.getId());

        QueryWrapper<UserEmpower> wrapper = new QueryWrapper<>();
        wrapper.eq("empower_id", vo.getId()).eq("user_id", vo.getUserId());
        UserEmpower userEMpower = iUserEmpowerService.getOne(wrapper);

        if (null == empowerVideo.getId()) {
            return AjaxResult.error(MyReturnCode.ERR_50000.getCode(), MyReturnCode.ERR_50000.getMsg());
        }
        //防止重复购买
        if (null != userEMpower) {
            return AjaxResult.error(MyReturnCode.ERR_50001.getCode(), MyReturnCode.ERR_50001.getMsg());
        }

        BigDecimal realPrice;
        if (null != empowerVideo.getRates() && empowerVideo.getRates().compareTo(empowerVideo.getPrice()) < 0) {
            realPrice = empowerVideo.getRates();
        } else {
            realPrice = empowerVideo.getPrice();
        }

        String appId = WxMaUtil.getAppId(request);
        WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = new WxPayUnifiedOrderRequest();
        wxPayUnifiedOrderRequest.setAppid(appId);
        String body = empowerVideo.getTitle();
        body = body.length() > 40 ? body.substring(0, 39) : body;
        wxPayUnifiedOrderRequest.setBody(body);
        wxPayUnifiedOrderRequest.setOutTradeNo(IdUtil.getSnowflake(0, 0).nextIdStr());
        wxPayUnifiedOrderRequest.setTotalFee(realPrice.multiply(new BigDecimal(100)).intValue());
        wxPayUnifiedOrderRequest.setTradeType("JSAPI");
        wxPayUnifiedOrderRequest.setNotifyUrl(mallConfigProperties.getNotifyHost() + "/weixin/api/ma/orderinfo/notify-order");
        wxPayUnifiedOrderRequest.setSpbillCreateIp("127.0.0.1");
        wxPayUnifiedOrderRequest.setOpenid(wxUser.getOpenId());
        WxPayService wxPayService = WxPayConfiguration.getPayService();
        return AjaxResult.success(JSONUtil.parse(wxPayService.createOrder(wxPayUnifiedOrderRequest)));
    }

    /**
     * @param id     课程ID
     * @param userId 用户ID
     * @return
     */
    @GetMapping("/usermoney/{id}/{userId}")
    public AjaxResult getUserMoney(@PathVariable Long id, @PathVariable String userId) {

//        Course course = courseService.getById(id);
//        QueryWrapper<UserCourse> wrapper = new QueryWrapper<>();
//        wrapper.eq("course_id", id).eq("user_id", userId);
//        UserCourse userCourse = userCourseService.getOne(wrapper);

//        //如果是有返现
//        if (null != userCourse && null != userCourse.getId() && userCourse.getCashReturn().compareTo(BigDecimal.ZERO) == 0 && "1".equals(userCourse.getIsPlanCourse())) {
//            userCourse.setCashReturn(userCourse.getOriginalMoney());
//            userCourse.setReturnable(0L);
//            userCourseService.updateById(userCourse);
//            WxUser wxUser = wxUserService.getById(userId);
//            BigDecimal existsMoney = wxUser.getMoney();
//            //加上当时购买时设定的返现金额
//            BigDecimal money = existsMoney.add(userCourse.getOriginalMoney());
//            wxUser.setMoney(money);
//            wxUserService.updateMoney(wxUser);
//            String msg = "恭喜您获得奖学金" + course.getCashReturn() + "元";
//            return AjaxResult.success(msg);
//        } else {
        return AjaxResult.success();
//        }
    }


    /**
     * 分页查询赋能中心视频列表
     *
     * @param userId 用户ID
     * @return 赋能中心视频列表
     */
    @GetMapping("/empower/{userId}")
    public AjaxResult getEmpowerVideo(@PathVariable String userId, String name, Page page) {
        page.setCurrent(1);
        page.setSize(100);
        IPage<EmpowerVideo> result = iEmpowerVideoService.getPage(userId, name, page);
        return AjaxResult.success(result);
    }

    @GetMapping("/empower/{id}/{userId}")
    public AjaxResult getEmpowerVideoById(@PathVariable Long id, @PathVariable String userId) {

        EmpowerVideo result = iUserEmpowerService.getOneById(userId, id);
        return AjaxResult.success(result);
    }

    /**
     * 获取随机语音回复信息
     *
     * @return
     */
    @GetMapping("/reply/{courseId}/{userId}/{audioId}")
    public AjaxResult getReplyByCourseId(@PathVariable Integer courseId, @PathVariable String userId, @PathVariable Integer audioId) {
        UserAudio one = userAudioService.getOne(Wrappers.<UserAudio>lambdaQuery().eq(UserAudio::getCourseId, courseId)
                .eq(UserAudio::getUserId, userId)
                .eq(UserAudio::getAudioId, audioId));
        if (Objects.nonNull(one) && Objects.nonNull(one.getReplyId())) {
            return AjaxResult.success(iCourseReplyService.getById(one.getReplyId()));
        }
        List<CourseReply> list = iCourseReplyService.list();
        if (CollectionUtils.isNotEmpty(list)) {
            int index = (int) (Math.random() * list.size());
            return AjaxResult.success(list.get(index));
        }
        return AjaxResult.success();
    }
}
