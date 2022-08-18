package com.joolun.mall.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.joolun.common.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 【请填写功能名称】对象 activity_category
 * 
 * @author Owen
 * @date 2022-08-12
 */
@Data
public class ActivityCategory extends Model<ActivityCategory>
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 活动分类名称 */
    @Excel(name = "活动分类名称")
    private String categoryName;


    @Excel(name = "图标")
    private String coverImage;

    /** 创建人id */
    @Excel(name = "创建人id")
    private Long creatorId;

    /** 创建人姓名 */
    @Excel(name = "创建人姓名")
    private String creator;

    private Date createTime;
}
