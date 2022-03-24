// pages/course/course-audio-list/index.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    list:[],
    title:'',
    courseId:null
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
      console.log(options) 
      const id = options.courseId
      const title = options.title
      this.setData({title:title,courseId:id})
      this.getCourseAudios(id)
  },

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

  },
  getCourseAudios(id){
      app.api.courseAudio(id)
          .then(res => {
              this.setData({
                list:res.data
              })
          })
  },
  toRecordPage(e){
    let audio = e.currentTarget.dataset.audio
    let question = e.currentTarget.dataset.question
    let audioId = e.currentTarget.dataset.id
    wx.navigateTo({
      url: '/pages/course/course-audio/index?courseId=' + this.data.courseId + '&title=' + this.data.title + '&question='  +question+ '&audioUrl=' +audio+'&audioId='+audioId
  })
  }
})