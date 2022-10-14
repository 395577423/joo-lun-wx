package com.joolun.web.api;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.mall.entity.Advertisement;
import com.joolun.mall.service.IAdvertisementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Owen
 * @date 2022/10/12 22:28
 */
@Slf4j
@RestController
@RequestMapping("/weixin/api/ad")
public class AdvertisementApi {

    @Resource
    private IAdvertisementService iAdvertisementService;

    /**
     * 查询社会活动列表
     */
    @PostMapping("/list")
    public AjaxResult list() {
        return AjaxResult.success(iAdvertisementService.list(Wrappers.<Advertisement>lambdaQuery().select(Advertisement::getId, Advertisement::getCover)));
    }

    @GetMapping("/{id}")
    public AjaxResult content(@PathVariable Integer id){
        return AjaxResult.success(iAdvertisementService.getById(id));
    }
}
