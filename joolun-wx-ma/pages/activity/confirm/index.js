/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，项目使用请保留此说明
 */
const app = getApp()
const util = require('../../../utils/util.js')
Page({
  data: {
    selectCaseIndex: 0,
    activityId: 0,
    activityContent: {},
    priceCases: [],
    minData: 1,
    maxData: 99,
    number: 1,
    date: '2022-08-25',
    persons: [],
    amount: 0,
    quantity: 1,
    remark: '',
    member: '0',
    level: 0
  },
  onLoad: function (options) {
    let wxUser = app.globalData.wxUser;
    var DATE = util.formatDate(new Date());
    this.setData({
      activityId: options.activityId,
      date: DATE,
      member: wxUser.member,
      level: wxUser.level
    })
    let activityId = options.activityId
    app.initPage()
      .then(() => {
        this.getDetail(activityId)
        this.getPersons();
        this.getPriceCases(activityId)
      })
  },
  getDetail(activityId) {
    app.api.getActivityDetail(activityId).then(res => {
      let activityContent = res.data
      this.setData({
        activityContent: activityContent,
      })

    })
  },
  getPriceCases(activityId) {
    app.api.getPriceCase(activityId).then(res => {
      let priceCases = res.data
      this.setData({
        priceCases: priceCases,
      })
      this.calAmount(this.data.quantity)
    })
  },
  getPersons() {
    app.api.getActitityPersons().then(res => {
      let persons = res.data;
      for (let i = 0, lenI = persons.length; i < lenI; ++i) {
        persons[i].checked = false;
      }
      this.setData({
        persons: persons
      })
    })
  },
  selectCase(e) {
    let index = e.currentTarget.dataset.index
    this.setData({
      selectCaseIndex: index
    })
  },
  selectPerson(e) {
    let index = e.currentTarget.dataset.index
    let persons = this.data.persons;
    persons[index].checked = !persons[index].checked
    this.setData({
      persons
    })
  },
  retrunResult(e) {
    let quantity = e.detail.number;
    this.calAmount(quantity)
  },
  calAmount(quantity) {
    let unitPrice = this.data.priceCases[this.data.selectCaseIndex].salesPrice;
    if(this.data.member=='1' && this.data.level==1){
      unitPrice = this.data.priceCases[this.data.selectCaseIndex].memberPrice;
    } else if (this.data.member=='1' && this.data.level==2){
      unitPrice = this.data.priceCases[this.data.selectCaseIndex].superMemberPrice;
    }
    let amount = quantity * unitPrice;
    this.setData({
      amount: amount,
      quantity: quantity
    })
  },
  dateChange(e) {
    this.setData({
      date: e.detail.value
    })
  },
  textareaAInput(e) {
    let remark = e.detail.value;
    this.setData({
      remark: remark
    })
  },
  showModal(error) {
    wx.showModal({
      content: error,
      showCancel: false,
    })
  },
  buy(e) {
    let that = this
    let reqData = {}
    reqData.activityId = this.data.activityContent.id;
    reqData.name = this.data.activityContent.name;
    reqData.activityImg = this.data.activityContent.imageUrl;
    reqData.activityDate = this.data.date;
    reqData.quantity = this.data.quantity;
    reqData.priceCase = JSON.stringify(this.data.priceCases[this.data.selectCaseIndex]);
    reqData.remark = this.data.remark
    reqData.persons = []
    this.data.persons.forEach(function (person) {
      if (person.checked) {
        reqData.persons.push(person);
      }
    })
    if (reqData.quantity < reqData.persons.length) {
      this.showModal('超出所需出行人总数,总共只需要' + reqData.quantity + '位出行人，目前已添加' + reqData.persons.length + '位')
      return false;
    } else if(reqData.quantity > reqData.persons.length) {
      this.showModal('缺少出行人,总共只需要' + reqData.quantity + '位出行人，目前已添加' + reqData.persons.length + '位')
      return false;
    }
    app.api.subActivityOrder(reqData).then(res => {
      if(res.code == 200) {
        let orderId = res.data.id;
        that.unifiedOrder({id:orderId});
      }
    })
  },
  unifiedOrder(orderInfo){
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