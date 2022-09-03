package com.joolun.mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 会员价格设置
 * </p>
 *
 * @author lanjian
 * @since 2022-09-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MemberConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 会员价格
     */
    private BigDecimal price;

    /**
     * 普通会员返现价格
     */
    private BigDecimal cashBackAmount;

    /**
     * 普通会员返现价格
     */
    private BigDecimal superCashBackAmount;

    /**
     * 创建人id
     */
    private String createId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}
