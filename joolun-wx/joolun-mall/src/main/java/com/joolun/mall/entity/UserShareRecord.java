package com.joolun.mall.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.joolun.common.annotation.Excel;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;

/**
 * 【请填写功能名称】对象 user_share_record
 * 
 * @author Lanjian
 * @date 2022-09-03
 */
public class UserShareRecord extends Model<UserShareRecord>
{
    private static final long serialVersionUID = 1L;


    /** 主键 */
    @TableId(type = IdType.AUTO)
    private Long id;


    /** 分享用户id */
    @Excel(name = "分享用户id")
    private String parentUserId;



    /** 扫码用户id */
    @Excel(name = "扫码用户id")
    private String userId;

    /** 分享时间 */
    private Date createTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setParentUserId(String parentUserId) 
    {
        this.parentUserId = parentUserId;
    }

    public String getParentUserId() 
    {
        return parentUserId;
    }

    public void setUserId(String userId) 
    {
        this.userId = userId;
    }

    public String getUserId() 
    {
        return userId;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
