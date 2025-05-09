package com.joolun.mall.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 用户书籍录音对象 user_audio
 *
 * @author Owen
 * @date 2021-12-08
 */
@Data
@TableName("user_audio")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "用户书籍录音对象")
public class UserAudio extends Model<UserAudio> {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 课程ID
     */
    private Long courseId;

    /**
     * 课程音频ID
     */
    private Long audioId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 录音地址
     */
    private String audioUrl;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @TableField(exist = false)
    private String question;

    @TableField(exist = false)
    private String courseUrl;
}
