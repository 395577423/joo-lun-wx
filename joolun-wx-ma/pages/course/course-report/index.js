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
    userAudio:null,
    showPainter:false,
    template:{}
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
  },
  share(){
    // wx.showLoading({
    //   title: '图片生成中',
    // })
    this.getPattle()
  },
  getPattle(){
    let _this = this
	  _this.setData({
	    template: {
	      width:"750rpx",
	      height:"1624rpx",
	      views:[
	         {
	            type: 'image',
	            url: "https://campaign.uglobal.com.cn/ikea/images/havemedal.jpg",
	            css: {
	              top: '0rpx',
	              left: '0px',
	              width: '750rpx',
	              height: '1624rpx'
	            }
	          },{
	            type: 'image',
	            url: _this.data.wxUser.headimgUrl,
	            css: {
	              top: '450rpx',
	              left: '270rpx',
	              width: '200rpx',
	              height: '200rpx',
	              borderRadius: '100rpx',
	              borderWidth: "10rpx",
				        borderColor: '#fed931'
	            }
	          }
	      ]
	    },
		showPainter:true
	  })
  }, onImgOK(e) {
    console.log("onImgOK")
    console.log(e.detail.path)
    this.setData({
      imgURL: e.detail.path
    })
  }

})