package com.joolun.web.api;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.mall.config.CommonConstants;
import com.joolun.mall.config.MallConfigProperties;
import com.joolun.mall.entity.ActivityOrderInfo;
import com.joolun.mall.entity.OrderInfo;
import com.joolun.mall.entity.UserIncomeRecord;
import com.joolun.mall.enums.ActivityOrderInfoEnum;
import com.joolun.mall.enums.OrderInfoEnum;
import com.joolun.mall.enums.ProductTypeEnum;
import com.joolun.mall.service.IActivityOrderInfoService;
import com.joolun.mall.service.INotifyService;
import com.joolun.mall.service.IUserIncomeRecordService;
import com.joolun.mall.service.IUserPayRecordService;
import com.joolun.weixin.config.WxPayConfiguration;
import com.joolun.weixin.constant.MyReturnCode;
import com.joolun.weixin.entity.WxUser;
import com.joolun.weixin.utils.LocalDateTimeUtils;
import com.joolun.weixin.utils.ThirdSessionHolder;
import com.joolun.weixin.utils.WxMaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


/**
 * 订单
 *
 * @author YangLinWei
 * @date 2022-08-25 14:06:58
 */
@Api(tags = "订单")
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/weixin/api/activity/order")
public class ActivityOrderApi {

    private final MallConfigProperties mallConfigProperties;


    private final IActivityOrderInfoService activityOrderInfoService;


    private final INotifyService notifyService;

    /**
     * 活动订单分页查询
     *
     * @param page
     * @param status
     * @return
     */
    @GetMapping("/page")
    public AjaxResult page(Page page, String status) {
        LambdaQueryWrapper<ActivityOrderInfo> query = Wrappers.<ActivityOrderInfo>lambdaQuery()
                .eq(StringUtils.isNotBlank(status), ActivityOrderInfo::getStatus, status)
                .eq(ActivityOrderInfo::getDelFlag, "0")
                .eq(ActivityOrderInfo::getUserId,ThirdSessionHolder.getWxUserId())
                .orderByDesc(ActivityOrderInfo::getCreateTime);
        page = activityOrderInfoService.page(page, query);
        return AjaxResult.success(page);
    }


    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @GetMapping("/{id}")
    public AjaxResult findById(@PathVariable Long id) {
        ActivityOrderInfo model = activityOrderInfoService.queryById(id);
        return AjaxResult.success(model);
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "保存")
    @PostMapping("/add")
    public AjaxResult addOrder(@RequestBody ActivityOrderInfo activityOrderInfo) {
        activityOrderInfo.setUserId(ThirdSessionHolder.getWxUserId());
        activityOrderInfoService.addOrder(activityOrderInfo);
        return AjaxResult.success(activityOrderInfo);
    }


    /**
     * 调用统一下单接口，并组装生成支付所需参数对象.
     *
     * @param orderInfo 统一下单请求参数
     * @return 返回 {@link com.github.binarywang.wxpay.bean.order}包下的类对象
     */
    @ApiOperation(value = "调用统一下单接口")
    @PostMapping("/unifiedOrder")
    public AjaxResult unifiedOrder(HttpServletRequest request, @RequestBody ActivityOrderInfo orderInfo) throws WxPayException {
        //检验用户session登录
        WxUser wxUser = new WxUser();
        wxUser.setId(ThirdSessionHolder.getThirdSession().getWxUserId());
        wxUser.setSessionKey(ThirdSessionHolder.getThirdSession().getSessionKey());
        wxUser.setOpenId(ThirdSessionHolder.getThirdSession().getOpenId());
        orderInfo = activityOrderInfoService.getById(orderInfo.getId());
        if (orderInfo == null) {
            return AjaxResult.error(MyReturnCode.ERR_70005.getCode(), MyReturnCode.ERR_70005.getMsg());
        }
        if (!CommonConstants.NO.equals(orderInfo.getIsPay())) {//只有未支付的详单能发起支付
            return AjaxResult.error(MyReturnCode.ERR_70004.getCode(), MyReturnCode.ERR_70004.getMsg());
        }
        if (orderInfo.getPaymentPrice().compareTo(BigDecimal.ZERO) == 0) {//0元购买不调支付
            orderInfo.setPaymentTime(new Date());
            orderInfo.setIsPay(CommonConstants.YES);
            activityOrderInfoService.updateById(orderInfo);
            return AjaxResult.success();
        }
        String appId = WxMaUtil.getAppId(request);
        WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = new WxPayUnifiedOrderRequest();
        wxPayUnifiedOrderRequest.setAppid(appId);
        String body = orderInfo.getName();
        body = body.length() > 40 ? body.substring(0, 39) : body;
        wxPayUnifiedOrderRequest.setBody(body);
        wxPayUnifiedOrderRequest.setOutTradeNo(orderInfo.getOrderNo());
        wxPayUnifiedOrderRequest.setTotalFee(orderInfo.getPaymentPrice().multiply(new BigDecimal(100)).intValue());
        wxPayUnifiedOrderRequest.setTradeType("JSAPI");
        wxPayUnifiedOrderRequest.setNotifyUrl(mallConfigProperties.getNotifyHost() + "/weixin/api/activity/order/notify-order");
        wxPayUnifiedOrderRequest.setSpbillCreateIp("127.0.0.1");
        wxPayUnifiedOrderRequest.setOpenid(wxUser.getOpenId());
        WxPayService wxPayService = WxPayConfiguration.getPayService();
        return AjaxResult.success(JSONUtil.parse(wxPayService.createOrder(wxPayUnifiedOrderRequest)));
    }

    /**
     * 支付回调
     *
     * @param xmlData
     * @return
     * @throws WxPayException
     */
    @ApiOperation(value = "支付回调")
    @PostMapping("/notify-order")
    public String notifyOrder(@RequestBody String xmlData) throws WxPayException {
        log.info("支付回调:" + xmlData);
        WxPayService wxPayService = WxPayConfiguration.getPayService();
        WxPayOrderNotifyResult notifyResult = wxPayService.parseOrderNotifyResult(xmlData);
        String respXml;
        try {
            notifyService.notify(notifyResult, ProductTypeEnum.ACTIVITY);
            respXml = WxPayNotifyResponse.success("成功");
        } catch (Exception e) {
            log.error("位置支付回调失败{}", e.getMessage());
            respXml = WxPayNotifyResponse.fail(e.getMessage());
        }
        log.info("支付回调resp:{}", respXml);
        return respXml;
    }


    /**
     * 新增or更新
     */
    @ApiOperation(value = "保存")
    @PostMapping
    public AjaxResult save(@RequestBody ActivityOrderInfo activityOrderInfo) {
        activityOrderInfoService.saveOrUpdate(activityOrderInfo);
        return AjaxResult.success("保存成功");
    }


    /**
     * 通过id删除商城订单
     *
     * @param id
     * @return R
     */
    @ApiOperation(value = "通过id删除商城订单")
    @DeleteMapping("/{id}")
    public AjaxResult delete(@PathVariable String id) {
        ActivityOrderInfo orderInfo = activityOrderInfoService.getById(id);
        if (orderInfo == null) {
            return AjaxResult.error(MyReturnCode.ERR_70005.getCode(), MyReturnCode.ERR_70005.getMsg());
        }
        if (!ActivityOrderInfoEnum.STATUS_3.getValue().equals(orderInfo.getStatus()) || CommonConstants.YES.equals(orderInfo.getIsPay())) {
            return AjaxResult.error(MyReturnCode.ERR_70001.getCode(), MyReturnCode.ERR_70001.getMsg());
        }
        orderInfo.setDelFlag("1");
        return AjaxResult.success(activityOrderInfoService.updateById(orderInfo));
    }

    /**
     * 取消商城订单
     *
     * @param id 商城订单
     * @return R
     */
    @ApiOperation(value = "取消商城订单")
    @PutMapping("/cancel/{id}")
    public AjaxResult orderCancel(@PathVariable String id) {
        ActivityOrderInfo orderInfo = activityOrderInfoService.getById(id);
        if (orderInfo == null) {
            return AjaxResult.error(MyReturnCode.ERR_70005.getCode(), MyReturnCode.ERR_70005.getMsg());
        }
        if (!CommonConstants.NO.equals(orderInfo.getIsPay())) {//只有未支付订单能取消
            return AjaxResult.error(MyReturnCode.ERR_70001.getCode(), MyReturnCode.ERR_70001.getMsg());
        }
        orderInfo.setStatus(ActivityOrderInfoEnum.STATUS_3.getValue());
        activityOrderInfoService.updateById(orderInfo);
        return AjaxResult.success(orderInfo);
    }
}
