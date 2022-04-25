
package com.joolun.mall.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 商城订单详情
 *
 * @author Owen
 * @date 2019-09-10 15:31:40
 */
@Data
@TableName("order_item")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "商城订单详情")
public class OrderItem extends Model<OrderItem> {
	private static final long serialVersionUID = 1L;

	/**
	 * PK
	 */
	@ApiModelProperty(value = "PK")
	@TableId(type = IdType.ASSIGN_ID)
	private String id;
	/**
	 * 逻辑删除标记（0：显示；1：隐藏）
	 */
	@ApiModelProperty(value = "逻辑删除标记")
	private String delFlag;
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
	 * 订单编号
	 */
	@ApiModelProperty(value = "订单编号")
	private String orderId;
	/**
	 * 商品Id
	 */
	@ApiModelProperty(value = "商品Id")
	private String spuId;
	/**
	 * 商品名
	 */
	@ApiModelProperty(value = "商品名")
	private String spuName;
	/**
	 * 图片
	 */
	@ApiModelProperty(value = "图片")
	private String picUrl;
	/**
	 * 商品数量
	 */
	@ApiModelProperty(value = "商品数量")
	private Integer quantity;
	/**
	 * 购买单价
	 */
	@ApiModelProperty(value = "购买单价")
	private BigDecimal salesPrice;
	/**
	 * 运费金额
	 */
	@ApiModelProperty(value = "运费金额")
	private BigDecimal freightPrice;

	/**
	 * 支付金额（购买单价*商品数量+运费金额）
	 */
	@ApiModelProperty(value = "支付金额（购买单价*商品数量+运费金额）")
	private BigDecimal paymentPrice;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

}
