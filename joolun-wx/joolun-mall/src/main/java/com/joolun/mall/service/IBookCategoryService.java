
package com.joolun.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.joolun.mall.entity.BookCategory;
import com.joolun.mall.entity.BookCategoryTree;

import java.util.List;

/**
 * 商品类目
 *
 * @author Owen
 * @date 2019-08-12 11:46:28
 */
public interface IBookCategoryService extends IService<BookCategory> {

    /**
     * 查询类目树
     *
     * @return 树
     */
    List<BookCategoryTree> selectTree(BookCategory goodsCategory);
}
