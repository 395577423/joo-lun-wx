package com.joolun.web.api;

import com.joolun.common.core.domain.AjaxResult;
import com.joolun.mall.service.IVideoSwitchService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pengjunming
 * @description
 * @date 2022/10/23 17:39
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/weixin/api/videoswitch")
@Api(value = "wxuser", tags = "小程序用户API")
public class VideoSwitchApi {

    final IVideoSwitchService iVideoSwitchService;

    @GetMapping
    public AjaxResult getSwitch() {
        return AjaxResult.success(iVideoSwitchService.getById(1L));
    }
}
