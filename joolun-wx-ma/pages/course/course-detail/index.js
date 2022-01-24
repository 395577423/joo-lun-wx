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
        course: null,
        tip: ''
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

                let realPrice
                if (null !== course.rates && course.price > course.rates) {
                    realPrice = course.rates
                } else {
                    realPrice = course.price
                }

                this.setData({
                    course: course,
                    title: course.title,
                    videoList: video,
                    coverUrl: course.coverUrl,
                    realPrice: realPrice,
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
            if (undefined === res.data) {} else {
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
        let wxUser = this.data.wxUser
        console.log(wxUser)
        if (null == wxUser.phone) {
            this.showModal('getPhone')
            return
        }
        this.buy()
    },

    buy() {
        let startTime = this.data.course.startTime
        let endTime = this.data.course.endTime

        if (this.compareDate(new Date(), new Date(endTime))) {
            this.setData({
                tip: '已经过了报名时间'
            })
            this.showModal('tip')
            return
        }
        if (this.compareDate(new Date(startTime), new Date())) {
            this.setData({
                tip: '还没到报名时间'
            })
            this.showModal('tip')
            return
        }

        this.showModal('Buy')
    },
    /**
     * 判断日期1是否大于日期2，只到年月日
     * @param {*} date1 
     * @param {*} date2 
     */
    compareDate(date1, date2) {
        var result = false;
        if (date1.getFullYear() > date2.getFullYear()) {
            result = true;
        } else if (date1.getFullYear() == date2.getFullYear()) {
            if (date1.getMonth() > date2.getMonth()) {
                result = true;
            } else if (date1.getMonth() == date2.getMonth()) {
                if (date1.getDate() > date2.getDate()) {
                    result = true;
                }
            }
        }
        return result;
    },

    showPic() {
        this.setData({
            modalName: 'showpic'
        })
    },
    showModal(name) {
        this.setData({
            modalName: name
        })
    },
    hideModal(e) {
        let modelName = this.data.modalName
        console.log(modelName)
        if ('getPhone' === modelName) {
            this.buy()
        } else {
            this.setData({
                modalName: null
            })
        }
    },
    //支付相关

    //添加课程归属,并且修改用户余额信息
    updateUserCourse() {
        this.setData({
            loading: true
        })
        var that = this
        let courseId = that.data.courseId
        let userId = that.data.wxUser.id
        app.api.courseUnifiedOrder({
                id: courseId,
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
    userInfoGet() {
        app.api.wxUserGet()
            .then(res => {
                this.setData({
                    wxUser: res.data
                })
            })
    },
    updateUserPayCourse() {

        let courseId = this.data.courseId
        let userId = this.data.wxUser.id
        app.api.userCourse({
            id: courseId,
            userId: userId
        }).then(res => {
            if (res.code === 200) {
                this.getUserCourse()
                this.userInfoGet()
                this.setData({
                    modalName: 'success'
                })
            }

        })
    },
    getPhoneNumber(e) {
        let userId = this.data.wxUser.id
        if (e.detail.iv) {
            app.api.bindWXPhoneNumber({
                code: e.detail.code,
                userId: userId
            }).then(res => {
                this.userInfoGet()
                this.buy()
            })
        } else { // 用户拒绝授权
            this.buy()
        }
    }
})