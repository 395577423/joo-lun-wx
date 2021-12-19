package com.joolun.mall.entity;

import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

/**
 * @author Owen
 * @date 2021/12/16 3:07 PM
 **/
public class BookCategoryTree extends TreeNode {


    /**
     * （1：开启；0：关闭）
     */
    @ApiModelProperty(value = "1：开启；0：关闭")
    private String enable;
    /**
     * 父分类编号
     */
    @ApiModelProperty(value = "父分类编号")
    private String parentId;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;
    /**
     * 图片
     */
    @ApiModelProperty(value = "图片")
    private String picUrl;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
    /**
     * 最后更新时间
     */
    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime updateTime;
    /**
     * 逻辑删除标记（0：显示；1：隐藏）
     */
    @ApiModelProperty(value = "逻辑删除标记")
    private String delFlag;
}
