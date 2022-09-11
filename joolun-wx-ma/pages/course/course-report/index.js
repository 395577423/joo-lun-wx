// pages/course/course-report/index.js
const app = getApp()
const myaudio = wx.createInnerAudioContext()
const WxParse = require('../../../public/wxParse/wxParse.js')
import Report from './report.js'


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
    userAudio: [],
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
    loadImagePath: '',
    msg: '',
    title: '',
    bookName: '',
    show: false
  },

  userInfoGet() {
    app.api.wxUserGet()
      .then(res => {
        this.setData({
          wxUser: res.data
        })
      })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.widget = this.selectComponent('.widget')
    this.userInfoGet()
    //设定用户信息
    this.setData({
      courseId: options.courseId
    })
    this.getReport()
    app.initPage()
      .then(res => {
        //获取用户报告
        //获取相册读取权限
        this.getAuth()
      })
  },
  onReady() {

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

                }
              })
            } else if (res.cancel) {

            }
          },
          fail() {

          }
        })
      }
    })
  },
  getReport() {
    console.log(app.globalData.wxUser.id)
    console.log(this.data.courseId)
    app.api.getUserReport(this.data.courseId, app.globalData.wxUser.id).then(res => {
      this.setData({
        course: res.data.course,
        courseQuestion: res.data.courseQuestion,
        story: res.data.story,
        userAudio: res.data.userAudio,
        userCourseReport: res.data.userCourse == null ? '' : res.data.userCourse.report,
        title: res.data.course.title,
        bookName: res.data.books[0].title
      })
      WxParse.wxParse('essence', 'html', res.data.course.essence, this, 0)
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

  playMyAudio(e) {
    const url = e.currentTarget.dataset.useraudio
    myaudio.src = url
    myaudio.play()
  },
  playAudio(e) {
    const url = e.currentTarget.dataset.courseaudio
    myaudio.src = url
    myaudio.play()
  },

  getUserMoney() {
    let id = this.data.courseId
    let userId = this.data.wxUser.id
    app.api.userMoney(id, userId).then(res => {
      console.log(res)
      let msg = res.msg
      this.setData({
        modalName: 'saveImage',
        msg: msg
      })
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
  share() {
    this.getDraw();
  },
  onClose() {
    this.setData({
      show: false,
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
      let that = this
      let nickName = that.data.wxUser.nickName
      let title = that.data.bookName
      let totalStar = that.data.totalStar
      let wxmaCode = app.globalData.config.basePath + "/weixin/api/activity/image/wxm/code?page=pages/home/index&param=" + encodeURIComponent(app.globalData.wxUser.id)
      let params = {
        qrcode: wxmaCode,
        nickName: nickName,
        title: title
      }
      let plate = new Report().palette(params)

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
  }
})