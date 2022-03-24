package com.joolun.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.mall.entity.Course;
import com.joolun.mall.dto.CourseVO;
import org.apache.ibatis.annotations.Param;

/**
 * 课程Mapper接口
 *
 * @author Owen
 * @date 2021-12-08
 */
public interface CourseMapper extends BaseMapper<Course> {

    IPage<Course> selectPage2(Page page, @Param("query") CourseVO course);
}
