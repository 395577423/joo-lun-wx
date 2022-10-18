package com.joolun.mall.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.joolun.common.annotation.Excel;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 课程导读对象 course_guide
 * 
 * @author Lanjian
 * @date 2022-10-18
 */
@Data
@TableName("course_guide")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "课程导读")
public class CourseGuide extends Model<CourseGuide>
{
    private static final long serialVersionUID = 1L;


    /** $column.columnComment */
    private Long id;


    /** 课程ID */
    @Excel(name = "课程ID")
    private Long courseId;


    /** 问题 */
    @Excel(name = "问题")
    private String question;


    /** 音频地址 */
    @Excel(name = "音频地址")
    private String audio;


    /** 排序 */
    @Excel(name = "排序")
    private Long sort;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCourseId(Long courseId) 
    {
        this.courseId = courseId;
    }

    public Long getCourseId() 
    {
        return courseId;
    }
    public void setQuestion(String question) 
    {
        this.question = question;
    }

    public String getQuestion() 
    {
        return question;
    }
    public void setAudio(String audio) 
    {
        this.audio = audio;
    }

    public String getAudio() 
    {
        return audio;
    }
    public void setSort(Long sort) 
    {
        this.sort = sort;
    }

    public Long getSort() 
    {
        return sort;
    }


}
