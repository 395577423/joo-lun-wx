export default () => {
  return {
    index: true,
    align: 'center',
    headerAlign: 'center',
    border: true,
    stripe: true,
    menu: false,
    addBtn: false,
    column: [
      {
        label: '是否开启视频播放',
        prop: 'status',
        slot: true
      }
    ]

  }
}

