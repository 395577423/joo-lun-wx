package com.joolun.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.mall.entity.BookStory;
import com.joolun.mall.mapper.BookStoryMapper;
import com.joolun.mall.service.IBookStoryService;
import org.springframework.stereotype.Service;

/**
 * 书籍故事Service业务层处理
 *
 * @author www.joolun.com
 * @date 2021-12-08
 */
@Service
public class BookStoryServiceImpl extends ServiceImpl<BookStoryMapper, BookStory> implements IBookStoryService {

}
