/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，项目使用请保留此说明
 */
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
      label: '课程图片',
      prop: 'coverUrl',
      type:'upload',
      width: 250,
      display: false,
      listType: 'picture-img',
    },
    {
      label: '介绍',
      prop: 'introduction',
      display: false
    },
    {
      label: '课程开始日期',
      prop: 'startTime',
      display: false
    },
    {
      label: '课程结束日期',
      prop: 'endTime',
      display: false
    },
    {
      label: '开始年龄',
      prop: 'ageStart',
      display: false
    },
    {
      label: '结束年龄',
      prop: 'ageEnd',
      display: false
    },
    {
      label: '价格',
      prop: 'price',
      slot: true,
      display: false
    },
    {
      label: '返现金额',
      prop: 'cashReturn',
      display: false
    },
    {
      label: '折扣率',
      prop: 'rates',
      display: false
    },
    {
      label: '奖学金计划',
      prop: 'plan',
      type: 'radio',
      search: true,
      slot: true,
      display: false,
      dicData: [{
        label: '开启',
        value: '1'
      }, {
        label: '关闭',
        value: '0'
      }]
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
      label: '状态',
      prop: 'status',
      type: 'radio',
      sortable: true,
      slot: true,
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
      label: '是否推荐',
      prop: 'recommend',
      type: 'radio',
      search: true,
      sortable: true,
      slot: true,
      display: false,
      dicData: [{
        label: '推荐',
        value: '1'
      }, {
        label: '不推荐',
        value: '0'
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
          label: '课程图片',
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
          label: '是否推荐',
          prop: 'recommend',
          type: 'radio',
          rules: [{
            required: true,
            message: '请选择是否推荐',
            trigger: 'blur'
          }],
          dicData: [{
            label: '不推荐',
            value: '0',
          }, {
            label: '推荐',
            value: '1'
          }]
        },
        {
          label: '课程开始日期',
          prop: 'startTime',
          type: 'datetime'
        },
        {
          label: '课程结束日期',
          prop: 'endTime',
          type: 'datetime'
        },
        {
          label: '开始年龄',
          prop: 'ageStart',
          type: 'number'
        },
        {
          label: '结束年龄',
          prop: 'ageEnd',
          type: 'number'
        },
        {
          label: '价格',
          prop: 'price',
          type: 'number'
        },
        {
          label: '返现金额',
          prop: 'cashReturn',
          type: 'number'
        },
        {
          label: '折扣率',
          prop: 'rates',
          type: 'number'
        },
        {
          label: '资学金计划',
          prop: 'plan',
          type: 'radio',
          rules: [{
            required: true,
            message: '请选择是否开启奖学金计划',
            trigger: 'blur'
          }],
          dicData: [{
            label: '开启',
            value: '0'
          }, {
            label: '关闭',
            value: '1'
          }]
        }
      ]

    },
    {
      icon: 'el-icon-grape',
      label: '其他信息',
      prop: 'group6',
      column: [
        {
          label: '介绍',
          prop: 'introduction',
          formslot: true,
          span: 12
        }]
    }
  ]
}
