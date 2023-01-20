<template>


</template>

<script>
import {ElNotification} from "element-plus";

export default {
  name: "userinfo",
  methods: {
    getUserInfo(index){
      let _this = this
      let temp = {
        id: parseInt(localStorage.getItem("userId"))
      }
      _this.$axios.post("user/index", temp).then(res =>{
        _this.userInfo = res.data.data
      })
    },
    changeUserInfo(){
      let _this = this
      _this.$axios.post('user/change', _this.userInfo).then(res =>{
        if(res.data.code == 200){
          ElNotification({
            title: 'Success',
            message: '修改成功',
            type: 'success',
          })
        } else {
          ElNotification({
            title: 'Error',
            message: '修改失败',
            type: 'error',
          })
        }
      })
    },
  }
}
</script>

<style scoped>

</style>