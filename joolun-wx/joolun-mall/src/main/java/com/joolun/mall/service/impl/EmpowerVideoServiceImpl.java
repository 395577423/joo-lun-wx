package com.joolun.mall.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.mall.entity.EmpowerVideo;
import com.joolun.mall.mapper.EmpowerVideoMapper;
import com.joolun.mall.service.IEmpowerVideoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Owen
 * @date 2022/4/16 21:20
 */
@Service
public class EmpowerVideoServiceImpl extends ServiceImpl<EmpowerVideoMapper, EmpowerVideo> implements IEmpowerVideoService {
    @Override
    public IPage<EmpowerVideo> getPage(String userId, String name, Page page) {
        IPage<EmpowerVideo> courseIPage = baseMapper.selectPage2(page, name, userId);
        List<EmpowerVideo> courseList = courseIPage.getRecords();
        courseIPage.setRecords(courseList);
        return courseIPage;
    }
}
