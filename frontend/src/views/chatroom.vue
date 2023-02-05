<template>
  <div class="page_back page_this"></div>

  <img src="../assets/images/wave.png" alt="" class="ourpage" style="position: fixed;background-color: transparent">
  <Header></Header>
  <el-container style=" width: 100%;height: 75%;position: absolute">
    <el-aside width="20%" style="margin-left: 15%;margin-right: 3%">
      <el-card style="height: 100%">
        <el-table :data="chatList" style="width: 100%">
          <el-table-column prop="avatar"  width="60">
            <template #default="scope">
              <el-image :src="scope.row.avatar"></el-image>
            </template>
          </el-table-column>
          <el-table-column prop="username" width="150">
            <template #default="scope">
              <div v-if="to == scope.row.others" v-on:click="callUser(scope.row.others)" style="color: #88b0ef">{{scope.row.username}}</div>
              <div v-else v-on:click="callUser(scope.row.others)" >{{scope.row.username}}</div>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
            @current-change="chatListChange"
            :current-page="currentPage"
            layout="prev, pager, next"
            :total="pageSize"
            :page-size="7"
            style="position: absolute;bottom: 2%"
        />

      </el-card>
    </el-aside>
    <el-main style="padding: 0; margin-right: 15%;background-color: white">
      <el-scrollbar height="80%">
        <div style="margin: 4%">
          <div v-for="item in chatDetail" style="margin-bottom: 1%">
            <div v-if="item.mine && !item.withdraw" class="chatBox chatBox-right" style="margin-left: 60%;margin-top: 1%">
              {{item.content}}
            </div>
            <div v-else-if="!item.mine && !item.withdraw" class="chatBox chatBox-left" style="margin-top: 1%">
              {{item.content}}
            </div>
            <div v-if="item.mine && !item.withdraw" style="margin-left: 60%">
              {{item.time.split("T")[0]+'  '}}{{item.time.split("T")[1]}} <span>
              <el-popconfirm title="确定撤回此消息吗?"
                             @confirm="doWithdraw(item.id)"
                             confirm-button-text="确定"
                             cancel-button-text="取消"
              >
                <template #reference>
                  <el-button style="border-color: transparent">撤回</el-button>
                </template>
              </el-popconfirm>
              </span>
            </div>
            <div v-else-if="!item.mine && !item.withdraw">
              {{item.time.split("T")[0]+'  '}}{{item.time.split("T")[1]}}
            </div>
          </div>
        </div>



      </el-scrollbar>




        <el-row style="position: absolute;bottom: 2%;width: 46%">
          <el-col :span="18" :offset="1">
            <el-input placeholder="请输入您要发送的消息" v-model="chatData.content" type="textarea" rows="3"></el-input>
          </el-col>
          <el-col :span="4" :offset="1">
            <el-button style="border-color: transparent" v-on:click="send">发送</el-button>
          </el-col>
        </el-row>


    </el-main>
  </el-container>
  <Footer></Footer>
</template>

<script>
import Header from "@/components/Header";
import Footer from "@/components/Footer";
import {ElNotification} from "element-plus";

export default {
  name: "chatroom",
  components: {
    Header,
    Footer
  },
  data() {
    return {
      userId: localStorage.getItem("userId"),
      chatList: [],
      chatDetail: [],
      currentPage: 1,
      pageSize: 0,
      chatData: {
        content: '',
        send: 0,
        recv: 0
      },
      to: 0

    }
  },
  methods: {
    doWithdraw(id){
      let _this = this
      _this.$axios.post("chat/doWithdraw", {id: id,uid: _this.userId}).then(res=>{
        ElNotification({
          title: 'Success',
          message: '撤回成功',
          type: 'success',
        })
        _this.callUser(_this.to)
      })
    },
    send() {
      let _this = this
      _this.chatData.send = _this.userId
      _this.chatData.recv = _this.to
      console.log(_this.chatData)
      _this.$axios.post("chat/addContent", _this.chatData).then(res=>{
        if(res.data.code == 200){
          _this.chatData.content = ''
          ElNotification({
            title: 'Success',
            message: '发送成功',
            type: 'success',
          })
          _this.callUser(_this.to)
        }


      }).catch(res=>{
        ElNotification({
          title: 'Error',
          message: '发送失败',
          type: 'error',
        })
        _this.callUser(_this.to)
      })
    },
    callUser(userId){
      let _this = this

      _this.$axios.post("chat/getAllContent", {current: 1,send: localStorage.getItem("userId"),recv: userId}).then(res=>{

        _this.chatDetail = res.data.data.records.reverse()
        console.log(_this.chatDetail)
        _this.to = userId
      })

    },
    chatListInit(currentPage){
      let _this = this
      _this.$axios.post("chat/getContactList", {uid: _this.userId, current: currentPage}).then(res=>{
        _this.chatList = res.data.data.records
        console.log(res.data.data)
        _this.pageSize = res.data.data.total
      })
    },
    chatListChange(currentPage){
      let _this = this
      _this.currentPage = currentPage

      _this.$axios.post("chat/getContactList", {uid: _this.userId, current: currentPage}).then(res=>{
        _this.chatList = res.data.data.records
      })
    }
  },
  created(){
    this.chatListInit(1)
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