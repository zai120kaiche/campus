<template>
  <el-container class="page_back">
    <el-aside width="13%" style="background-color: #545c64">
      <el-menu
          active-text-color="#000000"
          background-color="#545c64"
          class="el-menu-vertical-demo"
          default-active="1"
          text-color="#fff"

      >
        <el-menu-item index="1" @click="select(1)">
          <el-icon><icon-menu /></el-icon>
          <span>社区分类</span>
        </el-menu-item>
        <el-menu-item index="2" @click="select(2)">
          <el-icon><icon-menu /></el-icon>
          <span>Navigator two</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-main>
      <div v-if="selectOption == 1">
        <div style="font-weight: bold; font-size: xx-large">社区分类</div>
        <el-input
            v-model="addType.typename"
            placeholder="请输入要添加的社区类目"
            class="input-with-select"
            style="margin-top: 3%; width: 50%"
        >

          <template #append>
            <el-button v-on:click="typeAdd">
              <el-icon>
                <Finished />
              </el-icon>
            </el-button>
          </template>
        </el-input>
        <el-table :data="typeTableData"
                  border style="width: 100%"
                  :row-key="typeTableRow"
                  empty-text="NAN"
                  >
          <el-table-column prop="id" label="编号" width="180" />
          <el-table-column prop="typename" label="类目" width="180" />
          <el-table-column prop="count" label="数目" width="180" />
          <el-table-column fixed="right" label="操作" width="120">
            <template #default="scope">
              <el-popconfirm title="确定删除该社区类目吗?"
                             @confirm="typeDelete(scope.row)"
                             confirm-button-text="是的"
                             cancel-button-text="取消"
                             >
                <template #reference>
                  <el-button link type="primary" size="small">删除</el-button>
                </template>
              </el-popconfirm>

              <el-button link type="primary" size="small" @click="typeEdit">编辑</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="example-pagination-block">
          <el-pagination
              @current-change="typeTableChange"
              :current-page="typeCurrentPage"
              layout="prev, pager, next"
              :total="typeTotal"
              :page-size="10"
              />
        </div>
      </div>
      <div v-if="selectOption == 2">
        <div style="font-weight: bold; font-size: xx-large">商品分类</div>
      </div>
    </el-main>
  </el-container>
</template>

<script>
import {
  Document,
  Menu as IconMenu,
  Location,
  Setting,
} from '@element-plus/icons-vue'
import { ElNotification } from 'element-plus'
export default {
  name: "control",
  data() {

    return {
      selectOption: 1,
      addType: {
        id: null,
        typename: '',
        count: null
      },
      typeTableData: [],
      typeCurrentPage: 0,
      typePages: 0,
      typeTotal: 0
    }
  },
  created() {
    this.typeList(0)

  },
  methods: {
    select(key) {
      let _this = this
      _this.selectOption = key
    },
    typeAdd() {
      let _this = this
      _this.$axios.post("/type/add", _this.addType).then(res => {
        _this.addType.typename = ''
        _this.typeList(_this.typeCurrentPage)
        ElNotification({
          title: 'Success',
          message: '社区类目添加成功',
          type: 'success',
        })
      })
    },
    typeTableChange(currentPage){
      let _this = this
      _this.typeCurrentPage = currentPage
      _this.typeList(currentPage)
    },
    typeList(currentPage) {
      let _this = this
      _this.$axios.get("type/list/?currentPage=" + currentPage).then(res =>{
        _this.typeTableData = res.data.data.records
        _this.typePages = res.data.data.pages
        _this.typeTotal = res.data.data.total
      })
    },

    typeDelete(data) {
      let _this = this
      _this.$axios.post("/type/delete", data).then(res =>{
        _this.typeList(_this.typeCurrentPage)
      })
    }
  }
}
</script>

<style scoped>

</style>