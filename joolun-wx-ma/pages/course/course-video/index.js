// pages/course/course-video/index.js
const util = require('../../../utils/util.js')
Page({

  /**
   * 页面的初始数据
   */
  data: {
    videoList: [],
    _index: null,
    titleType: '第6-7天 好书放映室'
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    const videoList = JSON.parse(decodeURIComponent(options.videoList))
    const title = options.title
    const type = options.type
    if(type == "2"){
      this.setData({
        titleType:'第8-11天 知识小课堂'
      })
    }
    wx.setNavigationBarTitle({
      title: title + '视频列表',
    })
    this.setData({
      videoList: videoList
    })
  },
  /**
   * 播放视频
   * @param {*} e 
   */
  play(e) {

  },
  videoPlay(e) {
    var _index = e.currentTarget.id
    this.setData({
      _index: _index
    })
  }
})