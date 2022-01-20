export default () => {
  return {
    index: true,
    align: 'center',
    headerAlign: 'center',
    border: true,
    stripe: true,
    column: [
      {
        label: '标题',
        prop: 'title',
        search: true,
        rules: [
          {
            required: true,
            message: '请输入排序号'
          }
        ]
      },
      {
        label: '课程图片',
        prop: 'coverUrl',
        type: 'upload',
        listType: 'picture-img',
        rules: [{
          required: true,
          message: '请选择封面',
          trigger: 'blur'
        }],
        oss: 'ali',
        loadText: '附件上传中，请稍等',
        span: 24,
        tip: '只能上传jpg/png文件，且不超过50kb'
      }, {
        label: '课程开始日期',
        prop: 'startTime',
        type: 'datetime'
      }, {
        label: '课程结束日期',
        prop: 'endTime',
        type: 'datetime'
      }, {
        label: '开始年龄',
        prop: 'ageStart',
        type: 'number'
      },
      {
        label: '结束年龄',
        prop: 'ageEnd',
        type: 'number'
      }, {
        label: '价格',
        prop: 'price',
        type: 'number',
        precision: 2
      },
      {
        label: '返现金额',
        prop: 'cashReturn',
        type: 'number',
        precision: 2
      },
      {
        label: '折扣率',
        prop: 'rates',
        type: 'number',
        precision: 1,
        rules:[
          {required: true,
          message: '请输入折扣率，默认为1'}
        ]
      },
      {
        label: '奖学金计划',
        prop: 'plan',
        type: 'radio',
        slot: true,
        rules: [{
          required: true,
          message: '请选择是否开启奖学金计划',
          trigger: 'blur'
        }],
        dicData: [{
          label: '开启',
          value: '0'
        }, {
          label: '关闭',
          value: '1'
        }]
      }, {
        label: '创建时间',
        prop: 'createTime',
        sortable: true,
        display: false
      },
      {
        label: '更新时间',
        prop: 'updateTime',
        display: false
      }, {
        label: '介绍',
        prop: 'introduction',
        hide: true,
        formslot: true
      }, {
        label: '课程精华',
        prop: 'essence',
        hide: true,
        type: 'textarea'
      }, {
        label: '关联书籍',
        prop: 'books',
        type: 'select',
        multiple: true,
        props: {
          label: 'title',
          value: 'id'
        },
        dicUrl: '/course/book/list?name={{key}}',
        rules: [
          {
            required: true,
            message: '请选择关联书籍',
            trigger: 'blur'
          }
        ]
      },
      {
        label: '地址 暂时',
        prop: 'remark'
      },
      {
        label:'课程音频问题',
        prop:'question'
      },{
        label: '课程音频文件',
        prop: 'questionAudio',
        type: 'upload',
        rules: [{
          required: true,
          message: '请选择音频',
          trigger: 'blur'
        }],
        oss: 'ali',
        loadText: '附件上传中，请稍等',
        span: 24,
        tip: '只能上传音频文件，尽量控制大小',
        hide: true
      }
    ]

  }
}

