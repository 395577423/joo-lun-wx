package com.joolun.mall.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.joolun.mall.config.CommonConstants;
import com.joolun.mall.constant.MallConstants;
import com.joolun.mall.dto.PartnerVo;
import com.joolun.mall.dto.UserOrderBaseInfo;
import com.joolun.mall.entity.UserMemberOrder;
import com.joolun.mall.entity.UserShareRecord;
import com.joolun.mall.mapper.UserMemberOrderMapper;
import com.joolun.mall.service.IUserMemberOrderService;
import com.joolun.mall.service.IUserShareRecordService;
import com.joolun.weixin.entity.WxUser;
import com.joolun.weixin.service.WxUserService;
import com.joolun.weixin.utils.LocalDateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author Lanjian
 * @date 2022-09-03
 */
@Service
public class UserMemberOrderServiceImpl extends ServiceImpl<UserMemberOrderMapper, UserMemberOrder> implements IUserMemberOrderService {

    @Autowired
    WxUserService wxUserService;


    /**
     * 创建订单
     *
     * @param price
     * @param userId
     * @return
     */
    @Override
    public UserMemberOrder createOrder(BigDecimal price, String userId) {
        UserMemberOrder userMemberOrder = new UserMemberOrder();
        userMemberOrder.setOrderName("会员费");
        userMemberOrder.setOrderNo(IdUtil.getSnowflake(0, 0).nextIdStr());
        userMemberOrder.setUserId(userId);
        userMemberOrder.setPaymentPrice(price);
        userMemberOrder.setIsPay(CommonConstants.NO);
        userMemberOrder.setPaymentWay("2");
        userMemberOrder.setCreateTime(new Date());
        save(userMemberOrder);
        return userMemberOrder;
    }

    /**
     * 更新订单
     *
     * @param notifyResult
     */
    @Override
    public UserOrderBaseInfo updateOrder(WxPayOrderNotifyResult notifyResult) {
        String tradeNo = notifyResult.getOutTradeNo();
        LambdaQueryWrapper<UserMemberOrder> queryWrapper = Wrappers.<UserMemberOrder>lambdaQuery().eq(UserMemberOrder::getOrderNo, tradeNo);
        UserMemberOrder userMemberOrder = getOne(queryWrapper);
        if (userMemberOrder != null) {
            if (userMemberOrder.getPaymentPrice().multiply(new BigDecimal(100)).intValue() == notifyResult.getTotalFee()) {
                String timeEnd = notifyResult.getTimeEnd();
                LocalDateTime paymentTime = LocalDateTimeUtils.parse(timeEnd);
                userMemberOrder.setPaymentTime(LocalDateTimeUtils.asDate(paymentTime));
                userMemberOrder.setTransactionId(notifyResult.getTransactionId());
                if (CommonConstants.NO.equals(userMemberOrder.getIsPay())) {
                    userMemberOrder.setIsPay(CommonConstants.YES);
                    updateById(userMemberOrder);
                    setUserMember(userMemberOrder.getUserId());
                    UserOrderBaseInfo userOrderBaseInfo = BeanUtil.toBean(userMemberOrder, UserOrderBaseInfo.class);
                    return userOrderBaseInfo;
                } else {
                    throw new RuntimeException("订单已支付");
                }

            } else {
                throw new RuntimeException("付款金额与订单金额不等");
            }
        } else {
            throw new RuntimeException("无此订单");
        }
    }

    /**
     * 设置用户成为会员
     *
     * @param wxUserId
     */
    private void setUserMember(String wxUserId) {
        Date expiryDate = DateUtil.offsetMonth(new Date(), 12);
        WxUser wxUser = wxUserService.getById(wxUserId);
        wxUser.setVip(true);
        wxUser.setPartner("0");
        wxUser.setMember(CommonConstants.YES);
        wxUser.setLevel((short) 1);
        wxUser.setMemberExpiryDate(expiryDate);
        wxUserService.updateById(wxUser);
    }


}
