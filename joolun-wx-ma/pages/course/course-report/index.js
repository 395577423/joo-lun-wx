// pages/course/course-report/index.js
const app = getApp()
const myaudio = wx.createInnerAudioContext()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    wxUser:null,
    courseId:null,
    course:null,
    courseQuestion:[],
    story:[],
    userAudio:null
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log('课程ID',options.courseId)
    //设定用户信息
    this.setData({
      wxUser: app.globalData.wxUser,
      courseId:options.courseId
    })
    //获取用户报告
    this.getReport()
  },
  getReport(){
    app.api.getUserReport(this.data.courseId, this.data.wxUser.id).then(res => {
      this.setData({
        course:res.data.course,
        courseQuestion:res.data.courseQuestion,
        story:res.data.story,
        userAudio:res.data.userAudio
      })
    })
  },
  playMyAudio(){
    myaudio.src = this.data.userAudio.audioUrl
    myaudio.play()
  },
  playAudio(){
    myaudio.src = this.data.course.questionAudio
    myaudio.play()
  }

})