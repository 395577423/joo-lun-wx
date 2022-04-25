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
        label: '视频',
        prop: 'url',
        type: 'upload',
        rules: [{
          required: true,
          message: '请选择视频',
          trigger: 'blur'
        }],
        oss: 'ali',
        loadText: '附件上传中，请稍等',
        span: 24,
        tip: '只能上传视频文件，尽量控制大小',
        hide: true
      }, {
        label: '价格',
        prop: 'price',
        type: 'number',
        precision: 2
      },
      {
        label: '折扣价',
        prop: 'rates',
        type: 'number',
        precision: 2
      },

      {
        label: '创建时间',
        prop: 'createTime',
        sortable: true,
        display: false
      },
      {
        label: '介绍',
        prop: 'introduction',
        hide: true,
        formslot:true
      }, {
        label: '级别',
        prop: 'videoLevel',
        type: 'select',
        multiple: false,
        props: {
          label: 'dictLabel',
          value: 'dictValue'
        },
        dicUrl: '/system/dict/data/type/empower_level?data={{key}}',
        rules: [
          {
            required: true,
            message: '请选择关联书籍',
            trigger: 'blur'
          }
        ],
        sortable: true,
        editDisplay: true
      }]
  }
}

