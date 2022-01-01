const app = getApp()

Page({
  data: {
    searchCourseHistory: [],
    courseList: []
  },
  onShow() {
    this.setData({
      searchCourseHistory: wx.getStorageSync('searchCourseHistory') ? wx.getStorageSync('searchCourseHistory') : []
    })
  },
  onLoad(options) {
    app.initPage()
      .then(res => {
        this.coursePage()
      })
  },
  searchHandle(e) {
    let value
    if (e.detail.value) {
      value = e.detail.value
    } else if (e.currentTarget.dataset.name){
      value = e.currentTarget.dataset.name
    }
    let searchCourseHistory = this.data.searchCourseHistory
    searchCourseHistory.forEach(function (item, index) {
      let i = 9 //最多缓存10条
      if (item.name == value){
        searchCourseHistory.splice(index, 1)
        i++
      }
      if (index >= i){
        searchCourseHistory.splice(index, 1)
      }
    })
    searchCourseHistory.unshift({
      name: value
    })
    wx.setStorageSync('searchCourseHistory', searchCourseHistory)
    wx.navigateTo({
      url: '/pages/course/course-list/index?name=' + value
    })
  },
  clearSearchHistory(){
    let that = this
    wx.showModal({
      content: '确认删除全部历史记录？',
      cancelText: '我再想想',
      confirmColor: '#ff0000',
      success(res) {
        if (res.confirm) {
          that.setData({
            searchCourseHistory: []
          })
          wx.setStorageSync('searchCourseHistory', [])
        }
      }
    })
  },
  coursePage() {
    app.api.coursePage({
      searchCount: false,
      current: 1,
      size: 10,
      ascs: '',//升序字段
      descs: 'sale_num'
    })
      .then(res => {
        let courseList = res.data.records
        this.setData({
          courseList: courseList
        })
      })
  },
})