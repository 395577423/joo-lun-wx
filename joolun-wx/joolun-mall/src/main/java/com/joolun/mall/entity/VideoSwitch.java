package com.joolun.mall.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author pengjunming
 * @description
 * @date 2022/10/23 17:34
 */
@Data
@TableName("video_switch")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "视频开关")
public class VideoSwitch extends Model<VideoSwitch> {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /**
     * 1:打开 0:关闭
     */
    private String status;
}
