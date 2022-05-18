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
               @row-save="handleSave"
               @row-del="handleDel"
               @sort-change="sortChange"
               @search-change="searchChange"
               @selection-change="selectionChange"
    >
      <template slot="choosed" slot-scope="scope">
        <el-switch
          :active-value=1
          :inactive-value=0
          v-model="scope.row.choosed"
          active-color="#13ce66"
          inactive-color="#ff4949"
          @change="changeChoosed(scope.row)"
        >
        </el-switch>
      </template>
    </avue-crud>

  </div>
</template>

<script>
import {getChoicePage,getChoiceObj,addChoiceObj,putChoiceObj,delChoiceObj,listByQuestion} from '@/api/course/choice'
import {tableOption} from '@/const/crud/course/choice'

export default {
  name: 'choice',
  components: {},
  data() {
    return {
      form: {},
      tableData: [],
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
        ascs: [],//升序字段
        descs: 'question_id'//降序字段
      },
      paramsSearch: {},
      tableLoading: false,
      tableOption: tableOption,
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

    selectionChange(list) {
      this.selectionData = list
    },
    changeChoosed(row) {
      putChoiceObj({
        id: row.id,
        choosed: row.choosed
      }).then(data => {
      })
    },
    beforeOpen(done, type) {
      done()
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
      getChoicePage(Object.assign({
        current: page.currentPage,
        size: page.pageSize,
        descs: this.page.descs,
        ascs: this.page.ascs
      }, params, this.paramsSearch)).then(response => {
        this.tableData = response.data.records
        this.page.total = response.data.total
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
        return delChoiceObj(row.id)
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
      row.coverUrl = row.coverUrl ? row.coverUrl : ''
      putChoiceObj(row).then(data => {
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
      row.coverUrl = row.coverUrl ? row.coverUrl.toString() : ''
      addChoiceObj(row).then(data => {
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
<style lang="scss" scoped>
</style>
