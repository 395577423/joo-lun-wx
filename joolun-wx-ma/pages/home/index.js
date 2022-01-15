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
    winHeight:null,
    courses:[]
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad() {
    app.initPage()
      .then(res => {
        this.getPlanCourse()
        this.getRecommend()
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
        console.log(res)
        let result = res.data
        this.setData({
          courses:result
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
    let courseId = e.currentTarget.dataset.courseid
    wx.navigateTo({
      url: '/pages/course/course-detail/index?courseId=' + courseId,
    })
  }
})