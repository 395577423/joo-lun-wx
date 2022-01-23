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
    detail: null,
    courseId: null
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
    let courseId = e.currentTarget.dataset.courseid;
    this.setData({
      courseId: courseId
    })
    let userInfo = this.data.userInfo
    if (null == userInfo.headimgUrl) {
      this.getUserProfile(e)
    }  else {
      wx.navigateTo({
        url: '/pages/course/course-detail/index?courseId=' + courseId,
      })
    }
  },
  getUserProfile(e) {
    let courseId = this.data.courseId
    wx.getUserProfile({
      desc: '用于完善会员资料', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
      success: (detail) => {
        this.setData({
          detail: detail
        })
        app.api.wxUserSave(detail)
          .then(res => {
            let wxUser = res.data
              this.setData({
                wxUser: wxUser
              })
            app.globalData.wxUser = wxUser
            this.wxUserGet()
          })
      },
      fail: (e) => {
        wx.navigateTo({
          url: '/pages/course/course-detail/index?courseId=' + courseId,
        })
      }
    })
  },
  wxUserGet() {
    app.api.wxUserGet()
      .then(res => {
        this.setData({
          userInfo: res.data
        })
      })
  },
  
})