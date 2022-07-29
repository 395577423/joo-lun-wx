package com.joolun.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.mall.entity.Library;
import com.joolun.mall.mapper.LibraryMapper;
import com.joolun.mall.service.ILibraryService;
import org.springframework.stereotype.Service;

/**
 * @Auther ChaoYun
 * @Description
 * @Date 2022/7/23
 */
@Service
public class LibraryService extends ServiceImpl<LibraryMapper, Library> implements ILibraryService {
}
