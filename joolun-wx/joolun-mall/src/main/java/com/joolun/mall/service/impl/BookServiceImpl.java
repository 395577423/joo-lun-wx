package com.joolun.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.mall.entity.Book;
import com.joolun.mall.mapper.BookMapper;
import com.joolun.mall.service.IBookService;
import org.springframework.stereotype.Service;

/**
 * 书籍Service业务层处理
 *
 * @author www.joolun.com
 * @date 2021-12-08
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements IBookService {
}
