package com.joolun.web.api;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.mall.entity.Propaganda;
import com.joolun.mall.service.IPropagandaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Owen
 * @date 2022/10/13 22:19
 */
@Slf4j
@RestController
@RequestMapping("/weixin/api/propaganda")
public class PropagandaApi {

    @Resource
    private IPropagandaService iPropagandaService;

    /**
     * 查询社会活动列表
     */
    @PostMapping
    public AjaxResult getOne() {
        return AjaxResult.success(iPropagandaService.getOne(Wrappers.<Propaganda>lambdaQuery().eq(Propaganda::getActived, "1")));
    }
}
