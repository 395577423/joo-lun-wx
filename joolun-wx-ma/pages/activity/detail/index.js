/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，项目使用请保留此说明
 */
import Card from './card.js'
const app = getApp()
const WxParse = require('../../../public/wxParse/wxParse.js')
Page({
  data: {
    activityId: 0,
    activityContent: {},
    address: '',
    longitude: 0,
    latitude: 0,
    template: {},
    imageSrc: '',
    share_show: false,
    params: {},
    show: false,
    salesPrice: 0,
    superMemberPrice: 0,
    memberPrice: 0,
    closed: false,
    tips: '',
    interestedNum:1000,
    cashBackAmount:0
  },
  onLoad: function (options) {
    let activityId
    let userId
    if (options.scene) {
      //小程序扫码进入的
      let scene = decodeURIComponent(options.scene);
      activityId = scene.split("#")[0]
      userId = scene.split('#')[1]

    } else if (options.activityId) {
      //页面跳转的
      activityId = options.activityId
    }
    this.setData({
      activityId: activityId
    })

    app.initPage()
      .then(() => {
        this.getDetail(activityId)
        this.getPriceCase(activityId)
        this.getStatus(activityId)
        if (userId) {
          this.addShareRecord(userId)
        }
      })
  },
  getDetail(activityId) {
    let that = this
    app.api.getActivityDetail(activityId).then(res => {
      let activityContent = res.data
      let introduction = activityContent.introduction
      let addressInfo = activityContent.address;
      let address = addressInfo[2];
      let lat = addressInfo[1];
      let lon = addressInfo[0]
      this.setData({
        activityContent: activityContent,
        address: address,
        longitude: lon,
        latitude: lat,
        interestedNum:0
      })
      WxParse.wxParse('introduction', 'html', introduction, this, 0)
    })
  },
  getPriceCase(activityId) {
    let that = this;
    app.api.getPriceCase(activityId).then(res => {
      let priceCases = res.data
      let displaySalesPrice;
      let displayMemberPrice;
      let displaySuperMemberPrice;
      let cashBackAmount;
      if (priceCases && priceCases.length > 0) {
        priceCases.forEach(element => {
          cashBackAmount = element.cashBackAmount
          if (!displaySalesPrice) {
            displaySalesPrice = element.salesPrice
            displayMemberPrice = element.memberPrice
            displaySuperMemberPrice = element.superMemberPrice
          } else if (displaySalesPrice > element.salesPrice) {
            displaySalesPrice = element.salesPrice;
            displayMemberPrice = element.memberPrice
            displaySuperMemberPrice = element.superMemberPrice
          }
        });
      }
      this.setData({
        salesPrice: displaySalesPrice,
        memberPrice: displayMemberPrice,
        superMemberPrice: displaySuperMemberPrice,
        cashBackAmount:cashBackAmount
      })

    })
  },
  getStatus(activityId) {
    app.api.getActivityIsClosed(activityId).then(res => {
      this.setData({
        closed: res.msg == '操作成功' ? false : true,
        tips: res.msg
      })
    })
  },
  getBuyActivityCount(activityId) {
    app.api.getBuyActivityCount(activityId).then(res => {
      this.setData({
        buyCount: res.data
      })
    })
  },
  confirmOrder(e) {
    let tips = this.data.tips
    if (this.data.closed) {
      wx.showToast({
        title: tips,
        icon: 'error',
        duration: 2000
      })
      return false;
    }
    let activityId = e.target.dataset.id
    wx.navigateTo({
      url: '/pages/activity/confirm/index?activityId=' + activityId,
    })
  },
  addShareRecord(shareUserId) {
    app.api.addShareRecord(shareUserId).then(res => {
      console.log('添加分享记录完成')
    })
  },
  share() {
    this.getDraw();
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
      let wxmaCode = app.globalData.config.basePath + "/weixin/api/activity/image/wxm/code?page=pages/activity/detail/index&param=" + encodeURIComponent(this.data.activityId + "#" + app.globalData.wxUser.id)
      let nickName = app.globalData.wxUser.nickName
      let params = {
        qrCode: wxmaCode,
        activityImage: this.data.activityContent.imageUrl,
        textImage: '/public/img/text.png',
        nickName: nickName,
        activityName: this.data.activityContent.name,
        price: '￥' + this.data.salesPrice
      }
      let plate = new Card().palette(params)
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
  onClose() {
    this.setData({
      show: false,
    })
  }
})