// pages/course/course-detail/index.js
const app = getApp()
const WxParse = require('../../../public/wxParse/wxParse.js')
Page({

    /**
     * 页面的初始数据
     */
    data: {
        title: '',
        videoList: [],
        result: '生成学习报告',
        wxUser: null,
        //是否拥有课程
        isOwned: false,
        courseId: null,
        buttonStyle: 'cu-btn block line-blue margin-tb-sm lg',
        modalName: '',
        coverUrl: '',
        realPrice: null,
        audioUrl: '',
        question: '',
        paymentPrice: 0,
        //使用余额
        notUseBalance: true
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        this.setData({
            courseId: options.courseId
        })
        this.userInfoGet()
        let courseId = options.courseId
        app.initPage()
            .then(() => {
                this.getDetail(courseId)
                this.getUserCourse()
            })
    },

    getDetail(courseId) {
        app.api.courseDetail(courseId)
            .then(res => {
                
                let course = res.data.course
                let video = res.data.video
                let introduction = course.introduction

                this.setData({
                    title: course.title,
                    videoList: video,
                    coverUrl: course.coverUrl,
                    realPrice: course.realPrice,
                    audioUrl: course.questionAudio,
                    question: course.question
                })
                wx.setNavigationBarTitle({
                    title: this.data.title,
                })
                WxParse.wxParse('introduction', 'html', introduction, this, 0)
            })
    },
    getUserCourse() {
        let courseId = this.data.courseId
        let userId = this.data.wxUser.id
        app.api.getUserCourse(courseId, userId).then(res => {
            if (undefined === res.data) {
            } else {
                this.setData({
                    isOwned: true
                })
            }
        })
    },
    /**
     * 去视频列表页面
     * @param {*} e
     */
    toVideoPage(e) {
        if (this.data.isOwned) {
            let videos = encodeURIComponent(JSON.stringify(this.data.videoList))
           
            wx.navigateTo({
                url: '/pages/course/course-video/index?videoList=' + videos + '&title=' + this.data.title
            })
        } else {
            this.showModal('NeedBuy')
        }
    },
    /**
     * 去答案页面
     * @param {*} e 课程ID 标题
     */
    toQuestionPage(e) {
        if (this.data.isOwned) {
            wx.navigateTo({
                url: '/pages/course/course-question/index?courseId=' + this.data.courseId + '&title=' + this.data.title
            })
        } else {
            this.showModal('NeedBuy')
        }
    },
    /**
     * 去音频页面
     * @param {*} e
     */
    toAudioPage(e) {
        if (this.data.isOwned) {
            wx.navigateTo({
                url: '/pages/course/course-audio/index?courseId=' + this.data.courseId + '&title=' + this.data.title + '&question=' + this.data.question + '&audioUrl=' + this.data.audioUrl
            })
        } else {
            this.showModal('NeedBuy')
        }
    },
    toReportPage(e) {
        if (this.data.isOwned) {
            wx.navigateTo({
                url: '/pages/course/course-report/index?courseId=' + this.data.courseId
            })
        } else {
            this.showModal('NeedBuy')
        }
    },
    toBuy() {
        this.showModal('Buy')
    },
    showPic(){
        this.setData({
            modalName:'showpic'
        })
    },
    showModal(name) {
        this.setData({
            modalName: name,
            paymentPrice: this.data.realPrice
        })
    },
    hideModal(e) {
        this.setData({
            modalName: null
        })
    },
    //支付相关

    // 使用用户余额
    useBalance() {
        //使用余额
        let userMoney = this.data.wxUser.money
        let realPrice = this.data.realPrice
        let notUseBalance = this.data.notUseBalance
        if (notUseBalance) {
            if (userMoney >= realPrice) {
                this.setData({
                    paymentPrice: 0
                })
            } else {
                this.setData({
                    paymentPrice: (Number(realPrice) - Number(userMoney)).toFixed(2)
                })
            }
        } else {
            this.setData({
                paymentPrice: realPrice
            })
        }

        this.setData({
            notUseBalance: !notUseBalance
        })
    },

    //添加课程归属,并且修改用户余额信息
    updateUserCourse() {
        this.setData({
            loading: true
        })
        var that = this
        let courseId = that.data.courseId
        let userId = that.data.wxUser.id
        let useCoupon = '1'
        if (that.data.notUseBalance) {
            useCoupon = '0'
        }
        app.api.courseUnifiedOrder({
                id: courseId,
                userId: userId,
                useCoupon: useCoupon
            })
            .then(res => {
                this.setData({
                    loading: false
                })
                if (that.data.paymentPrice <= 0) { //0元付款
                    this.setData({
                        modalName: 'success'
                    })
                } else {
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
                }
                that.getUserCourse()
                that.userInfoGet()
            }).catch(() => {
                this.setData({
                    loading: false
                })
            })
    },
    userInfoGet() {
        app.api.wxUserGet()
            .then(res => {
                this.setData({
                    wxUser: res.data
                })
            })
    },
    updateUserPayCourse(){

        let courseId = this.data.courseId
        let userId = this.data.wxUser.id
        let useCoupon = '1'
        if (this.data.notUseBalance) {
            useCoupon = '0'
        }
        app.api.userCourse({
            id: courseId,
            userId: userId,
            useCoupon: useCoupon
        }).then(res=>{
            if(res.code === 200){
                this.getUserCourse()
                this.userInfoGet()
                this.setData({
                    modalName: 'success'
                })
            }

        })
    }
})
