<template>
  <div class="page_back page_this"></div>
  <img src="../assets/images/wave.png" alt="" class="ourpage" style="position: fixed;background-color: transparent">
  <Header></Header>

  <el-card class="  animate__animated animate__fadeIn "></el-card>
</template>

<script>
import Header from "@/components/Header";
export default {
  name: "communitydetail",
  components: {
    Header
  },
  data() {
    return {
      communityId: 0
    }
  },
  created() {

  },
  methods: {
    init() {
      let _this = this
      _this.communityId = _this.$route.params.communityId
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