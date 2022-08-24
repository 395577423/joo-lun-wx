/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，项目使用请保留此说明
 */
const app = getApp()
const WxParse = require('../../../public/wxParse/wxParse.js')
Page({
  data: {
    activityId: 0,
    activityContent: {},
  },
  onLoad: function (options) {
    this.setData({
      activityId: options.activityId
    })
    let activityId = options.activityId
    app.initPage()
      .then(() => {
        this.getDetail(activityId)
      })
  },
  getDetail(activityId) {
    app.api.getActivityDetail(activityId).then(res => {
      let activityContent = res.data
      this.setData({
        activityContent: activityContent
      })
    })
  }
})