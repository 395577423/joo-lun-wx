package com.joolun.web.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.mall.config.CommonConstants;
import com.joolun.mall.entity.*;
import com.joolun.mall.service.*;
import com.joolun.system.service.ISysDictDataService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private final ISysDictDataService sysDictDataService;

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
    public AjaxResult getRecommendPlan(Page page) {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("recommend", "1");
        wrapper.eq("plan", "0");
        wrapper.orderByDesc("create_time");
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
        QueryWrapper<CourseVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", id).orderByAsc("sort");
        List<CourseVideo> courseVideoList = courseVideoService.list(wrapper);
        Map<String, Object> result = new HashMap<>();
        result.put("course", course);
        result.put("video", courseVideoList);
        return AjaxResult.success(result);
    }

    /**
     * 查询课程闯关问题
     *
     * @param id 课程ID
     * @return 课程音频问题
     */
    @GetMapping("/question/{id}")
    public AjaxResult getCourseQuestion(@PathVariable Long id) {
        QueryWrapper<CourseQuestion> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", id).orderByAsc("sort");
        return AjaxResult.success(courseQuestionService.list(wrapper));
    }


    /**
     * 查询课程闯关问题答案
     *
     * @param id 问题ID
     * @return 课程音频问题答案
     */
    @GetMapping("/choice/{id}")
    public AjaxResult getCourseQuestionChoice(@PathVariable Long id) {
        QueryWrapper<CourseQuestionChoice> wrapper = new QueryWrapper<>();
        wrapper.eq("question_id", id).orderByAsc("sort");
        return AjaxResult.success(courseQuestionChoiceService.list(wrapper));
    }

    /**
     * 返回书籍树形集合
     */
    @ApiOperation(value = "返回树形集合")
    @GetMapping("/tree")
    public AjaxResult goodsCategoryTree(Book book) {
        return AjaxResult.success(bookService.selectTree(book));
    }

    /**
     * 获取读书报告
     *
     * @param id     课程ID
     * @param userId 用户ID
     * @return 读书报告
     */
    @GetMapping("/report/{id}/{userId}")
    public AjaxResult getReport(@PathVariable Long id, @PathVariable Long userId) {

        return AjaxResult.success();
    }


}
