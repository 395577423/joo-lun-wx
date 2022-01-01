const app = getApp()

Page({
  data: {
    config: app.globalData.config,
    TabCur: 0,
    MainCur: 0,
    VerticalNavTop: 0,
    bookCategory: [],
    load: true
  },
  onLoad() { 
    app.initPage()
      .then(res => {
        this.booksCategoryGet()
      })
  },
  onShow() {
    //更新tabbar购物车数量
    wx.setTabBarBadge({
      index: 2,
      text: app.globalData.shoppingCartCount + ''
    })
  },
  booksCategoryGet() {
    app.api.booksCategoryGet()
      .then(res => {
        let bookCategory = res.data
        this.setData({
          bookCategory: bookCategory
        })
      })
  },
  tabSelect(e) {
    this.setData({
      TabCur: e.currentTarget.dataset.id,
      MainCur: e.currentTarget.dataset.id,
      VerticalNavTop: (e.currentTarget.dataset.id - 1) * 50
    })
  },
  VerticalMain(e) {
    let that = this
    let list = this.data.bookCategory
    let tabHeight = 0
    if (this.data.load) {
      for (let i = 0; i < list.length; i++) {
        let view = wx.createSelectorQuery().select("#main-" + i)
        view.fields({
          size: true
        }, data => {
          list[i].top = tabHeight
          tabHeight = tabHeight + data.height
          list[i].bottom = tabHeight
        }).exec()
      }
      that.setData({
        load: false,
        bookCategory: list
      })
    }
    let scrollTop = e.detail.scrollTop + 20
    for (let i = 0; i < list.length; i++) {
      if (scrollTop > list[i].top && scrollTop < list[i].bottom) {
        that.setData({
          VerticalNavTop: (i - 1) * 50,
          TabCur: i
        })
        return false
      }
    }
  }
})
