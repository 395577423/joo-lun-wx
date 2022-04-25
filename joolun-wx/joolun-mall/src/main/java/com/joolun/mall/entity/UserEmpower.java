package com.joolun.mall.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Owen
 * @date 2022/4/25 23:21
 */
@Data
@TableName("user_empower")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "用户购买课程对象")
public class UserEmpower extends Model<UserCourse> {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String userId;
    private Long empowerId;
}
