<template>
  <div>


    <el-drawer v-model="serviceShow" :direction="'rtl'">
      <template #header>
        <h4>客服咨询</h4>
      </template>
      <template #default>
        <el-card  style="width: 100%; height: 100%; overflow: auto">
          <div v-for="item in chatList">
            <div v-if="item.who == 0" class="chatBox chatBox-right" style="margin-left: 39%;margin-top: 1%">
              {{item.content}}
            </div>
            <div  v-if="item.who == 1" class="chatBox chatBox-left" style="margin-top: 1%">
              {{item.content}}
            </div>
          </div>
        </el-card>
      </template>
      <template #footer>
        <el-row gutter="20" style="">
          <el-col :span="15"><el-input v-model="serviceData"
                                       placeholder="请输入问题"
                                       clearable
                                       type="textarea"
                                       :rows="3"
          ></el-input></el-col>
          <el-col :span="2" >
            <el-button v-on:click="serviceSend">查询</el-button>
          </el-col>
          <el-col :span="2" :offset="2">
            <el-button v-on:click="cancle">取消</el-button>
          </el-col>

        </el-row>
      </template>
    </el-drawer>



    <el-drawer v-model="returnShow" :direction="'rtl'">
      <template #header>
        <h4>意见反馈及投诉</h4>
      </template>
      <template #default>
        <el-input v-model="returnData"
                  placeholder="请输入您要反馈的问题"
                  clearable
                  type="textarea"
                  :rows="12"
                  v-if="!showThank"
        ></el-input>
        <div v-else>
          <h4>您的意见已送达，稍后将有客服人员与您进行联系</h4>
        </div>
      </template>
      <template #footer>
        <el-row gutter="20" style="">
          <el-col :span="2" :offset="15" v-if="!showThank">
            <el-button v-on:click="returnSend">发送</el-button>
          </el-col>
          <el-col :span="2" :offset="15" v-else>
            <el-button v-on:click="showThank = false">继续反馈</el-button>
          </el-col>
          <el-col :span="2" :offset="2">
            <el-button v-on:click="returnShow = false, showThank = false">取消</el-button>
          </el-col>

        </el-row>
      </template>
    </el-drawer>
    <!-- 悬浮菜单开始 -->
    <div class="realTranslationInfos" v-drag v-if="serviceShow != true && returnShow != true">


      <div class="RealtranslationBox">
        <el-card>
          <el-row v-on:click="upper">
            <el-tooltip
                class="box-item"
                effect="dark"
                content="置顶"
                placement="left"
            >
              <el-icon><ArrowUp /></el-icon>
            </el-tooltip>
          </el-row>
          <el-row style="margin-top: 80%" v-on:click="service">
            <el-row v-on:click="upper">
              <el-tooltip
                  class="box-item"
                  effect="dark"
                  content="客服"
                  placement="left"
              >
                <el-icon><Service /></el-icon>
              </el-tooltip>
            </el-row>

          </el-row>
          <el-row style="margin-top: 80%" v-on:click="returnShow = true">
            <el-tooltip
                class="box-item"
                effect="dark"
                content="意见反馈"
                placement="left"
            >
              <el-icon><ChatLineRound /></el-icon>
            </el-tooltip>

          </el-row>
        </el-card>

      </div>
    </div>

    <!-- 悬浮菜单结束 -->
  </div>
</template>
<script>
import {ElNotification} from "element-plus";

export default {
  // 组件名字
  name: 'Tool',
  // 组件参数
  props: {
    id: {
      type: String,
      default: ''
    },
    index: {
      type: Number,
      default: 0
    },
  },
  data() {
    return {
      serviceShow: false,
      returnShow: false,
      serviceData: '',
      returnData: '',
      chatList: [],
      showThank: false
    }
  },
  mounted() {
  },
  // 组件方法
  methods: {
    openFeedbackDialog() {
    },
    upper() {
      document.documentElement.scrollTop = 0;
    },
    service() {
      let _this = this
      _this.serviceShow = true
    },
    cancle() {
      let _this = this
      _this.serviceShow = false
    },
    serviceSend() {
      let _this = this
      _this.chatList.push({who: 0, content: _this.serviceData})
      _this.$axios.post("ai/send", {request: _this.serviceData}).then(res=>{

        _this.chatList.push({who: 1, content: res.data.data})
      })
    },
    returnSend() {
      let _this = this
      let temp = {
        uid: localStorage.getItem("userId"),
        content: _this.returnData
      }
      _this.$axios.post("feedback/send", temp).then(res=>{
        ElNotification({
          title: 'Success',
          message: '发送成功',
          type: 'success',
        })
        _this.returnData = ''
        _this.showThank = true
      }).catch(res=>{
        ElNotification({
          title: 'Error',
          message: '发送失败',
          type: 'error',
        })
      })
    }
  }
}

</script>
<style scoped="scoped" lang="scss">
.realTranslationInfos {
  width: 66px;
  height: 66px;
  // background: red;
  position: fixed;
  // position: absolute;
  bottom: 56px;
  right: 20px;
  // bottom: 122px;
  // right: -30px;
  .translationContent {
    min-height: 100px;
    line-height: 23px;
    width: 338px;
    padding: 18px 74px 19px 19px;
    background: #004CB3;
    opacity: 0.80;
    border-radius: 12px;
    color: #fff;
    position: fixed;
    // position: absolute;
    right: 58px;
    bottom: 22px;
    z-index: 99999;
  }
  .RealtranslationBox {
    display: flex;
    align-items: center;
    position: absolute;
    right: 2px;
    bottom: 5px;
    z-index: 99999;
    img {
      cursor: pointer;
    }
  }
}
</style>