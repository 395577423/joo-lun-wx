package com.joolun.mall.dto;

import lombok.Data;

import java.io.Serializable;


/**
 * @author lanjian
 */

@Data
public class PartnerVo implements Serializable {

    private String userHeadImg;

    private String userName;

    private String createTime;

    private int vipLevel;

    private boolean vip;

    private boolean sVip;

    public int getVipLevel() {
        if (sVip) {
            vipLevel = 2;
        } else if (vip) {
            vipLevel = 1;
        } else {
            vipLevel = 0;
        }
        return vipLevel;
    }
}
