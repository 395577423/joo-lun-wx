export const tableOption = {
  dialogType: 'drawer',
  border: true,
  stripe: true,
  menuAlign: 'center',
  align: 'center',
  menuType: 'text',
  searchShow: false,
  excelBtn: true,
  printBtn: true,
  dialogWidth: '88%',
  selection: true,
  searchMenuSpan: 6,
  column: [
    {
      label: '标题',
      prop: 'title',
      search: true,
      display: false
    },
    {
      label: '封面地址',
      prop: 'coverUrl',
      type: 'upload',
      width: 250,
      display: false,
      listType: 'picture-img'
    },
    {
      label: '介绍',
      prop: 'introduction',
      display: false
    },
    {
      label: '作者',
      prop: 'author',
      display: false
    },
    {
      label: '书籍类型',
      prop: 'dictCode',
      type: 'select',
      search: true,
      props: {
        label: 'dictLabel',
        value: 'dictCode'
      },
      dicUrl: '/course/book/type',
      display: false
    },
    {
      label: '创建时间',
      prop: 'createTime',
      sortable: true,
      display: false
    },
    {
      label: '更新时间',
      prop: 'updateTime',
      display: false
    },
    {
      label: '创建者',
      prop: 'createBy',
      sortable: true,
      display: false
    },
    {
      label: '更新者',
      prop: 'updateBy',
      display: false
    },
    {
      label: '状态',
      prop: 'status',
      type: 'radio',
      sortable: true,
      display: false,
      dicData: [{
        label: '正常',
        value: '1'
      }, {
        label: '停用',
        value: '0'
      }]
    },
    {
      label: '备注',
      prop: 'remark',
      display: false
    }
  ],
  group: [
    {
      icon: 'el-icon-goods',
      label: '基本信息',
      prop: 'group1',
      column: [
        {
          label: '标题',
          prop: 'title',
          span: 24,
          rules: [{
            required: true,
            message: '标题不能为空',
            trigger: 'blur'
          }, {
            max: 20,
            message: '长度在不能超过20个字符'
          }]
        },
        {
          label: '封面地址',
          prop: 'coverUrl',
          type: 'upload',
          listType: 'picture-img',
          width: 250,
          rules: [{
            required: true,
            message: '图片不能为空',
            trigger: 'change'
          }],
          oss: 'ali',
          loadText: '附件上传中，请稍等',
          span: 24,
          tip: '只能上传jpg/png文件，且不超过50kb'
        },
        {
          label: '介绍',
          prop: 'introduction'
        },
        {
          label: '作者',
          prop: 'author'
        },
        {
          label: '书籍类型',
          prop: 'dictCode',
          type: 'select',
          props: {
            label: 'dictLabel',
            value: 'dictCode'
          },
          dicUrl: '/course/book/type',
          rules: [{
            required: true,
            message: '请选择类目',
            trigger: 'blur'
          }]
        },
        {
          label: '创建时间',
          prop: 'createTime',
          sortable: true,
        },
        {
          label: '更新时间',
          prop: 'updateTime',
        },
        {
          label: '创建者',
          prop: 'createBy',
          sortable: true,
        },
        {
          label: '更新者',
          prop: 'updateBy',
        },
        {
          label: '状态',
          prop: 'status',
          type: 'radio',
          sortable: true,
          dicData: [{
            label: '正常',
            value: '1'
          }, {
            label: '停用',
            value: '0'
          }]
        },
        {
          label: '备注信息',
          prop: 'remark',
        }
      ]
    }
  ]
}
