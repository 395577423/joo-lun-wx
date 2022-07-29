package com.joolun.web.controller.bookstore;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.common.annotation.Log;
import com.joolun.common.core.controller.BaseController;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.common.enums.BusinessType;
import com.joolun.common.utils.StringUtils;
import com.joolun.mall.entity.Library;
import com.joolun.mall.service.ILibraryService;
import com.spatial4j.core.io.GeohashUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;

/**
 * 图书店Controller
 *
 * @author Owen
 * @date 2022-07-23
 */
@RestController
@RequestMapping("/library")
public class LibraryController extends BaseController {
    @Autowired
    private ILibraryService libraryService;

    /**
     * 查询图书店列表
     */
    @GetMapping("/page")
    public AjaxResult list(Page page, Library library) {
        QueryWrapper<Library> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(library.getName()), "name", library.getName());
        return AjaxResult.success(libraryService.page(page, queryWrapper));
    }

    /**
     * 获取图书店详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(libraryService.getById(id));
    }

    /**
     * 新增图书店
     */
    @Log(title = "图书店", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Library library) {
        library.setCreateAt(LocalDate.now().toString());
        library.setGeoHash(GeohashUtils.encodeLatLon(Double.parseDouble(library.getLatitude().toString()), Double.parseDouble(library.getLongitude().toString())));
        return AjaxResult.success(libraryService.save(library));
    }

    /**
     * 修改图书店
     */
    @Log(title = "图书店", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Library library) {
        return AjaxResult.success(libraryService.updateById(library));
    }

    /**
     * 删除图书店
     */
    @Log(title = "图书店", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return AjaxResult.success(libraryService.removeByIds(Arrays.asList(ids)));
    }
}