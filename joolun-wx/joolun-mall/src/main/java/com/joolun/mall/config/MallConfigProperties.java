
package com.joolun.mall.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 商城相关配置
 *
 * @author Owen
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "mall")
public class MallConfigProperties {

	private String notifyHost = "notify-host";

	private String logisticsKey = "logistics-key";
}
