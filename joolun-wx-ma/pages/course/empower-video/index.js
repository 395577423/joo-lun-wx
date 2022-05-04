// pages/course/empower-video/index.js
const WxParse = require('../../../public/wxParse/html2json.js')
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    videoList:[],
    name:'',
    search:'',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let userId = options.userId
    this.setData({
      userId:userId
    })
    let name = this.data.name
    app.initPage()
      .then(res => {
        this.wxUserGet()
        this.getEmpowerVideo(name)
      })
  },
  wxUserGet() {
    app.api.wxUserGet()
      .then(res => {
        this.setData({
          level: res.data.level
        })
      })
  }, 
  getEmpowerVideo(name){
    let userId = this.data.userId
    app.api.empowerVideo(userId,name)
      .then(res => {
        let result = res.data.records
        for (var i = 0; i < result.length; i++) {
          result[i]["introduction"]=WxParse.html2json(result[i]["introduction"], 'introduction');
        }
        this.setData({
          videoList: result,
          name:'',
          theNum:null
        })
      })
  },
  handleSearch(e){
    let name = this.data.name
    this.getEmpowerVideo(name)
  },
  handlewords(e){
    let name = e.detail.value
    this.setData({
      name:name
    })
  },
})