package com.joolun.mall.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.joolun.mall.mapper.UserWithdrawRecordMapper;
import com.joolun.mall.entity.UserWithdrawRecord;
import com.joolun.mall.service.IUserWithdrawRecordService;

/**
 * 用户提现记录Service业务层处理
 * 
 * @author Lanjian
 * @date 2022-10-16
 */
@Service
public class UserWithdrawRecordServiceImpl extends ServiceImpl<UserWithdrawRecordMapper, UserWithdrawRecord> implements IUserWithdrawRecordService
{

}
