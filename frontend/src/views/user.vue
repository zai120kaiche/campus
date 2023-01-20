<template>
  <div class="page_back page_this"></div>
  <img src="../assets/images/wave.png" alt="" class="ourpage" style="position: fixed;background-color: transparent">
  <Header></Header>
  <el-card style="margin-top: 4%; margin-left: 10%; margin-right: 10%"></el-card>
  <el-container  style="margin-top: 1%; margin-left: 10%; margin-right: 10%">
    <el-main style="padding: 0; margin-right: 2%">
      <el-card></el-card>
      <el-card style="margin-top: 2%"></el-card>
    </el-main>
    <el-aside width="40%">
      <el-card></el-card>
      <el-card style="margin-top: 4%"></el-card>
    </el-aside>
  </el-container>

</template>

<script>
import Header from "@/components/Header";
import {ElNotification} from "element-plus";
export default {
  name: "user",
  created() {
    this.getUserInfo(localStorage.getItem("userId"))
  },
  components: {
    Header
  },
  data() {
    return {
      userInfo: {}
    }
  },
  methods: {
    getUserInfo(index){
      let _this = this
      let temp = {
        id: localStorage.getItem("userId")
      }
      _this.$axios.post("user/index", temp).then(res =>{
        _this.userInfo = res.data.data
      })
    },

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