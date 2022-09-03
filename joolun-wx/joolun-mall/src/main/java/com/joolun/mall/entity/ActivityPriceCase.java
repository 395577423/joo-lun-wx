package com.joolun.mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.joolun.framework.config.typehandler.ArrayLongTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author lanjian
 * @since 2022-09-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ActivityPriceCase implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 活动id
     */
    private Long activityId;

    /**
     * 套餐名称
     */
    private String name;

    /**
     * 套餐选项
     */
    @TableField(typeHandler = ArrayLongTypeHandler.class, jdbcType= JdbcType.VARCHAR)
    private Long[] caseOption;

    /**
     * 套餐原价
     */
    private BigDecimal salesPrice;

    /**
     * 套餐会员价
     */
    private BigDecimal memberPrice;

    /**
     * 套餐超级会员价
     */
    private BigDecimal superMemberPrice;

    /**
     * 会员返现金额
     */
    private BigDecimal cashBackAmount;

    /**
     * 超级会员返现金额
     */
    private BigDecimal superCashBackAmount;


}
