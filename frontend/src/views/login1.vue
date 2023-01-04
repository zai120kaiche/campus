<template>
  <page class="login1">
          <part1>
          <img alt="摘星图标" src="../assets/images/摘星.png">
          <font1 style="font-family: SourceHanSans-Bold;color:white">摘 星</font1>
          <font2 style="font-family: SourceHanSans-Bold;color:white">S T A R</font2>
            <font3 style="font-family:SourceHanSans-Bold;color:white">校园生活平台</font3>
        </part1>
          <UI><img alt="UI" src="../assets/images/UI.png"></UI>
          <UI><img alt="UI" src="../assets/images/UI.png"></UI>
          <servebar><span style="font-family: SourceHanSans-Normal">智能客服</span> | <img alt="客服" src="../assets/images/客服.png"><span style="font-family: SourceHanSans-Normal;position:absolute;padding-left:106px;padding-top:3px">2023@zhaixin</span></servebar>
    <pic style="position: absolute;width:479px;height:210px;margin-left:458px;margin-top:253px"><img alt="一起走进校园生活！" src="../assets/images/校园.png"></pic>
    <part3>
      <border style="position:absolute;width:430px;height:410px;left:123px;top:42px;background: white;border-radius: 12px;">
        <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal">
          <el-menu-item index="1" style="font-family: 'SourceHanSans-Normal';font-size: 24pt;"><a href="/">密码登录</a></el-menu-item>
          <el-menu-item index="2" style="font-family: 'SourceHanSans-Normal';font-size: 24pt;color: #D8D8D8"><a href="/login2">短信登录</a></el-menu-item>
        </el-menu>
        <ft style="font-family: 'SourceHanSans-Normal';font-size:12pt;left:15px;position:absolute">没有账号？<a href="/register">注册</a></ft>
        <form>
          <input1 style="left:0px;top:16px"><el-input size="large" v-model="phoneNumber" placeholder="手机" clearable/></input1>
          <input1 style="left:0px;top:110px"><el-input size="large" type="password" v-model="passWord" placeholder="密码" clearable show-password/></input1>
        </form>
        <button1><el-button type="primary" size="large" plain>登录</el-button></button1>
      </border>

      <ft style="position:absolute;left:207px;top:424px;font-family:'SourceHanSans-Normal';font-size:12pt;color:#C4CDD5;">
      注册登录即代表同意<a style="color:#1449AA" href="/userAgreement">用户协议</a>和<a style="color:#1449AA" href="/privacy">隐私政策</a>
    </ft>
    </part3>
  </page>
</template>

<script>
export default {
  name: 'login1',
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
font3 {
  /*校园生活平台*/
  position:absolute;
  left:123px;
  bottom:4px;
  width:154px;
  height:49px;
  font-size:18pt;
  line-height:0px;
  letter-spacing:0px;
}
page {
  /* 登录页面 */
  position: absolute;
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
part3 {
  position:absolute;
  margin-left: 909px;
  margin-top: 222px;
  width:556px;
  height: 569px;
}
UI{
  /* UI */
  position: absolute;
  left: 99px;
  top: 271px;
  width: 624px;
  height: 452px;
  mix-blend-mode: multiply;
}
servebar {
  position:absolute;
  width:218px;
  height:16px;
  left:155px;
  top:785px;
}
form {
  position:absolute;
  width: 377px;
  height: 147px;
  left: 32px;
  top:136px;
}
input1 {
  position: absolute;
  width:350px;
  height:37px;
}
button1 {
  position: absolute;
  width:336px;
  height:42px;
  left:165px;
  top:304px;
}
</style>
