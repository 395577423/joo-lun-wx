<template>
  <div class="app-container">
    <div class="app-container">
      <avue-crud ref="crud"
                 :page="page"
                 :data="tableData"
                 :table-loading="tableLoading"
                 :option="tableOption"
                 :before-open="beforeOpen"
                 v-model="form"
                 @on-load="getPage"
                 @refresh-change="refreshChange"
                 @row-update="handleUpdate"
                 @search-change="searchChange"
                 @selection-change="selectionChange"
      >
        <template slot-scope="{type,size,row}" slot="menu">
          <el-button icon="el-icon-check" size="small" type="text" v-if="row.status==1" @click="complete(row)">完成</el-button>
        </template>
      </avue-crud>
    </div>
  </div>
</template>

<script>
import {
  listOrder,
  getPerson,
  updateOrder,
  completeOrder
} from "@/api/activity/order";

export default {
  name: "Order",
  data() {
    return {
      form: {},
      tableData: [],
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 10, // 每页显示多少条
        ascs: [],//升序字段
        descs: 'create_time',//降序字段
      },
      paramsSearch: {},
      tableLoading: false,
      dialogAppraises: false,
      selectionData: '',
      pointsConfig: null,
      tableOption: {
        dialogType: 'drawer',
        border: true,
        stripe: true,
        menuAlign: 'center',
        align: 'center',
        menuType: 'text',
        searchShow: true,
        printBtn: false,
        addBtn: false,
        editBtn: false,
        delBtn: false,
        viewBtn: true,
        dialogWidth: '88%',
        selection: false,
        searchMenuSpan: 6,
        rowKey: 'id',
        menuWidth: 130,
        column: [
          {
            label: '订单名',
            prop: 'name',
            addDisplay: false,
            editDisplay: false,
            width: 200,
            viewDisplay: false

          },
          {
            label: '订单单号',
            prop: 'orderNo',
            addDisplay: false,
            editDisplay: false,
            search: true,
            width: 200,
            viewDisplay: false

          },

          {
            label: '订单封面图',
            prop: 'activityImg',
            type: 'img',
            addDisplay: false,
            editDisplay: false,
            width: 100,
            viewDisplay: false
          },

          {
            label: '是否支付',
            prop: 'isPay',
            addDisplay: false,
            editDisplay: false,
            search: true,
            type: 'select',
            dicData: [
              {
                value: '0',
                label: '未支付'
              },
              {
                value: '1',
                label: '已支付'
              }
            ],
            viewDisplay: false

          },
          {
            label: '订单状态',
            prop: 'status',
            addDisplay: false,
            editDisplay: false,
            search: true,
            type: 'select',
            dicData: [
              {
                value: '0',
                label: '待支付'
              },
              {
                value: '1',
                label: '待完成'
              },
              {
                value: '2',
                label: '已完成'
              },
              {
                value: '3',
                label: '已取消'
              }
            ],
            viewDisplay: false
          },

          {
            label: '销售金额',
            prop: 'salesPrice',
            addDisplay: false,
            editDisplay: false,
            viewDisplay: false

          },
          {
            label: '支付金额',
            prop: 'paymentPrice',
            addDisplay: false,
            editDisplay: false,
            viewDisplay: false
          },

          {
            label: '付款时间',
            prop: 'paymentTime',
            addDisplay: false,
            editDisplay: false,
            width: 100,
            viewDisplay: false
          },
          {
            label: '购买数量',
            prop: 'quantity',
            addDisplay: false,
            editDisplay: false,
            viewDisplay: false

          },

          {
            label: '活动日期',
            prop: 'activityDate',
            addDisplay: false,
            editDisplay: false,
            width: 100,
            viewDisplay: false

          }

        ],
        group: [
          {
            label: '基本信息',
            prop: 'base',
            column: [
              {
                label: '订单单号',
                prop: 'orderNo',

              },
              {
                label: '订单名',
                prop: 'name',
                addDisplay: false,
                editDisplay: false
              },
              {
                label: '订单状态',
                prop: 'status',
                type: 'select',
                dicData: [
                  {
                    value: '0',
                    label: '待支付'
                  },
                  {
                    value: '1',
                    label: '待完成'
                  },
                  {
                    value: '2',
                    label: '已完成'
                  },
                  {
                    value: '3',
                    label: '已取消'
                  }
                ],
              },
              {
                label: '购买数量',
                prop: 'quantity',
              },

              {
                label: '活动日期',
                prop: 'activityDate',
              }
            ]
          },
          {
            label: '支付信息',
            prop: 'base',
            column: [
              {
                label: '支付金额',
                prop: 'paymentPrice'
              },
              {
                label: '商品原价',
                prop: 'salesPrice'
              },
              {
                label: '是否支付',
                prop: 'isPay',
                type: 'select',
                dicData: [
                  {
                    value: '0',
                    label: '未支付'
                  },
                  {
                    value: '1',
                    label: '已支付'
                  }
                ]
              },
            ]
          },
          {
            label: '出行人信息',
            prop: 'base',
            column: [
              {
                prop: 'persons',
                type: 'dynamic',
                span: 24,
                children: {
                  align: 'center',
                  headerAlign: 'center',
                  column: [{
                    width: 200,
                    label: '姓名',
                    prop: "name"
                  },
                    {
                      width: 200,
                      label: '身份证号码',
                      prop: "identityNo"
                    }, {
                      width: 200,
                      label: '联系电话',
                      prop: "tel"
                    },
                    {
                      width: 200,
                      label: '性别',
                      prop: "gender",
                      type: 'checkbox',
                      dicData: [
                        {
                          value: '0',
                          label: '女'
                        },
                        {
                          value: '1',
                          label: '男'
                        }
                      ]
                    }]
                }
              }
            ]
          }
        ]
      },
    };
  },
  watch: {},
  created() {
  },
  mounted: function () {
  },
  computed: {},
  methods: {
    selectionChange(list) {
      this.selectionData = list
    },
    beforeOpen(done, type) {
      if (type == 'view') {
        getPerson(this.form.id).then(response => {
          let result = response.data;

          this.$set(this.form, 'persons', result)
        })
        done()
      }
    },
    searchChange(params, done) {
      params = this.filterForm(params)
      this.paramsSearch = params
      this.page.currentPage = 1
      this.getPage(this.page, params)
      done()
    },
    /**
     * 刷新回调
     */
    refreshChange(page) {
      this.getPage(this.page)
    },
    getPage(page, params) {
      this.tableLoading = true
      listOrder(Object.assign({
        pageNum: page.currentPage,
        pageSize: page.pageSize,
        descs: this.page.descs,
        ascs: this.page.ascs
      }, params, this.paramsSearch)).then(response => {
        let tableData = response.rows
        this.tableData = tableData
        this.page.total = response.total
        this.page.currentPage = page.currentPage
        this.page.pageSize = page.pageSize
        this.tableLoading = false
      }).catch(() => {
        this.tableLoading = false
      })
    },
    /**
     * @title 数据更新
     * @param row 为当前的数据
     * @param index 为当前更新数据的行数
     * @param done 为表单关闭函数
     *
     **/
    handleUpdate: function (row, index, done, loading) {
      updateOrder(row).then(data => {
        this.$message({
          showClose: true,
          message: '修改成功',
          type: 'success'
        })
        done()
        this.getPage(this.page)
      }).catch(() => {
      })
    },
    complete(row){
      completeOrder(row.id).then(data =>{
        this.$message({
          showClose: true,
          message: '操作完成',
          type: 'success'
        })
        done()
        this.getPage(this.page)
      }).catch(() => {
      })
    }
  }
};
</script>
