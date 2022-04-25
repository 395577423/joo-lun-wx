package com.joolun.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.joolun.mall.entity.EmpowerVideo;

/**
 * @author Owen
 * @date 2022/4/16 21:20
 */
public interface IEmpowerVideoService extends IService<EmpowerVideo> {
    IPage<EmpowerVideo> getPage(String userId,String name, Page page);
}
