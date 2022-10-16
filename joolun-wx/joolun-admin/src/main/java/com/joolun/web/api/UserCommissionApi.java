package com.joolun.web.api;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.mall.dto.PartnerVo;
import com.joolun.mall.entity.UserCommission;
import com.joolun.mall.entity.UserShareRecord;
import com.joolun.mall.entity.UserWithdrawRecord;
import com.joolun.mall.service.IUserCommissionService;
import com.joolun.mall.service.IUserShareRecordService;
import com.joolun.mall.service.IUserWithdrawRecordService;
import com.joolun.weixin.utils.ThirdSessionHolder;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author lanjian
 * @Description
 * @create 2022-09-05
 */
@Api(tags = "会员")
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/weixin/api/commission")
public class UserCommissionApi {

    private final IUserCommissionService userCommissionService;

    private final IUserShareRecordService userShareRecordService;

    private final IUserWithdrawRecordService userWithdrawRecordService;

    @GetMapping("/get")
    public AjaxResult get(){
        String userId = ThirdSessionHolder.getWxUserId();
        LambdaQueryWrapper<UserCommission> queryWrapper = Wrappers.<UserCommission>lambdaQuery()
                .eq(UserCommission::getUserId, userId);
        UserCommission userCommission = userCommissionService.getOne(queryWrapper);
        if(userCommission == null) {
            userCommission = new UserCommission();
            userCommission.setUserId(userId);
            userCommission.setWithdrawAmount(BigDecimal.ZERO);
            userCommission.setTotalAmount(BigDecimal.ZERO);
            userCommission.setCompletedAmount(BigDecimal.ZERO);
        }
        return AjaxResult.success(userCommission);
    }

    @GetMapping("/partners")
    public AjaxResult getPartners(){
        List<PartnerVo> shareRecords = userShareRecordService.listPartner(ThirdSessionHolder.getWxUserId());
        return AjaxResult.success(shareRecords);
    }

    @GetMapping("/getUserBankInfo")
    public AjaxResult getUserBankInfo() {
        String userId = ThirdSessionHolder.getWxUserId();
        List<UserWithdrawRecord> records = userWithdrawRecordService.list(Wrappers.<UserWithdrawRecord>lambdaQuery()
                .eq(UserWithdrawRecord::getWxUserId, userId).orderByDesc(UserWithdrawRecord::getApplyTime));
        UserWithdrawRecord userWithdrawRecord = null;
        if(CollectionUtil.isNotEmpty(records)) {
            userWithdrawRecord = records.get(0);
        }
        return AjaxResult.success(userWithdrawRecord);
    }

    @PostMapping("/withdraw/apply/save")
    public AjaxResult saveWithdrawApplyRecord(@RequestBody UserWithdrawRecord userWithdrawRecord){
        String userId = ThirdSessionHolder.getWxUserId();
        userWithdrawRecord.setWxUserId(userId);
        userWithdrawRecord.setApplyTime(new Date());
        userWithdrawRecord.setCompleted(0);
        return AjaxResult.success();
    }
}
