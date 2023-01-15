<template>
  <div class="page_back page_this">
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
  </div>

</template>

<script>
export default {
  name: "user"
}
</script>

<style scoped>
.page_this {
  background-image: linear-gradient(45deg, rgba(0, 200, 255, 0.4) 0%, rgba(228, 90, 225, 0.1), rgba(239, 239, 239) 100%);
}
</style>