<template>
  <div class="app-container">
    <avue-crud ref="crud"
               :page="page"
               :data="tableData"
               :table-loading="tableLoading"
               :option="tableOption"
               :before-open="beforeOpen"
               :upload-before="uploadBefore"
               v-model="form"
               @on-load="getPage"
               @row-update="handleUpdate"
               @row-save="handleSave"
               @row-del="handleDel"
               @search-change="searchChange"
               @selection-change="selectionChange"

    >
    </avue-crud>

  </div>
</template>
<script>
import {listConfig, getConfig, delConfig, addConfig, updateConfig} from "@/api/vip/config";


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
        dialogWidth: '88%',
        selection: true,
        searchMenuSpan: 6,
        column: [
          {
            label: '会员价格',
            prop: 'price',
            type: 'number',
            display: true
          },
          {
            label: '普通会员返现价格',
            prop: 'cashBackAmount',
            type: 'number',
            display: true
          },
          {
            label: '超级会员返现价格',
            prop: 'superCashBackAmount',
            type: 'number',
            display: true
          },
          {
            label: '宣传图片',
            prop: 'imgUrl',
            type: 'upload',
            listType: 'picture-img',
            width: 250,
            rules: [{
              required: true,
              message: '图片不能为空',
              trigger: 'change'
            }],
            oss: 'ali',
            propsHttp: {
              fileName: '39329329239239.png'
            },
            loadText: '附件上传中，请稍等',
            tip: '只能上传jpg/png文件，且不超过50kb'
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
    beforeOpen(done, type) {
      if (type == 'add') {
        done()
      } else if (type == 'edit') {
        this.tableLoading = true
        getConfig(this.form.id).then(response => {

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
      listConfig(Object.assign({
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
        return delConfig(row.id)
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
      updateConfig(row).then(data => {
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
      addConfig(row).then(data => {
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

    uploadBefore(file, done, loading, column) {
      console.log(file, column)
      //如果你想修改file文件,由于上传的file是只读文件，必须复制新的file才可以修改名字，完后赋值到done函数里,如果不修改的话直接写done()即可
      let timeStamp = Date.now().toString();
      let fileName = file.name.substring(0, file.name.lastIndexOf("."))
      let ext = file.name.substring(file.name.lastIndexOf("."), file.name.length)
      fileName = fileName + timeStamp + ext;
      let newFile = new File([file], fileName, {type: file.type});
      done(newFile)
    }
  }

}


</script>
