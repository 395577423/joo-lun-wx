// pages/course/course-audio/index.js
import __config from '../../../config/env'
const app = getApp()
const myaudio = wx.createInnerAudioContext()
const recorderManager = wx.getRecorderManager()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    courseId: null,
    title: '',
    question: '',
    audioUrl: '',
    wxUser: '',
    audioUrl: '',
    isRecorded:false,
    modalName:'',
    tempFilePath:'',
    userAudioUrl:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

    this.setData({
      courseId: options.courseId,
      title: options.title,
      question: options.question,
      audioUrl: options.audioUrl,
      wxUser: app.globalData.wxUser
    })
    wx.setNavigationBarTitle({
      title: this.data.title,
    })
    this.queryUserAudio()
    
  },
  queryUserAudio(){
    let courseId = this.data.courseId
    let userId = this.data.wxUser.id
    app.api.getUserAudio(courseId, userId).then(res => {
      console.log('useraudio', res)
      if (undefined === res.data) {
        console.log('未录音')
      } else {
        console.log('已录音')
        this.setData({
          userAudioUrl: res.data.audioUrl,
          isRecorded:true
        })
      }
    })
  },

  playAudio() {
    myaudio.src = this.data.audioUrl
    myaudio.play()
  },
  playMyAudio(){
    myaudio.src = this.data.userAudioUrl
    myaudio.play()
  },
  start(){
    this.setData({modalName:'record'})
    const options = {
      duration: 10000,//指定录音的时长，单位 ms
      sampleRate: 16000,//采样率
      numberOfChannels: 1,//录音通道数
      encodeBitRate: 96000,//编码码率
      format: 'mp3',//音频格式，有效值 aac/mp3
      frameSize: 50,//指定帧大小，单位 KB
    }
    //开始录音
    recorderManager.start(options);
    recorderManager.onStart(() => {
      console.log('recorder start')
    });
    //错误回调
    recorderManager.onError((res) => {
      console.log(res);
    })
  },
  stop(){
    let that = this
    this.setData({modalName:''})
    recorderManager.stop();
    recorderManager.onStop((res) => {
      this.tempFilePath = res.tempFilePath;
      console.log('停止录音', res.tempFilePath)
      const { tempFilePath } = res
      console.log('第三方session',getApp().globalData.thirdSession)
      wx.uploadFile({
        filePath: tempFilePath,
        name: 'file',
        url: __config.basePath+'/weixin/api/course/audio',
        header:{
          'Content-type':'multipart/form-data',
          'app-id': wx.getAccountInfoSync().miniProgram.appId,
          'third-session': getApp().globalData.thirdSession != null ? getApp().globalData.thirdSession : ''
        },
        formData:{
          'userId':that.data.wxUser.id,
          'courseId':that.data.courseId
        },
        success(res){
          console.log(res)
          that.setData({
            userAudioUrl:res.msg,
            isRecorded:true
          })
          let data = JSON.parse(res.data)
          myaudio.src = data.msg
        }
      })
    })
  },
  play(){
    myaudio.autoplay = true
    myaudio.src = this.tempFilePath,
    myaudio.onPlay(() => {
      console.log('开始播放')
    })
    myaudio.onError((res) => {
      console.log(res.errMsg)
      console.log(res.errCode)
    })
  }
})