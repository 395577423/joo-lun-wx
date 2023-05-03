/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，项目使用请保留此说明
 */
const app = getApp()
import Paint from './paint.js'
Page({

  /**
   * 页面的初始数据
   */
  data: {
    bgImage: '',
    qrCode: '',
    template: {},
    imageSrc:'',
    show: false,
    wxUser : app.globalData.wxUser
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    let scene = options.scene;
    let that = this;
    app.initPage().then(res => {
      let wxmaCode = app.globalData.config.basePath + "/weixin/api/activity/image/wxm/code?page=pages/user/user-member/index&param=" + encodeURIComponent(app.globalData.wxUser.id)
      console.log(wxmaCode)
      this.setData({
        qrCode: wxmaCode
      })
      app.api.getMemberConfig().then(res => {
        if (res.data) {
          that.setData({
            'bgImage': res.data.imgUrl
          })
        }
      })
      if (scene) {
        if (scene) {
          scene = decodeURIComponent(options.scene);
          this.addShareRecord(scene)
        }
      }

  
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {},
  buy(e) {
    app.api.buyMember().then(res => {
      if (res.data) {
        let payData = res.data
        wx.requestPayment({
          'timeStamp': payData.timeStamp,
          'nonceStr': payData.nonceStr,
          'package': payData.packageValue,
          'signType': payData.signType,
          'paySign': payData.paySign,
          'success': function (res) {
            let wxUser = app.globalData.wxUser
            wxUser.member = true
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
  },
  getPhoneNumber (e) {
    debugger
    let code = e.detail.code;
    if(code) {
      app.api.bindWXPhoneNumber({code:code,userId:app.globalData.wxUser.id});
      this.buy();
    }
  },
  addShareRecord(shareUserId) {
    app.api.addShareRecord(shareUserId).then(res => {
      console.log('添加分享记录完成')
    })
  },
  share() {
    this.getDraw();
  },
  getDraw() {
    // 做判断，如果已经生成过，就不用反复生成了
    if (this.data.imageSrc) {
      this.setData({
        imageSrc: this.data.imageSrc,
        show: true
      })
    } else {
      wx.showLoading({
        title: '生成中',
      })
      let wxmaCode = app.globalData.config.basePath + "/weixin/api/activity/image/wxm/code?page=pages/user/user-member/index&param=" + encodeURIComponent(app.globalData.wxUser.id)
      let nickName = app.globalData.wxUser.nickName
      let bgImage = this.data.bgImage;
      let params = {
        qrcode: wxmaCode,
        nickName: nickName,
        bgImage:bgImage
      }
      let plate = new Paint().palette(params)
      this.setData({
        template: plate
      })
      console.log(plate);
    }
  },
  save() {
    wx.getSetting({
      success: (set) => {
        wx.saveImageToPhotosAlbum({
          filePath: this.data.imageSrc,
          success: (res) => {
            if (res.errMsg == "saveImageToPhotosAlbum:ok") {
              wx.showToast({
                title: '保存成功',
              });
            }
          },
        });
        if (set.authSetting['scope.writePhotosAlbum'] == false) {
          wx.openSetting()
        }
      }
    })
  },
  share() {
    this.getDraw();
  },
  onClose() {
    this.setData({
      show: false,
    })
  },
  canvasSuc(e) {
    this.setData({
      imageSrc: e.detail.path,
      show: true
    })
    wx.hideLoading({
      success: (res) => {},
    })
  },
  canvasFail(e) {
    console.log("生成图片失败" + e.detail);
    wx.hideLoading({
      success: (res) => {},
    })
  },
})