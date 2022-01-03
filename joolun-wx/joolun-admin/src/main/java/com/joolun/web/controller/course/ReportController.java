package com.joolun.web.controller.course;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.joolun.mall.entity.*;
import com.joolun.mall.service.*;
import com.joolun.weixin.entity.WxUser;
import com.joolun.weixin.service.WxUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Owen
 * @date 2022/1/2 9:47 PM
 */
@Controller
@RequestMapping("/report")
@AllArgsConstructor
public class ReportController {

    private final ICourseStoryService iCourseStoryService;

    private final IUserAudioService userAudioService;

    private final ICourseService courseService;

    private final ICourseQuestionService courseQuestionService;

    private final IUserAnswerService userAnswerService;

    private final ICourseQuestionChoiceService choiceService;

    private final WxUserService wxUserService;

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
