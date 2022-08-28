const util = require('../../../../utils/util.js')
const app = getApp()

Page({
  data:{
    tabCur: 0,
    orderStatus: [
      {
        value: '全部订单',
        key: ''
      },
      {
        value: '待支付',
        key: '0'
      }, {
        value: '待完成',
        key: '1'
      }, {
        value: '已完成',
        key: '2'
      }
    ],
    page: {
      searchCount: false,
      current: 1,
      size: 10
    },
    parameter: {},
    loadmore: true,
    orderList: []
  },
  onLoad: function (options) {
    let that = this
    if (options.status) {
      this.setData({
        ['parameter.status']: options.status
      })
      this.data.orderStatus.forEach(function (status, index) {
        if (status.key == options.status){
          that.setData({
            tabCur: index
          })
        }
      })
    }
    app.initPage()
      .then(res => {
        this.orderPage()
      })
  },
  onPullDownRefresh() {
    // 显示顶部刷新图标
    wx.showNavigationBarLoading()
    this.refresh()
    // 隐藏导航栏加载框
    wx.hideNavigationBarLoading()
    // 停止下拉动作
    wx.stopPullDownRefresh()
  },
  onReachBottom() {
    if (this.data.loadmore) {
      this.setData({
        ['page.current']: this.data.page.current + 1
      })
      this.orderPage()
    }
  },
  orderPage() {
    app.api.listActivityOrder(Object.assign(
      {},
      this.data.page,
      util.filterForm(this.data.parameter)
    ))
      .then(res => {
        let orderList = res.data.records
        this.setData({
          orderList: [...this.data.orderList, ...orderList]
        })
        if (orderList.length < this.data.page.size) {
          this.setData({
            loadmore: false
          })
        }
      })
  },
  orderCancel(e) {
    let id = e.currentTarget.dataset.id
    let index = e.currentTarget.dataset.index
    app.api.cancelActivityOrder(id)
      .then(res => {
        this.data.orderList[index] = res.data
        this.setData({
          orderList: this.data.orderList
        })
      })
  },
  orderDel(e) {
    let id = e.currentTarget.dataset.id
    let index = e.currentTarget.dataset.index
    app.api.deleteActivityOrder(id)
    .then(res => {
      if(res.code == 200) {
        this.data.orderList.splice(index, 1)
        this.setData({
          orderList: this.data.orderList
        })
      }
    })

  },
  tabSelect(e) {
    let dataset = e.currentTarget.dataset
    if (dataset.index != this.data.tabCur){
      this.setData({
        tabCur: dataset.index,
        ['parameter.status']: dataset.key
      })
      this.refresh()
    }
  },
  refresh(){
    this.setData({
      loadmore: true,
      orderList: [],
      ['page.current']: 1
    })
    this.orderPage()
  },
  unifiedOrder(e){
    let orderId = e.currentTarget.dataset.id
    let orderInfo = {id:orderId}
    app.api.unifiedActivityOrder(orderInfo).then(res => {
      if(res.data){
        let payData = res.data
        wx.requestPayment({
          'timeStamp': payData.timeStamp,
          'nonceStr': payData.nonceStr,
          'package': payData.packageValue,
          'signType': payData.signType,
          'paySign': payData.paySign,
          'success': function (res) {
            wx.redirectTo({
              url: '/pages/activity/order/list/index',
            })
          },
          'fail': function (res) {

          },
          'complete': function (res) {
            wx.redirectTo({
              url: '/pages/activity/order/detail/index?id=' + orderInfo.id,
            })
          }
        })
      }
    })
  }
})