package com.joolun.mall.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 用户收货地址
 *
 * @author Owen
 * @date 2019-09-11 14:28:59
 */
@Data
@TableName("user_address")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "用户收货地址")
public class UserAddress extends Model<UserAddress> {
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
   * 收货人名字
   */
	@ApiModelProperty(value = "收货人名字")
    private String userName;
    /**
   * 邮编
   */
	@ApiModelProperty(value = "邮编")
    private String postalCode;
    /**
   * 省名
   */
	@ApiModelProperty(value = "省名")
    private String provinceName;
    /**
   * 市名
   */
	@ApiModelProperty(value = "市名")
    private String cityName;
    /**
   * 区名
   */
	@ApiModelProperty(value = "区名")
    private String countyName;
    /**
   * 详情地址
   */
	@ApiModelProperty(value = "详情地址")
    private String detailInfo;
    /**
   * 电话号码
   */
	@ApiModelProperty(value = "电话号码")
    private String telNum;
    /**
   * 是否默认 1是0否
   */
	@ApiModelProperty(value = "是否默认")
    private String isDefault;

}
