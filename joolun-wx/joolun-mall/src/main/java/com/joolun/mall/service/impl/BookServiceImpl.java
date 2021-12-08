package com.joolun.mall.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.mall.entity.Book;
import com.joolun.mall.mapper.BookMapper;
import com.joolun.mall.service.IBookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 书籍Service业务层处理
 *
 * @author www.joolun.com
 * @date 2021-12-08
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements IBookService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save1(Book book) {
        baseMapper.insert(book);
        return true;
    }

    @Override
    public IPage<Book> page1(Page page, Book book) {
        return baseMapper.selectPage1(page, book);
    }
}
