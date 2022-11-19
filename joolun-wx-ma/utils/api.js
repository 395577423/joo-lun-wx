import __config from '../config/env'

const request = (url, method, data, showLoading) => {
  let _url = __config.basePath + url
  return new Promise((resolve, reject) => {
    if (showLoading) {
      wx.showLoading({
        title: '加载中',
      })
    }
    wx.request({
      url: _url,
      method: method,
      data: data,
      header: {
        'app-id': wx.getAccountInfoSync().miniProgram.appId,
        'third-session': getApp().globalData.thirdSession != null ? getApp().globalData.thirdSession : ''
      },
      success(res) {
        if (res.statusCode == 200) {
          if (res.data.code != 200) {

            wx.showModal({
              title: '提示',
              content: res.data.msg ? res.data.msg : '没有数据' + '',
              success() {

              },
              complete() {
                if (res.data.code == 60001) {
                  //session过期，则清除过期session，并重新加载当前页
                  getApp().globalData.thirdSession = null
                  wx.reLaunch({
                    url: getApp().getCurrentPageUrlWithArgs()
                  })
                }
              }
            })
            reject(res.data.msg)
          }
          resolve(res.data)
        } else if (res.statusCode == 404) {
          wx.showModal({
            title: '提示',
            content: '接口请求出错，请检查手机网络',
            success(res) {

            }
          })
          reject()
        } else {

          wx.showModal({
            title: '提示',
            content: res.errMsg + ':' + res.data.message + ':' + res.data.msg,
            success(res) {

            }
          })
          reject()
        }
      },
      fail(error) {

        wx.showModal({
          title: '提示',
          content: '接口请求出错：' + error.errMsg,
          success(res) {

          }
        })
        reject(error)
      },
      complete(res) {
        wx.hideLoading()
      }
    })
  })
}

module.exports = {
  request,
  login: (data) => { //小程序登录接口
    return request('/weixin/api/ma/wxuser/login', 'post', data, false)
  },
  wxUserGet: (data) => { //微信用户查询
    return request('/weixin/api/ma/wxuser', 'get', null, false)
  },
  wxUserSave: (data) => { //微信用户新增
    return request('/weixin/api/ma/wxuser', 'post', data, true)
  },
  goodsCategoryGet: (data) => { //商品分类查询
    return request('/weixin/api/ma/goodscategory/tree', 'get', data, true)
  },
  goodsPage: (data) => { //商品列表
    return request('/weixin/api/ma/goodsspu/page', 'get', data, false)
  },
  goodsGet: (id) => { //商品查询
    return request('/weixin/api/ma/goodsspu/' + id, 'get', null, false)
  },
  shoppingCartPage: (data) => { //购物车列表
    return request('/weixin/api/ma/shoppingcart/page', 'get', data, false)
  },
  shoppingCartAdd: (data) => { //购物车新增
    return request('/weixin/api/ma/shoppingcart', 'post', data, true)
  },
  shoppingCartEdit: (data) => { //购物车修改
    return request('/weixin/api/ma/shoppingcart', 'put', data, true)
  },
  shoppingCartDel: (data) => { //购物车删除
    return request('/weixin/api/ma/shoppingcart/del', 'post', data, false)
  },
  shoppingCartCount: (data) => { //购物车数量
    return request('/weixin/api/ma/shoppingcart/count', 'get', data, false)
  },
  orderSub: (data) => { //订单提交
    return request('/weixin/api/ma/orderinfo', 'post', data, true)
  },
  orderPage: (data) => { //订单列表
    return request('/weixin/api/ma/orderinfo/page', 'get', data, false)
  },
  orderGet: (id) => { //订单详情查询
    return request('/weixin/api/ma/orderinfo/' + id, 'get', null, false)
  },
  orderCancel: (id) => { //订单确认取消
    return request('/weixin/api/ma/orderinfo/cancel/' + id, 'put', null, true)
  },
  orderReceive: (id) => { //订单确认收货
    return request('/weixin/api/ma/orderinfo/receive/' + id, 'put', null, true)
  },
  orderDel: (id) => { //订单删除
    return request('/weixin/api/ma/orderinfo/' + id, 'delete', null, false)
  },
  orderCountAll: (data) => { //订单计数
    return request('/weixin/api/ma/orderinfo/countAll', 'get', data, false)
  },
  unifiedOrder: (data) => { //下单接口
    return request('/weixin/api/ma/orderinfo/unifiedOrder', 'post', data, true)
  },
  userAddressPage: (data) => { //用户收货地址列表
    return request('/weixin/api/ma/useraddress/page', 'get', data, false)
  },
  userAddressSave: (data) => { //用户收货地址新增
    return request('/weixin/api/ma/useraddress', 'post', data, true)
  },
  userAddressDel: (id) => { //用户收货地址删除
    return request('/weixin/api/ma/useraddress/' + id, 'delete', null, false)
  },
  planCourse: () => { //获取奖学金计划课程
    return request('/weixin/api/course/plan', 'get')
  },
  getRecommends: (data) => { //获取奖学金计划课程
    return request('/weixin/api/course/recommend', 'get', data, false)
  },
  booksCategoryGet: () => { //书籍目录获取
    return request('/weixin/api/course/tree', 'get')
  },
  coursePage: (data) => { //根据书籍分类查询课程列表
    return request('/weixin/api/course/list', 'get', data, false)
  },
  courseDetail: (courseId) => { //获取课程详情
    return request('/weixin/api/course/detail/' + courseId, 'get', null, false)
  },
  getUserCourse: (courseId, userId) => { //用户课程信息
    return request('/weixin/api/course/usercourse/' + courseId + '/' + userId, 'get', null, false)
  },
  getCourseQuestion: (courseId, userId) => { //课程问题与答案
    return request('/weixin/api/course/question/' + courseId + '/' + userId, 'get', null, false)
  },
  setUserChoice: (data) => { //保存用户问题答案
    return request('/weixin/api/course/choice', 'post', data, false)
  },
  getUserAudio: (courseId, userId, audioId) => { //查询用户录音
    return request('/weixin/api/course/audio/' + courseId + '/' + userId + '/' + audioId, 'get', null, false)
  },
  getUserReport: (courseId, userId) => { //获取用户读书报告
    return request('/weixin/api/course/report/' + courseId + '/' + userId, 'get', null, false)
  },
  userCourse: (data) => {
    return request('/weixin/api/course/usercourse', 'post', data, false)
  },
  userEmpower: (data) => {
    return request('/weixin/api/course/userempower', 'post', data, false)
  },
  courseUnifiedOrder: (data) => { //
    return request('/weixin/api/course/unifiedOrder', 'post', data, true)
  },
  empowerUnifiedOrder: (data) => { //
    return request('/weixin/api/course/unifiedEmpower', 'post', data, true)
  },
  userMoney: (id, userId) => {
    return request('/weixin/api/course/usermoney/' + id + '/' + userId, 'get', null, false)
  },
  bindWXPhoneNumber: (data) => {
    return request('/weixin/api/ma/wxuser/phone', 'post', data, true)
  },
  courseAudio: (id) => {
    return request('/weixin/api/course/audios/' + id, 'get', null, false)
  },
  empowerVideo: (userId, name) => {
    return request('/weixin/api/course/empower/' + userId + '?name=' + name, 'get', null, false)
  },
  empowerVideoDetail: (id, userId) => {
    return request('/weixin/api/course/empower/' + id + '/' + userId, 'get', null, false)
  },
  getLibiary: (data) => {
    return request('/weixin/api/bookstore/library', 'post', data, true)
  },
  getActivityCategory: (data) => {
    return request('/weixin/api/activity/category/list', 'get', data)
  },
  getActivityList: (data, pageNo, pageSize) => {
    return request('/weixin/api/activity/list?current=' + pageNo + '&size=' + pageSize, 'post',data,true)
  },
  getActivityDetail: (activityId) => {
    return request('/weixin/api/activity/' + activityId, 'get')
  },
  getActivityIsClosed: (activityId) => {
    return request('/weixin/api/activity/getStatus?activityId=' + activityId, 'get')
  },
  getPriceCase: (activityId) => {
    return request('/weixin/api/activity/getPriceCase?activityId=' + activityId, 'get')
  },
  getActitityPersons: () => {
    return request('/weixin/api/activity/person/list', 'get')
  },
  saveActivityPerson: (data) => {
    return request('/weixin/api/activity/person/save', 'post', data)
  },
  getActivityPerson: (data) => {
    return request('/weixin/api/activity/person/' + data, 'get')
  },
  deleteActivityPerson: (data) => {
    return request('/weixin/api/activity/person/' + data, 'delete')
  },
  subActivityOrder: (data) => {
    return request('/weixin/api/activity/order/add','post',data)
  },
  unifiedActivityOrder: (data) => {
    return request('/weixin/api/activity/order/unifiedOrder','post',data)
  },
  listActivityOrder: (data) => {
    return request('/weixin/api/activity/order/page', 'get', data, false)
  },
  cancelActivityOrder: (orderId) => {
    return request('/weixin/api/activity/order/cancel/'+orderId, 'put', false)
  },
  deleteActivityOrder: (orderId) => {
    return request('/weixin/api/activity/order/'+orderId, 'delete', false)
  },
  getActivityOrder: (orderId) => {
    return request('/weixin/api/activity/order/'+orderId, 'get', false)
  },
  getRelationActivity: (courseId) => {
    return request('/weixin/api/activity/listByCourseId?courseId='+courseId, 'get', false)
  },
  getBuyActivityCount: (activityId) => {
    return request('/weixin/api/activity/order/getBuyActivityCount?activityId='+activityId,'get',false)
  },
  addShareRecord:(shareUserId) =>{
    return request('/weixin/api/share/record/add?shareUserId='+shareUserId,'get',false)
  },
  buyMember:() =>{
    return request('/weixin/api/member/buy','get',false)
  },
  getCommission:() => {
    return request('/weixin/api/commission/get','get',false)
  },
  getPartners: () => {
    return request('/weixin/api/commission/partners','get',false)
  },
  getMemberConfig: () =>{
    return request('/weixin/api/member/config','get',false)
  },
  getAdList:() =>{
    return request('/weixin/api/ad/list','post',false)
  },
  getAd:(id) =>{
    return request('/weixin/api/ad/'+id,'get',false)
  },
  getPanda:() =>{
    return request('/weixin/api/propaganda','post',false)
  },
  getUserBankInfo:() => {
    return request('/weixin/api/commission/getUserBankInfo','get')
  },
  saveWithdrawApplyRecord:(data) => {
    return request('/weixin/api/commission/withdraw/apply/save','post',data,true);
  },
  listWithdrawApplyRecord:() => {
    return request('/weixin/api/commission/withdraw/apply/list','get');
  },
  getSwitch:()=>{ //视频开关
    return request('/weixin/api/videoswitch','get')
  },
  getReply:(courseId,userId,audioId) =>{ //获取语音回复
    return request('/weixin/api/course/reply/'+courseId+'/'+userId+'/'+audioId,'get')
  },
  getUserCourseCount:(userId) =>{ //获取用户已购买课程数量
    return request('/weixin/api/course/usercourse-count/'+userId,'get')
  }

}