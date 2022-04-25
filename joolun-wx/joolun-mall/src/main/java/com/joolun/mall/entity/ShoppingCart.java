
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
import java.util.List;

/**
 * 购物车
 *
 * @author Owen
 * @date 2019-08-29 21:27:33
 */
@Data
@TableName("shopping_cart")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "购物车")
public class ShoppingCart extends Model<ShoppingCart> {
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
   * 用户编号
   */
	@ApiModelProperty(value = "用户编号")
    private String userId;
    /**
   * 商品 SPU 编号
   */
	@ApiModelProperty(value = "spuId")
    private String spuId;
	/**
	 * 加入时价格
	 */
	@ApiModelProperty(value = "加入时价格")
	private BigDecimal addPrice;
    /**
   * 商品购买数量
   */
	@ApiModelProperty(value = "商品购买数量")
    private Integer quantity;
	/**
	 * 加入时的spu名字
	 */
	@ApiModelProperty(value = "加入时的spu名字")
	private String spuName;
	/**
	 * 图片
	 */
	@ApiModelProperty(value = "图片")
	private String picUrl;

	@TableField(exist = false)
	private GoodsSpu goodsSpu;
}
