export default (safe)=>{
  console.log(safe) //vue的this对象
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
      dicUrl: `/course/list?title={{key}}`,
      rules: [{
        required: true,
        message: "请输入名称(name)",
        trigger: "blur"
      }]
    }, {
      label: "封面地址",
      prop: "imageUrl",
      type: 'upload',
      slot: true,
      rules: [{
        required: true,
        message: "请输入值(value)",
        trigger: "blur"
      }]
    }, {
      label: "问题",
      prop: "question",
      search: true,
      rules: [{
        required: true,
        message: "请输入参数(code)",
        trigger: "blur"
      }]
    }, {
      label: '排序',
      prop: 'sort'
    }],
  }
}
