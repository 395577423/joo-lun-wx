// pages/course/empower/index.js
const WxParse = require('../../../public/wxParse/html2json.js')
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    videoList:[],
    name:'',
    userId:'',
    search:'',
    level:0,
    id:null,
    realPrice:null,
    theNum:null
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let userId = options.userId
    this.setData({
      userId:userId
    })
    let name = this.data.name
    app.initPage()
      .then(res => {
        this.wxUserGet()
        this.getEmpowerVideo(name)
      })
  },

  getEmpowerVideo(name){
    let userId = this.data.userId
    app.api.empowerVideo(userId,name)
      .then(res => {
        let result = res.data.records
        for (var i = 0; i < result.length; i++) {
          // WxParse.wxParse('introduction'+i, 'html', result[i]["introduction"], this, 0)
          result[i]["introduction"]=WxParse.html2json(result[i]["introduction"], 'introduction');
        }
        this.setData({
          videoList: result,
          name:'',
          theNum:null
        })
      })
  },
  handleSearch(e){
    let name = this.data.name
    this.getEmpowerVideo(name)
  },
  handlewords(e){
    let name = e.detail.value
    console.log('name',name)
    this.setData({
      name:name
    })
  },

  wxUserGet() {
    app.api.wxUserGet()
      .then(res => {
        this.setData({
          level: res.data.level
        })
      })
  }, 
 videoPlay(e) {
   var _index = e.currentTarget.id
   const level = this.data.level
   const list = this.data.videoList
   let video = list[_index]
   //先判定用户是否有等级 ,如果等级大于等于视频等级，则可以播放
   let price = list[_index].price
   let rates = list[_index].rates
   let buyer = list[_index].userId
   console.log(list[_index])
   let realPrice = price 
   if(null != rates && rates>0){
     realPrice = rates
   }
   let id = list[_index].id
   if(level>= video.videoLevel || buyer === this.data.userId){
    this.setData({
      _index: _index,
      theNum:_index
    })
   }else{
    this.showModal('toBuy',id,realPrice)
   }

   
 },
 showModal(name,id,realPrice) {
  this.setData({
      modalName: name,
      realPrice:realPrice,
      id:id
  })
},
hideModal(e) {


      this.setData({
          modalName: null
      })
  },
pay() {
  this.setData({
      loading: true
  })
  var that = this
  let id = that.data.id
  let userId = that.data.userId
  app.api.empowerUnifiedOrder({
          id: id,
          userId: userId
      })
      .then(res => {
          this.setData({
              loading: false
          })
          let payData = res.data
          wx.requestPayment({
              'timeStamp': payData.timeStamp,
              'nonceStr': payData.nonceStr,
              'package': payData.packageValue,
              'signType': payData.signType,
              'paySign': payData.paySign,
              'success': function (res) {
                  that.updateUserPayCourse()
              },
              'fail': function (res) {

              },
              'complete': function (res) {

              }
          })
          that.getUserCourse()
          that.userInfoGet()
      }).catch(() => {
          this.setData({
              loading: false
          })
      })
},
updateUserPayCourse() {

  let id = this.data.id
  let userId = this.data.userId
  let that = this
  app.api.userEmpower({
      id: id,
      userId: userId
  }).then(res => {
      if (res.code === 200) {
          this.getEmpowerVideo(that.data.name)
          this.setData({
              modalName: 'success'
          })
      }

  })
},
})