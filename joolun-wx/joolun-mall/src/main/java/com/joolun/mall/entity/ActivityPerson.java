package com.joolun.mall.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.joolun.common.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * @author lanjian
 * @Description
 * @create 2022-08-25
 */
@Data
@TableName("activity_person")
public class ActivityPerson extends Model<ActivityOrderInfo> {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 电话 */
    @Excel(name = "电话")
    private String tel;

    /** 身份证号码 */
    @Excel(name = "身份证号码")
    private String identityNo;

    /** 性别 */
    @Excel(name = "性别")
    private String gender;

    private String image;

    private String userId;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    private Date updateTime;
}
