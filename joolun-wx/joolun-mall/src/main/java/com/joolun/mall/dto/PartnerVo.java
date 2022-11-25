package com.joolun.mall.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * @author lanjian
 */

@Data
public class PartnerVo implements Serializable {

    private String userHeadImg;

    private String userName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private int vipLevel;

    private boolean vip;

    private String partner;

    public int getVipLevel() {
        if ("1".equals(partner)) {
            vipLevel = 2;
        } else if (vip) {
            vipLevel = 1;
        } else {
            vipLevel = 0;
        }
        return vipLevel;
    }
}
