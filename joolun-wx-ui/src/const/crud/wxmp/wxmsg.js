export const tableOption = {
  dialogDrag:true,
  border: true,
  stripe: true,
  menuAlign: 'center',
  align: 'center',
  menuType:'text',
  editBtn: false,
  delBtn: false,
  addBtn: false,
  viewBtn: false,
  searchBtn: true,
  searchMenuSpan: 6,
  defaultSort:{
    prop:'createTime',
    order:'descending'
  },
  column: [
    {
      label: '消息类型',
      prop: 'repType',
      type: 'select',
      dicData: [{
        value: 'text',
        label: '文本'
      },{
        value: 'image',
        label: '图片'
      },{
        value: 'voice',
        label: '语音'
      },{
        value: 'video',
        label: '视频'
      },{
        value: 'shortvideo',
        label: '小视频'
      },{
        value: 'location',
        label: '地理位置'
      },{
        value: 'link',
        label: '链接消息'
      },{
        value: 'event',
        label: '事件推送'
      }],
      search:true,
      sortable:true,
      width: 120
    },
    {
      label: '用户',
      prop: 'nickName',
      overHidden: false,
      align: 'right',
      search: true,
      slot:true,
      width: 100
    },{
      label: '内容',
      prop: 'repContent',
      overHidden: true,
      slot:true,
      align: 'left',
    },{
      label: '时间',
      type: 'date',
      prop: 'createTime',
      sortable: true,
      width: 200
    },
	  {
      label: '是否已读',
      prop: 'readFlag',
      type: 'select',
      dicData: [
        {
          value: '1',
          label: '是'
        },{
          value: '0',
          label: '否'
        }
      ],
      search:true,
      sortable:true,
      slot:true,
      width: 50
    }
  ]
}
