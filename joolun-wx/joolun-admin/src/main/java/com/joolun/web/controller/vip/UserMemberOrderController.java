package com.joolun.web.controller.vip;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.joolun.common.annotation.Log;
import com.joolun.common.core.controller.BaseController;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.common.core.page.TableDataInfo;
import com.joolun.common.enums.BusinessType;
import com.joolun.common.utils.poi.ExcelUtil;
import com.joolun.mall.entity.UserMemberOrder;
import com.joolun.mall.service.IUserMemberOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 【请填写功能名称】Controller
 *
 * @author Lanjian
 * @date 2022-09-07
 */
@RestController
@RequestMapping("/vip/order")
public class UserMemberOrderController extends BaseController {
    @Autowired
    private IUserMemberOrderService userMemberOrderService;

    /**
     * 查询【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('mall:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserMemberOrder userMemberOrder) {
        startPage();
        List<UserMemberOrder> list = userMemberOrderService.list(Wrappers.lambdaQuery(userMemberOrder));
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('mall:order:export')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(UserMemberOrder userMemberOrder) {
        List<UserMemberOrder> list = userMemberOrderService.list(Wrappers.lambdaQuery(userMemberOrder));
        ExcelUtil<UserMemberOrder> util = new ExcelUtil<UserMemberOrder>(UserMemberOrder.class);
        return util.exportExcel(list, "order");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @PreAuthorize("@ss.hasPermi('mall:order:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(userMemberOrderService.getById(id));
    }

}
