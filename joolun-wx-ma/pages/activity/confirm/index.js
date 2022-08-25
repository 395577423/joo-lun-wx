/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，项目使用请保留此说明
 */
const app = getApp()
const util = require('../../../utils/util.js')
Page({
  data: {
    selectCaseIndex: 0,
    activityId: 0,
    activityContent: {},
    subInfo:[],
    minData:1,
    maxData:99,
    number:1,
    date:'2022-08-25'
  },
  onLoad: function (options) {
    var DATE = util.formatDate(new Date());
    this.setData({
      activityId: options.activityId,
      date:DATE
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
      let subInfo = JSON.parse(activityContent.subInfo);
      this.setData({
        activityContent: activityContent,
        subInfo: subInfo
      })
    })
  },
  selectCase(e){
    let index = e.currentTarget.dataset.index
    this.setData({
      selectCaseIndex:index
    })
  },
  retrunResult(e){
    debugger
  },
  dateChange(e){
    this.setData({
      date: e.detail.value
    })
  }
})