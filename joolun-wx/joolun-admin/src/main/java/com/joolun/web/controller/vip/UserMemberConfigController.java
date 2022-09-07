package com.joolun.web.controller.vip;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.joolun.common.annotation.Log;
import com.joolun.common.core.controller.BaseController;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.common.core.domain.entity.SysUser;
import com.joolun.common.core.page.TableDataInfo;
import com.joolun.common.enums.BusinessType;
import com.joolun.common.utils.SecurityUtils;
import com.joolun.common.utils.poi.ExcelUtil;
import com.joolun.mall.entity.UserMemberConfig;
import com.joolun.mall.service.IUserMemberConfigService;
import com.joolun.weixin.utils.ThirdSessionHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 会员价格设置Controller
 *
 * @author Lanjian
 * @date 2022-09-07
 */
@RestController
@RequestMapping("/vip/config")
public class UserMemberConfigController extends BaseController {
    @Autowired
    private IUserMemberConfigService userMemberConfigService;

    /**
     * 查询会员价格设置列表
     */
    @PreAuthorize("@ss.hasPermi('mall:config:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserMemberConfig userMemberConfig) {
        startPage();
        userMemberConfig.setDeleted(false);
        List<UserMemberConfig> list = userMemberConfigService.list(Wrappers.lambdaQuery(userMemberConfig));
        return getDataTable(list);
    }

    /**
     * 获取会员价格设置详细信息
     */
    @PreAuthorize("@ss.hasPermi('mall:config:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(userMemberConfigService.getById(id));
    }

    /**
     * 新增会员价格设置
     */
    @PreAuthorize("@ss.hasPermi('mall:config:add')")
    @Log(title = "会员价格设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserMemberConfig userMemberConfig) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        userMemberConfig.setCreateId(String.valueOf(user.getUserId()));
        userMemberConfig.setCreateTime(new Date());
        boolean result = userMemberConfigService.save(userMemberConfig);
        return toAjax(result ? 1 : 0);
    }

    /**
     * 修改会员价格设置
     */
    @PreAuthorize("@ss.hasPermi('mall:config:edit')")
    @Log(title = "会员价格设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserMemberConfig userMemberConfig) {
        boolean result = userMemberConfigService.updateById(userMemberConfig);
        return toAjax(result ? 1 : 0);
    }

    /**
     * 删除会员价格设置
     */
    @PreAuthorize("@ss.hasPermi('mall:config:remove')")
    @Log(title = "会员价格设置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        boolean result = userMemberConfigService.removeByIds(Lists.newArrayList(ids));
        return toAjax(result ? 1 : 0);
    }
}
