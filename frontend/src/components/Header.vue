<template>
  <el-row style="background-color: transparent; font-weight: bold; font-size: x-large">
    <el-col v-if="!isPhone" :offset="2" :span="4">
      <el-image style="width: 80%; height: 80%; z-index: 1; background-color: transparent"
                :src="require('../assets/images/logo.png')"
                :fit="'fill'"></el-image>
    </el-col>
    <el-col v-else :span="6">
      <el-image style="width: 100%; height: 100%; z-index: 1; background-color: transparent"
                :src="require('../assets/images/logo.png')"
                :fit="'fill'"></el-image>
    </el-col>
    <el-col v-if="!isPhone" :span="8" style="margin-top: 3%">
      <el-button v-if="index == 1"
                 style="background-color: transparent; border-color: transparent; font-size: large; color: #333333">
        <el-icon style="margin-right: 1%">
          <House/>
        </el-icon>
        <div>
          学生社区
        </div>
      </el-button>
      <el-button v-if="index == 2 || index == 3 || index == -1 || index == 4"
                 style="background-color: transparent; border-color: transparent; font-size: large; color: #919191"
                 v-on:click="changeCommunity">
        <el-icon style="margin-right: 1%">
          <House/>
        </el-icon>
        <div>
          学生社区
        </div>
      </el-button>
      <el-button v-if="index == 2"
                 style="background-color: transparent; border-color: transparent; font-size: large; color: #333333">
        <el-icon style="margin-right: 1%">
          <Money/>
        </el-icon>
        <div>
          二手易物
        </div>
      </el-button>
      <el-button v-if="index == 1 || index == 3 || index == -1 || index == 4"
                 style="background-color: transparent; border-color: transparent; font-size: large; color: #919191"
                 v-on:click="changeGood">
        <el-icon style="margin-right: 1%">
          <Money/>
        </el-icon>
        <div>
          二手易物
        </div>
      </el-button>

      <el-button v-if="index == 4"
                 style="background-color: transparent; border-color: transparent; font-size: large; color: #333333">
        <el-icon style="margin-right: 1%">
          <Compass/>
        </el-icon>
        <div>
          活动集锦
        </div>
      </el-button>
      <el-button v-if="index == 1 || index == 2 || index == 3 || index == -1"
                 style="background-color: transparent; border-color: transparent; font-size: large; color: #919191"
                 v-on:click="changeActivity">
        <el-icon style="margin-right: 1%">
          <Compass/>
        </el-icon>
        <div>
          活动集锦
        </div>
      </el-button>
    </el-col>
<!--    <el-col :span="6" style="margin-top: 3%">-->
<!--      <el-input-->
<!--          v-model="searchInput"-->
<!--          placeholder="搜索"-->
<!--          class="input-with-select"-->

<!--      >-->
<!--        <template #append>-->
<!--          <el-button style="background-color: transparent; border-color: transparent">-->
<!--            <el-icon>-->
<!--              <Search/>-->
<!--            </el-icon>-->

<!--          </el-button>-->
<!--        </template>-->


<!--      </el-input>-->
<!--    </el-col>-->
    <el-col v-if="!isPhone" :span="5" :offset="5" style="margin-top: 2%">
      <el-button
          v-on:click="service"
          style="background-color: transparent; border-color: transparent; font-size: large; color: #333333; padding: 0">
        <el-icon>
          <Service/>
        </el-icon>
      </el-button>
<!--      <el-popover trigger="hover" placement="top" :width="160">-->
<!--        <el-row style="font-weight: bold">-->
<!--          <el-icon style="margin-top: 2%">-->
<!--            <Grid/>-->
<!--          </el-icon>-->
<!--          <div style="margin-left: 11%">-->
<!--            签到-->
<!--          </div>-->
<!--        </el-row>-->
<!--        <template #reference>-->
<!--          <el-button-->
<!--              style="background-color: transparent; border-color: transparent; font-size: large; color: #333333; padding: 0">-->
<!--            <el-icon>-->
<!--              <Ticket/>-->
<!--            </el-icon>-->
<!--          </el-button>-->
<!--        </template>-->
<!--      </el-popover>-->

      <el-popover trigger="hover" placement="top" :width="160">
        <el-row style="font-weight: bold" v-on:click="toNoteRoom">
          <el-icon style="margin-top: 2%">
            <Message/>
          </el-icon>
          <div style="margin-left: 11%">
            通知中心
          </div>
          <i v-if="redDotNot" class="dotClass" style="background-color: crimson ;width:7px;  height:7px;  border-radius: 50%;  "></i>
        </el-row>
        <el-row style="font-weight: bold; margin-top: 5%" v-on:click="toChatRoom">
          <el-icon style="margin-top: 2%">
            <ChatRound/>
          </el-icon>
          <div style="margin-left: 11%">
            私信
          </div>
          <i v-if="redDotChat" class="dotClass" style="background-color: crimson ;width:7px;  height:7px;  border-radius: 50%;  "></i>
        </el-row>
        <el-divider style="padding-bottom: 0; margin-top: 10px; margin-bottom: 0"></el-divider>
        <el-row>
          <el-col :span="24">
            <el-button v-on:click="AllRead" style="background-color: transparent; border-color: transparent">
              全部已读
            </el-button>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-button v-on:click="toChatRoom" style="background-color: transparent; border-color: transparent">
              查看所有私信
            </el-button>
          </el-col>
        </el-row>
        <template #reference>
          <el-button
              style="background-color: transparent; border-color: transparent; font-size: large; color: #333333; padding: 0">
            <el-icon>
              <Bell/>
            </el-icon>
          </el-button>
        </template>
      </el-popover>


      <el-avatar v-on:click="changeUser" :size="40" :src="avatarUrl=='null'?'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png':avatarUrl" style="margin-left: 12%"/>

    </el-col>
    <el-col v-if="isPhone" style="position: absolute;right: 6px">
      <el-menu
          :default-active="activeIndex"
          class="el-menu-demo"
          mode="vertical"
          @select="handleSelect"
          active-text-color="transprent"
          style="z-index: 99;background-color: transparent;"
      >
        <el-sub-menu index="1">
          <template #title style="background-color: transparent">菜单</template>
          <el-menu-item index="1-1" style="background-color: transparent">
            <el-avatar  :size="30" :src="avatarUrl=='null'?'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png':avatarUrl" style="margin-right: 10%"/>
            用户中心
          </el-menu-item>
          <el-menu-item index="1-2" style="background-color: transparent"><el-icon style="margin-top: 1%">
            <House/>
          </el-icon>学生社区</el-menu-item>
          <el-menu-item index="1-3"><el-icon style="margin-top: 1%">
            <Money/>
          </el-icon>二手易物</el-menu-item>
          <el-menu-item index="1-4"><el-icon style="margin-top: 1%">
            <Compass/>
          </el-icon>活动集锦</el-menu-item>
          <el-sub-menu index="1-5">
            <template #title><el-icon>
              <Bell/>
            </el-icon>站内信息</template>
            <el-menu-item index="1-5-1"><el-icon style="margin-top: 2%">
              <Message/>
            </el-icon>通知中心</el-menu-item>
            <el-menu-item index="1-5-2"><el-icon style="margin-top: 2%">
              <ChatRound/>
            </el-icon>私信</el-menu-item>
          </el-sub-menu>
        </el-sub-menu>
      </el-menu>
    </el-col>
  </el-row>
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

</template>

<script>
import {Search} from '@element-plus/icons-vue'
import {getCurrentInstance, inject} from "vue";

export default {
  name: "Header",
  data() {
    let index = this.getIndexed()
    return {
      index: index,
      serviceShow: false,
      searchInput: '',
      avatarUrl: localStorage.getItem("userAvatar"),
      chatList: [],
      serviceData: '',
      redDotChat: false,
      redDotNot: false,
      activeIndex: 1,
      isPhone: inject('isPhone')

    }
  },
  created(){

    console.log(this.isPhone)
    this.$axios.post("chat/hasMessage", {cid: localStorage.getItem("userId")}).then(res=>{
      this.redDotChat = res.data.data
    }).catch(res=>{

    })
    this.$axios.post("notification/hasMessage", {cid: localStorage.getItem("userId")}).then(res=>{
      this.redDotNot = res.data.data
    }).catch(res=>{

    })
  },
  methods: {
    handleSelect(res){
      console.log(res)
      switch (res){
        case '1-1':{
          this.changeUser()
          break
        }
        case '1-2':{
          this.changeCommunity()
          break
        }case '1-3':{
          this.changeGood()
          break
        }case '1-4':{
          this.changeActivity()
          break
        }case '1-5-1':{
          this.toNoteRoom()
          break
        }case '1-5-2':{
          this.toChatRoom()
          break
        }
      }
    },
    AllRead(){
      let _this = this
      _this.$axios.post("chat/setAllRead",{cid: localStorage.getItem("userId")}).then(res=>{
        _this.redDotChat = false
      })
    },
    cancle() {
      let _this = this
      _this.serviceShow = false
    },
    service() {
      let _this = this
      _this.serviceShow = true
    },
    serviceSend() {
      let _this = this
      _this.chatList.push({who: 0, content: _this.serviceData})
      _this.$axios.post("ai/send", {request: _this.serviceData}).then(res=>{

        _this.chatList.push({who: 1, content: res.data.data})
      })
    },

    toChatRoom() {
      this.$store.commit('SET_INDEX', -1)

      this.$router.push("/user/chatroom")
    },
    toNoteRoom(){
      this.$store.commit('SET_INDEX', -1)

      this.$router.push("/user/noteroom")
    },
    getIndexed() {
      return this.$store.getters.getIndex
    },
    changeCommunity() {
      this.$store.commit('SET_INDEX', 1)

      this.$router.push("/index/community")
    },
    changeGood() {
      this.$store.commit('SET_INDEX', 2)

      this.$router.push("/index/shop")
    },
    changeUser() {
      this.$store.commit('SET_INDEX', 3)

      this.$router.push({name: "user", params: {flag: 4}})
    },
    changeActivity() {
      this.$store.commit('SET_INDEX', 4)

      this.$router.push("/index/activity")
    }
  }
}
</script>

<style scoped>

.dotClass {
  width:7px;
  height:7px;
  border-radius: 50%;
  display: block;
}
</style>