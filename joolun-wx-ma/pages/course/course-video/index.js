// pages/course/course-video/index.js
const util = require('../../../utils/util.js')
Page({

  /**
   * 页面的初始数据
   */
  data: {
    videoList: [],
    _index: null,
    titleType: '任务一：了解这本好书'
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
        titleType:'任务二：拓展百科知识'
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