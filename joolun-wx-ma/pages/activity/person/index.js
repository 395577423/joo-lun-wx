/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，项目使用请保留此说明
 */
const app = getApp()
Page({
  data: {
    persons:[]
  },
  onLoad: function (options) {
    app.initPage()
    .then(() => {
      this.getDetail(activityId)
    })
  },
  getPersons() {
    app.api.getActitityPersons().then(res => {
      let persons = res.data;
      this.setData({
        persons:persons
      })
    })
  },
})