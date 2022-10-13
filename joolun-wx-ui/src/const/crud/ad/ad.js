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
        label: '广告封面',
        prop: 'cover',
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
        label: '有效截止期',
        prop: 'validDate',
        type: 'datetime'
      },
      {
        label: '创建时间',
        prop: 'createTime',
        sortable: true,
        display: false
      },
       {
        label: '介绍',
        prop: 'introduction'
      },
      {
        label: '投放人',
        prop: 'user'
      }
      ,
      {
        label: '内容',
        prop: 'content',
        formslot: true
      }
    ]

  }
}

