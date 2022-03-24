
package com.joolun.weixin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.joolun.weixin.entity.WxMenu;
import me.chanjar.weixin.common.error.WxErrorException;

import java.io.Serializable;

/**
 * 自定义菜单
 *
 * @author Owen
 * @date 2019-03-27 16:52:10
 */
public interface WxMenuService extends IService<WxMenu> {

	/***
	 * 获取WxApp下的菜单
	 * @return
	 */
	String getWxMenuButton();

	/**
	 * 保存并发布菜单
	 * @param
	 */
	void saveAndRelease( String strWxMenu) throws WxErrorException;

}
