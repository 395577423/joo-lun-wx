<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="店名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入店名"
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
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
        >删除
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="libraryList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="店主键ID" align="center" prop="id"/>
      <el-table-column label="名称" align="center" prop="name"/>
      <el-table-column label="简介" align="center" prop="description"/>
      <el-table-column label="详细地址" align="center" prop="address"/>
      <el-table-column label="联系人" align="center" prop="contact"/>
      <el-table-column label="手机号" align="center" prop="mobile"/>
      <el-table-column label="电话" align="center" prop="telephone"/>
      <el-table-column label="经度" align="center" prop="longitude"/>
      <el-table-column label="纬度" align="center" prop="latitude"/>
      <el-table-column label="创建日期" align="center" prop="createAt"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
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

    <!-- 添加或修改用户答案对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="80%" append-to-body>
      <el-row :gutter="0">
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-col :span="12">
            <el-form-item label="店名" prop="name">
              <el-input v-model="form.name" placeholder="请输入店名"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系人" prop="contact">
              <el-input v-model="form.contact" placeholder="请输入联系人"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号" prop="mobile">
              <el-input v-model="form.mobile" placeholder="请输入手机号"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="电话" prop="telephone">
              <el-input v-model="form.telephone" placeholder="请输入电话"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="经度" prop="longitude">
              <el-input v-model="form.longitude" placeholder="请输入经度"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="纬度" prop="latitude">
              <el-input v-model="form.latitude" placeholder="请输入纬度"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="详细地址" prop="description">
              <el-input v-model="form.address" placeholder="请输入地址"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="简介" prop="description">
              <el-input v-model="form.description" placeholder="请输入简介"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="上传" prop="image">
              <el-upload ref="image" :file-list="imageFileList" :http-request="uploadImageOss" :limit="5"
                         :before-upload="imageBeforeUpload" list-type="picture-card" action="##"
                         :on-remove="imageOnRemove">
                <i class="el-icon-plus"></i>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-form>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
    import {
        listLibrary,
        getLibrary,
        delLibrary,
        addLibrary,
        updateLibrary
    } from "@/api/bookstore/library";
    import {ossAli} from '@/config/env'

    export default {
        name: "Library",
        components: {},
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
                // 用户答案表格数据
                libraryList: [],
                // 弹出层标题
                title: "",
                // 是否显示弹出层
                open: false,
                // 查询参数
                queryParams: {
                    current: 1,
                    size: 10,
                    userId: null,
                    libraryId: null,
                },
                // 表单参数
                form: {},
                // 表单校验
                rules: {
                    name: [
                        {required: true, message: "名称不能为空", trigger: "blur"}
                    ],
                    longitude: [
                        {required: true, message: "经度不能为空", trigger: "blur"}
                    ],
                    latitude: [
                        {required: true, message: "纬度不能为空", trigger: "blur"}
                    ]
                },
                imageFileList: [],
                imageUrlList: [],
            };
        },
        created() {
            this.getList();
        },
        methods: {
            /** 查询用户答案列表 */
            getList() {
                this.loading = true;
                listLibrary(this.queryParams).then(response => {
                    this.libraryList = response.data.records;
                    this.total = response.data.total;
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
                    userId: null,
                    libraryId: null,
                    createTime: null,
                    updateTime: null
                };
                this.resetForm("form");
            },
            /** 搜索按钮操作 */
            handleQuery() {
                this.queryParams.current = 1;
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
                this.single = selection.length !== 1
                this.multiple = !selection.length
            },
            /** 新增按钮操作 */
            handleAdd() {
                this.reset();
                this.open = true;
                this.title = "添加书店";
            },
            /** 修改按钮操作 */
            handleUpdate(row) {
                this.reset();
                let that = this;
                const id = row.id || this.ids
                getLibrary(id).then(response => {
                    this.form = response.data;
                    if (response.data.image) {
                        this.imageUrlList = response.data.image;
                        response.data.image.forEach(function (item) {
                            let fileName = item.substring(item.lastIndexOf('/')+1)
                            let f = {};
                            f.uid = fileName;
                            f.url = item;
                            f.name = fileName;
                            that.imageFileList.push(f);
                        })
                    }
                    this.open = true;
                    this.title = "修改书店";
                });
            },
            /** 提交按钮 */
            submitForm() {
                this.$refs["form"].validate(valid => {
                    if (valid) {
                        this.form.image = this.imageUrlList;
                        if (this.form.id != null) {
                            updateLibrary(this.form).then(response => {
                                this.msgSuccess("修改成功");
                                this.open = false;
                                this.getList();
                            });
                        } else {
                            addLibrary(this.form).then(response => {
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
                this.$confirm('是否确认删除用户答案编号为"' + ids + '"的数据项?', "警告", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                }).then(function () {
                    return delLibrary(ids);
                }).then(() => {
                    this.getList();
                    this.msgSuccess("删除成功");
                })
            },
            imageBeforeUpload(file) {
                let isRightSize = file.size / 1024 / 1024 < 2
                if (!isRightSize) {
                    this.$message.error('文件大小超过 2MB')
                } else {
                    this.loading = this.$loading({
                        lock: true,
                        text: "上传中",
                        background: "rgba(0, 0, 0, 0.7)",
                    });
                }

                return isRightSize
            },
            imageOnRemove(file, imageFileList) {
                let removeIndex;
                this.imageUrlList.forEach(function (item, index) {
                    if (item.indexOf(file.name) > 0) {
                        removeIndex = index;
                    }
                })
                if (removeIndex) {
                    this.imageUrlList.splice(removeIndex, 1);
                }
                console.log(this.imageUrlList);
            },
            uploadImageOss(file) {
                let client = new OSS({
                    region: ossAli.region,
                    endpoint: ossAli.endpoint,
                    stsToken: '',
                    accessKeyId: ossAli.accessKeyId,
                    accessKeySecret: ossAli.accessKeySecret,
                    bucket: ossAli.bucket,
                    secure: true
                });

                (() => {
                    return client.put(file.file.name, file.file)
                })()
                    .then((res) => {
                        console.log(this)
                        let result = res.url;
                        this.imageUrlList.push(result)
                        console.log(this.imageUrlList);
                        this.loading.close();
                    })
                    .catch((err) => {
                        console.log(err);
                        this.loading.close();
                    })
            }

        }
    };
</script>
