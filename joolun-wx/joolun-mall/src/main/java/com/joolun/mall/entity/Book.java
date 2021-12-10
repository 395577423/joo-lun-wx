package com.joolun.mall.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.joolun.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 书籍对象 book
 *
 * @author Owen
 * @date 2021-12-08
 */
@Data
@TableName("book")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "书籍　")
public class Book extends Model<Book> {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 标题
     */
    @Excel(name = "标题")
    private String title;

    /**
     * 简介
     */
    @Excel(name = "简介")
    private String introduction;

    /**
     * 封面地址
     */
    @Excel(name = "封面地址")
    private String coverUrl;

    /**
     * 作者
     */
    @Excel(name = "作者")
    private String author;

    /**
     * 书籍类型ID
     */
    @Excel(name = "书籍类型ID")
    private Long dictCode;

    /**
     * 0 停用 1 正常
     */
    @Excel(name = "0 停用 1 正常")
    private String status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 最后更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 创建者
     */
    private String createBy;
    /**
     * 备注
     */
    private String remark;
}
