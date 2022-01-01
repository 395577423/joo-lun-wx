package com.joolun.mall.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.joolun.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.time.LocalDateTime;

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
    @Excel(name = "课程ID")
    private Long courseId;

    private String userId;
    /**
     * 录音地址
     */
    @Excel(name = "录音地址")
    private String audioUrl;

    private LocalDateTime createTime;
}
