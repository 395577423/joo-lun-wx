package com.joolun.mall.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 订单相关枚举
 */
public enum ActivityOrderInfoEnum implements IEnum<String> {

	STATUS_0("0","待支付"),
	STATUS_1("1","已支付"),
	STATUS_2("2","已完成"),
	STATUS_3("3","已取消");

	ActivityOrderInfoEnum(final String value, final String desc) {
		this.value = value;
		this.desc = desc;
	}
	public static String STATUS_PREFIX = "STATUS";
	private String value;
	private String desc;

	@Override
	public String getValue() {
		return this.value;
	}

	@JsonValue
	public String getDesc(){
		return this.desc;
	}
}
