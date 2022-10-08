// pages/user/user-commission/index.js
const app = getApp()
Page({
  /**
   * 页面的初始数据
   */
  data: {
    total: 0,
    completedAmount: 0,
    withdrawAmount: 0,
    tabs: ['我的团队', '返佣'],
    TabCur: 0,
    partners: [],
    vipCount: 0,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    app.initPage().then(res => {
      this.getCommission();
      this.getPartners();
    })

  },
  getCommission() {
    app.api.getCommission().then(resp => {
      if (resp.data) {
        this.setData({
          total: resp.data.totalAmount,
          completedAmount: resp.data.completedAmount,
          withdrawAmount: resp.data.withdrawAmount
        })
      }
    })
  },
  getPartners() {
    app.api.getPartners().then(resp => {
      let vipCount = 0;
      if (resp.data.length > 0) {
        resp.data.forEach(function (item) {
          if (item.vip) {
            vipCount++;
          }
        });
      }
      this.setData({
        partners: resp.data,
        vipCount: vipCount
      })
    })
  },
  tabSelect(e) {
    this.setData({
      TabCur: e.currentTarget.dataset.id,
    })
  }
})