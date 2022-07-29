var app = getApp()
var mymap = '';
var lat = '';
var long = '';
Page({
  data: {
    controls: [{
      id: 1,
      position: {
        left: 0,
        top: 300 - 1,
        width: 50,
        height: 50
      },
      clickable: true
    }],
    latitude: undefined,
    longitude: undefined,
    name: '',
    markers: [],
    showModalStatus: false,
    marker: undefined
  },
  //引入数据库
  onLoad: function (option) {
    let that = this
    wx.getLocation({
      type: 'gcj02', //返回可以用于wx.openLocation的经纬度
      success: (res) => {
        console.log(res)
        let latitude = res.latitude;
        let longitude = res.longitude;
        this.setData({
          longitude: longitude,
          latitude: latitude,
          // markers: this.getLingyuanMarkers(),
        })
      }
    })

    let latitude = this.data.latitude
    let longitude = this.data.longitude
    this.setData({
      latitude: latitude,
      longitude: longitude
    })
    app.api.getLibiary({}).then(res => {
      that.setData({
        markers: res.data
      })
    })
  },
  navigate() {
    ////使用微信内置地图查看标记点位置，并进行导航
    wx.openLocation({
      latitude: this.data.marker.latitude, //要去的纬度-地址
      longitude: this.data.marker.longitude, //要去的经度-地址
    })
  },
  //显示对话框
  showModal: function (event) {
    console.log(event.markerId);
    console.log(event)
    var markerId = event.markerId;
    let markers = this.data.markers
    for (let item of markers) {
      if (item.id === markerId) {
        this.setData({
          marker: item
        })

      }
    }
    // 显示遮罩层
    var animation = wx.createAnimation({
      duration: 200,
      timingFunction: "linear",
      delay: 0
    })
    this.animation = animation
    animation.translateY(300).step()
    this.setData({
      animationData: animation.export(),
      showModalStatus: true
    })
    setTimeout(function () {
      animation.translateY(0).step()
      this.setData({
        animationData: animation.export()
      })
    }.bind(this), 200)
  },
  //隐藏对话框
  hideModal: function () {
    // 隐藏遮罩层
    var animation = wx.createAnimation({
      duration: 200,
      timingFunction: "linear",
      delay: 0
    })
    this.animation = animation
    animation.translateY(300).step()
    this.setData({
      animationData: animation.export(),
    })
    setTimeout(function () {
      animation.translateY(0).step()
      this.setData({
        animationData: animation.export(),
        showModalStatus: false
      })
    }.bind(this), 200)
  },

  calling: function (event) {
    var tel = this.data.marker.mobile;
    console.log(event)
    wx.makePhoneCall({
      phoneNumber: tel,
      success: function () {
        console.log("拨打电话成功！")
      },
      fail: function () {
        console.log("拨打电话失败！")
      }
    })
  }
})