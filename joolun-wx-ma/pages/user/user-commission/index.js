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
    showModal: false,
    records: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    app.initPage().then(res => {
      this.getCommission();
      this.getPartners();
      this.listRecord();
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
  getBankInfo() {
    app.api.getUserBankInfo().then(resp => {
      if (resp.data) {
        let bankAccountNo = resp.data.bankAccountNo
        let bankName = resp.data.bankName
        this.setData({
          bankName: bankName,
          bankAccount: bankAccountNo
        })
      }
    })
  },
  listRecord() {
    app.api.listWithdrawApplyRecord().then(resp => {
      if (resp.data) {
        this.setData({
          records: resp.data
        })
      }
    })
  },
  tabSelect(e) {
    this.setData({
      TabCur: e.currentTarget.dataset.id,
    })
  },
  showModal(e) {
    this.getBankInfo()
    this.setData({
      showModal: true
    })

  },
  hideModal() {
    this.setData({
      showModal: false
    })
  },
  save(e) {
    let data = e.detail.value;
    if (data.bankAccountNo == null || data.bankAccountNo == '') {
      return false
    }
    if (data.bankName == null || data.bankName == '') {
      return false
    }
    data.amount = this.data.completedAmount - this.data.withdrawAmount
    app.api.saveWithdrawApplyRecord(data).then(resp => {
      if (resp.code == 200) {
        this.hideModal()
      }
    })
  }
})