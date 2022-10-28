// pages/course/empower/index.js
const WxParse = require('../../../public/wxParse/wxParse.js')
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    video: null,
    userId: '',
    level: 0,
    id: null,
    realPrice: null,
    canPlay: false,
    playing: false,
    videoSwitch: '0'
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let id = options.empowerid
    this.setData({
      id: id
    })
    this.getSwitch()
    app.initPage()
      .then(res => {
        this.wxUserGet()
      })
  },
  getSwitch() {
    app.api.getSwitch()
      .then(res => {
        this.setData({
          videoSwitch: res.data.status
        })
      })
  },
  getEmpowerVideoDetail() {
    let userId = this.data.userId
    let id = this.data.id
    let that = this
    app.api.empowerVideoDetail(id, userId)
      .then(res => {
        let video = res.data
        WxParse.wxParse('introduction', 'html', video.introduction, this, 0)
        const level = that.data.level
        //先判定用户是否有等级 ,如果等级大于等于视频等级，则可以播放
        let price = video.price
        let rates = video.rates
        let buyer = video.userId
        let realPrice = price
        if (null != rates && rates > 0) {
          realPrice = rates
        }
        let id = video.id
        if (level >= video.videoLevel || buyer === this.data.userId) {
          this.setData({
            canPlay: true,
            id: id
          })
        }
        this.setData({
          video: video,
          theNum: null,
          realPrice: realPrice
        })
      })
  },
  wxUserGet() {
    app.api.wxUserGet()
      .then(res => {
        this.setData({
          level: res.data.level,
          userId: res.data.id
        })
        this.getEmpowerVideoDetail()
      })
  },
  videoPlay(e) {
    let canPlay = this.data.canPlay
    if (!canPlay) {
      this.showModal('toBuy')
    } else {
      this.setData({
        playing: true
      })
    }
  },
  toBuy() {
    this.showModal('toBuy')
  },
  showModal(name) {
    this.setData({
      modalName: name
    })
  },
  hideModal(e) {
    this.setData({
      modalName: null
    })
  },
  pay() {
    this.setData({
      loading: true
    })
    var that = this
    let id = that.data.id
    let userId = that.data.userId
    app.api.empowerUnifiedOrder({
        id: id,
        userId: userId
      })
      .then(res => {
        this.setData({
          loading: false
        })
        let payData = res.data
        wx.requestPayment({
          'timeStamp': payData.timeStamp,
          'nonceStr': payData.nonceStr,
          'package': payData.packageValue,
          'signType': payData.signType,
          'paySign': payData.paySign,
          'success': function (res) {
            that.updateUserPayCourse()
          },
          'fail': function (res) {

          },
          'complete': function (res) {

          }
        })
        that.getUserCourse()
        that.userInfoGet()
      }).catch(() => {
        this.setData({
          loading: false
        })
      })
  },
  updateUserPayCourse() {

    let id = this.data.id
    let userId = this.data.userId
    app.api.userEmpower({
      id: id,
      userId: userId
    }).then(res => {
      if (res.code === 200) {
        this.getEmpowerVideoDetail()
        this.setData({
          modalName: 'success'
        })
      }
    })
  },
})