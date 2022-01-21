const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    config: app.globalData.config,
    recommends: [],
    version: '一',
    dataILu: true,
    page: {
      current: 0,
      size: 10,
      ascs: '', //升序字段
      descs: ''
    },
    winHeight: null,
    courses: [],
    userInfo: null,
    modalName: '',
    detail:null
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad() {
    app.initPage()
      .then(res => {
        this.getPlanCourse()
        this.getRecommend()
        this.wxUserGet()
      })
  },
  /**
   * 滑到底部事件
   */
  onReachBottom() {
    this.getRecommend()
  },
  /**
   * 获取奖学金计划课程
   */
  getPlanCourse() {
    app.api.planCourse()
      .then(res => {
        let result = res.data
        this.setData({
          courses: result
        })
      })
  },
  /**
   * 获取推荐课程
   */
  getRecommend() {
    if (!this.data.dataILu) return
    this.data.page.current++
    app.api.getRecommends(this.data.page)
      .then(res => {
        if (res.code == 200) {
          const records = [...this.data.recommends, ...res.data.records]
          const total = res.data.total
          this.setData({
            recommends: records,
            dataILu: records.length < total
          })
        }
      })
    wx.hideLoading({
      success: (res) => {},
    })
  },
  /**
   * 去课程详情页面
   * @param {*} e 课程ID
   */
  toDetail(e) {

    let userInfo = this.data.userInfo
    if (null == userInfo.headimgUrl) {
      this.getUserProfile(e)
    } else if (null == userInfo.getPhoneNumber) {
      this.setData({
        modalName: 'getPhone'
      })
    } else {
      let courseId = e.currentTarget.dataset.courseid
      wx.navigateTo({
        url: '/pages/course/course-detail/index?courseId=' + courseId,
      })
    }
  },
  getUserProfile(e) {

    wx.getUserProfile({
      desc: '用于完善会员资料', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
      success: (detail) => {
        console.log(detail)
        this.setData({
          detail:detail
        })
        app.api.wxUserSave(detail)
          .then(res => {
            let wxUser = res.data
            this.setData({
              wxUser: wxUser,
              modalName: 'getPhone'
            })
            app.globalData.wxUser = wxUser
            this.wxUserGet()
          })
      }
    })

  },
  wxUserGet() {
    app.api.wxUserGet()
      .then(res => {
        console.log(res)
        this.setData({
          userInfo: res.data
        })
      })
  },
  getPhoneNumber(e) {
    if (e.detail.iv) {
      server.post(api.user.bindWXPhoneNumber(), {
        encrypted_data: e.detail.encryptedData,
        encrypt_iv: e.detail.iv
      }, data => {
        wx.showToast({
          title: '绑定成功'
        })
        // do something
      }, error => {
        wx.showModal({
          title: '绑定失败',
          content: '[服务端返回的错误信息]',
          showCancel: false,
          success: res => {
            if (res.confirm) { // 用户确认后
              // do something
            }
          }
        })
      })
    } else { // 用户拒绝授权
      // do something
    }
  },
  hideModal(e) {
    this.setData({
      modalName: ''
    })
  }
})