<template>
  <div class="page_back page_this"></div>

  <img src="../assets/images/wave.png" alt="" class="ourpage" style="position: fixed;background-color: transparent">
  <Header></Header>
  <el-card v-for="(item, index) in list" style="margin-top: 2%;margin-left: 20%;margin-right: 20%" v-on:click="forDetail(item.pid)" >
    <el-row>
      <el-col :span="6">{{item.title}}</el-col>
      <el-col :span="7" :offset="11">{{item.time.split('T')[0]+' '}}{{item.time.split('T')[1]}}</el-col>
    </el-row>
    <div style="margin-top: 2%">
      您的评论"{{item.content}}"有了新的回复，点击前往查看
    </div>
  </el-card>
  <el-card style="margin-top: 3%;margin-left: 20%;margin-right: 20%">
    <el-pagination
        @current-change="init"
        :current-page="currentPage"
        layout="prev, pager, next"
        :total="pageTotal"
        :page-size="10"
        style="width: 100%; margin: 0"
    />
  </el-card>


  <Footer></Footer>
</template>

<script>


import Header from "@/components/Header";
import Footer from "@/components/Footer";

export default {
  name: "noteroom",
  components: {
    Header,
    Footer
  },
  data() {
    return {
      currentPage: 1,
      pageTotal: 0,
      userId: localStorage.getItem("userId"),
      list: []
    }
  },
  created() {
    this.init(1)
  },
  methods: {
    forDetail(id){
      let _this = this
      _this.$router.push({
        name: 'communitydetail',
        params: {
          communityId: id
        }
      })
    },
    init(currentPage){
      let _this = this
      let temp = {
        uid: _this.userId,
        current: currentPage,
        order: 1
      }
      _this.currentPage = currentPage
      _this.$axios.post("/notification/getList", temp).then(res=>{
        _this.list = res.data.data.records
        _this.pageTotal = res.data.data.total
        console.log(res.data.data)
      })
    }
  }
}
</script>

<style scoped>
.ourpage {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 30%;
  z-index: -1;
  border-radius: 5px;

}

.page_this {
  background-color: #eef5f8;
  z-index: -2;
}
</style>