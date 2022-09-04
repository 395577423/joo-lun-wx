const app = getApp()
Page({
  data: {
    orderInfo: {
    }
  },
  onLoad: function (options) {
    let that = this
    let orderId = options.id
    app.initPage()
      .then(res => {
        app.api.getActivityOrder(orderId).then(res => {
          let orderInfo = res.data
          let persons = orderInfo.persons
          let personNames = '';
          persons.forEach(function (person) {
            personNames = personNames + person.name + '  '
          });
          that.setData({
            orderInfo: orderInfo,
            persons: personNames
          })
        })

      })
  },
  unifiedOrder(e){
    let orderId = this.data.orderInfo.id
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

          }
        })
      }
    })
  }


})