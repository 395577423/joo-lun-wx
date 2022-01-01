package com.joolun.mall.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.common.core.domain.entity.SysDictData;
import com.joolun.mall.config.CommonConstants;
import com.joolun.mall.entity.*;
import com.joolun.mall.mapper.BookMapper;
import com.joolun.mall.service.IBookService;
import com.joolun.mall.util.TreeUtil;
import com.joolun.system.service.ISysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 书籍Service业务层处理
 *
 * @author www.joolun.com
 * @date 2021-12-08
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements IBookService {


    @Override
    public List<Book> getListByCourse(Long id) {
        return baseMapper.getListByCourse(id);
    }

    @Override
    public boolean deleteRelatedBooks(Long id) {
        return baseMapper.deleteByCourse(id);
    }

    @Override
    public void addRelatedCourse(Long id, Long bookId) {
        baseMapper.addRelatedCourse(id,bookId);
    }
}
