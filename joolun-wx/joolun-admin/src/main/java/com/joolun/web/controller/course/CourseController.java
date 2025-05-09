package com.joolun.web.controller.course;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.common.annotation.Log;
import com.joolun.common.core.controller.BaseController;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.common.enums.BusinessType;
import com.joolun.common.utils.StringUtils;
import com.joolun.mall.entity.Book;
import com.joolun.mall.entity.Course;
import com.joolun.mall.service.IBookService;
import com.joolun.mall.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 课程Controller
 *
 * @author Owen
 * @date 2021-12-08
 */
@RestController
@RequestMapping("/course")
public class CourseController extends BaseController {
    @Autowired
    private ICourseService courseService;

    @Autowired
    private IBookService bookService;

    /**
     * 查询课程列表
     */
    @GetMapping("/page")
    public AjaxResult page(Page page, Course course) {
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.like(StringUtils.isNotEmpty(course.getTitle()), "title", course.getTitle());
        Page<Course> courses = courseService.page(page, courseQueryWrapper);
        List<Course> records = courses.getRecords();
        records.forEach(t -> {
            List<Book> books = bookService.getListByCourse(t.getId());
            List<Long> bookIds = books.stream().map(Book::getId).collect(Collectors.toList());
            t.setBooks(bookIds);
        });
        courses.setRecords(records);
        return AjaxResult.success(courses);
    }

    /**
     * 获取课程详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(courseService.getById(id));
    }

    /**
     * 新增课程
     */
    @Log(title = "课程", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Course course) {
        if (null == course.getRates() || course.getRates().compareTo(BigDecimal.ZERO) == 0) {
            course.setRates(null);
        }
        boolean save = courseService.save(course);
        //删除关联表中的记录
        bookService.deleteRelatedBooks(course.getId());
        //添加记录
        if (null != course.getBooks() && course.getBooks().size() > 0) {
            course.getBooks().forEach(t -> {
                bookService.addRelatedCourse(course.getId(), t);
            });
        }
        return AjaxResult.success(save);
    }

    /**
     * 修改课程
     */
    @Log(title = "课程", businessType = BusinessType.UPDATE)
    @PutMapping
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult edit(@RequestBody Course course) {
        if (null == course.getRates() || course.getRates().compareTo(BigDecimal.ZERO) == 0) {
            course.setRates(BigDecimal.ZERO);
        }
        boolean udpate = courseService.updateById(course);
        //删除关联表中的记录
        bookService.deleteRelatedBooks(course.getId());
        //添加记录
        if (null != course.getBooks() && course.getBooks().size() > 0) {
            course.getBooks().forEach(t -> {
                bookService.addRelatedCourse(course.getId(), t);
            });
        }
        return AjaxResult.success(udpate);
    }

    /**
     * 删除课程
     */
    @Log(title = "课程", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return AjaxResult.success(courseService.removeByIds(Arrays.asList(ids)));
    }

    /**
     * 查询课程列表
     */
    @GetMapping("/list")
    public AjaxResult list(String title) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(title)) {
            queryWrapper.like("title", title);
        }
        List<Course> list = courseService.list(queryWrapper.lambda().select(Course::getId, Course::getTitle));
        return AjaxResult.success(list);
    }

}
