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
    this.getAuth()
  },
  queryUserAudio(){
    let courseId = this.data.courseId
    let userId = this.data.wxUser.id
    app.api.getUserAudio(courseId, userId).then(res => {
      if (undefined === res.data) {

      } else {

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

    });
    //错误回调
    recorderManager.onError((res) => {

    })
  },
  stop(){
    let that = this
    this.setData({modalName:''})
    recorderManager.stop();
    recorderManager.onStop((res) => {
      this.tempFilePath = res.tempFilePath;

      const { tempFilePath } = res

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

    })
    myaudio.onError((res) => {

    })
  },
  getAuth(){
    wx.authorize({
      scope: 'scope.record',
      success (res){

           // 用户已经同意小程序使用录音功能，后续调用 wx.startRecord 接口不会弹窗询问
          // wx.startRecord();
         recorderManager.start(options);//使用新版录音接口，可以获取录音文件
      },
      fail(){

        wx.showModal({
          title: '提示',
          content: '您未授权录音，功能将无法使用',
          showCancel: true,
          confirmText: "授权",
          confirmColor: "#AF1F25",  
          success(res){
            if (res.confirm){
                 //确认则打开设置页面（自动切到设置页）
                 wx.openSetting({ 
                  success: (res) => {

                    if (!res.authSetting['scope.record']) {
                      //未设置录音授权

                      wx.showModal({
                        title: '提示',
                        content: '您未授权录音，功能将无法使用',  // 可以自己编辑
                        showCancel: false,
                        success: function (res) {

                        },
                      })
                    } else {
                      //第二次才成功授权

                      recorderManager.start(options);
                    }
                  },
                  fail: function () {

                  }
                 })
            } else if (res.cancel){

            }
          } ,
          fail (){

          }
        })
      }
    })
  }
})