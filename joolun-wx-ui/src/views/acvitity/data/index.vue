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
      <template slot-scope="scope" slot="menu">
        <el-button icon="el-icon-connection" size="small" type="text" @click="selectCourse(scope.row,scope.row.$index)">
          关联课程
        </el-button>
      </template>

      <template slot="published" slot-scope="scope">
        <el-switch
          v-model="scope.row.published"
          active-color="#13ce66"
          inactive-color="#ff4949"
          @change="handlePublish(scope)"
        >
        </el-switch>
      </template>

      <template slot="introductionForm" slot-scope="row">
        <BaseEditor v-model="form.introduction"></BaseEditor>
      </template>

      <template slot="explanationForm" slot-scope="row">
        <BaseEditor v-model="form.explanation"></BaseEditor>
      </template>
      <template slot="courseForm">
        <avue-data-imgtext :option="imgTextOption"></avue-data-imgtext>
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
                 @refresh-change="refreshCourseChange"
      >
        <template slot="header">
          <el-button type="success" size="small" @click="handleRelate">确定</el-button>
        </template>

      </avue-crud>

    </el-dialog>

  </div>
</template>

<script>
    import {
        listActivity,
        delActivity,
        addActivity,
        updateActivity,
        getActivity,
        relateCourse,
        listPriceCase, publishActivity
    } from "@/api/activity/data";
    import {getCoursePage} from '@/api/course/course'
    import {courseTableOption, tableOption, imgTextOption} from "@/const/crud/activity/data";
    import BaseEditor from '@/components/Editor/index'

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
                    pageSize: 10, // 每页显示多少条
                    ascs: [],//升序字段
                    descs: 'create_time',//降序字段
                },
                paramsSearch: {},
                tableLoading: false,
                tableOption: tableOption,
                imgTextOption: imgTextOption,
                dialogAppraises: false,
                selectionData: '',
                pointsConfig: null,
                selectionCourseId: [],
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
                selectedActivityId: 0,
                relateCourseData: {},
                mapParams: {
                    zoom: 10,
                }
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
                    this.imgTextOption.data = [];
                    this.form = {};
                    this.$set(this.form, 'address', [112.93888200000004, 28.228304, "湖南省长沙市"])
                    done()
                } else if (type == 'edit') {
                    this.tableLoading = true
                    getActivity(this.form.id).then(response => {
                        let result = response.data;
                        let activity = result.activity;
                        let priceCases = result.priceCases;
                        let courses = result.courses;
                        let courseData = [];
                        if (courses) {
                            courses.forEach(item => {
                                let imageItem = {title: item.title, imgsrc: item.coverUrl}
                                courseData.push(imageItem);
                            })
                        }
                        this.imgTextOption.data = courseData;
                        this.$set(this.form, 'introduction', activity.introduction)
                        this.$set(this.form, 'explanation', activity.explanation)
                        this.$set(this.form, 'address', activity.address)
                        this.$set(this.form, 'priceCases', priceCases)
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
                listActivity(Object.assign({
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
                let reqParams = {};
                reqParams.activity = row;
                reqParams.priceCases = row.priceCases;
                delete reqParams.activity.priceCases;
                updateActivity(reqParams).then(data => {
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
            /**
             * @title 数据添加
             * @param row 为当前的数据
             * @param done 为表单关闭函数
             *
             **/
            handleSave: function (row, done, loading) {
                let reqParams = {};
                reqParams.activity = row;
                reqParams.priceCases = row.priceCases;
                delete reqParams.activity.priceCases;
                addActivity(reqParams).then(data => {
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
            selectCourse(row, index) {
                this.selectedActivityId = row.id;
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
            handleRelate() {
                var _this = this;
                if (this.selectionCourseId.length == 0) {
                    _this.$message({
                        showClose: true,
                        message: '请选择要关联的课程！',
                        type: 'success'
                    })
                    return false;
                }
                this.relateCourseData.activityId = this.selectedActivityId;
                this.relateCourseData.courseIds = this.selectionCourseId;
                relateCourse(this.relateCourseData).then(data => {
                    if (data.code == 200) {
                        _this.$message({
                            showClose: true,
                            message: '关联成功！',
                            type: 'success'
                        })
                        this.open = false;
                    }
                })
            },
            selectionCourseChange(list) {
                this.selectionCourseId = [];
                list.forEach(item => {
                    this.selectionCourseId.push(item.id)
                });
            },
            handlePublish(scope) {
                let row = scope.row;
                publishActivity(row).then(data => {
                    this.$message({
                        showClose: true,
                        message: '发布成功',
                        type: 'success'
                    })
                }).catch(() => {
                    row.published = false;
                })
            }


        }
    };
</script>
