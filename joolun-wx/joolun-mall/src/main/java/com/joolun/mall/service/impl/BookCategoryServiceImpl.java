
package com.joolun.mall.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.mall.config.CommonConstants;
import com.joolun.mall.entity.BookCategory;
import com.joolun.mall.entity.BookCategoryTree;
import com.joolun.mall.mapper.BookCategoryMapper;
import com.joolun.mall.service.IBookCategoryService;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品类目
 *
 * @author Owen
 * @date 2019-08-12 11:46:28
 */
@Service
public class BookCategoryServiceImpl extends ServiceImpl<BookCategoryMapper, BookCategory> implements IBookCategoryService {

    @Override
    public List<BookCategoryTree> selectTree(BookCategory goodsCategory) {
        return getTree(this.list(Wrappers.lambdaQuery(goodsCategory)));
    }

    /**
     * 构建树
     *
     * @param entitys
     * @return
     */
    private List<BookCategoryTree> getTree(List<BookCategory> entitys) {
        List<BookCategoryTree> treeList = entitys.stream()
                .filter(entity -> !entity.getId().equals(entity.getParentId()))
                .sorted(Comparator.comparingInt(BookCategory::getSort))
                .map(entity -> {
                    BookCategoryTree node = new BookCategoryTree();
                    BeanUtil.copyProperties(entity, node);
                    return node;
                }).collect(Collectors.toList());
        return build(treeList, CommonConstants.LONG_PARENT_ID);
    }

    @Override
    public boolean removeById(Serializable id) {
        super.removeById(id);
        remove(Wrappers.<BookCategory>query()
                .lambda().eq(BookCategory::getParentId, id));
        return true;
    }

    private List<BookCategoryTree> build(List<BookCategoryTree> treeNodes, Object root) {
        List<BookCategoryTree> trees = new ArrayList<>();
        for (BookCategoryTree treeNode : treeNodes) {
            if (root.equals(treeNode.getParentId())) {
                trees.add(treeNode);
            }
            for (BookCategoryTree it : treeNodes) {
                if (it.getParentId().equals(treeNode.getId())) {
                    treeNode.addChildren(it);
                }
            }
        }
        return trees;
    }
}
