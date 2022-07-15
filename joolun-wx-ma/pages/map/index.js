// pages/map/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    //自定义标记点数组
    markers: [],
    //纬度
    latitude: '',
    //经度
    longitude: '',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    var that = this; 
    //  获取当前定位的经纬度信息
    wx.showLoading({
      title:"定位中",
      mask:true
    })
    wx.getLocation({
      type: 'gcj02',
      altitude:true,//高精度定位
      //定位成功，更新定位结果
      success: function (res) {
        var latitudee = res.latitude
        var longitudee = res.longitude
        that.setData({
          longitude:parseFloat(longitudee),
          latitude: parseFloat(latitudee),
        })
      },
      //定位失败回调
      fail:function(){
        wx.showToast({
          title:"定位失败",
          icon:"none"
        })
      },
      complete:function(){
        //隐藏定位中信息进度
        wx.hideLoading()
      }
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