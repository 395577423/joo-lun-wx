<template>
  <div class="app-container">
    <avue-crud ref="crud"
               :page="page"
               :table-loading="tableLoading"
               :option="option"
               v-model="form"
               :data.sync="tableData"
               :search.sync="search"
               :page.sync="page"
               :before-open="beforeOpen"
               :before-close="beforeClose"
               @search-change="searchChange"
    >
    </avue-crud>
  </div>
</template>

<script>
import {getCoursePage} from '@/api/course/course'
import option from "@/const/crud/course/course";

export default {
  name: "SelectCourse",
  data() {
    return {
      title: '选择课程',
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

  methods:{
    beforeOpen(done, type) {

    },
    searchChange(params, done) {
      params = this.filterForm(params)
      this.paramsSearch = params
      this.page.currentPage = 1
      this.getPage(this.page, params)
      done()
    },
    beforeClose(done,type){
      done();
    },
  }
}
</script>

