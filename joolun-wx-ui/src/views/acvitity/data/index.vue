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
               @search-change="searchChange"
               @selection-change="selectionChange"
    >
      <template slot-scope="{type,size}" slot="menu">
        <el-button icon="el-icon-check" :size="size" :type="type" @click="selectCourse">关联课程</el-button>
        <el-button icon="el-icon-check" :size="size" :type="type" @click="selectCourse">发布</el-button>
      </template>

      <template slot="introductionForm" slot-scope="scope">
        <BaseEditor v-model="scope.row.introduction"></BaseEditor>
      </template>

      <template slot="explanationForm" slot-scope="scope">
        <BaseEditor v-model="scope.row.explanation"></BaseEditor>
      </template>

    </avue-crud>

    <el-dialog title="选择课程"
               :visible.sync="open"
               dialogClickModal="false"
               append-to-body
               width="80%"
    >

      <avue-crud ref="crud"
                 :page="coursePage"
                 :data="courseTableData"
                 :table-loading="tableLoading"
                 :option="courseTableOption"
                 @on-load="getCoursePage"
                 @search-change="searchCourseChange"
                 @selection-change="selectionCourseChange"
      >
        <template slot="header">
          <el-button type="success"
                     size="small"
                     @click="">确定</el-button>
        </template>
      </avue-crud>

    </el-dialog>

  </div>
</template>

<script>
import {listActivity, delActivity, addActivity, updateActivity, getActivity} from "@/api/activity/data";
import {getCoursePage} from '@/api/course/course'
import {courseTableOption, tableOption} from "@/const/crud/activity/data";
import BaseEditor from '@/components/Editor/index.vue'

export default {
  name: "Data",
  components: {BaseEditor},
  data() {
    return {
      form: {},
      tableData: [],
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
        ascs: [],//升序字段
        descs: 'create_time',//降序字段
      },
      paramsSearch: {},
      tableLoading: false,
      tableOption: tableOption,
      dialogAppraises: false,
      selectionData: '',
      pointsConfig: null,
      selectionCourseData: {},
      courseParamsSearch: {},
      coursePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 10, // 每页显示多少条
        ascs: [],//升序字段
        descs: 'create_time',//降序字段
      },
      courseTableData: [],
      courseTableOption: courseTableOption,
      open: false,
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
    beforeOpen(done, type) {
      if (type == 'add') {
        done()
      } else if (type == 'edit') {
        this.tableLoading = true
        getActivity(this.form.id).then(response => {
          this.$set(this.form, 'introduction', response.data.introduction)
          this.$set(this.form, 'explanation', response.data.explanation)
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
    getPage(page, params) {
      this.tableLoading = true
      if (this.paramsSearch.categoryId) {
        this.paramsSearch.categoryFirst = this.paramsSearch.categoryId[0]
        this.paramsSearch.categorySecond = this.paramsSearch.categoryId[1]
      }
      listActivity(Object.assign({
        current: page.currentPage,
        size: page.pageSize,
        descs: this.page.descs,
        ascs: this.page.ascs
      }, params, this.paramsSearch)).then(response => {
        let tableData = response.rows
        this.tableData = tableData
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
    handleDel: function (row, index) {
      var _this = this
      this.$confirm('是否确认删除此数据', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function () {
        return delActivity(row.id)
      }).then(data => {
        _this.$message({
          showClose: true,
          message: '删除成功',
          type: 'success'
        })
        this.getPage(this.page)
      }).catch(function (err) {
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
      row.coverUrl = row.coverUrl ? row.coverUrl : ''
      updateActivity(row).then(data => {
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
    handleSave: function (row, done, loading) {
      addActivity(row).then(data => {
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
    },
    selectCourse() {
      this.open = true;
    },
    getCoursePage() {
      this.tableLoading = true
      getCoursePage(Object.assign({
        current: this.coursePage.currentPage,
        size: this.coursePage.pageSize,
        descs: this.coursePage.descs,
        ascs: this.coursePage.ascs
      }, this.courseParamsSearch)).then(response => {
        this.courseTableData = response.data.records
        this.coursePage.total = response.data.total
        this.coursePage.currentPage = page.currentPage
        this.coursePage.pageSize = page.pageSize
        this.tableLoading = false
      }).catch(() => {
        this.tableLoading = false
      })
    },
    refreshCourseChange(page) {
      this.getCoursePage(this.page)
    },
    searchCourseChange(params, done) {
      params = this.filterForm(params)
      this.courseParamsSearch = params
      this.coursePage.currentPage = 1
      this.getCoursePage(this.page, params)
      done()
    },
    selectionCourseChange(list) {
      this.selectionCourseData = list
      console.log(this.selectionCourseData);
    }


  }
};
</script>
