package com.joolun.mall.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.joolun.common.annotation.Excel;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 广告对象 advertisement
 * 
 * @author Lanjian
 * @date 2022-10-10
 */
@Data
@TableName("advertisement")
@EqualsAndHashCode(callSuper = true)
public class Advertisement extends Model<Advertisement>
{
    private static final long serialVersionUID = 1L;


    /** $column.columnComment */
    private Long id;


    /** 内容 */
    @Excel(name = "内容")
    private String content;


    /** 投放人 */
    @Excel(name = "投放人")
    private String user;


    /** 广告封面 */
    @Excel(name = "广告封面")
    private String cover;


    /** 创建日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;


    /** 有效截止期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "有效截止期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date validDate;


    /** 标题 */
    @Excel(name = "标题")
    private String title;


    /** 简介 */
    @Excel(name = "简介")
    private String introduction;

}
