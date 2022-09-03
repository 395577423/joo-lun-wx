package com.joolun.web.api;

import com.joolun.common.core.domain.AjaxResult;
import com.joolun.mall.service.IUserMemberConfigService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private final


    /**
     * 购买会员
     */
    @GetMapping("/buy")
    public AjaxResult buy(){

    }


}
