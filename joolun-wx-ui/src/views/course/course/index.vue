<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入标题"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="课程图片地址" prop="coverUrl">
        <el-input
          v-model="queryParams.coverUrl"
          placeholder="请输入课程图片地址"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="1 推荐 0 不推荐" prop="recommend">
        <el-input
          v-model="queryParams.recommend"
          placeholder="请输入1 推荐 0 不推荐"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="1 正常 0 停用" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择1 正常 0 停用" clearable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="课程开始日期" prop="startTime">
        <el-date-picker clearable size="small"
          v-model="queryParams.startTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择课程开始日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="课程结束日期" prop="endTime">
        <el-date-picker clearable size="small"
          v-model="queryParams.endTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择课程结束日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="适应开始年龄" prop="ageStart">
        <el-input
          v-model="queryParams.ageStart"
          placeholder="请输入适应开始年龄"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="适应结束年龄" prop="ageEnd">
        <el-input
          v-model="queryParams.ageEnd"
          placeholder="请输入适应结束年龄"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="课程价格" prop="price">
        <el-input
          v-model="queryParams.price"
          placeholder="请输入课程价格"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="返现金额" prop="cashReturn">
        <el-input
          v-model="queryParams.cashReturn"
          placeholder="请输入返现金额"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="折扣率 0-1" prop="rates">
        <el-input
          v-model="queryParams.rates"
          placeholder="请输入折扣率 0-1"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否参与奖学金计划 1 参加 0 不参加" prop="plan">
        <el-input
          v-model="queryParams.plan"
          placeholder="请输入是否参与奖学金计划 1 参加 0 不参加"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:course:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:course:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:course:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:course:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="courseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="id" />
      <el-table-column label="标题" align="center" prop="title" />
      <el-table-column label="介绍" align="center" prop="introduction" />
      <el-table-column label="课程图片地址" align="center" prop="coverUrl" />
      <el-table-column label="1 推荐 0 不推荐" align="center" prop="recommend" />
      <el-table-column label="1 正常 0 停用" align="center" prop="status" />
      <el-table-column label="课程开始日期" align="center" prop="startTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="课程结束日期" align="center" prop="endTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="适应开始年龄" align="center" prop="ageStart" />
      <el-table-column label="适应结束年龄" align="center" prop="ageEnd" />
      <el-table-column label="课程价格" align="center" prop="price" />
      <el-table-column label="返现金额" align="center" prop="cashReturn" />
      <el-table-column label="折扣率 0-1" align="center" prop="rates" />
      <el-table-column label="是否参与奖学金计划 1 参加 0 不参加" align="center" prop="plan" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:course:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:course:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改课程对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="介绍" prop="introduction">
          <el-input v-model="form.introduction" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="课程图片地址" prop="coverUrl">
          <el-input v-model="form.coverUrl" placeholder="请输入课程图片地址" />
        </el-form-item>
        <el-form-item label="1 推荐 0 不推荐" prop="recommend">
          <el-input v-model="form.recommend" placeholder="请输入1 推荐 0 不推荐" />
        </el-form-item>
        <el-form-item label="1 正常 0 停用">
          <el-radio-group v-model="form.status">
            <el-radio label="1">请选择字典生成</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="课程开始日期" prop="startTime">
          <el-date-picker clearable size="small"
            v-model="form.startTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择课程开始日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="课程结束日期" prop="endTime">
          <el-date-picker clearable size="small"
            v-model="form.endTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择课程结束日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="适应开始年龄" prop="ageStart">
          <el-input v-model="form.ageStart" placeholder="请输入适应开始年龄" />
        </el-form-item>
        <el-form-item label="适应结束年龄" prop="ageEnd">
          <el-input v-model="form.ageEnd" placeholder="请输入适应结束年龄" />
        </el-form-item>
        <el-form-item label="课程价格" prop="price">
          <el-input v-model="form.price" placeholder="请输入课程价格" />
        </el-form-item>
        <el-form-item label="返现金额" prop="cashReturn">
          <el-input v-model="form.cashReturn" placeholder="请输入返现金额" />
        </el-form-item>
        <el-form-item label="折扣率 0-1" prop="rates">
          <el-input v-model="form.rates" placeholder="请输入折扣率 0-1" />
        </el-form-item>
        <el-form-item label="是否参与奖学金计划 1 参加 0 不参加" prop="plan">
          <el-input v-model="form.plan" placeholder="请输入是否参与奖学金计划 1 参加 0 不参加" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listCourse, getCourse, delCourse, addCourse, updateCourse, exportCourse } from "@/api/system/course";

export default {
  name: "Course",
  components: {
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 课程表格数据
      courseList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: null,
        introduction: null,
        coverUrl: null,
        recommend: null,
        status: null,
        startTime: null,
        endTime: null,
        ageStart: null,
        ageEnd: null,
        price: null,
        cashReturn: null,
        rates: null,
        plan: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询课程列表 */
    getList() {
      this.loading = true;
      listCourse(this.queryParams).then(response => {
        this.courseList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        title: null,
        introduction: null,
        coverUrl: null,
        recommend: null,
        status: 0,
        startTime: null,
        endTime: null,
        ageStart: null,
        ageEnd: null,
        price: null,
        cashReturn: null,
        rates: null,
        plan: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加课程";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCourse(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改课程";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCourse(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCourse(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除课程编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delCourse(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有课程数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportCourse(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    }
  }
};
</script>
