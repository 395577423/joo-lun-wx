<template>
  <div class="app-container">
    <!--搜索条件-->
    <el-form :model="queryParams" ref="queryForm" v-show="showSearch" :inline="true">
      <el-form-item label="课程名称" prop="courseName">
        <el-input
          v-model="queryParams.courseName"
          placeholder="请输入课程名称"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="课程问题" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入课程问题"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        <el-button type="primary" icon="el-icon-download" size="mini" @click="addQuestion">添加课程问题</el-button>
      </el-form-item>
    </el-form>
    <el-table v-loading="loading" :data="questionList">
      <el-table-column label="展开" type="expand"
      >
        <template slot-scope="props">
          <el-table :data="props.row.choices">
            <el-table-column label="选项" prop="choice"></el-table-column>
            <el-table-column label="排序" prop="sort"></el-table-column>
            <el-table-column label="是否正确答案" align="center" width="100">
              <template slot-scope="scope">
                <el-switch
                  v-model="scope.row.choosed"
                  :active-value="1"
                  :inactive-value="0"
                  @change="changeChoosed(scope.row)"
                ></el-switch>
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="handleUpdate(scope.row.id)"
                >修改
                </el-button>
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  @click="handleDelete(scope.row.id)"
                >删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </template>
      </el-table-column>
      <el-table-column label="ID" prop="question.id"></el-table-column>
      <el-table-column label="课程" prop="question.title"></el-table-column>
      <el-table-column label="问题" prop="question.question"></el-table-column>
      <el-table-column label="正确答案" prop="question.answer"></el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="updateQuestion(scope.row)"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="deleteQuestion(scope.row.question.id)"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.current"
      :limit.sync="queryParams.size"
      @pagination="getList"
    />

    <!-- 添加或修改课程问题对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="课程" prop="courseId">
          <el-select v-model="form.courseId">
            <el-option
              v-for="item in courseList"
              :key="item.id"
              :label="item.title"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="课程问题" prop="question">
          <el-input v-model="form.question" placeholder="请输入课程问题"/>
        </el-form-item>
        <el-form-item label="正确答案" prop="answer">
          <el-input v-model="form.answer" placeholder="请输入正确答案"/>
        </el-form-item>
        <el-form-item label="排序号" prop="sort">
          <el-input v-model="form.sort" type="number" placeholder="请输入数字的排序号"/>
        </el-form-item>
        <div v-for="(choice, index) in form.choices" :key="index">
          <el-row>
            <el-col :span="8">
              <el-form-item
                label-width="140px"
                label="选项:"
              >
                <el-input
                  v-model="choice.choice"
                  placeholder="请输入选项值名称"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="4">
              <el-form-item
                label-width="140px"
                label="排序:"
              >
                <el-input
                  placeholder="排序"
                  v-model="choice.sort"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="4">
              <el-form-item
                label-width="140px"
                label="是否正确答案:"
              >
                <el-radio-group
                  placeholder="是否正确答案"
                  v-model="choice.choosed"
                >
                  <el-radio :label="1">是</el-radio>
                  <el-radio :label="0">否</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="1" style="margin-left:50px;">
              <el-button @click="deleteCurrentChoice(index)" type="text"
              >删除
              </el-button
              >
            </el-col>
          </el-row>
        </div>
        <el-button @click="addChoiceToForm">
          添加一项答案
        </el-button>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!--添加或修改课程问题选项 -->
    <el-dialog :title="choiceTitle" :visible.sync="choiceOpen" width="50%" append-to-body>
      <el-form ref="form" :model="choiceForm" :rules="rules2" label-width="80px">
        <el-form-item label="问题选项" prop="question">
          <el-input v-model="choiceForm.choice" placeholder="请输入课程问题"/>
        </el-form-item>
        <el-form-item label="排序" prop="answer">
          <el-input v-model="choiceForm.sort" placeholder="请输入正确答案"/>
        </el-form-item>
        <el-form-item
          label-width="140px"
          label="是否正确答案:"
        >
          <el-radio-group
            placeholder="是否正确答案"
            v-model="choiceForm.choosed"
          >
            <el-radio :label="1">是</el-radio>
            <el-radio :label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitChoiceForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>


<script>
import {
  getObj,
  getPage,
  addObj,
  putObj,
  delObj,
  getPage2,
  questionDetail,
  addBoth,
  putBoth
} from '@/api/course/question'
import {
  getChoicePage,
  getChoiceObj,
  addChoiceObj,
  putChoiceObj,
  delChoiceObj,
  listByQuestion
} from '@/api/course/choice'

import { getCoursePage } from '@/api/course/course'

export default {
  data() {
    return {
      loading: true,
      //问题列表
      questionList: [],
      questionChoiceList: [],
      total: 0,
      // 显示搜索条件
      showSearch: true,
      courseList: [],
      queryParams: {
        current: 1,
        size: 10,
        name: undefined,
        courseId: undefined,
        courseName: undefined
      },
      // 是否显示弹出层
      open: false,

      choiceOpen: false,
      // 表单参数
      form: {},
      choiceForm: {},
      // 弹出层标题
      title: '',

      choiceTitle: '',
      // 表单校验
      rules: {
        courseId: [
          { required: true, message: '课程一定要选！', trigger: 'blur' }
        ],
        question: [
          { required: true, message: '问题不能为空', trigger: 'blur' }
        ],
        answer: [
          { required: true, message: '正确答案不能为空', trigger: 'blur' }
        ]
      },
      rules2: {}
    }
  },
  created() {
    this.getList()
  },
  methods: {

    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },

    /**查询课程问题列表*/
    getList() {
      this.loading = true
      getPage2(this.queryParams).then(
        resp => {
          this.questionList = resp.data.records
          this.total = resp.data.total
          this.loading = false
        }
      )
      getCoursePage().then(
        resp => {
          this.courseList = resp.data.records
        }
      )
    },
    changeChoosed(row) {
      putChoiceObj({
        id: row.id,
        choosed: row.choosed
      }).then(data => {
      })
    },
    updateQuestion(row) {
      this.title = '修改课程问题'
      const id = row.question.id
      questionDetail(id).then(
        resp => {
          this.form = resp.data
        }
      )
      this.open = true
    },
    addQuestion() {
      this.title = '添加课程问题'
      this.reset()
      this.open = true
    },
    deleteQuestion(id) {
      this.$confirm('是否确认删除课程问题ID为"' + id + '"的数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return delObj(id)
      }).then(() => {
        this.getList()
        this.msgSuccess('删除成功')
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.questionId !== undefined) {
            putBoth(this.form).then(response => {
              this.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addBoth(this.form).then(response => {
              this.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }

          this.reset()
        }
      })
    },
    submitChoiceForm() {
      if (this.choiceForm.id !== undefined) {
        putChoiceObj(this.choiceForm).then(resp=>{
          this.msgSuccess('修改成功')
          this.choiceOpen = false
          this.getList()
        })
      } else {
        addChoiceObj(this.choiceForm).then(resp=>{
          this.msgSuccess('新增成功')
          this.choiceOpen = false
          this.getList()
        })
      }

    },
// 取消按钮
    cancel() {
      this.open = false
      this.choiceOpen = false
      this.reset()
      this.choiceFormReset()
    },
    reset() {
      this.menuExpand = false,
        this.menuNodeAll = false,
        this.deptExpand = true,
        this.deptNodeAll = false,
        this.form = {
          questionId: undefined,
          sort: 0,
          title: '',
          courseId: undefined,
          question: '',
          answer: '',
          choices: []
        }
      this.resetForm('form')
    },
    choiceFormReset() {
      this.choiceForm = {
        choice: '',
        choosed: undefined,
        id: undefined,
        questionId: undefined,
        sort: 0
      }
    },
    deleteCurrentChoice(index) {
      let list = this.form.choices
      if (list.length >= 0) {
        list.splice(index, 1)
      }
    },
    addChoiceToForm() {
      this.form.choices.push({
        'choice.choice': '', 'choice.sort': 0, 'choice.choosed': 0
      })
    },
    handleUpdate(row) {
      console.log('------------',row)
      this.choiceTitle = '修改问题选项'
      this.choiceOpen = true
      getChoiceObj(row).then(
        resp=>{
          this.choiceForm = resp.data
        }
      )
    },
    handleDelete(id){
      this.$confirm('是否确认删除选项ID为"' + id + '"的数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return delChoiceObj(id)
      }).then(() => {
        this.getList()
        this.msgSuccess('删除成功')
      })
    },
    handleAdd(id){
      this.choiceFormReset()
    }
  }
}
</script>
