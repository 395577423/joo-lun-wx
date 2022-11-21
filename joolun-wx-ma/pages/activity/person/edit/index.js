/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，项目使用请保留此说明
 */
import WxValidate from '../../../../utils/wxValidate'
const app = getApp()
const uploadImage = require('../../../../js/uploadImg/uploadImg.js');
const logger = wx.getRealtimeLogManager()
Page({
  data: {
    person: {
      name: '',
      gender: '',
      identityNo: '',
      tel: ''
    },
    images: [],
    avatar:'',
    tips: '',
    maleChecked: false,
    femaleChecked: false,
    id: null
  },
  onLoad: function (options) {
    let id = options.id

    app.initPage()
      .then(() => {
        if (id) {
          this.getPerson(id);
        }
      })
    this.initValidate()
  },
  showModal(error) {
    wx.showModal({
      content: error.msg,
      showCancel: false,
    })
  },
  getPerson(personId) {
    this.setData({
      id: personId
    })
    app.api.getActivityPerson(personId).then(res => {
      let person = res.data
      let maleChecked = false
      let femaleChecked = false
      let image = []
      if (person.image) {
        image.push(person.image)
      }
      if (person.gender == 1) {
        maleChecked = true
      } else {
        femaleChecked = true
      }
      this.setData({
        person: person,
        images: image,
        maleChecked: maleChecked,
        femaleChecked: femaleChecked
      })
    })
  },
  ChooseImage() {
    wx.chooseMedia({
      count: 1, //默认9
      mediaType: ['image'],
      sizeType: ['compressed'], //可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album'], //从相册选择
      success: (res) => {
        let that = this;
        let imageList = [];
        wx.showLoading({
          title: '上传中',
        })
        uploadImage(res.tempFiles[0].tempFilePath, '',
          function (result) {
            imageList.push(res.tempFiles[0].tempFilePath);
            that.setData({
              images: imageList,
              avatar: result
            })
            wx.hideLoading();
          },
          function (result) {
            log.error('upload faild')
            logger.error(result)
            wx.hideLoading()
          }
        )
      }
    });
  },
  save(e) {
    const params = e.detail.value
    // 传入表单数据，调用验证方法
    if (!this.WxValidate.checkForm(params)) {
      const error = this.WxValidate.errorList[0]
      this.showModal(error)
      return false
    }
    params.image = this.data.avatar
    if(this.data.id){
      params.id = this.data.id
    }
    app.api.saveActivityPerson(params).then(res => {
      if(res.code == 200) {
        wx.redirectTo({
          url: '/pages/activity/person/index',
        })
      }
    })
  },
  initValidate() {
    // 验证字段的规则
    const rules = {
      name: {
        required: true,
      },
      tel: {
        required: true,
        tel: true,
      },
      identityNo: {
        idcard: true,
      },
      gender: {
        required: true,
      }

    }

    // 验证字段的提示信息，若不传则调用默认的信息
    const messages = {
      gender: {
        required: '请选择性别',
      },
      tel: {
        required: '请输入手机号',
        tel: '请输入正确的手机号',
      },
      idcard: {
        required: '请输入身份证号码',
        idcard: '请输入正确的身份证号码',
      },
      name: {
        required: '请输入姓名'
      },
    }

    // 创建实例对象
    this.WxValidate = new WxValidate(rules, messages)
  },
})