/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，项目使用请保留此说明
 */
const app = getApp()
const uploadImage = require('../../../js/uploadImg/uploadImg.js');
Page({
  data: {
    config: app.globalData.config,
    wxUser: null,
    userInfo: null,
    level:'',
    editStatus:false
  },

  onShow() {
    let wxUser = app.globalData.wxUser
    let str = '普通用户'
    console.log(wxUser)
    if(wxUser.level === 1){
      str = "会员"
    }else if(wxUser.level === 2){
      str = '超级会员'
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
  onChooseAvatar(e) {
    const avatarUrl  = e.detail.avatarUrl
    console.log(avatarUrl)
    let that = this;
    uploadImage(avatarUrl, '',
      function (result) {
        let wxUserInfo = {}
        wxUserInfo.headImgUrl = result
        app.api.wxUserSave(wxUserInfo)
        .then(res => {
          let wxUser = res.data
          that.setData({
            wxUser: wxUser
          })
          app.globalData.wxUser = wxUser
          that.wxUserGet()
        })
      },
      function (result) {
        log.error('upload faild')
        logger.error(result)

      }
    )
   
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
  },
  bugVip(e) {
    wx.navigateTo({
      url: '/pages/user/user-member/index',
    })
  },
  editNickName(e) {
    this.setData({
      editStatus: true
    })
  },
  updateNickName(e) {
    let nickName = e.detail.value.nickName
    let wxUserInfo = {}
    wxUserInfo.nickName = nickName
    app.api.wxUserSave(wxUserInfo)
    .then(res => {
      let wxUser = res.data
      this.setData({
        wxUser: wxUser
      })
      app.globalData.wxUser = wxUser
      this.wxUserGet()
    })
  
    this.setData({
      editStatus:false
    })
  },
  toLoginPage(e) {
    wx.navigateTo({
      url: '/pages/user/login/index',
    })
  }
})