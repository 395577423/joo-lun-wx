let dicDataSex = [{
  label: '未知',
  value: '0'
}, {
  label: '男',
  value: '1'
}, {
  label: '女',
  value: '2'
}]

export const tableOption = {
  dialogDrag:true,
  border: true,
  index: false,
  indexLabel: '序号',
  stripe: true,
  menuAlign: 'center',
  align: 'center',
  editBtn: true,
  delBtn: false,
  addBtn: false,
  excelBtn: true,
  printBtn: true,
  viewBtn: true,
  searchShow: false,
  menuWidth: 150,
  menuType:'text',
  searchMenuSpan: 6,
  defaultSort:{
    prop: 'createTime',
    order: 'descending'
  },
  column: [
    {
      label: '用户标识',
      prop: 'openId',
      editDisplay:false
    },
    {
      label: '头像',
      prop: 'headimgUrl',
      imgWidth:50,
      dataType: 'string',
      type: 'upload',
      listType: 'picture-img',
      editDisplay:false
    },
    {
      label: '昵称',
      prop: 'nickName',
      width:100,
      sortable:true,
      search:true,
      editDisplay:false
    },
    {
      label: '性别',
      prop: 'sex',
      width: 60,
      type: 'select',
      sortable:true,
      slot:true,
      search:true,
      editDisplay:false,
      dicData: dicDataSex
    },
    {
      label: '所在国家',
      prop: 'country',
      sortable:true,
      search:true,
      editDisplay:false
    },
    {
      label: '所在省份',
      prop: 'province',
      sortable:true,
      editDisplay:false
    },
    {
      label: '所在城市',
      prop: 'city',
      sortable:true,
      search:true,
      editDisplay:false
    },
    {
      label: '用户语言',
      prop: 'language',
      sortable:true,
      editDisplay:false
    },
    {
      label: '用户备注',
      prop: 'remark',
      hide:true
    },
    {
      label: 'union_id',
      prop: 'unionId',
      hide:true,
      editDisplay:false
    },
    {
      label: '创建时间',
      prop: 'createTime',
      type: 'datetime',
      sortable:true,
      editDisplay:false
    },
    {
      label: '更新时间',
      prop: 'updateTime',
      type: 'datetime',
      sortable:true,
      hide:true,
      editDisplay:false
    },{
      label: '余额',
      prop: 'money',
      type: 'number',
      precision: 2,
      sortable:true,
      editDisplay:true
    },{
      label: '手机号',
      prop: 'phone',
      sortable:true,
      editDisplay:false
    },
    {
      label: '会员',
      prop: 'member',
      type: 'radio',
      slot: true,
      rules: [{
        required: true,
        message: '设置是否为会员',
        trigger: 'blur'
      }],
      dicData: [{
        label: '开启',
        value: '1'
      }, {
        label: '关闭',
        value: '0'
      }],
      sortable:true,
      editDisplay:true
    },{
      label: '合伙人',
      prop: 'partner',
      type: 'radio',
      slot: true,
      dicData: [{
        label: '开启',
        value: '1'
      }, {
        label: '关闭',
        value: '0'
      }],
      sortable:true,
      editDisplay:true
    },{
      label: '赋能级别',
      prop: 'level',
      type: 'select',
      multiple: false,
      props: {
        label: 'dictLabel',
        value: 'dictValue'
      },
      dicUrl: '/system/dict/data/type/empower_level?data={{key}}',
      sortable:true,
      editDisplay:true
    },
    {
      label:'1UP值',
      prop:'empowerNum',
      type: 'number',
      editDisplay: true
    }
  ]
}
