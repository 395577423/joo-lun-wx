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
    wxUser: null,
    userInfo: null,
    level:''
  },

  onShow() {
    let wxUser = app.globalData.wxUser
    let level = wxUser.level

    let str = '书迷'
    if(level ==1 ){
       str = '阅读推广大使'
    }else if(level == 2){
      str = '大Boss'
    }else if(level == 3){
      str ='城市合伙人'
    }

    this.setData({
      level:str
    })


    this.setData({
      wxUser: wxUser
    })
    if (this.data.config.adEnable) {
      // 在页面中定义插屏广告
      let interstitialAd = null
      // 在页面onLoad回调事件中创建插屏广告实例
      if (wx.createInterstitialAd) {
        interstitialAd = wx.createInterstitialAd({
          adUnitId: this.data.config.adInsertScreenID
        })
        interstitialAd.onLoad(() => {})
        interstitialAd.onError((err) => {})
        interstitialAd.onClose(() => {})
      }
      // 在适合的场景显示插屏广告
      if (interstitialAd) {
        interstitialAd.show().catch((err) => {
          console.error(err)
        })
      }
    }
  },
  onLoad() {
    this.wxUserGet()
  },
  onReady(){
  },
  /**
   * 小程序设置
   */
  settings: function () {
    wx.openSetting({
      success: function (res) {

      }
    })
  },
  getUserProfile(e) {
    wx.getUserProfile({
      desc: '用于完善会员资料', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
      success: (detail) => {
        app.api.wxUserSave(detail)
          .then(res => {
            let wxUser = res.data
            this.setData({
              wxUser: wxUser
            })
            app.globalData.wxUser = wxUser
            this.wxUserGet()
          })
      }
    })
  },
  //获取商城用户信息
  wxUserGet() {
    app.api.wxUserGet()
      .then(res => {
        this.setData({
          userInfo: res.data
        })
      })
  },
  showPic() {
    this.setData({
      modalName: 'showpic'
    })
  },
  showPic2() {
    this.setData({
      modalName: 'showpic2'
    })
  },
  hideModal(e) {
    this.setData({
      modalName: null
    })
  }
})