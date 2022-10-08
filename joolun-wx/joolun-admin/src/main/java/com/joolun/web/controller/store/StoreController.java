package com.joolun.web.controller.store;

import com.joolun.common.core.domain.AjaxResult;
import com.joolun.mall.dto.StoreDataVo;
import com.joolun.mall.service.IUserShareRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lanjian
 * @Description
 * @create 2022-10-07
 */
@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    IUserShareRecordService userShareRecordService;

    @GetMapping("/share/list")
    public AjaxResult listShare(String nickName) {
        List<StoreDataVo> storeDataVos = userShareRecordService.queryStoreData(nickName);
        return AjaxResult.success(storeDataVos);
    }

}
