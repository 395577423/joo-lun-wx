
package com.joolun.mall.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.joolun.framework.config.typehandler.ArrayStringTypeHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.JdbcType;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * spu商品
 *
 * @author Owen
 * @date 2019-08-12 16:25:10
 */
@Data
@TableName("goods_spu")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "spu商品")
public class GoodsSpu extends Model<GoodsSpu> {
    private static final long serialVersionUID = 1L;

    /**
     * PK
     */
    @ApiModelProperty(value = "PK")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * spu编码
     */
    @ApiModelProperty(value = "spu编码")
    private String spuCode;
    /**
     * spu名字
     */
    @ApiModelProperty(value = "spu名字")
    private String name;
    /**
     * 卖点
     */
    @ApiModelProperty(value = "卖点")
    private String sellPoint;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;
    /**
     * 一级分类ID
     */
    @ApiModelProperty(value = "一级分类ID")
    private String categoryFirst;
    /**
     * 二级分类ID
     */
    @ApiModelProperty(value = "二级分类ID")
    private String categorySecond;
    /**
     * 商品主图
     */
    @ApiModelProperty(value = "商品主图")
    @TableField(typeHandler = ArrayStringTypeHandler.class, jdbcType = JdbcType.VARCHAR)
    private String[] picUrls;
    /**
     * 是否上架（0否 1是）
     */
    @ApiModelProperty(value = "是否上架（0否 1是）")
    private String shelf;
    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段")
    private Integer sort;
    /**
     * 销售价格
     */
    @ApiModelProperty(value = "销售价格")
    private BigDecimal salesPrice;
    /**
     * 市场价
     */
    @ApiModelProperty(value = "市场价")
    private BigDecimal marketPrice;
    /**
     * 成本价
     */
    @ApiModelProperty(value = "成本价")
    private BigDecimal costPrice;
    /**
     * 成本
     */
    @ApiModelProperty(value = "库存")
    private Integer stock;
    /**
     * 销量
     */
    @ApiModelProperty(value = "销量")
    private Integer saleNum;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 最后更新时间
     */
    @ApiModelProperty(value = "最后更新时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     * 逻辑删除标记（0：显示；1：隐藏）
     */
    @ApiModelProperty(value = "逻辑删除标记（0：显示；1：隐藏）")
    private String delFlag;

}
