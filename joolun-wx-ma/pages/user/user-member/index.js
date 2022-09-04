/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，项目使用请保留此说明
 */
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },
  buy(e) {
    app.api.buyMember().then(res =>{
      if(res.data){
        let payData = res.data
        wx.requestPayment({
          'timeStamp': payData.timeStamp,
          'nonceStr': payData.nonceStr,
          'package': payData.packageValue,
          'signType': payData.signType,
          'paySign': payData.paySign,
          'success': function (res) {
            let wxUser = app.globalData.wxUser 
            wxUser.member = 1
            app.globalData.wxUser = wxUser;
            wx.redirectTo({
              url: '/pages/user/user-center/index',
            })
          },
          'fail': function (res) {

          },
          'complete': function (res) {
            wx.redirectTo({
              url: '/pages/user/user-center/index',
            })
          }
        })
      }
    })
  }
})