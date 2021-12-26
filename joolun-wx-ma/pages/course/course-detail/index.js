// pages/course/course-detail/index.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    title:'',
    introduction:'',
    videoList:[],
    result:'生成学习报告'
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let courseId = options.courseId
    console.log(courseId)
    app.initPage()
    .then(res => {
      this.getDetail(courseId)
      this.getUserCourse()
    })
  },

  getDetail(courseId){
    app.api.courseDetail(courseId)
      .then(res => {
        console.log(res)
        let course = res.data.course
        let video = res.data.video
        this.setData({
          title: course.title,
          introduction:course.introduction,
          videoList:video
        })
      })
  },
  getUserCourse(){},

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})