<template>
  <div class="page_back page_this"></div>
  <img src="../assets/images/wave.png" alt="" class="ourpage"  style="position: fixed;background-color: transparent">
  <Header></Header>
</template>

<script>
import Header from "@/components/Header";
export default {
  name: "shop",
  components: {
    Header
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