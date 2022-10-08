/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，项目使用请保留此说明
 */
const app = getApp()

Page({
  data: {
    config: app.globalData.config,
    VerticalNavTop: 0,
    activityList:[],
    load: true,
    pageNo:1,
    pageSize:18,
    categoryId:0,
    activityName:''
  },
  onLoad(options) { 
    let categoryId = options.categoryId
    let activityName = options.activityName
    app.initPage()
      .then(res => {
        this.getActivityList(categoryId,activityName,1,18);
      })
  },
  onShow() {
    //更新tabbar购物车数量
    wx.setTabBarBadge({
      index: 2,
      text: app.globalData.shoppingCartCount + ''
    })
  },
  getActivityList(categoryId,name,pageNo,pageSize) {
    wx.showLoading({
      title: '加载中',
    })
    app.api.getActivityList(categoryId,name,pageNo,pageSize).then(res => {
      const activityList = [...this.data.activityList, ...res.data.records]
      this.setData({
        activityList: activityList
      })
      wx.hideLoading()
    })
  },
  getMoreActivity(e){
    this.setData({
      pageNo: this.data.pageNo+1
    })
    this.getActivityList(this.data.categoryId,this.data.pageNo,this.data.pageSize);
  }
})
