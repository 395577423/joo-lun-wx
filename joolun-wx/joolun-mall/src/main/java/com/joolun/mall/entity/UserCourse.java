package com.joolun.mall.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableName;
import com.joolun.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * 用户购买课程对象 user_course
 *
 * @author Owen
 * @date 2021-12-08
 */
@Data
@TableName("user_course")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "用户购买课程对象")
public class UserCourse extends Model<UserCourse> {
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

    /**
     * 用户ID
     */
    @Excel(name = "用户ID")
    private String userId;

    /**
     * 得到的返现金额
     */
    @Excel(name = "得到的返现金额")
    private BigDecimal cashReturn;

    /**
     * 购买价格
     */
    @Excel(name = "购买价格")
    private BigDecimal price;

    /**
     * 是否有返现权限
     */
    @Excel(name = "是否有返现权限")
    private Long returnable;

    /**
     * 无返现原因
     */
    @Excel(name = "无返现原因")
    private String reason;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 用户读书报告图片地址
     */
    private String report;
}
