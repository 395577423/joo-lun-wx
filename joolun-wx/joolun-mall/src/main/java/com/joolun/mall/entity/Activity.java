package com.joolun.mall.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.joolun.common.annotation.Excel;
import com.joolun.framework.config.typehandler.ArrayLongTypeHandler;
import com.joolun.framework.config.typehandler.ArrayStringTypeHandler;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.ibatis.type.JdbcType;

/**
 * 社会活动对象 activity
 * 
 * @author Owen
 * @date 2022-08-12
 */
@Data
public class Activity extends Model<Activity>
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 活动分类id */
    @Excel(name = "活动分类id")
    private Long categoryId;

    /** 活动名称 */
    @Excel(name = "活动名称")
    private String name;

    /** 活动地点 */
    @Excel(name = "活动地点")
    @TableField(typeHandler = ArrayStringTypeHandler.class, jdbcType= JdbcType.VARCHAR)
    private String[] address;

    /** $column.columnComment */
    @Excel(name = "活动封面图片")
    private String imageUrl;

    @Excel(name = "是否发布")
    private Boolean published;

    /** 活动位置信息 */
    @Excel(name = "活动位置信息")
    private String location;

    /** 活动介绍 */
    @Excel(name = "活动介绍")
    private String introduction;

    /** 活动说明 */
    @Excel(name = "活动说明")
    private String explanation;

    /** 创建人id */
    @Excel(name = "创建人id")
    private Long creatorId;

    /** 创建人姓名 */
    @Excel(name = "创建人姓名")
    private String creator;

    private Date createTime;

}
