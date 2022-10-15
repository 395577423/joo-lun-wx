const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    activityCategory: [],
    gridCol:4,
    hotActivity:[]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    app.initPage()
      .then(res => {
        this.getActivityCategory()
        this.getHotActivity()
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

  },
  getActivityCategory() {
    app.api.getActivityCategory()
      .then(res => {
        let activityCategory = res.data
        this.setData({
          activityCategory: activityCategory
        })
      })
  },
  searchActivity(e) {
    wx.navigateTo({
      url: '/pages/activity/list/index?activityName='+e.detail.value,
    })
  },
  getHotActivity() {
    let reqParams = {};
    reqParams.hot = 1;
    app.api.getActivityList(reqParams,1,20).then(res => {
      const activityList = res.data.records;
      this.setData({
        hotActivity: activityList
      })
    })
  }
})