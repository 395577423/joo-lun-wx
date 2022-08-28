const app = getApp()

Page({
  data:{
    gridCol:3,
    activityList:[]
  },
  onLoad(options){
    let courseId = options.courseId
    app.initPage()
    .then(res => {
      this.getRelationActivity(courseId)
    })
  },
  getRelationActivity(courseId){
    app.api.getRelationActivity(courseId).then(res => {
      let activityList = res.data
      this.setData({
        activityList : activityList
      })
    })
  }
})