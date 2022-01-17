export const tableOption = {
  dialogDrag: true,
  border: true,
  stripe: true,
  menuAlign: 'center',
  align: 'center',
  menuType: 'text',
  viewBtn: true,
  searchMenuSpan: 6,
  column: [{
    label: '课程问题',
    prop: 'questionId',
    type: 'select',
    remote: true,
    search: true,
    props: {
      label: 'question',
      value: 'id'
    },
    dicUrl: `/course/question/list?question={{key}}`,
    rules: [{
      required: true,
      message: '请输入名称(name)',
      trigger: 'blur'
    }]
  }, {
    label: '是否为正确答案',
    prop: 'choosed',
    type: 'radio',
    slot: true,
    search: true,
    sortable: true,
    span: 24,
    rules: [{
      required: true,
      message: '请选择是否为正确答案',
      trigger: 'blur'
    }],
    dicData: [{
      label: '正确',
      value: 1
    }, {
      label: '错误',
      value: 0
    }]
  },
    {
      label: '问题选项',
      prop: 'choice',
      search: true,
      rules: [{
        required: true,
        message: '请输入问题选项',
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
