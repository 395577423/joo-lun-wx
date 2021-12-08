package com.joolun.mall.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.joolun.common.annotation.Excel;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 书籍问题选项对象 book_question_choice
 *
 * @author Owen
 * @date 2021-12-08
 */
@Data
@TableName("book_question_choice")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "书籍问题选项对象")
public class BookQuestionChoice extends Model<BookQuestionChoice> {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 问题ID
     */
    @Excel(name = "问题ID")
    private Long questionId;

    /**
     * 1 是 0 否
     */
    @Excel(name = "1 是 0 否")
    private Long choosed;

    /**
     * 问题的选项
     */
    @Excel(name = "问题的选项")
    private String choice;

    /**
     * 排序
     */
    @Excel(name = "排序")
    private Long sort;

}
