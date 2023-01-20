<template>
  <el-container class="page_back page_this">
    <el-header style="margin-top: 5%; margin-left: 10%">
      <el-row>
        <el-col :span="12">
          <el-row>
            <el-col :span="4"><el-image style="width: 80%; height: 80%; z-index: 1; background-color: transparent" :src="require('../assets/images/logo.png')"
                                        :fit="'fill'"></el-image></el-col>
          </el-row>
        </el-col>

      </el-row>
    </el-header>
    <el-container style="margin-left: 10%; margin-right: 10%">
      <el-aside width="60%">


        <el-image style="width: 100%; height: 90%; z-index: -1;" :src="require('../assets/images/login_back.png')"
                  :fit="'fill'"/>

      </el-aside>
      <el-main>
        <el-card v-if="loginOrRegister" style="height: 100%; ">
          <el-menu
              :default-active="activeIndex"
              class="el-menu-demo"
              mode="horizontal"
              @select="handleSelect"

              active-text-color="#524d4c"
          >
            <el-menu-item index="1">密码登录</el-menu-item>

            <el-menu-item index="2">短信登录</el-menu-item>
          </el-menu>
          <div v-if="activeIndex == 1">
            <el-row style="margin-top: 3%">
              <el-col :span="6" :offset="2">没有账号？</el-col>
              <el-col :span="4" style="color: deepskyblue" @click="register()">注册</el-col>
            </el-row>
            <el-row>
              <el-col :offset="2">
                <el-input style="margin-top: 8%; width: 80%" v-model="loginData.phoneOrEmail" placeholder="手机号/邮箱"/>
              </el-col>
            </el-row>
            <el-row>
              <el-col :offset="2">
                <el-input style="margin-top: 5%; width: 80%" v-model="loginData.password" placeholder="密码"/>
              </el-col>
            </el-row>
            <el-row>
              <el-col :offset="2">
                <el-button style="margin-top: 5%; width: 80%" color="#4a86e8" v-on:click="loginSend">登录</el-button>
              </el-col>
            </el-row>

          </div>
          <div v-else>
            <el-row style="margin-top: 3%">
              <el-col :span="6" :offset="2">没有账号？</el-col>
              <el-col :span="4" style="color: deepskyblue" @click="register()">注册</el-col>
            </el-row>
            <el-row>
              <el-col :offset="2">
                <el-input style="margin-top: 3%; width: 80%" v-model="sendData.phoneOrEmail" placeholder="手机号/邮箱">
                  <template #prepend>+ 86</template>
                </el-input>
              </el-col>
            </el-row>
            <!--            <el-row style="margin-top: 3%; width: 80%; margin-left: 1%">-->
            <!--              <el-col :offset="2">-->
            <!--                <Slider status="{{status}}"-->
            <!--                        :successFun="smsSendSucc()"-->
            <!--                        :errorFun="smsSendFail()"></Slider>-->
            <!--              </el-col>-->
            <!--            </el-row>-->

            <el-row>
              <el-col :offset="2">
                <el-input style="margin-top: 3%; width: 48%" v-model="checkNumber" placeholder="验证码"/>
                <el-button v-if="sms_send == true" style="margin-top: 3%; width: 29%; margin-left: 3%" color="#4a86e8"
                           @click="sendSms2()">发送验证码
                </el-button>
                <el-button v-else style="margin-top: 3%; width: 29%; margin-left: 3%" color="#4a86e8" disabled>
                  {{ sms_submit }}
                </el-button>
              </el-col>
            </el-row>


            <el-row>
              <el-col :offset="2">
                <el-button style="margin-top: 5%; width: 80%" color="#4a86e8" v-on:click="loginBySms">登录</el-button>
              </el-col>
            </el-row>
          </div>
          <el-row style="margin-top: 4%">
            <el-col :span="12" :offset="2">
              <el-checkbox label="下次自动登录" name="type" style="width: 40%"/>

            </el-col>
            <el-col :span="6" :offset="1">

              <el-button style="color: deepskyblue; border-color: transparent">忘记密码</el-button>
            </el-col>
          </el-row>
          <div style="text-align: center">

            注册登录即代表同意
            <button style="color: deepskyblue; border-color: transparent; background-color: transparent" @click="dialogVisible = true">用户协议</button>
            和
            <button style="color: deepskyblue; border-color: transparent; background-color: transparent" @click="secretVisible = true">隐私政策</button>


          </div>



        </el-card>
        <el-card v-else style="height: 100%;">
          <el-row>
            <el-col :offset="1">
              <div style="font-weight: bold; font-size: 20px;">欢迎注册</div>
            </el-col>
          </el-row>
          <el-row style="margin-top: 3%">
            <el-col :span="6" :offset="2">已有账号？</el-col>
            <el-col :span="4" style="color: deepskyblue" @click="login()">登录</el-col>
          </el-row>
          <el-row>
            <el-col :offset="2">用户名</el-col>
          </el-row>
          <el-row>
            <el-col :offset="2">
              <el-input style="margin-top: 1%; width: 80%" v-model="registerData.username" placeholder="用户名"/>
            </el-col>
          </el-row>
          <el-row>
            <el-col :offset="2">手机号/邮箱</el-col>
          </el-row>
          <el-row>
            <el-col :offset="2">
              <el-input style="margin-top: 1%; width: 80%" v-model="sendData.phoneOrEmail" placeholder="手机号/邮箱"/>
            </el-col>
          </el-row>
          <el-row>
            <el-col :offset="2">密码</el-col>
          </el-row>
          <el-row>
            <el-col :offset="2">
              <el-input show-password style="margin-top: 1%; width: 80%" v-model="registerData.password" placeholder="密码"/>
            </el-col>
          </el-row>
          <el-row>
            <el-col :offset="2">验证码</el-col>
          </el-row>
          <el-row>
            <el-col :offset="2">
              <el-input style="margin-top: 1%; width: 48%" v-model="checkNumber" placeholder="验证码"/>
              <el-button v-if="sms_send == true" style="margin-top: 1%; width: 29%; margin-left: 3%" color="#4a86e8"
                         @click="sendSms()">发送验证码
              </el-button>
              <el-button v-else style="margin-top: 1%; width: 29%; margin-left: 3%" color="#4a86e8" disabled>
                {{ sms_submit }}
              </el-button>
            </el-col>
          </el-row>
          <el-row>
            <el-col :offset="2">
              <el-button style="margin-top: 5%; width: 80%" color="#4a86e8" v-on:click="smsCheck">注册</el-button>
            </el-col>
          </el-row>
          <div style="text-align: center; margin-top: 3%">

            注册登录即代表同意
            <button style="color: deepskyblue; border-color: transparent; background-color: transparent" @click="dialogVisible = true">用户协议</button>
            和
            <button style="color: deepskyblue; border-color: transparent; background-color: transparent" @click="secretVisible = true">隐私政策</button>


          </div>


        </el-card>
      </el-main>
    </el-container>
    <el-footer style="margin-bottom: 5%; margin-left: 10%">
      <el-row>
        <el-col :span="6">
          <el-row>
            <el-col :span="12">智能客服 | <el-icon style="padding-top: 5%"><User /></el-icon></el-col>
            <el-col :span="11" :offset="1">xingyuls@163.com</el-col>
          </el-row>
        </el-col>

      </el-row>
    </el-footer>
  </el-container>
  <el-dialog
      v-model="dialogVisible"
      title="用户协议"
      width="30%"
      :before-close="handleClose"
  >
    <span>用户协议</span>
    <template #footer>
              <span class="dialog-footer">
                <el-button @click="dialogVisible = false">关闭</el-button>
                <el-button type="primary" @click="dialogVisible = false">
                  确认
                </el-button>
              </span>
    </template>
  </el-dialog>
  <el-dialog
      v-model="secretVisible"
      title="隐私政策"
      width="30%"
      :before-close="handleClose"
  >
    <span>隐私政策</span>
    <template #footer>
              <span class="dialog-footer">
                <el-button @click="secretVisible = false">关闭</el-button>
                <el-button type="primary" @click="secretVisible = false">
                  确认
                </el-button>
              </span>
    </template>
  </el-dialog>
</template>

<script>
import Slider from '@/components/Slider.vue'
import {ElMessageBox, ElNotification} from 'element-plus'
export default {
  name: "login",
  components: {
    Slider
  },
  data() {
    return {
      checkNumber: '',
      activeIndex: 1,
      id: '',
      password: '',
      loginData: {
        phoneOrEmail: '',
        password: ''
      },
      phoneOrEmail: '',
      registerData: {
        id: null,
        username: '',
        password: '',
        email: '',
        phone: ''
      },
      sendData: {
        identifier: 0,
        phoneOrEmail: ''
      },
      ruleForm: {
        type: []
      },
      //验证码倒计时
      sms_send: true,
      sms_submit: 30,
      //滑块验证码
      status: false,
      //login by sms
      sliderCheck: false,
      dialogVisible: false,
      secretVisible: false,
      //flag判断注册还是登录
      loginOrRegister: true,

    }
  },
  methods: {
    //发送验证码
    sendSms() {
      let _this = this
      if(_this.sendData.phoneOrEmail == '' || _this.registerData.username == ''){
        ElNotification({
          title: 'Error',
          message: '账号不能为空',
          type: 'error',
        })
      } else {
        if(_this.sendData.phoneOrEmail.search("@") != -1){

          _this.$axios.post('/identify/send', _this.sendData).then(res =>{
            ElNotification({
              title: 'Success',
              message: '验证码发送成功',
              type: 'success',
            })
          })
          _this.sms_send = false
          _this.sms_submit = 60
          var times = setInterval(() => {
            _this.sms_submit--; //递减
            if (_this.sms_submit <= 0) {
              // <=0 变成获取按钮
              _this.sms_send = true;
              clearInterval(times);
            }
          }, 1000); //1000毫秒后执行
        } else {
          if(_this.sendData.phoneOrEmail.length != 11){
            ElNotification({
              title: 'Error',
              message: '手机格式不正确',
              type: 'error',
            })
          } else {
            _this.$axios.post('/identify/send', _this.sendData).then(res =>{
              ElNotification({
                title: 'Success',
                message: '验证码发送成功',
                type: 'success',
              })
            })
            _this.sms_send = false
            _this.sms_submit = 60
            var times = setInterval(() => {
              _this.sms_submit--; //递减
              if (_this.sms_submit <= 0) {
                // <=0 变成获取按钮
                _this.sms_send = true;
                clearInterval(times);
              }
            }, 1000); //1000毫秒后执行
          }
        }
      }



    },
    sendSms2() {
      let _this = this
      console.log(_this.sliderCheck)

      if(_this.sendData.phoneOrEmail == ''){
        ElNotification({
          title: 'Error',
          message: '账号不能为空',
          type: 'error',
        })
      } else {
        if(_this.sendData.phoneOrEmail.search("@") != -1){

          _this.$axios.post('/identify/send', _this.sendData).then(res =>{
            ElNotification({
              title: 'Success',
              message: '验证码发送成功',
              type: 'success',
            })
          })
          _this.sms_send = false
          _this.sms_submit = 60
          var times = setInterval(() => {
            _this.sms_submit--; //递减
            if (_this.sms_submit <= 0) {
              // <=0 变成获取按钮
              _this.sms_send = true;
              clearInterval(times);
            }
          }, 1000); //1000毫秒后执行
        } else {
          if(_this.sendData.phoneOrEmail.length != 11){
            ElNotification({
              title: 'Error',
              message: '手机格式不正确',
              type: 'error',
            })
          } else {
            _this.$axios.post('/identify/send', _this.sendData).then(res =>{
              ElNotification({
                title: 'Success',
                message: '验证码发送成功',
                type: 'success',
              })
            })
            _this.sms_send = false
            _this.sms_submit = 60
            var times = setInterval(() => {
              _this.sms_submit--; //递减
              if (_this.sms_submit <= 0) {
                // <=0 变成获取按钮
                _this.sms_send = true;
                clearInterval(times);
              }
            }, 1000); //1000毫秒后执行
          }
        }

      }

    },
    smsSendSucc() {
      let _this = this
      _this.sliderCheck = true
      console.log(_this.sliderCheck)
    },
    smsSendFail() {
      let _this = this
      console.log(_this.sliderCheck)
    },
    smsCheck() {
      let _this = this
      _this.sendData.identifier = _this.checkNumber
      _this.$axios.post('/identify/check', _this.sendData).then(res => {
        if(res.data.code == 200){
          _this.registerSend()
        } else {
          ElNotification({
            title: 'Error',
            message: '验证码不正确',
            type: 'error',
          })
        }
      })
    },
    registerSend() {
      let _this = this
      if(_this.sendData.phoneOrEmail.search("@") != -1){
        _this.registerData.email = _this.sendData.phoneOrEmail
      } else {
        _this.registerData.phone = _this.sendData.phoneOrEmail
      }
      _this.$axios.post('/register', _this.registerData).then(res =>{
        if(res.data.data == 1){
          ElNotification({
            title: 'Error',
            message: '账号已存在，请直接登录',
            type: 'error',
          })
        } else {
          ElNotification({
            title: 'Success',
            message: '注册成功',
            type: 'success',
          })
        }
        _this.login()
      })
    },
    loginSend() {
      let _this = this
      _this.$axios.post('login', _this.loginData).then(res =>{
        console.log(res.data.data)
        localStorage.setItem("userId", res.data.data.id)
        localStorage.setItem("userAvatar", res.data.data.avatar)
        this.$store.commit('SET_INDEX', 3)
        _this.$router.push('/user')
      })
    },
    loginBySms() {
      let _this = this
      _this.sendData.identifier = _this.checkNumber
      _this.$axios.post('/identify/check', _this.sendData).then(res => {
        if(res.data.code == 200){
          _this.loginSendBySms()
        } else {
          ElNotification({
            title: 'Error',
            message: '验证码不正确',
            type: 'error',
          })
        }
      })
    },
    loginSendBySms() {
      let _this = this
      let temp = {
        phoneOrEmail: _this.sendData.phoneOrEmail,
        flag: 1,
        password: 1
      }
      _this.$axios.post('/login', temp).then(res =>{
        console.log(res.data.data)
        localStorage.setItem("userId", res.data.data.id)
        localStorage.setItem("userAvatar", res.data.data.avatar)
        this.$store.commit('SET_INDEX', 3)
        _this.$router.push('/user')
      })
    },
    handleSelect(key) {

      let _this = this
      _this.activeIndex = key
    },
    handleClose() {
      ElMessageBox.confirm('你确定要关闭对话框吗?')
          .then(() => {
            done()
          })
          .catch(() => {
            // catch error
          })
    },
    //注册
    register() {
      let _this = this
      _this.loginOrRegister = false
    },
    //登录
    login() {
      let _this = this
      _this.loginOrRegister = true
    }
  }
}
</script>

<style scoped>
.page_this {
  background-image: linear-gradient(45deg, rgba(0, 200, 255, 0.4) 0%, rgba(228, 90, 225, 0.1), rgba(239, 239, 239) 100%);
}

.pic_this {
  position: absolute;
  top: 0;
  left: 0;

  z-index: -1;
  border-radius: 5px;
}
</style>