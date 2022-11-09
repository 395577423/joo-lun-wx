package com.joolun.mall.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.joolun.common.annotation.Excel;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 课程音频回复对象 course_reply
 *
 * @author Lanjian
 * @date 2022-11-08
 */
@Data
@TableName("course_reply")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "课程音频回复对象")
public class CourseReply extends Model<CourseReply> {
    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    private Long id;


    /**
     * 回复内容
     */
    @Excel(name = "回复内容")
    private String content;

    /**
     * $column.columnComment
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    /**
     * 回复类型
     */
    @Excel(name = "回复类型")
    private Long replayType;

}
