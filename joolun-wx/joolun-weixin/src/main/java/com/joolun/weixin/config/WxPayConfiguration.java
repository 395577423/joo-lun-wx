
package com.joolun.weixin.config;

import cn.hutool.core.util.StrUtil;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * 微信支付Configuration
 * @author Owen
 *
 */
@Slf4j
@Configuration
public class WxPayConfiguration {

	private static WxMaProperties wxMaProperties;

	@Autowired
	public WxPayConfiguration(WxMaProperties wxMaProperties) {
		this.wxMaProperties = wxMaProperties;
	}

	/**
	 *  获取WxMpService
	 * @return
	 */
	public static WxPayService getPayService() {
		WxPayService wxPayService = null;
		WxPayConfig payConfig = new WxPayConfig();
		payConfig.setAppId(wxMaProperties.getConfigs().get(0).getAppId());
		payConfig.setMchId(wxMaProperties.getConfigs().get(0).getMchId());
		payConfig.setMchKey(wxMaProperties.getConfigs().get(0).getMchKey());
		payConfig.setKeyPath(wxMaProperties.getConfigs().get(0).getKeyPath());
		// 可以指定是否使用沙箱环境
		payConfig.setUseSandboxEnv(false);
		wxPayService = new WxPayServiceImpl();
		wxPayService.setConfig(payConfig);
		return wxPayService;
    }

}
