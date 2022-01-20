export default ()=>{
  return {
    index: true,
    align: 'center',
    headerAlign: 'center',
    border: true,
    stripe: true,
    column: [{
      label: "课程",
      prop: "courseId",
      type: 'select',
      remote: true,
      search: true,
      props:{
        label:'title',
        value:'id'
      },
      dicUrl: `/course/list?title={{key}}`,
      rules: [{
        required: true,
        message: "请输入名称(name)",
        trigger: "blur"
      }]
    }, {
      label: "问题",
      prop: "question",
      rules: [{
        required: true,
        message: "请输入课程问题",
        trigger: "blur"
      }]
    }, {
      label: "正确答案",
      prop: "answer",
      rules: [{
        required: true,
        message: "请输入正确答案",
        trigger: "blur"
      }]
    }, {
      label: '排序',
      prop: 'sort',
      type: 'number',
      rules: [
        {
          required: true,
          message: "请输入排序号"
        }
      ]
    },{
      label:'问题选项',
      prop:'choices',
    }]
  }
}
