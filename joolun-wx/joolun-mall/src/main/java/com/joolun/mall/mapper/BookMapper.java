package com.joolun.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.mall.entity.Book;


/**
 * 书籍Mapper接口
 *
 * @author www.joolun.com
 * @date 2021-12-08
 */
public interface BookMapper extends BaseMapper<Book> {
    IPage<Book> selectPage1(Page page, Book book);
}
