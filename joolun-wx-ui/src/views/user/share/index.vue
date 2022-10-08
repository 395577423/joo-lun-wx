<template>
  <div class="app-container">
    <avue-crud ref="crud"
               :page="page"
               :data="tableData"
               :table-loading="tableLoading"
               :option="tableOption"
               v-model="form"
               @on-load="getPage"
               @refresh-change="refreshChange"
               @search-change="searchChange">

    </avue-crud>
  </div>
</template>
<script>
    import {listShare} from "@/api/user/share";

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
                },
                tableLoading: false,
                paramsSearch: {},
                tableOption: {
                    border: true,
                    stripe: true,
                    menuAlign: 'center',
                    align: 'center',
                    menuType: 'text',
                    searchShow: true,
                    printBtn: false,
                    delBtn: false,
                    editBtn: false,
                    addBtn: false,
                    dialogWidth: '88%',
                    selection: true,
                    searchMenuSpan: 6,
                    column: [
                        {
                            label: '昵称',
                            prop: 'nickName',
                            type: 'input',
                            display: false,
                            search: true
                        },
                        {
                            label: '下级人数',
                            prop: 'subCount',
                            type: 'input',
                            display: false
                        },
                        {
                            label: '会员人数',
                            prop: 'memberCount',
                            type: 'input',
                            display: false
                        },
                        {
                            label: '总返佣',
                            prop: 'totalAmount',
                            type: 'input',
                            display: false
                        },
                        {
                            label: '已返佣',
                            prop: 'completedAmount',
                            type: 'input',
                            display: false
                        },
                        {
                            label: '已提现',
                            prop: 'withDrawAmount',
                            type: 'input',
                            display: false
                        }

                    ]
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
            refreshChange() {
                this.getPage(this.page, this.paramsSearch)
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
                listShare(Object.assign({
                    pageNum: page.currentPage,
                    pageSize: page.pageSize
                }, params, this.paramsSearch)).then(response => {
                    let tableData = response.data
                    this.tableData = tableData
                    this.page.total = response.total
                    this.page.currentPage = page.currentPage
                    this.page.pageSize = page.pageSize
                    this.tableLoading = false
                }).catch(() => {
                    this.tableLoading = false
                })
            },
        }

    }

</script>
