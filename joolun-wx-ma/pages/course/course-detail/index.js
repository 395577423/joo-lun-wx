// pages/course/course-detail/index.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    title: '',
    introduction: '',
    videoList: [],
    result: '生成学习报告',
    wxUser: null,
    //是否拥有课程
    isOwned: false,
    courseId: null,
    buttonStyle: 'cu-btn block line-blue margin-tb-sm lg',
    modalName: '',
    coverUrl: '',
    realPrice: null,
    audioUrl: '',
    question: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let wxUser = app.globalData.wxUser
    this.setData({
      wxUser: wxUser,
      courseId: options.courseId
    })
    let courseId = options.courseId
    app.initPage()
      .then(res => {
        this.getDetail(courseId)
        this.getUserCourse()
      })
  },

  getDetail(courseId) {
    app.api.courseDetail(courseId)
      .then(res => {
        console.log(res)
        let course = res.data.course
        let video = res.data.video
        this.setData({
          title: course.title,
          introduction: course.introduction,
          videoList: video,
          coverUrl: course.coverUrl,
          realPrice: course.realPrice,
          audioUrl: course.questionAudio,
          question: course.question
        })
        wx.setNavigationBarTitle({
          title: this.data.title,
        })
      })
  },
  getUserCourse() {
    let courseId = this.data.courseId
    let userId = this.data.wxUser.id
    app.api.getUserCourse(courseId, userId).then(res => {
      console.log('usercourse', res)
      if (undefined === res.data) {
        console.log('未购买课程')
      } else {
        console.log('已购买课程')
        this.setData({
          isOwned: true
        })
      }
    })
  },
  /**
   * 去视频列表页面
   * @param {*} e 
   */
  toVideoPage(e) {
    if (this.data.isOwned) {
      let videos = encodeURIComponent(JSON.stringify(this.data.videoList))
      console.log(this.data.videoList)
      wx.navigateTo({
        url: '/pages/course/course-video/index?videoList=' + videos + '&title=' + this.data.title
      })
    } else {
      this.showModal('NeedBuy')
    }
  },
  /**
   * 去答案页面
   * @param {*} e 课程ID 标题
   */
  toQuestionPage(e) {
    if (this.data.isOwned) {
      wx.navigateTo({
        url: '/pages/course/course-question/index?courseId=' + this.data.courseId + '&title=' + this.data.title
      })
    } else {
      this.showModal('NeedBuy')
    }
  },
  /**
   * 去音频页面
   * @param {*} e 
   */
  toAudioPage(e) {
    if (this.data.isOwned) {
      wx.navigateTo({
        url: '/pages/course/course-audio/index?courseId=' + this.data.courseId + '&title=' + this.data.title+'&question='+this.data.question+'&audioUrl='+this.data.audioUrl
      })
    } else {
      this.showModal('NeedBuy')
    }
  },
  toReportPage(e){
    if (this.data.isOwned) {
      wx.navigateTo({
        url: '/pages/course/course-report/index?courseId=' + this.data.courseId
      })
    } else {
      this.showModal('NeedBuy')
    }
  },
  toBuy() {
    this.showModal('Buy')
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
  }
})