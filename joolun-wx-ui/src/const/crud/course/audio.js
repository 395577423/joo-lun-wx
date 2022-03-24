export default () => {
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
      search: true,
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
      label: '音频问题',
      prop: 'question',
      rules: [{
        required: true,
        message: '请输入课程音频问题',
        trigger: 'blur'
      }]
    }, {
      label: '课程音频文件',
      prop: 'questionAudio',
      type: 'upload',
      // rules: [{
      //   required: true,
      //   message: '请选择音频',
      //   trigger: 'blur'
      // }],
      oss: 'ali',
      loadText: '附件上传中，请稍等',
      span: 24,
      tip: '只能上传音频文件，尽量控制大小',
      hide: true
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
    }]
  }
}
