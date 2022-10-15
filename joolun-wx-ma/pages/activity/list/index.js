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
        this.getActivityList(1,18,categoryId,activityName);
      })
  },
  onShow() {
    //更新tabbar购物车数量
    wx.setTabBarBadge({
      index: 2,
      text: app.globalData.shoppingCartCount + ''
    })
  },
  getActivityList(pageNo,pageSize,categoryId,name) {
    wx.showLoading({
      title: '加载中',
    })
    let reqParams = {}
    if(categoryId){
      reqParams.categoryId=categoryId
      this.setData({

      })
    }
    if(name){
      reqParams.name = name;
    }
    app.api.getActivityList(reqParams,pageNo,pageSize).then(res => {
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
    this.getActivityList(this.data.pageNo,this.data.pageSize,this.data.categoryId,null);
  }
})
