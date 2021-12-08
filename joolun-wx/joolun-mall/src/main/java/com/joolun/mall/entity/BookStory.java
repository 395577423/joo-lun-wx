package com.joolun.mall.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.joolun.common.annotation.Excel;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 书籍故事对象 book_story
 *
 * @author Owen
 * @date 2021-12-08
 */
@Data
@TableName("goods_category")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "书籍故事对象")
public class BookStory extends Model<BookStory> {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 书籍ID
     */
    @Excel(name = "书籍ID")
    private Long bookId;

    /**
     * 内容
     */
    @Excel(name = "内容")
    private String content;

    /**
     * 排序
     */
    @Excel(name = "排序")
    private Long sort;
}
