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
      label: "封面地址",
      prop: "imageUrl",
      type: 'upload',
      listType: 'picture-img',
      rules: [{
        required: true,
        message: "请选择封面",
        trigger: "blur"
      }],
      oss: 'ali',
      loadText: '附件上传中，请稍等',
      span: 24,
      tip: '只能上传jpg/png文件，且不超过50kb'
    }, {
      label: "问题",
      prop: "question",
      search: true,
      rules: [{
        required: true,
        message: "请输入课程问题",
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
    }],
  }
}
