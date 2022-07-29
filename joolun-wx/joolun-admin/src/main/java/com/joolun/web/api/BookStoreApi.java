package com.joolun.web.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.common.utils.StringUtils;
import com.joolun.mall.dto.LibraryDTO;
import com.joolun.mall.entity.Book;
import com.joolun.mall.entity.Course;
import com.joolun.mall.entity.Library;
import com.joolun.mall.service.ILibraryService;
import com.spatial4j.core.io.GeohashUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther ChaoYun
 * @Description
 * @Date 2022/7/25
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/weixin/api/bookstore")
public class BookStoreApi {

    @Resource
    private ILibraryService libraryService;

    /**
     * 查询奖学金计划课程
     *
     * @return 参与奖学金计划的课程
     */
    @PostMapping("/library")
    public AjaxResult getLibrary(@RequestBody LibraryDTO dto) {
        String geoHashStr = "";
        if (StringUtils.isNotEmpty(dto.getLongitude()) && StringUtils.isNotEmpty(dto.getLatitude())) {
            geoHashStr = GeohashUtils.encodeLatLon(Double.parseDouble(dto.getLatitude()), Double.parseDouble(dto.getLongitude()), dto.getLevel());
        }
        QueryWrapper<Library> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(dto.getName()), "name", dto.getName())
                .like(StringUtils.isNotEmpty(geoHashStr), "geo_hash", geoHashStr);
        List<Library> list = libraryService.list(queryWrapper);
        return AjaxResult.success(list);
    }
}
