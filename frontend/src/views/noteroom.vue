<template>
  <div class="page_back page_this"></div>

  <img src="../assets/images/wave.png" alt="" class="ourpage" style="position: fixed;background-color: transparent">
  <Header></Header>



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
      userId: localStorage.getItem("userId")
    }
  },
  created() {
    this.init(1)
  },
  methods: {
    init(currentPage){
      let _this = this
      let temp = {
        uid: _this.userId,
        current: currentPage,
        order: 1
      }
      _this.$axios.post("/notification/getList", temp).then(res=>{
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