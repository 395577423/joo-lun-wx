// pages/course/course-audio/index.js
import __config from '../../../config/env'
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    courseId: null,
    audioId: null,
    title: '',
    question: '',
    wxUser: '',
    courseAudio: '',
    isRecorded: false,
    modalName: '',
    userAudio: '',
    isPlayingCourse: false,
    isPlayingUser: false,
    tempFilePath:'',
    auth:false,
    coursePlaying:false,
    userPlaying:false,
    isReplyed:false,
    courseReply:null
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      courseId: options.courseId,
      title: options.title,
      question: options.question,
      courseAudio: options.audioUrl,
      audioId: options.audioId,
      wxUser: app.globalData.wxUser
    })
    wx.setNavigationBarTitle({
      title: this.data.title,
    })
    this.queryUserAudio()
    this.getReply()
    this.getAuth()
    this.innerAudio = wx.createInnerAudioContext()

    let that = this
    this.innerAudio.onEnded(function(){
      that.setData({
        coursePlaying:false,
        userPlaying:false
      })
    })
  },

  onReady(){

  },
  
  getReply(){
    let courseId = this.data.courseId
    let userId = this.data.wxUser.id
    let audioId = this.data.audioId
    app.api.getReply(courseId,userId,audioId).then(res =>{
      this.setData({
        courseReply:res.data
      })
    })
  },
  queryUserAudio() {
    let courseId = this.data.courseId
    let userId = this.data.wxUser.id
    let audioId = this.data.audioId
    app.api.getUserAudio(courseId, userId, audioId).then(res => {
      if (undefined === res.data) {
      } else {
        this.setData({
          userAudio: res.data.audioUrl,
          isRecorded: true
        })
        if(res.data.replyId != null){
          this.setData({
            isReplyed: true
          })
        }
      }
    })
  },
  /**
   * 播放课程音频
   */
  playAudio(e) {
    let url = ''
    let type = e.currentTarget.dataset.type
    if(type == 'course'){
      url = this.data.courseAudio
      this.setData({
        coursePlaying:true,
        userPlaying:false
      })
    }else if(type == 'user'){
      url = this.data.userAudio
      this.setData({
        coursePlaying:false,
        userPlaying:true
      })
    }else{
      wx.showModal({
        title: '提示',
        content: '系统异常，请联系管理员', // 可以自己编辑
        showCancel: true,
        success: function (res) {},
      })
      return
    }
    this.innerAudio.stop()
    this.innerAudio.src = url
    this.innerAudio.play()
  },

  stopAudio(e){
    let type = e.currentTarget.dataset.type
    if(type == 'course'){
      this.setData({
        coursePlaying:false
      })
    }else if(type == 'user'){
      this.setData({
        userPlaying:false
      })
    }
    this.innerAudio.stop()
  },

  start() {
    let auth = this.data.auth
    if(!auth){
      wx.showModal({
        title: '提示',
        content: '您未授权录音，功能将无法使用', // 可以自己编辑
        showCancel: false,
        success: function (res) {},
      })
      return
    }
    let recorderManager = wx.getRecorderManager()
    this.setData({
      modalName: 'record'
    })
    const options = {
      duration: 60000, //指定录音的时长，单位 ms
      sampleRate: 44100, //采样率
      numberOfChannels: 1, //录音通道数
      encodeBitRate: 96000, //编码码率
      format: 'aac', //音频格式，有效值 aac/mp3
      frameSize: 50, //指定帧大小，单位 KB
    }
    //开始录音
    recorderManager.start(options)
  },

  stop() {
    let recorderManager = wx.getRecorderManager()
    recorderManager.stop();
    let that = this
    this.setData({
      modalName: ''
    })
    recorderManager.onStop((res) => {
      // this.tempFilePath = res.tempFilePath;
      let {
        tempFilePath
      } = res
      wx.uploadFile({
        filePath: tempFilePath,
        name: 'file',
        url: __config.basePath + '/weixin/api/course/audio',
        header: {
          'Content-type': 'multipart/form-data',
          'app-id': wx.getAccountInfoSync().miniProgram.appId,
          'third-session': getApp().globalData.thirdSession != null ? getApp().globalData.thirdSession : ''
        },
        formData: {
          'userId': that.data.wxUser.id,
          'courseId': that.data.courseId,
          'audioId': that.data.audioId,
          'replyId':that.data.courseReply.id
        },
        success(res) {
          let result = JSON.parse(res.data)
          that.setData({
            userAudio: result.msg,
            isRecorded: true
          })
          that.queryUserAudio()
        },
        fail(){
        }
      })
    })
  },
  getAuth() {
    let that = this
    wx.authorize({
      scope: 'scope.record',
      success(res) {
        that.setData({
          auth:true
        })
      },
      fail() {
        wx.showModal({
          title: '提示',
          content: '您未授权录音，功能将无法使用',
          showCancel: true,
          confirmText: "授权",
          confirmColor: "#AF1F25",
          success(res) {
            if (res.confirm) {
              //确认则打开设置页面（自动切到设置页）
              wx.openSetting({
                success: (res) => {
                  if (!res.authSetting['scope.record']) {
                    //未设置录音授权
                    wx.showModal({
                      title: '提示',
                      content: '您未授权录音，功能将无法使用', // 可以自己编辑
                      showCancel: false,
                      success: function (res) {

                      },
                    })
                  } else {
                    //第二次才成功授权
                    // recorderManager.start(options);
                    that.setData({
                      auth:true
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
  }
})