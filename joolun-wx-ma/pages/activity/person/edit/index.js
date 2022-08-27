/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，项目使用请保留此说明
 */
const app = getApp()
Page({
  data: {
    person:{}
  },
  onLoad: function (options) {
    app.initPage()
    .then(() => {
      this.getDetail(activityId)
    })
  },
  getPerson() {
    app.api.getActitityPersons().then(res => {
      let person = res.data;
      this.setData({
        person:person
      })
    })
  },
  ChooseImage() {
    wx.chooseImage({
      count: 4, //默认9
      sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album'], //从相册选择
      success: (res) => {
        
      }
    });
  },
})