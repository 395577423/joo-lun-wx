/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，项目使用请保留此说明
 */
const app = getApp()
Page({
  data: {
    persons:[]
  },
  onLoad: function (options) {
    app.initPage()
    .then(() => {
      this.getPersons()
    })
  },
  getPersons() {
    app.api.getActitityPersons().then(res => {
      let persons = res.data;
      this.setData({
        persons:persons
      })
    })
  },
  edit(e) {
    let id = e.currentTarget.dataset.id
    let url = '/pages/activity/person/edit/index'
    if(id){
      url = url.concat('?id=')+id
    }
    wx.navigateTo({
      url: url,
    })
  },
  delete(e) {
    let id = e.currentTarget.dataset.id
    let index = e.currentTarget.dataset.index;
    let persons = this.data.persons;
    let that = this
    wx.showModal({
      cancelColor: 'cancelColor',
      content: '确认删除出行人？',
      success(res) {
        if (res.confirm) {
          app.api.deleteActivityPerson(id).then(res => {
            if(res.code == 200){
              persons.splice(index,1)
              that.setData({
                persons:persons
              })
            }
          })
        }
      }
    })


  },

// ListTouch触摸开始
ListTouchStart(e) {
  this.setData({
    ListTouchStart: e.touches[0].pageX
  })
},

// ListTouch计算方向
ListTouchMove(e) {
  this.setData({
    ListTouchDirection: e.touches[0].pageX - this.data.ListTouchStart > 0 ? 'right' : 'left'
  })
},

// ListTouch计算滚动
ListTouchEnd(e) {
  if (this.data.ListTouchDirection =='left'){
    this.setData({
      modalName: e.currentTarget.dataset.target
    })
  } else {
    this.setData({
      modalName: null
    })
  }
  this.setData({
    ListTouchDirection: null
  })
},
})