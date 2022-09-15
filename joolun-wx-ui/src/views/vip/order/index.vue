<template>
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


    </avue-crud>
  </div>
</template>
<script>
import {listOrder, getOrder, delOrder, addOrder, updateOrder, exportOrder} from "@/api/vip/order";


export default {
  name: "Config",
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
      tableOption: {
        dialogType: 'drawer',
        border: true,
        stripe: true,
        menuAlign: 'center',
        align: 'center',
        menuType: 'text',
        searchShow: true,
        printBtn: false,
        delBtn:false,
        editBtn:false,
        dialogWidth: '88%',
        selection: true,
        searchMenuSpan: 6,
        column: [
          {
            label: '订单号',
            prop: 'orderNo',
            type: 'input',
            display: true
          },
          {
            label: '支付状态',
            prop: 'isPay',
            type: 'select',
            dicData: [
              {
                label: '已支付',
                value: '1'
              }, {
                label: '未支付',
                value: '0'
              }
            ],
            display: true,
            search: true
          },
          {
            label: '支付金额',
            prop: 'paymentPrice',
            type: 'number',
            display: true
          },
          {
            label: '支付时间',
            prop: 'paymentTime',

          },
        ]
      },
      dialogAppraises: false,
      selectionData: '',
      pointsConfig: null
    }
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
    refreshChange() {
      this.getPage(this.page,this.paramsSearch)
    },
    beforeOpen(done, type) {
      if (type == 'add') {
        done()
      } else if (type == 'edit') {
        this.tableLoading = true
        getOrder(this.form.id).then(response => {

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
  }

}
</script>
