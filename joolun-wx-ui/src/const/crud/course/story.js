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
      label: '内容',
      prop: 'content',
      search: true,
      type:'textarea',
      rules: [{
        required: true,
        message: '请输入课程问题',
        trigger: 'blur'
      }]
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
