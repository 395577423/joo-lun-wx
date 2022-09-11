package com.joolun.web.api;

import cn.hutool.core.collection.CollectionUtil;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.mall.config.MallConfigProperties;
import com.joolun.mall.entity.UserMemberConfig;
import com.joolun.mall.entity.UserMemberOrder;
import com.joolun.mall.enums.ProductTypeEnum;
import com.joolun.mall.service.*;
import com.joolun.weixin.config.WxPayConfiguration;
import com.joolun.weixin.utils.ThirdSessionHolder;
import com.joolun.weixin.utils.WxMaUtil;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author lanjian
 * @Description
 * @create 2022-09-03
 */
@Api(tags = "会员")
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/weixin/api/member")
public class UserMemberApi {

    private final IUserMemberConfigService userMemberConfigService;

    private final IUserMemberOrderService userMemberOrderService;

    private final MallConfigProperties mallConfigProperties;

    private final INotifyService notifyService;

    /**
     * 获取会员配置信息
     */
    @GetMapping("/config")
    public AjaxResult getConfig() {
        List<UserMemberConfig> userMemberConfigs = userMemberConfigService.list();
        if (CollectionUtil.isNotEmpty(userMemberConfigs)) {
            UserMemberConfig userMemberConfig = userMemberConfigs.get(0);
            return AjaxResult.success(userMemberConfig);
        }
        return AjaxResult.success();
    }

    /**
     * 购买会员
     */
    @GetMapping("/buy")
    public AjaxResult buy(HttpServletRequest request) throws WxPayException {
        String userId = ThirdSessionHolder.getWxUserId();
        List<UserMemberConfig> userMemberConfigs = userMemberConfigService.list();
        if (CollectionUtil.isNotEmpty(userMemberConfigs)) {
            UserMemberConfig userMemberConfig = userMemberConfigs.get(0);
            BigDecimal price = userMemberConfig.getPrice();
            UserMemberOrder order = userMemberOrderService.createOrder(price, userId);
            return AjaxResult.success(createWxPay(request, order));
        } else {
            return AjaxResult.error("联系客服");
        }
    }

    private Object createWxPay(HttpServletRequest request, UserMemberOrder order) throws WxPayException {
        String appId = WxMaUtil.getAppId(request);
        WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = new WxPayUnifiedOrderRequest();
        wxPayUnifiedOrderRequest.setAppid(appId);
        String body = order.getOrderName();
        body = body.length() > 40 ? body.substring(0, 39) : body;
        wxPayUnifiedOrderRequest.setBody(body);
        wxPayUnifiedOrderRequest.setOutTradeNo(order.getOrderNo());
        wxPayUnifiedOrderRequest.setTotalFee(order.getPaymentPrice().multiply(new BigDecimal(100)).intValue());
        wxPayUnifiedOrderRequest.setTradeType("JSAPI");
        wxPayUnifiedOrderRequest.setNotifyUrl(mallConfigProperties.getNotifyHost() + "/weixin/api/member/order/notify-order");
        wxPayUnifiedOrderRequest.setSpbillCreateIp("127.0.0.1");
        wxPayUnifiedOrderRequest.setOpenid(ThirdSessionHolder.getThirdSession().getOpenId());
        WxPayService wxPayService = WxPayConfiguration.getPayService();
        return wxPayService.createOrder(wxPayUnifiedOrderRequest);
    }

    @PostMapping("/order/notify-order")
    public String notifyOrder(@RequestBody String xmlData) throws WxPayException {
        log.info("支付回调:" + xmlData);
        WxPayService wxPayService = WxPayConfiguration.getPayService();
        WxPayOrderNotifyResult notifyResult = wxPayService.parseOrderNotifyResult(xmlData);
        String respXml;
        try {
            notifyService.notify(notifyResult, ProductTypeEnum.MEMBER);
            respXml = WxPayNotifyResponse.success("成功");
        } catch (Exception e) {
            log.error("位置支付回调失败{}", e.getMessage());
            respXml = WxPayNotifyResponse.fail(e.getMessage());
        }
        log.info("支付回调resp:{}", respXml);
        return respXml;
    }

}
