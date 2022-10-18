const app = getApp()
const WxParse = require('../../../public/wxParse/wxParse.js')
Page({

  /**
   * 页面的初始数据
   */
  data: {
    TabCur: 0,
    scrollLeft:0,
    courseId:null,
    activityList:[],
    modalName:'',
    //是否拥有课程
    isOwned: false,
    isMember: false,
    //奖学金计划范围内
    inTime: false,
    wxUser: null,
    audioUrl:null,
    question:null,
    guide:null
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    let courseId = options.courseId;
    this.setData({
      courseId:courseId
    })
    this.getActivityList(courseId)
    this.userInfoGet()
    this.getUserCourse()
    app.initPage()
      .then(() => {
        this.getDetail(courseId)
      })
      this.innerAudio = wx.createInnerAudioContext()  
  },
  userInfoGet() {
    app.api.wxUserGet()
      .then(res => {
        this.setData({
          wxUser: res.data,
          isMember: res.data.member == "1" ? true : false
        })
      })
  },
  getUserCourse() {
    let courseId = this.data.courseId
    let userId = app.globalData.wxUser.id
    app.api.getUserCourse(courseId, userId).then(res => {
      if (undefined === res.data) {} else {
        this.setData({
          isOwned: true
        })
      }
    })
  },
  getDetail(courseId) {
    let that = this
    app.api.courseDetail(courseId)
      .then(res => {
        let course = res.data.course
        let video = res.data.video
        let introduction = course.introduction
        let clazz = res.data.clazz
        let guide = res.data.guide

        //当前日期比较开始日期
        let result = that.compareDate(new Date(), course.startTime)
        //当前日期比较结束日期
        let result2 = that.compareDate(new Date(), course.endTime)

        //如果大于等于开始日期，并且小于结束日期，则为有效
        console.log('开始日期大于等于当前日期？' + result)
        console.log('当前日期大于等于结束日期？' + result2)
        if ((result == 1 || result == 2) && (result2 == 2 || result2 == 3)) {
          this.setData({
            inTime: true
          })
        }
        let realPrice
        if (null !== course.rates &&  course.rates > 0) {
          realPrice = course.rates
        } else {
          realPrice = course.price
        }

        this.setData({
          course: course,
          title: course.title,
          videoList: video,
          videoList2: clazz,
          coverUrl: course.coverUrl,
          realPrice: realPrice,
          audioUrl: course.questionAudio,
          question: course.question,
          guide:guide
        })
        wx.setNavigationBarTitle({
          title: this.data.title,
        })
        WxParse.wxParse('introduction', 'html', introduction, this, 0)
      })
  },
  /**
   * 判断日期1是否大于日期2，只到年月日
   * @param {*} date1 
   * @param {*} date2 
   */
  compareDate(date10, date2) {

    let date1 = this.formatDate(date10)

    if (new Date(date1) > new Date(date2)) {
      return 1;
    }
    if (new Date(date1) == new Date(date2)) {
      return 2;
    } else {
      return 3;
    }
  },

  formatDate(d) {
    let month = '' + (d.getMonth() + 1)
    let day = '' + d.getDate()
    let year = d.getFullYear()
    if (month.length < 2) {
      month = '0' + month
    }
    if (day.length < 2) {
      day = '0' + day
    }
    return [year, month, day].join('-')
  },

   /**
   * 去音频页面
   * @param {*} e
   */
  toAudioPage(e) {
    if (this.data.isOwned || this.data.isMember || this.data.inTime) {
      wx.navigateTo({
        url: '/pages/course/course-audio-list/index?courseId=' + this.data.courseId + '&title=' + this.data.title + '&question=' + this.data.question + '&audioUrl=' + this.data.audioUrl
      })
    } else {
      this.showModal('NeedBuy')
    }
  },

  /**
   * 播放课程音频
   */
  playAudio(e) {
    let index = e.currentTarget.dataset.id
    let guide = this.data.guide[index]
    let url = guide.audio
    this.innerAudio.stop()
    this.innerAudio.src = url
    this.innerAudio.play()
  },

  toVideoPage(e){
    let index = e.currentTarget.dataset.id
    let video = this.data.videoList[index]
    let url = video.videoUrl
    wx.navigateTo({
      url:'/pages/course/def-video/index?url=' + url,
    })
  },

   /**
   * 去答案页面
   * @param {*} e 课程ID 标题
   */
  toQuestionPage(e) {
    if (this.data.isOwned || this.data.isMember || this.data.inTime) {
      wx.navigateTo({
        url: '/pages/course/course-question/index?courseId=' + this.data.courseId + '&title=' + this.data.title
      })
    } else {
      this.showModal('NeedBuy')
    }
  },
  /**
   * 导读数据
   */


   /**
    * 伴读数据
    */

    /**
     * 悟读数据
     */

     /**
      * 研读数据
      */
     getActivityList(courseId){
      app.api.getRelationActivity(courseId).then(res => {
        let activityList = res.data
        activityList.map((item) => {
          if (item.createTime) item.createTime = this.dateFormat(new Date(item.createTime), 'yyyy-MM-dd')
        })
        this.setData({
          activityList : activityList
        })
      })
     },

     toActivity(e){
       let activityId = e.currentTarget.dataset.id;
       wx.navigateTo({
        url:'/pages/activity/detail/index?activityId=' +activityId
      })
     },

     toBuy() {
      let wxUser = this.data.wxUser
      if (null == wxUser.phone) {
        this.showModal('getPhone')
        return
      }
      this.buy()
    },
    buy() {
      let plan = this.data.course.plan
      if (plan == 1) {
        let startTime = this.data.course.startTime
        let endTime = this.data.course.endTime
  
        if (this.compareDate(new Date(), new Date(endTime))) {
          this.setData({
            tip: '已经过了报名时间'
          })
          this.showModal('tip')
          return
        }
        if (this.compareDate(new Date(startTime), new Date())) {
          this.setData({
            tip: '还没到报名时间'
          })
          this.showModal('tip')
          return
        }
      }
      this.showModal('Buy')
    },

    toReportPage(e) {
      console.log(e)
      if (this.data.isOwned || this.data.isMember || this.data.inTime) {
        wx.navigateTo({
          url: '/pages/course/course-report/index?courseId=' + this.data.courseId
        })
      } else {
        this.showModal('NeedBuy')
      }
    },

    
  showModal(name) {
    this.setData({
      modalName: name
    })
  },
  hideModal(e) {
    let modelName = this.data.modalName
    console.log(modelName)
    if ('getPhone' === modelName) {
      this.buy()
    } else {
      this.setData({
        modalName: null
      })
    }
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  },
  tabSelect(e) {
    this.setData({
      TabCur: e.currentTarget.dataset.id,
      scrollLeft: (e.currentTarget.dataset.id-1)*60
    })
  },
  dateFormat(date, fmt) { // author: meizz
    var o = {
      'M+': date.getMonth() + 1, // 月份
      'd+': date.getDate(), // 日
      'H+': date.getHours(), // 小时
      'm+': date.getMinutes(), // 分
      's+': date.getSeconds(), // 秒
      'q+': Math.floor((date.getMonth() + 3) / 3), // 季度
      S: date.getMilliseconds() // 毫秒
    }
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length))
    for (var k in o) { if (new RegExp('(' + k + ')').test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length))) }
    return fmt
  },
})