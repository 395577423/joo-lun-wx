export const tableOption = {
  dialogType: 'drawer',
  border: true,
  stripe: true,
  menuAlign: 'center',
  align: 'center',
  menuType: 'text',
  searchShow: true,
  printBtn: false,
  dialogWidth: '88%',
  selection: true,
  searchMenuSpan: 6,
  column: [
    {
      label: '活动名称',
      prop: 'name',
      search: true,
      display: false
    },
    {
      label: '活动分类',
      prop: 'categoryId',
      type: "select",
      dicUrl: '/activity/category/list',
      props: {
        label: 'categoryName',
        value: 'id'
      },
      display: false
    },
    {
      label: '活动图片',
      prop: 'imageUrl',
      type: 'upload',
      display: false,
      listType: 'picture-img'
    },
    {
      label: '是否发布',
      prop: 'published',
      type: 'radio',
      slot:true,
      display: false,
      editDisplay: false
    },
    {
      label: '活动地址',
      prop: 'address',
      display: false
    },
    {
      label: '创建者',
      prop: 'creator',
      sortable: true,
      display: false,
      editDisplay: false
    },
    {
      label: '创建时间',
      prop: 'createTime',
      sortable: true,
      editDisplay: false,
      addDisplay: false
    }

  ],
  group: [
    {
      prop: 'group1',
      column: [
        {
          label: '活动名称',
          prop: 'name',
          rules: [{
            required: true,
            message: '活动名称不能为空',
            trigger: 'blur'
          }, {
            max: 20,
            message: '长度在不能超过20个字符'
          }]
        },
        {
          label: '活动分类',
          prop: 'categoryId',
          search: true,
          type: 'select',
          props: {
            label: 'categoryName',
            value: 'id'
          },
          dicUrl: '/activity/category/list',
          rules: [{
            required: true,
            message: '请选择活动分类',
            trigger: 'blur'
          }]
        },
        {
          label: '活动地址',
          prop: 'address',
          type: 'map',
          span: 20,
          params:{
            zoom: 10,
            zoomEnable: true
          }
        },
        {
          label: '活动图片',
          prop: 'imageUrl',
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
          tip: '只能上传jpg/png文件，且不超过50kb'
        },

        {
          label: '活动介绍',
          prop: 'introduction',
          span: 20,
          formslot: true,
          rules: [{
            required: true,
            message: '活动介绍不能为空',
            trigger: 'blur'
          }]
        },
        {
          label: '活动详情',
          prop: 'explanation',
          span: 20,
          formslot: true
        },
        {
          label: '关联课程',
          prop: 'course',
          span: 20,
          formslot: true
        },
        {
          label: '套餐',
          prop: 'priceCases',
          type: 'dynamic',
          span:24,
          children: {
            align: 'center',
            headerAlign: 'center',
            rowAdd:(done)=>{
              done();
            },
            rowDel:(row,done)=>{
              done();
            },
            column: [{
              width: 200,
              label: '套餐名称',
              prop: "name"
            }, {
              width: 200,
              label: '多选',
              prop: "caseOption",
              type: 'checkbox',
              dicData: [{
                label: '成人',
                value: 1
              }, {
                label: '儿童',
                value: 2
              }]
            }, {
              width: 140,
              label: '原价',
              prop: "salesPrice",
              type: 'number'
            }, {
              width: 140,
              label: '会员价',
              prop: "memberPrice",
              type: 'number'
            }, {
              width: 140,
              label: '超级会员价',
              prop: "superMemberPrice",
              type: 'number'
            }, {
              width: 140,
              label: '会员返现金额',
              prop: "cashBackAmount",
              type: 'number'
            }, {
              width: 140,
              label: '超级会员返现金额',
              prop: "superCashBackAmount",
              type: 'number'
            }]
          }
        }
      ]
    }
  ]
}


export const courseTableOption = {
  selection: true,
  searchShowBtn: false,
  addBtn: false,
  menu: false,
  searchMenuSpan: 6,
  maxHeight: 420,
  column: [
    {
      label: '标题',
      prop: 'title',
      search: true,
      searchSpan: 12,
      rules: [
        {
          required: true,
          message: '请输入排序号'
        }
      ]
    },
    {
      label: '课程图片',
      prop: 'coverUrl',
      dataType: 'string',
      type: 'img',
    }, {
      label: '课程开始日期',
      prop: 'startTime',
      type: 'datetime'
    }, {
      label: '课程结束日期',
      prop: 'endTime',
      type: 'datetime'
    }
  ]
}

export const imgTextOption = {
  span:8,
  data:[
  ]
}
