
package com.joolun.mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.joolun.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商城订单
 *
 * @author Owen
 * @date 2019-09-10 15:21:22
 */
@Data
@TableName("activity_order_info")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "活动订单")
public class ActivityOrderInfo extends Model<ActivityOrderInfo> {
	private static final long serialVersionUID = 1L;

	/** PK */
	@TableId(type = IdType.ASSIGN_ID)
	private String id;

	/** 逻辑删除标记（0：显示；1：隐藏） */
	private String delFlag;

	/** 用户id */
	@Excel(name = "用户id")
	private String userId;

	/** 订单单号 */
	@Excel(name = "订单单号")
	private String orderNo;

	/** 支付方式1、货到付款；2、在线支付 */
	@Excel(name = "支付方式1、货到付款；2、在线支付")
	private String paymentWay;

	/** 是否支付0、未支付 1、已支付 */
	@Excel(name = "是否支付0、未支付 1、已支付")
	private String isPay;

	/** 订单名 */
	@Excel(name = "订单名")
	private String name;

	/** 订单状态1、待发货 2、待收货 3、确认收货/已完成 5、已关闭 */
	@Excel(name = "订单状态1、待发货 2、待收货 3、确认收货/已完成 5、已关闭")
	private String status;

	/** 销售金额 */
	@Excel(name = "销售金额")
	private BigDecimal salesPrice;

	/** 优惠金额（用户余额抵扣） */
	@Excel(name = "优惠金额", readConverterExp = "用=户余额抵扣")
	private BigDecimal couponPrice;

	/** 支付金额（销售金额+运费金额） */
	@Excel(name = "支付金额", readConverterExp = "销=售金额+运费金额")
	private BigDecimal paymentPrice;

	/** 付款时间 */
	@Excel(name = "付款时间", width = 30, dateFormat = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date paymentTime;

	/** 成交时间 */
	@Excel(name = "成交时间", width = 30, dateFormat = "yyyy-MM-dd")
	private Date closingTime;

	/** 支付交易ID */
	@Excel(name = "支付交易ID")
	private String transactionId;

	/** 套餐类型描述 */
	@Excel(name = "套餐类型描述")
	private String priceDesc;

	/** 购买数量 */
	@Excel(name = "购买数量")
	private Long quantity;

	/** 活动日期 */
	@Excel(name = "活动日期", width = 30, dateFormat = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date activityDate;

	/** 活动天数 */
	@Excel(name = "活动天数")
	private Long activityDays;

	@Excel(name = "订单封面图")
	private String activityImg;

	@Excel(name = "出行人信息")
	private String persons;

	private Long activityId;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createTime;

}
