// pages/user/user-commission/index.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    total:0,
    completedAmount:0,
    withdrawAmount:0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    this.getCommission();
  },
  getCommission() {
    app.api.getCommission().then(resp =>{
      if(resp.data){
        this.setData({
          total:resp.data.totalAmount,
          completedAmount:resp.data.completedAmount,
          withdrawAmount:resp.data.withdrawAmount
        })
      }
    })
  }
})