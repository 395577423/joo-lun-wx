package com.joolun.mall.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.joolun.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.time.LocalDateTime;

/**
 * 课程视频对象 course_video
 *
 * @author Owen
 * @date 2021-12-08
 */
@Data
@TableName("course_video")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "课程视频对象")
public class CourseVideo extends Model<CourseVideo> {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 标题
     */
    @Excel(name = "标题")
    private String title;

    /**
     * 视频缩略图片地址
     */
    @Excel(name = "视频缩略图片地址")
    private String coverUrl;

    /**
     * 视频地址
     */
    @Excel(name = "视频地址")
    private String videoUrl;

    /**
     * 课程id
     */
    @Excel(name = "课程id")
    private Long courseId;

    /**
     * 1 正常 0 停用
     */
    @Excel(name = "1 正常 0 停用")
    private Long status;

    /**
     * 排序
     */
    @Excel(name = "排序")
    private Long sort;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 视频时长 秒
     */
    private Integer duration;
}
