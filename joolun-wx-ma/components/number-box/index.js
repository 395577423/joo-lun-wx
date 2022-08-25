// components/number-box/number-box.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    number:{
      type:Number,
      value:1
    },
    min:{
      type:Number,
      value:1
    },
    max:{
      type:Number,
      value:99
    },
    index:{ //如果是数组形式的输入框，则需要传入下标，数值改变后返回相应下标的值
      type:Number,
      value:0
    },
    indexs:{
      type:Number,
      value:0
    }
  },

  /**
   * 组件的初始数据
   */
  data: {

  },

  /**
   * 组件的方法列表
   */
  methods: {
    minusNumber:function(e){
      var number = this.data.number;
      var min = this.data.min;
      if(number<=min){
        wx.showToast({
          title: '不能再减少了！',
          icon:'none'
        })
      }else{
        this.setData({
          number:number-1
        })
      }
      this.retrunResult();
    },
    plusNumber:function(e){
      var number = this.data.number;
      var max = this.data.max;
      if(number>=max){
        wx.showToast({
          title: '超出限购数量！',
          icon:'none'
        })
      }else{
        this.setData({
          number:number+1
        })
      }
      this.retrunResult();
    },
    inputBlur:function(e){
      var min = this.data.min;
      var max = this.data.max;
      var number = e.detail.value;
      if(number<min||number>max){
        wx.showToast({
          title: '请输入正确的数量！',
          icon:'none'
        })
        this.setData({
          number:this.data.number
        })
      }else{
        this.setData({number})
      }
      this.retrunResult();
    },
    retrunResult:function(e){
      this.triggerEvent('numberBoxChange',{
        index:this.data.index,
        indexs:this.data.indexs,
        number:this.data.number
      })
    }
  }
})
