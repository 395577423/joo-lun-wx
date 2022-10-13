const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    config: app.globalData.config,
    recommends: [],
    version: '一',
    dataILu: true,
    page: {
      current: 0,
      size: 10,
      ascs: '', //升序字段
      descs: ''
    },
    winHeight: null,
    courses: [],
    userInfo: null,
    modalName: '',
    detail: null,
    courseId: null,
    swiperList:[],
    videoPlayed:false,
    panda:null
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    let scene = options.scene;
    app.initPage()
      .then(res => {
        this.getPlanCourse()
        this.getRecommend()
        this.wxUserGet()
        this.getAd()
        this.getPanda()
      if (scene) {
        scene = decodeURIComponent(options.scene);
        this.addShareRecord(scene)
      }
      })

  },

  getPanda(){
    app.api.getPanda()
    .then(res=>{
      let result = res.data
      this.setData({
        panda:result
      })
    })
  },

  /**
   * 滑到底部事件
   */
  onReachBottom() {
    this.getRecommend()
  },
  /**
   * 获取奖学金计划课程
   */
  getPlanCourse() {
    app.api.planCourse()
      .then(res => {
        let result = res.data
        this.setData({
          courses: result
        })
      })
  },
  
  getAd(){
    app.api.getAdList()
    .then(res=>{
      let result = res.data
      this.setData({
        swiperList:result
      })
    })
  },

  swipclick(e){
    let index = e.target.dataset.adid;
    let ad = this.data.swiperList[index];
    console.log(ad.id)
    wx.navigateTo({
      url: '/pages/ad/index?id=' + ad.id,
    })
  },

  videoPlay(){
    this.setData({
      videoPlayed:true
    })
  },
  /**
   * 获取推荐课程
   */
  getRecommend() {
    if (!this.data.dataILu) return
    this.data.page.current++
    app.api.getRecommends(this.data.page)
      .then(res => {
        if (res.code == 200) {
          const records = [...this.data.recommends, ...res.data.records]
          const total = res.data.total
          this.setData({
            recommends: records,
            dataILu: records.length < total
          })
        }
      })
    wx.hideLoading({
      success: (res) => {},
    })
  },
  /**
   * 去课程详情页面
   * @param {*} e 课程ID
   */
  toDetail(e) {
    let courseId = e.currentTarget.dataset.courseid;
    this.setData({
      courseId: courseId
    })
    let userInfo = this.data.userInfo
    if (null == userInfo.headimgUrl) {
      this.getUserProfile(e)
    }  else {
      wx.navigateTo({
        url: '/pages/course/course-detail/index?courseId=' + courseId,
        // url:'/pages/course/tttt/index'
      })
    }
  },
  getUserProfile(e) {
    let courseId = this.data.courseId
    wx.getUserProfile({
      desc: '用于完善会员资料', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
      success: (detail) => {
        this.setData({
          detail: detail
        })
        app.api.wxUserSave(detail)
          .then(res => {
            let wxUser = res.data
              this.setData({
                wxUser: wxUser
              })
            app.globalData.wxUser = wxUser
            this.wxUserGet()
          })
      },
      fail: (e) => {
        wx.navigateTo({
          url: '/pages/course/course-detail/index?courseId=' + courseId,
        })
      }
    })
  },
  wxUserGet() {
    app.api.wxUserGet()
      .then(res => {
        this.setData({
          userInfo: res.data
        })
      })
  },
  addShareRecord(shareUserId) {
    app.api.addShareRecord(shareUserId).then(res=>{
      console.log('添加分享记录完成')
    })
  },
  
})