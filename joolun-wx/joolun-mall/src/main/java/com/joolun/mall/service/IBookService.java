package com.joolun.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.joolun.mall.entity.Book;
import com.joolun.mall.entity.BookCategoryTree;

import java.util.List;

/**
 * 书籍Service接口
 *
 * @author www.joolun.com
 * @date 2021-12-08
 */
public interface IBookService extends IService<Book> {
    List<BookCategoryTree> selectTree(Book book);
}
