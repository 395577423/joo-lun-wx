package com.joolun.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.mall.entity.*;
import com.joolun.mall.enums.IncomeStatusEnum;
import com.joolun.mall.enums.MemberStatusEnum;
import com.joolun.mall.enums.ProductTypeEnum;
import com.joolun.mall.mapper.UserIncomeRecordMapper;
import com.joolun.mall.service.*;
import com.joolun.weixin.entity.WxUser;
import com.joolun.weixin.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author lanjian
 * @Description
 * @create 2022-09-03
 */
@Service
public class UserIncomeRecordImpl extends ServiceImpl<UserIncomeRecordMapper, UserIncomeRecord>
        implements IUserIncomeRecordService {



    /**
     * 增加用户收入记录
     *
     * @param userIncomeRecord
     */
    @Override
    public void addUserIncomeRecord(UserIncomeRecord userIncomeRecord) {

        if (userIncomeRecord != null) {
            save(userIncomeRecord);
        }


    }





}
