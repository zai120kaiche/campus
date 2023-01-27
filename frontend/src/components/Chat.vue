<template>
  <el-drawer v-if="show" v-model="showChat" :direction="'ltr'" :before-close="handleClose">
    <template #header>
      <h4>和{{chatInit.toUser.username}}的对话</h4>
    </template>
    <template #default>

      <div v-for="item in chatList">
        <div v-if="item.mine" class="chatBox chatBox-right" style="margin-left: 39%;margin-top: 1%">
          {{item.content}}

        </div>

        <div  v-else class="chatBox chatBox-left" style="margin-top: 1%">
          {{item.content}}
        </div>
        <div v-if="item.mine" style="margin-left: 39%">
          {{item.time.split("T")[0]}}
        </div>
        <div v-else>
          {{item.time.split("T")[0]}}
        </div>

      </div>
      <div v-if="chatInit.tradeId">
        <el-card>
          <el-image
              v-if="chatInit.tradeDetail.pic != 'undefined'"
              :src="chatInit.tradeDetail.pic.length == 1?chatInit.tradeDetail.pic:chatInit.tradeDetail.pic[0]"
              class="image"
              style="border-radius: 5px"
          />
          <div>{{chatInit.tradeDetail.content}}</div>
          <div>{{chatInit.tradeDetail.date.split("T")[0]}}</div>
        </el-card>
      </div>
    </template>
    <template #footer>
      <el-row>
        <el-col :span="18">
          <el-input placeholder="请输入您要发送的消息" v-model="chatData.content" type="textarea" rows="3"></el-input>
        </el-col>
        <el-col :span="4" :offset="1">
          <el-button style="border-color: transparent" v-on:click="send">发送</el-button>
        </el-col>
      </el-row>
    </template>
  </el-drawer>
</template>

<script>
import {ElNotification} from "element-plus";

export default {
  name: "Chat",
  props: {
    show: false,
    chatInit: {
      from: Number,
      to: Number,
      tradeId: Boolean,
      toUser: {},
      tradeDetail: {}
    }
  },
  data() {
    return {
      showChat: true,
      chatData: {
        content: '',
        send: 0,
        recv: 0
      },
      updateDataSend: {
        current: 1,
        send: null,
        recv: null
      },
      chatList: []
    }
  },

  methods: {
    handleClose() {
      this.$emit("handleClose", false)
    },
    updateChat() {
      let _this = this
      _this.updateDataSend.send = _this.chatInit.from
      _this.updateDataSend.recv = _this.chatInit.to
      _this.$axios.post("chat/getAllContent", _this.updateDataSend).then(res=>{
        _this.chatList = res.data.data.records.reverse()
      })
    },
    send() {
      let _this = this
      _this.chatData.send = _this.chatInit.from
      _this.chatData.recv = _this.chatInit.to
      console.log(_this.chatData)
      _this.$axios.post("chat/addContent", _this.chatData).then(res=>{
        if(res.data.code == 200){
          _this.chatData.content = ''
          ElNotification({
            title: 'Success',
            message: '发送成功',
            type: 'success',
          })
          _this.updateChat()
        }


      }).catch(res=>{
        ElNotification({
          title: 'Error',
          message: '发送失败',
          type: 'error',
        })
        _this.updateChat()
      })
    },

  }
}
</script>

<style scoped>


</style>