package com.joolun.mall.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ProductTypeEnum implements IEnum<Integer> {
    MEMBER(1,"会员"),COURSE(2,"课程"),ACTIVITY(3,"活动"),GOODS(4,"商品");

    ProductTypeEnum(final Integer value,final String desc) {
        this.value = value;
        this.desc = desc;
    }

    private Integer value;

    private String desc;



    @Override
    public Integer getValue() {
        return this.value;
    }

    @JsonValue
    public String getDesc(){
        return this.desc;
    }
}
