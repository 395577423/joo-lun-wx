package com.joolun.mall.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.joolun.common.annotation.Excel;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

/**
 * 宣传视频对象 propaganda
 * 
 * @author Lanjian
 * @date 2022-10-13
 */
@Data
@TableName("propaganda")
public class Propaganda extends Model<Propaganda>
{
    private static final long serialVersionUID = 1L;


    /** 主键 */
    private Long id;


    /** 视频地址 */
    @Excel(name = "视频地址")
    private String url;


    /** 创建日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;


    /** 是否激活 */
    @Excel(name = "是否激活")
    private String actived;


}
