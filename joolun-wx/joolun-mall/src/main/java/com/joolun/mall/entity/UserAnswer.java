package com.joolun.mall.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.joolun.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.time.LocalDateTime;

/**
 * 用户答案对象 user_answer
 *
 * @author Owen
 * @date 2021-12-08
 */
@Data
@TableName("user_answer")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "用户答案对象")
public class UserAnswer extends Model<UserAnswer> {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 用户ID
     */
    @Excel(name = "用户ID")
    private String userId;

    /**
     * 答案ID
     */
    @Excel(name = "答案ID")
    private Long answerId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 最后更新时间
     */
    private LocalDateTime updateTime;
}
