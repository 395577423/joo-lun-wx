<template>
  <div class="app-container">
    <avue-crud ref="crud"
               :page="page"
               :data="tableData"
               :table-loading="tableLoading"
               :option="option"
               :before-open="beforeOpen"
               v-model="form"
               @on-load="getPage"
               @refresh-change="refreshChange"
               @row-update="handleUpdate"
               @row-save="handleSave"
               @row-del="handleDel"
               @sort-change="sortChange"
               @search-change="searchChange"
    >
      <template slot="actived" slot-scope="scope">
        <el-switch
          active-value="1"
          inactive-value="0"
          v-model="scope.row.actived"
          active-color="#13ce66"
          inactive-color="#ff4949"
          @change="changeActived(scope.row)">
        </el-switch>
      </template>
    </avue-crud>
  </div>
</template>

<script>
import { addObj, delObj, getObj, listObj, putObj } from '@/api/propaganda/propaganda'
import option from '@/const/crud/propaganda/propaganda'

export default {
  name: 'propaganda',
  data() {
    return {
      form: {},
      tableData: [],
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
        ascs: [],//升序字段
        descs: 'create_time'//降序字段
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
    changeActived(row){

      putObj({
        id: row.id,
        actived: row.actived
      }).then(() => {
        this.getPage(this.page)
      })
    },
    beforeOpen(done, type) {
      if (type == 'add') {
        done()
      } else if (type == 'edit') {
        this.tableLoading = true
        getObj(this.form.id).then(() => {
          this.tableLoading = false
          done()
        })
      }
    },
    searchChange(params, done) {
      params = this.filterForm(params)
      this.paramsSearch = params
      this.page.currentPage = 1
      this.getPage(this.page, params)
      done()
    },
    sortChange(val) {
      let prop = val.prop ? val.prop.replace(/([A-Z])/g, '_$1').toLowerCase() : ''
      if (val.order == 'ascending') {
        this.page.descs = []
        this.page.ascs = prop
      } else if (val.order == 'descending') {
        this.page.ascs = []
        this.page.descs = prop
      } else {
        this.page.ascs = []
        this.page.descs = []
      }
      this.getPage(this.page)
    },
    getPage(page, params) {
      this.tableLoading = true
      listObj(Object.assign({
        current: page.currentPage,
        size: page.pageSize,
        descs: this.page.descs,
        ascs: this.page.ascs
      }, params, this.paramsSearch)).then(response => {
        this.tableData = response.rows
        this.page.total = response.total
        this.page.currentPage = page.currentPage
        this.page.pageSize = page.pageSize
        this.tableLoading = false
      }).catch(() => {
        this.tableLoading = false
      })
    },
    /**
     * @title 数据删除
     * @param row 为当前的数据
     * @param index 为当前删除数据的行数
     *
     **/
    handleDel: function(row, index) {
      var _this = this
      this.$confirm('是否确认删除此数据', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return delObj(row.id)
      }).then(data => {
        _this.$message({
          showClose: true,
          message: '删除成功',
          type: 'success'
        })
        this.getPage(this.page)
      }).catch(function(err) {
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
      console.log(row)
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
     * @title 数据添加
     * @param row 为当前的数据
     * @param done 为表单关闭函数
     *
     **/
    handleSave: function(row, done, loading) {
      row.url = row.url ? row.url.toString() : ''
      addObj(row).then(data => {
        this.$message({
          showClose: true,
          message: '添加成功',
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
