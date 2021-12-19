package com.joolun.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.mall.entity.Course;
import com.joolun.mall.mapper.CourseMapper;
import com.joolun.mall.service.ICourseService;
import org.springframework.stereotype.Service;

/**
 * 课程Service业务层处理
 *
 * @author www.joolun.com
 * @date 2021-12-08
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

    @Override
    public Course selectPlan() {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("plan", "1").orderByDesc("create_time");
        return baseMapper.selectOne(wrapper);
    }
}
