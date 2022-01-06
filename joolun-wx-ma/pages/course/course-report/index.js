// pages/course/course-report/index.js
const app = getApp()
const myaudio = wx.createInnerAudioContext()
let {
  wxml,
  style
} = require('./report.js')

Page({
  /**
   * 页面的初始数据
   */
  data: {
    wxUser: null,
    courseId: null,
    course: null,
    courseQuestion: [],
    story: [],
    userAudio: null,
    showPainter: false,
    template: {},
    winHeight: null,
    modalName: '',
    //所获得星星数量
    totalStar: 0,
    userCourseReport: '',
    reportUrl: '',
    src: '',
    container: null,
    loadImagePath:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.widget = this.selectComponent('.widget')

    //设定用户信息
    this.setData({
      wxUser: app.globalData.wxUser,
      courseId: options.courseId
    })

    //获取用户报告
    this.getReport()

    //获取相册读取权限
    this.getAuth()

    //获取用户报告图片

  },
  getAuth() {
    wx.authorize({
      scope: 'scope.writePhotosAlbum',
      success(res) {},
      fail() {
        wx.showModal({
          title: '提示',
          content: '请您未授权文件保存,授权保存文件分享照片后能得到返现哦~',
          showCancel: true,
          confirmText: "授权",
          confirmColor: "#AF1F25",
          success(res) {
            if (res.confirm) {
              //确认则打开设置页面（自动切到设置页）
              wx.openSetting({
                success: (res) => {
                  if (!res.authSetting['scope.writePhotosAlbum']) {
                    wx.showModal({
                      title: '提示',
                      content: '您未授权保存文件将无法获得返现哦', // 可以自己编辑
                      showCancel: false,
                      success: function (res) {},
                    })
                  }
                },
                fail: function () {
                  console.log("授权保存文件失败");
                }
              })
            } else if (res.cancel) {
              console.log("cancel");
            }
          },
          fail() {
            console.log("openfail");
          }
        })
      }
    })
  },
  getReport() {
    app.api.getUserReport(this.data.courseId, this.data.wxUser.id).then(res => {
      this.setData({
        course: res.data.course,
        courseQuestion: res.data.courseQuestion,
        story: res.data.story,
        userAudio: res.data.userAudio,
        userCourseReport: res.data.userCourse.report
      })
    })
    let questions = this.data.courseQuestion
    let total = questions.length
    let count = 0
    for (var i = 0; i < total; i++) {
      if (questions[i].correct) {
        count++
      }
    }
    this.setData({
      totalStar: 15 + count
    })
  },

  playMyAudio() {
    myaudio.src = this.data.userAudio.audioUrl
    myaudio.play()
  },
  playAudio() {
    myaudio.src = this.data.course.questionAudio
    myaudio.play()
  },
  share() {
    wxml = wxml.replace('userNameText', this.data.wxUser.nickName)
      .replace('challengeText', '4')
      .replace('bookText', this.data.course.title)
      .replace('starText', this.data.totalStar)
      .replace('durationText', '5分钟')
    const p1 = this.widget.renderToCanvas({
      wxml,
      style
    })
    p1.then((res) => {
      console.log('container', res.layoutBox)
      this.container = res
    })
    wx.showLoading({
      title: '使劲生成ing.....',
    })
    setTimeout(this.extraImage,2000)
  },
  extraImage() {
    const p2 = this.widget.canvasToTempFilePath()
    p2.then(res => {
      this.setData({modalName:'share'})
      wx.hideLoading()
      this.setData({
        src: res.tempFilePath,
        width: this.container.layoutBox.width,
        height: this.container.layoutBox.height
      })
    })
  },
  saveImage() {
    wx.saveImageToPhotosAlbum({
      filePath: this.data.src,
    })
  },

  onImgOK(e) {
    this.setData({
      imgURL: e.detail.path
    })
  },
  hideModal(e) {
    this.setData({
      modalName: null
    })
  },
  hideModal(e) {
    this.setData({
      modalName: null
    })
  }
})