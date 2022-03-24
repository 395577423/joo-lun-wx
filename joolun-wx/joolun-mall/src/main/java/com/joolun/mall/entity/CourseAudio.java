package com.joolun.mall.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Owen
 * @date 2022/3/21 15:25
 */
@Data
@TableName("course_audio")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "课程对象")
public class CourseAudio extends Model<CourseAudio> {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    private Long courseId;

    private String question;

    private String audio;

    private Integer sort;


}
