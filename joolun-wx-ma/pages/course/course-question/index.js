// pages/course/course-question/index.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    //课程ID
    courseId: null,
    //课程标题
    title: '',
    //课程问题数组
    questions: [],
    //
    choiceShow: true,
    //答案样式
    answer: 'cuIcon-check lg text-white',
    //题目总数
    total: 0,
    //当前第几题
    index: 1,
    wxUser: null
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    //设定用户信息
    this.setData({
      wxUser: app.globalData.wxUser
    })

    const courseId = options.courseId
    const title = options.title
    this.setData({
      courseId: courseId,
      title: title
    })
    wx.setNavigationBarTitle({
      title: title,
    })
    app.initPage()
      .then(res => {
        this.getQuestion(this.data.courseId, this.data.wxUser.id)
      })
    //部分完成

    //全部完成

  },
  /**
   * 获取课程问题及答案
   * @param {*} courseId 课程ID
   * @param {*} userId 用户ID
   */
  getQuestion(courseId, userId) {
    app.api.getCourseQuestion(courseId, userId)
      .then(res => {
        this.setData({
          questions: res.data,
          total: res.data.length
        })
      })
  },
  /**
   * 滑动事件
   * @param {*} e 请求参数
   */
  changeItem(e) {
    const index = e.detail.current
    this.setData({
      index: index + 1
    })
  },
  chooseChoice(e) {
    let index = this.data.index
    let that = this
    //1.保存答案
    const choiceId = e.currentTarget.dataset.choiceid
    const questionId = e.currentTarget.dataset.questionid
    const choosedId = e.currentTarget.dataset.choosedid
    const choiceCorrect = e.currentTarget.dataset.choicecorrect

    if (null == choosedId) {
      const choice = {
        userId: this.data.wxUser.id,
        questionId: questionId,
        courseId: this.data.courseId,
        answerId: choiceId,
        correct: choiceCorrect == 1 ? 1 : 0
      }

      app.api.setUserChoice(choice).then(res => {
        this.getQuestion(this.data.courseId, this.data.wxUser.id)
        if (index < this.data.total) {
          setTimeout(()=>{
            that.setData({
              index: index + 1
            })
          },500)
        }
      })
    }else{
      if (index < this.data.total) {
        this.setData({
          index: index + 1
        })
      }
    }
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

  }
})