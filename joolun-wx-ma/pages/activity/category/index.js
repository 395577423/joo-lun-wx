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
    TabCur: 0,
    MainCur: 0,
    VerticalNavTop: 0,
    activityCategory: [],
    activityList:[],
    load: true,
    pageNo:1,
    pageSize:18,
    categoryId:0,
  },
  onLoad() { 
    app.initPage()
      .then(res => {
        this.getActivityCategory()
      })
  },
  onShow() {
    //更新tabbar购物车数量
    wx.setTabBarBadge({
      index: 2,
      text: app.globalData.shoppingCartCount + ''
    })
  },
  getActivityCategory() {
    app.api.getActivityCategory()
      .then(res => {
        let activityCategory = res.data
        this.setData({
          activityCategory: activityCategory
        })
        if(activityCategory.length>0){
          let categoryId = activityCategory[0].id;
          this.setData({
            categoryId: categoryId
          })
          this.getActivityList(categoryId,this.data.pageNo,this.data.pageSize)
        }
      })
  },
  getActivityList(categoryId,pageNo,pageSize) {
    app.api.getActivityList(categoryId,pageNo,pageSize).then(res => {
      const activityList = [...this.data.activityList, ...res.data.records]
      this.setData({
        activityList: activityList
      })
    })
  },
  tabSelect(e) {
    this.setData({
      TabCur: e.currentTarget.dataset.index,
      MainCur: e.currentTarget.dataset.index,
      categoryId: e.currentTarget.dataset.id,
      VerticalNavTop: (e.currentTarget.dataset.id - 1) * 50,
      pageNo: 1,
      activityList: []
    })
    this.getActivityList(e.currentTarget.dataset.id,this.data.pageNo,this.data.pageSize);
  },
  getMoreActivity(e){
    this.setData({
      pageNo: this.data.pageNo+1
    })
    this.getActivityList(this.data.categoryId,this.data.pageNo,this.data.pageSize);
  }
})
