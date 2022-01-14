export default (safe) => {
  return {
    index: true,
    align: 'center',
    headerAlign: 'center',
    border: true,
    stripe: true,
    column: [{
      label: '课程',
      prop: 'courseId',
      type: 'select',
      remote: true,
      props: {
        label: 'title',
        value: 'id'
      },
      dicUrl: `/course/list?title={{key}}`,
      rules: [{
        required: true,
        message: '请输入名称(name)',
        trigger: 'blur'
      }]
    }, {
      label: '视频缩略图片地址',
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
      label: '视频地址',
      prop: 'videoUrl',
      type: 'upload',
      rules: [{
        required: true,
        message: '请选择视频',
        trigger: 'blur'
      }],
      oss: 'ali',
      loadText: '附件上传中，请稍等',
      span: 24,
      tip: '只能上传视频文件，尽量控制大小'
    }, {
      label: '标题',
      prop: 'title',
      search: true,
      rules: [{
        required: true,
        message: '请输入课程问题',
        trigger: 'blur'
      }]
    }, {
      label: '状态',
      prop: 'status',
      type: 'radio',
      search: true,
      display: false,
      dicData: [{
        label: '正常',
        value: '1'
      }, {
        label: '停用',
        value: '0'
      }],
      rules: [
        {
          required: true,
          message: '请选择状态'
        }
      ]
    }, {
      label: '排序',
      prop: 'sort',
      type: 'number',
      rules: [
        {
          required: true,
          message: '请输入排序号'
        }
      ]
    },
      {
        label: '创建人',
        prop: 'createBy',
        display: false
      },
      {
        label: '创建日期',
        prop: 'createTime',
        display: false
      },
      {
        label: '视频时长（秒）',
        prop: 'duration',
        type: 'number',
        rule: [
          {
            min: 1,
            message: '视频时长要大于0'
          }
        ]
      }
    ]
  }
}
