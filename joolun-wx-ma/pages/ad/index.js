const WxParse = require('../../public/wxParse/html2json.js')
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    ad: null
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    console.log(options)
    let id = options.id
    this.getAd(id)
  },

  getAd(id) {
    app.api.getAd(id)
      .then(res => {
        let result = res.data
        result.content=WxParse.html2json(result.content, 'introduction');
        this.setData({
          ad:result
        })
      })
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

  }
})