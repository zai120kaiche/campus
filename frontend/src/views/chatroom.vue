<template>
  <div class="page_back page_this"></div>

  <img src="../assets/images/wave.png" alt="" class="ourpage" style="position: fixed;background-color: transparent">
  <Header></Header>
  <el-container style=" width: 100%;height: 80%;position: absolute">
    <el-aside width="20%" style="margin-left: 15%;margin-right: 3%">
      <el-card style="height: 100%"></el-card>
    </el-aside>
    <el-main style="padding: 0; margin-right: 15%">
      <el-card style="height: 100%"></el-card>
    </el-main>
  </el-container>
  <Footer></Footer>
</template>

<script>
import Header from "@/components/Header";
import Footer from "@/components/Footer";

export default {
  name: "chatroom",
  components: {
    Header,
    Footer
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