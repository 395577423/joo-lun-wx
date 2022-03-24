// pages/course/course-audio/index.js
import __config from '../../../config/env'
const app = getApp()
const myAudioManager = wx.createInnerAudioContext()
const courseManager = wx.createInnerAudioContext()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    courseId: null,
    audioId: null,
    title: '',
    question: '',
    audioUrl: '',
    wxUser: '',
    courseAudio: '',
    isRecorded: false,
    modalName: '',
    userAudioUrl: '',
    isPlayingCourse: false,
    isPlayingUser: false,
    tempFilePath:'',
    auth:false,
    log:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let that = this
    myAudioManager.onEnded(function(){
      myAudioManager.stop()
      that.setData({
        isPlayingUser: false
      })
    })

    courseManager.onEnded(function(){
      courseManager.stop()
      that.setData({
        isPlayingCourse: false
      })
    })

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
    this.getAuth()
  },

  onReady(){
    this.setData({
      log:this.data.log
    })
  },
  queryUserAudio() {
    let courseId = this.data.courseId
    let userId = this.data.wxUser.id
    let audioId = this.data.audioId
    console.log('audioId :'+ audioId)
    app.api.getUserAudio(courseId, userId, audioId).then(res => {
      if (undefined === res.data) {

      } else {

        this.setData({
          userAudioUrl: res.data.audioUrl,
          isRecorded: true,
          log:'查询用户音频成功,音频路径为:'+res.data.audioUrl+'\n'
        })
      }
    })
  },
  playCourseAudio() {
    let isPlayingCourse = this.data.isPlayingCourse
    let isPlayingUser = this.data.isPlayingUser

    let log = this.data.log
    if(isPlayingUser){
      myAudioManager.stop()
    }
    if (isPlayingCourse) {
      courseManager.stop()
      this.setData({
        isPlayingCourse: false,
        log:log+'停止播放课程音频\n'
      })
    } else {
      myAudioManager.stop()
      courseManager.src = this.data.courseAudio
      courseManager.play()
      this.setData({
        isPlayingCourse: true,
        log:log+'开始播放课程音频\n'
      })
    }
  },

  playMyAudio() {
    let isPlayingUser = this.data.isPlayingUser
    let isPlayingCourse = this.data.isPlayingCourse
    let log = this.data.log
    if(isPlayingCourse){
      courseManager.stop()
    }
    if (isPlayingUser) {
      myAudioManager.stop()
      this.setData({
        isPlayingUser: false,
        log:log+'停止播放用户音频\n'
      })
    } else {
      courseManager.stop()
      myAudioManager.src = this.data.userAudioUrl
      myAudioManager.play()
      this.setData({
        isPlayingUser: true,
        log:log+'开始播放用户音频\n'
      })

    }
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

    let log = this.data.log
    this.setData({
      log:log+'开始录音\n'
    })
  },

  stop() {
    let recorderManager = wx.getRecorderManager()
    recorderManager.stop();
    let log = this.data.log
    this.setData({
      log:log+'结束录音'
    })

    let that = this
    this.setData({
      modalName: ''
    })
    recorderManager.onStop((res) => {
      // this.tempFilePath = res.tempFilePath;
      let {
        tempFilePath
      } = res
      log = that.data.log
      that.setData({
        log:log+'结束录音,录音临时文件目录为:'+tempFilePath+'\n'
      })

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
          'audioId': that.data.audioId
        },
        success(res) {
          let result = JSON.parse(res.data)
          log = that.data.log
          that.setData({
            log:log+'成功上传录音文件,后台返回数据为:'+res.data+'\n'
          })
          that.setData({
            userAudioUrl: result.msg,
            isRecorded: true
          })
          let data = JSON.parse(res.data)
          myAudioManager.src = data.msg
        },
        fail(res){
          log = that.data.log
          that.setData({
            log:log+'录音上传失败,'+res+'\n'+JSON.stringify(res)
          })
        }
      })
    })
  },
  getAuth() {
    let that = this
    let log = this.data.log
    wx.authorize({
      scope: 'scope.record',
      success(res) {
        that.setData({
          auth:true,
          log:log+'获取录音权限成功\n'
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