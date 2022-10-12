package com.joolun.mall.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.joolun.common.annotation.Excel;
import com.joolun.framework.config.typehandler.ArrayStringTypeHandler;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.JdbcType;

import java.math.BigDecimal;

/**
 * @Auther ChaoYun
 * @Description
 * @Date 2022/7/23
 */
@Data
@TableName("library")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "图书店对象")
public class Library extends Model<Library> {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 经度 */
    @Excel(name = "经度")
    private BigDecimal longitude;

    /** 纬度 */
    @Excel(name = "纬度")
    private BigDecimal latitude;

    /** Geohash值 */
    @Excel(name = "Geohash值")
    private String geoHash;

    @Excel(name = "书馆图片")
    @TableField(typeHandler = ArrayStringTypeHandler.class, jdbcType= JdbcType.VARCHAR)
    private String[] image;

    @Excel(name = "书馆介绍")
    private String introduction;

    /** 创建时间 */
    @Excel(name = "创建时间")
    private String createAt;

    /** 描述信息 */
    @Excel(name = "描述信息")
    private String description;

    @Excel(name = "详细地址")
    private String address;

    /** 联系人 */
    @Excel(name = "联系人")
    private String contact;

    /** 手机号 */
    @Excel(name = "手机号")
    private String mobile;

    /** 电话 */
    @Excel(name = "电话")
    private String telephone;

    @TableField(exist = false)
    private Integer width = 20;

    @TableField(exist = false)
    private Integer height = 30;
}
