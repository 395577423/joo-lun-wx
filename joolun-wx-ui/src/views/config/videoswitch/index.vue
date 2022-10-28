<template>
  <div class="app-container">
    <avue-crud ref="crud"
               :page="page"
               :data="tableData"
               :table-loading="tableLoading"
               :option="option"
               v-model="form"
               @on-load="getPage"
    >

      <template slot="status" slot-scope="scope">
        <el-switch
          active-value="1"
          inactive-value="0"
          v-model="scope.row.status"
          active-color="#13ce66"
          inactive-color="#ff4949"
          @change="openVideo(scope.row)"
        >
        </el-switch>
      </template>

    </avue-crud>
  </div>
</template>

<script>
import { getObj, listObj, putObj } from '@/api/config/videoswitch'
import option from '@/const/crud/config/videoswitch'

export default {
  name: 'switch',
  data() {
    return {
      form: {},
      tableData: [],
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20 // 每页显示多少条
      },
      paramsSearch: {},
      tableLoading: false,
      option: option(this),
      dialogAppraises: false,
      selectionData: '',
      pointsConfig: null
    }
  },
  watch: {},
  created() {
  },
  mounted: function() {
  },
  computed: {},
  methods: {
    openVideo(row) {
      putObj({
        id: row.id,
        status: row.status
      }).then(data => {

      })
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
      listObj(Object.assign({
        current: page.current,
        size: page.size
      }, params, this.paramsSearch)).then(response => {
        this.tableData = response.data.records
        this.page.total = response.data.total
        this.page.currentPage = page.data.current
        this.page.pageSize = page.data.size
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
    handleUpdate: function(row, index, done, loading) {
      row.cover = row.cover ? row.cover.toString() : ''
      putObj(row).then(data => {
        this.$message({
          showClose: true,
          message: '修改成功',
          type: 'success'
        })
        done()
        this.getPage(this.page)
      }).catch(() => {
        loading()
      })
    },
    /**
     * 刷新回调
     */
    refreshChange(page) {
      this.getPage(this.page)
    }
  }
}
</script>
