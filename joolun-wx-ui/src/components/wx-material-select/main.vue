<template>
  <div v-if="objData.repType == 'image'">
    <div class="waterfall" v-loading="tableLoading">
      <div class="waterfall-item" v-for="item in tableData" :key="item.mediaId">
        <img class="material-img" :src="item.url">
        <p class="item-name">{{item.name}}</p>
        <el-row class="ope-row">
          <el-button size="mini" type="success" @click="selectMaterial(item)">选择<i class="el-icon-circle-check el-icon--right"></i></el-button>
        </el-row>
      </div>
    </div>
    <div v-if="tableData.length <=0 && !tableLoading" class="el-table__empty-block">
      <span class="el-table__empty-text">暂无数据</span>
    </div>
    <span slot="footer" class="dialog-footer">
      <el-pagination
        @size-change="sizeChange"
        @current-change="currentChange"
        :current-page.sync="page.currentPage"
        :page-sizes="[10, 20]"
        :page-size="page.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="page.total"
        class="pagination"
      >
      </el-pagination>
    </span>
  </div>
  <div v-else-if="objData.repType == 'voice'">
    <avue-crud ref="crud"
               :page="page"
               :data="tableData"
               :table-loading="tableLoading"
               :option="tableOptionVoice"
               @on-load="getPage"
               @size-change="sizeChange"
               @refresh-change="refreshChange">
      <template slot-scope="scope"
                slot="menu">
        <el-button type="text"
                   icon="el-icon-circle-plus"
                   size="small"
                   plain
                   @click="selectMaterial(scope.row)">选择</el-button>
      </template>
    </avue-crud>
  </div>
  <div v-else-if="objData.repType == 'video'">
    <avue-crud ref="crud"
               :page="page"
               :data="tableData"
               :table-loading="tableLoading"
               :option="tableOptionVideo"
               @on-load="getPage"
               @size-change="sizeChange"
               @refresh-change="refreshChange">
      <template slot-scope="scope"
                slot="menu">
        <el-button type="text"
                   icon="el-icon-circle-plus"
                   size="small"
                   plain
                   @click="selectMaterial(scope.row)">选择</el-button>
      </template>
    </avue-crud>
  </div>
  <div v-else-if="objData.repType == 'news'">
    <div class="waterfall" v-loading="tableLoading">
      <div class="waterfall-item" v-for="item in tableData" :key="item.mediaId" v-if="item.content && item.content.articles">
        <WxNews :objData="item.content.articles"></WxNews>
        <el-row class="ope-row">
          <el-button size="mini" type="success" @click="selectMaterial(item)">选择<i class="el-icon-circle-check el-icon--right"></i></el-button>
        </el-row>
      </div>
    </div>
    <div v-if="tableData.length <=0 && !tableLoading" class="el-table__empty-block">
      <span class="el-table__empty-text">暂无数据</span>
    </div>
    <span slot="footer" class="dialog-footer">
      <el-pagination
        @size-change="sizeChange"
        :current-page.sync="page.currentPage"
        :page-sizes="[10, 20]"
        :page-size="page.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="page.total"
        class="pagination"
      >
      </el-pagination>
    </span>
  </div>
</template>

<script>
  import { getPage, getMaterialVideo } from '@/api/wxmp/wxmaterial'
  import { tableOptionVoice } from '@/const/crud/wxmp/wxmaterial_voice'
  import { tableOptionVideo } from '@/const/crud/wxmp/wxmaterial_video'
  import WxNews from '@/components/wx-news/main.vue'

  export default {
    name: "wxMaterialSelect",
    components: {
      WxNews
    },
    props: {
      objData:{
        type:Object
      }
    },
    data() {
      return {
        tableLoading: false,
        tableData: [],
        page: {
          total: 0, // 总页数
          currentPage: 1, // 当前页数
          pageSize: 20, // 每页显示多少条
          ascs:[],//升序字段
          descs:[]//降序字段
        },
        tableOptionVoice: tableOptionVoice,
        tableOptionVideo: tableOptionVideo,
      }
    },
    created() {
      this.getPage(this.page)
    },
    mounted(){

    },
    computed: {

    },
    methods:{
      selectMaterial(item){
        this.$emit('selectMaterial', item)
      },
      getPage(page, params) {
        this.tableLoading = true
        getPage(Object.assign({
          current: page.currentPage,
          size: page.pageSize,
          type:this.objData.repType
        }, params)).then(response => {
          this.tableData = response.data.items
          this.page.total = response.data.totalCount
          this.page.currentPage = page.currentPage
          this.page.pageSize = page.pageSize
          this.tableLoading = false
        })
      },
      sizeChange(val) {
        this.page.currentPage = 1
        this.page.pageSize = val
        this.getPage(this.page)
      },
      currentChange(val) {
        this.page.currentPage = val
        this.getPage(this.page)
      },
      /**
       * 刷新回调
       */
      refreshChange(page) {
        this.getPage(this.page)
      }
    }
  };
</script>

<style lang="scss" scoped>
  /*瀑布流样式*/
  .waterfall {
    width: 100%;
    column-gap:10px;
    column-count: 5;
    margin: 0 auto;
  }
  .waterfall-item {
    padding: 10px;
    margin-bottom: 10px;
    break-inside: avoid;
    border: 1px solid #eaeaea;
  }
  .material-img {
    width: 100%;
  }
  p {
    line-height: 30px;
  }
  @media (min-width: 992px) and (max-width: 1300px) {
    .waterfall {
      column-count: 3;
    }
    p {
      color:red;
    }
  }
  @media (min-width: 768px) and (max-width: 991px) {
    .waterfall {
      column-count: 2;
    }
    p {
      color: orange;
    }
  }
  @media (max-width: 767px) {
    .waterfall {
      column-count: 1;
    }
  }
  /*瀑布流样式*/
</style>
