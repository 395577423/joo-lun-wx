package com.joolun.mall.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author lanjian
 * @Description
 * @create 2022-09-05
 */
public enum MemberStatusEnum implements IEnum<String> {

    NO("0"), YES("1");

    MemberStatusEnum(String value) {
        this.value = value;
    }

    private String value;

    @Override
    public String getValue() {
        return this.value;
    }

}
