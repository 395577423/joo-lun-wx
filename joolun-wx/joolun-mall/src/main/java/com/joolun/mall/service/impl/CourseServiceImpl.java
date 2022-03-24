package com.joolun.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.mall.dto.CourseVO;
import com.joolun.mall.entity.Course;
import com.joolun.mall.mapper.CourseMapper;
import com.joolun.mall.service.ICourseService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 课程Service业务层处理
 *
 * @author Owen
 * @date 2021-12-08
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

    @Override
    public List<Course> selectPlan() {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("plan", "1").orderByDesc("create_time");
        return baseMapper.selectList(wrapper);
    }

    @Override
    public IPage<Course> selectDataPage(Page page, CourseVO course) {
        IPage<Course> courseIPage = baseMapper.selectPage2(page, course);
        List<Course> courseList = courseIPage.getRecords();
        courseIPage.setRecords(courseList);
        return courseIPage;
    }
}
