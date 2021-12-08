package com.joolun.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.joolun.mall.entity.Book;
import com.joolun.mall.entity.GoodsSpu;

/**
 * 书籍Service接口
 *
 * @author www.joolun.com
 * @date 2021-12-08
 */
public interface IBookService extends IService<Book> {
    boolean save1(Book book);

    IPage<Book> page1(Page page, Book book);
}
