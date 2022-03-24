package com.joolun.weixin.entity;

import lombok.Data;

/**
 * imageManager
 * @author Owen
 * @date 2019-03-23 21:26:35
 */
@Data
public class ImageManager{
private static final long serialVersionUID = 1L;

    private String url;
    private String thumb;
    private String tag;
    private String name;
	private Integer id;
}
