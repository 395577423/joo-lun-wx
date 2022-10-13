export default () => {
  return {
    index: true,
    align: 'center',
    headerAlign: 'center',
    border: true,
    stripe: true,
    column: [
       {
         label: '是否激活',
         prop: 'actived',
         type: 'radio',
         slot: true,
         rules: [{
           required: true,
           message: '请选择是否开启奖学金计划',
           trigger: 'blur'
         }],
         dicData: [{
           label: '激活',
           value: '1'
         }, {
           label: '关闭',
           value: '0'
         }]
      },
      {
        label: '创建时间',
        prop: 'createTime',
        sortable: true,
        display: false
      },
      {
        label: '视频',
        prop: 'url',
        type: 'upload',
        rules: [{
          required: false,
          message: '请选择视频',
          trigger: 'blur'
        }],
        oss: 'ali',
        loadText: '附件上传中，请稍等',
        span: 24,
        tip: '只能上传视频文件，尽量控制大小',
        hide:true
      }
    ]

  }
}

