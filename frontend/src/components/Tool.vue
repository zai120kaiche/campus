<template>
  <div>
    <el-card v-if="serviceShow" style="width: 40%; height: 80%;
                                      position: fixed; z-index: 99999;
                                      bottom: 0; right: 0">
      <el-card  style="position: fixed; width: 36%; height: 62%; overflow: auto">
        <div v-for="item in chatList">
          <el-card v-if="item.who == 0" style="width: 60%; margin-left: 39%;margin-top: 1%">{{item.content}}</el-card>
          <el-card v-if="item.who == 1"  style="width: 60%;margin-top: 1%">{{item.content}}</el-card>
        </div>
      </el-card>
      <el-row gutter="20" style="bottom: 1%; position: fixed">
        <el-col :span="16"><el-input v-model="serviceData"
                                     placeholder="请输入问题"
                                     clearable
                                     type="textarea"
                                     :rows="3"
        ></el-input></el-col>
        <el-col :span="3" >

            <el-button v-on:click="serviceSend">查询</el-button>

        </el-col>
        <el-col :span="3" :offset="1">
          <el-button v-on:click="cancle">取消</el-button>
        </el-col>

      </el-row>


    </el-card>
    <!-- 悬浮菜单开始 -->
    <div class="realTranslationInfos" v-drag v-else>


      <div class="RealtranslationBox">
        <el-card>
          <el-row v-on:click="upper">
            <el-icon><ArrowUp /></el-icon>
          </el-row>
          <el-row style="margin-top: 80%" v-on:click="service">
            <el-icon><Service /></el-icon>
          </el-row>
          <el-row style="margin-top: 80%">
            <el-icon><ChatLineRound /></el-icon>
          </el-row>
        </el-card>

      </div>
    </div>

    <!-- 悬浮菜单结束 -->
  </div>
</template>
<script>
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
      serviceData: '',
      chatList: []
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