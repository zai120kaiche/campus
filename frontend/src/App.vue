<template>
  <div class="index">
    <router-view></router-view>

  </div>


</template>
<script>

import {getCurrentInstance, inject, provide} from "vue";

export default {
  mounted() {

    if (this._isMobile()) {
      provide('isPhone', true)
      console.log(inject('isPhone'));

    } else {
      provide('isPhone', false)
      console.log(inject('isPhone'));

    }
  },
  methods:{
    _isMobile() {
      let flag = navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i)
      return flag;
    }
  }
}
</script>
<style>
</style>
