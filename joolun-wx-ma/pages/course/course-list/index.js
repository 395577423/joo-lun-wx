const util = require('../../../utils/util.js')
const app = getApp()

Page({
  data: {
    page: {
      searchCount: false,
      current: 1,
      size: 10,
      ascs: '',//升序字段
      descs: ''
    },
    parameter: {},
    loadmore: true,
    courseList: [],
    viewType: true,
    price: '',
    sales: '',
    createTime: '',
    title: '',
    userId:''
  },
  onLoad(options) {
    if(''!== options.userId){
      wx.setNavigationBarTitle({
        title: '我的书屋',
      })
    }
    let title = options.title ? decodeURI(options.title) : '默认'
    this.setData({
      title: title,
      userId:options.userId
    })
    if (options.categorySecond){
      this.setData({
        ['parameter.categorySecond']: options.categorySecond
      })
    }
    if(options.userId){
      this.setData({
        ['parameter.userId']: options.userId
      })
    }
    if (options.name) {
      this.setData({
        ['parameter.name']: options.name
      })
    }
    if (options.type) {
      if (options.type == '1'){
        this.setData({
          title: '新品首发',
          ['page.descs']: 'create_time'
        })
      }
      if (options.type == '2') {
        this.setData({
          title: '热销单品',
          ['page.descs']: 'sale_num'
        })
      }
    }
    app.initPage()
      .then(res => {
        this.coursePage()
      })
  },
  coursePage() {
    app.api.coursePage(Object.assign(
      {},
      this.data.page,
      util.filterForm(this.data.parameter)
    ))
      .then(res => {
        let courseList = res.data.records
        this.setData({
          courseList: [...this.data.courseList, ...courseList]
        })

        if (courseList.length < this.data.page.size) {
          this.setData({
            loadmore: false
          })
        }
      })
  },
  viewTypeEdit(){
    this.setData({
      viewType: !this.data.viewType
    })
  },
  onReachBottom() {
    if (this.data.loadmore) {
      this.setData({
        ['page.current']: this.data.page.current + 1
      })
      this.coursePage()
    }
  },
  sortHandle(e){
    let type = e.target.dataset.type
    switch (type) {
      case 'price':
        if (this.data.price == ''){
          this.setData({
            price: 'asc',
            ['page.descs']: '',
            ['page.ascs']: 'price'
          })
        } else if (this.data.price == 'asc'){
          this.setData({
            price: 'desc',
            ['page.descs']: 'price',
            ['page.ascs']: ''
          })
        } else if (this.data.price == 'desc'){
          this.setData({
            price: '',
            ['page.ascs']: '',
            ['page.descs']: ''
          })
        }
        this.setData({
          sales: '',
          createTime: ''
        })
        break
      case 'sales':
        if (this.data.sales == ''){
          this.setData({
            sales: 'desc',
            ['page.descs']: 'sale_num',
            ['page.ascs']: ''
          })
        }else if (this.data.sales == 'desc'){
          this.setData({
            sales: 'asc',
            ['page.descs']: '',
            ['page.ascs']: 'sale_num'
          })
        }else if (this.data.sales == 'asc'){
          this.setData({
            sales: '',
            ['page.ascs']: '',
            ['page.descs']: ''
          })
        }
        this.setData({
          price: '',
          createTime: ''
        })
        break
      case 'createTime':
        if (this.data.createTime == ''){
          this.setData({
            createTime: 'desc',
            ['page.descs']: 'create_time',
            ['page.ascs']: ''
          })
        }else if (this.data.createTime == 'desc'){
          this.setData({
            createTime: '',
            ['page.ascs']: '',
            ['page.descs']: ''
          })
        }
        this.setData({
          price: '',
          sales: ''
        })
        break
    }
    this.relod()
  },
  relod(){
    this.setData({
      loadmore: true,
      courseList: [],
      ['page.current']: 1
    })
    this.coursePage()
  }
})
