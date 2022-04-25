package com.joolun.mall.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Owen
 * @date 2021/12/16 3:07 PM
 **/
@Data
public class BookCategoryTree {

    private Long id;

    /**
     * （1：开启；0：关闭）
     */
    @ApiModelProperty(value = "1：开启；0：关闭")
    private String enable;
    /**
     * 父分类编号
     */
    @ApiModelProperty(value = "父分类编号")
    private Long parentId;
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
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 最后更新时间
     */
    @ApiModelProperty(value = "最后更新时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     * 逻辑删除标记（0：显示；1：隐藏）
     */
    @ApiModelProperty(value = "逻辑删除标记")
    private String delFlag;

    private List<BookCategoryTree> children = new ArrayList<>();

    public void addChildren(BookCategoryTree treeNode) {
        children.add(treeNode);
    }

    public List<BookCategoryTree> getChildren() {
        if (children.size() <= 0) {
            return null;
        }
        return children;
    }

}
