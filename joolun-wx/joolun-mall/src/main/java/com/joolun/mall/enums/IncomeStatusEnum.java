package com.joolun.mall.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author lanjian
 * @Description
 * @create 2022-09-05
 */
public enum  IncomeStatusEnum implements IEnum<String> {

    IN_PROCESS("0"),COMPLETED("1");

    IncomeStatusEnum(String value) {
        this.value = value;
    }

    private String value;

    @Override
    public String getValue() {
        return this.value;
    }

}
