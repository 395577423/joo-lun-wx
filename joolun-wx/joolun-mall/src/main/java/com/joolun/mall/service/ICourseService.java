package com.joolun.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.joolun.mall.dto.CourseVO;
import com.joolun.mall.entity.Course;

import java.util.List;

/**
 * 课程Service接口
 *
 * @author www.joolun.com
 * @date 2021-12-08
 */
public interface ICourseService extends IService<Course> {
    List<Course> selectPlan();

    IPage<Course> selectDataPage(Page page, CourseVO course);
}
