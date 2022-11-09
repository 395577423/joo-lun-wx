export default () => {
  return {
    index: true,
    align: 'center',
    headerAlign: 'center',
    border: true,
    stripe: true,
    column: [
      {
      label: '音频回复信息',
      prop: 'content',
      rules: [{
        required: true,
        message: '请输入音频回复信息',
        trigger: 'blur'
      }]
    }, {
      label: '类型',
      prop:'replayType',
      type: 'number',
      rules: [
        {
          required: true,
          message: '请输入类型'
        }
      ]
    }]
  }
}
