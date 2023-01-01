<template>
  <page>
    <div class="common-layout">
      <el-container width="1600px" height="900px">
        <el-aside>
          <part1>
          <img alt="摘星图标" src="../assets/images/摘星.png">
          <font1 style="font-family: SourceHanSans-Bold;color:white">摘 星</font1>
          <font2 style="font-family: SourceHanSans-Bold;color:white">S T A R</font2>
        </part1>
          <UI><img alt="UI" src="../assets/images/UI.png"></UI>
        </el-aside>
        <el-main></el-main>
      </el-container>
    </div>
  </page>
</template>

<script>
export default {
  name: 'HomeView',
  components: {

  }
}
</script>

<style>
part1 {
  /* 组合1 */
  position: absolute;
  left: 145px;
  top: 77px;
  width: 277px;
  height: 77px;
}
font1 {
  /* 摘 星 */
  position: absolute;
  left: 67px;
  top: 17px;
  width: 78px;
  height: 49px;
  font-size: 14pt;
  line-height: 0px;
  letter-spacing: 0px;
}
font2 {
  /* S T A R */
  position: absolute;
  left: 61px;
  top: 34px;
  width: 67px;
  height: 49px;
  font-size: 12pt;
  line-height: 0px;
  letter-spacing: 0px;
}
page {
  /* 登录页面 */
  position: fixed;
  width: 1600px;
  height: 900px;
  background: linear-gradient(0deg, #F3F8FF, #F3F8FF), linear-gradient(180deg, rgba(51,125,217,0.50) 2%, rgba(83,148,229,0.50) 33%, rgba(155,199,255,0.27) 100%);
  background-blend-mode: normal,multiply;
}
part2 {
  /* 组 2 */
  position: absolute;
  left: 155px;
  top: 785px;
  width: 218px;
  height: 16px;
  border: 1px solid #3D3D3D;
}
UI{
  /* UI */
  position: absolute;
  left: 50px;
  top: 140px;
  width: 312px;
  height: 226px;
  mix-blend-mode: multiply;
}
</style>
