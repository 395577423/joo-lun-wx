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
      label: '商品名称',
      prop: 'name',
      search: true,
      display: false
    },
    {
      label: '商品图片',
      prop: 'picUrls',
      width: 120,
      display: false,
      slot:true
    },
    {
      label: '类目',
      prop: 'categoryId',
      type: 'cascader',
      search: true,
      props: {
        label: 'name',
        value: 'id'
      },
      dicUrl: '/goodscategory/tree',
      display: false
    },
    {
      label: '卖点',
      prop: 'sellPoint',
      display: false
    },
    {
      label: '销售价',
      prop: 'salesPrice',
      slot: true,
      display: false
    },
    {
      label: '市场价',
      prop: 'marketPrice',
      display: false
    },
    {
      label: '成本价',
      prop: 'costPrice',
      display: false
    },
    {
      label: '库存',
      prop: 'stock',
      display: false
    },
    {
      label: '商品编码',
      prop: 'spuCode',
      search: true,
      sortable: true,
      display: false
    },

    {
      label: '虚拟销量',
      prop: 'saleNum',
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
      label: '是否上架',
      prop: 'shelf',
      type: 'radio',
      slot: true,
      search: true,
      sortable: true,
      display: false,
      dicData: [{
        label: '下架',
        value: '0'
      }, {
        label: '上架',
        value: '1'
      }]
    }
  ],
  group: [
    {
      icon: 'el-icon-goods',
      label: '基本信息',
      prop: 'group1',
      column: [
        {
          label: '商品名称',
          prop: 'name',
          span: 24,
          rules: [{
            required: true,
            message: '商品名称不能为空',
            trigger: 'blur'
          }, {
            max: 100,
            message: '长度在不能超过100个字符'
          }]
        },

        {

          label: '商品图片',
          prop: 'picUrls',
          listType: 'picture-card',
          dataType: 'string',
          type: 'upload',
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
          label: '类目',
          prop: 'categoryId',
          type: 'cascader',
          props: {
            label: 'name',
            value: 'id'
          },
          dicUrl: '/goodscategory/tree',
          rules: [{
            required: true,
            message: '请选择类目',
            trigger: 'blur'
          }]
        },
        {
          label: '是否上架',
          prop: 'shelf',
          type: 'radio',
          rules: [{
            required: true,
            message: '请选择是否上架',
            trigger: 'blur'
          }],
          dicData: [{
            label: '下架',
            value: '0'
          }, {
            label: '上架',
            value: '1'
          }]
        },
        {
          label: '商品编码',
          prop: 'spuCode',
          rules: [{
            max: 32,
            message: '长度在不能超过32个字符'
          }]
        },
        {
          label: '库存',
          prop: 'stock',
          type: 'number',
          rules: [
            {
              required: true,
              message: '库存必须填写',
              trigger: 'blur'
            }]
        },
        {
          label: '销售价',
          prop: 'salesPrice',
          type: 'number',
          rules: [
            {
              required: true,
              message: '销售价必须填写',
              trigger: 'blur'
            }]
        },
        {
          label: '市场价',
          prop: 'marketPrice'
        },
        {
          label: '成本价',
          prop: 'costPrice'
        },
        {
          label: '虚拟销量',
          prop: 'saleNum',
          type: 'number',
          tip: '可以按自己需求设置，系统会按销售情况自动累加'
        },
        {
          label: '卖点',
          prop: 'sellPoint',
          span: 24,
          rules: [{
            max: 500,
            message: '长度在不能超过500个字符'
          }]
        }]
    },

    {
      icon: 'el-icon-grape',
      label: '辅助信息',
      column: [
        {
          label: '描述',
          prop: 'description',
          formslot: true,
          span: 12
        }]
    }
  ]
}

