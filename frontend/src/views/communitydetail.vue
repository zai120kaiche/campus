<template>
  <div class="page_back page_this"></div>
  <img src="../assets/images/wave.png" alt="" class="ourpage" style="position: fixed;background-color: transparent">
  <Header></Header>
  <el-container >
    <el-main style="padding: 0;margin-left: 12%; margin-right: 3%;">
      <el-card class="  animate__animated animate__fadeIn "  >
        <el-row v-on:click="goOff" style="margin-bottom: 2%">
          <el-icon style="margin-top: 0.5%"><ArrowLeftBold /></el-icon>
          返回
        </el-row>
        <el-row>
          <el-col :span="3" v-on:click="callUserInfo(postDetail.owner)">
            <el-avatar :src="postDetail.avatar"></el-avatar>
          </el-col>
          <el-col :span="21">
            <el-row>
              <el-col :span="8"  v-on:click="callUserInfo(postDetail.owner)">{{postDetail.authorname}}</el-col>
              <el-col :span="8" style="color: #919191; font-weight: lighter; font-size: xx-small">类别: {{postDetail.kindName}}</el-col>
              <el-col :span="8" style="color: #919191; font-weight: lighter; font-size: xx-small">学校: {{postDetail.universityName}}
              </el-col>
            </el-row>
          </el-col>
        </el-row>

        <div style="margin-top: 6%;margin-bottom: 5%; font-weight: bold; font-size: xx-large">{{postDetail.title}}</div>
        <el-row style="margin-left: 5%"><el-image v-for="item in postDetail.pic" style="width: 150px; height: 150px;
                  margin-right: 5%" :src="item" :fit="'fill'"
                                                  :preview-src-list="postDetail.pic"/></el-row>

        <div style="word-wrap:break-word; word-break:break-all;
              margin-top: 5%;margin-left: 8%;margin-right: 8%;
              font-size: large">{{ postDetail.content }}
        </div>

        <el-row :gutter="20" style="margin-top: 1%;">
          <el-col :span="4">
            <el-icon style="margin-top: 2%; margin-right: 15%; margin-left: 15%">
              <View/>
            </el-icon>
            {{postDetail.viewNum}}
          </el-col>
          <el-divider direction="vertical"/>

          <el-col v-if="likeFlag" :span="4" v-on:click="dislike(postDetail.id)">
            <el-icon style="margin-top: 2%; margin-right: 15%; margin-left: 15%; color: #88b0ef">
              <Pointer />
            </el-icon>
            {{postDetail.likeNum}}
          </el-col>
          <el-col v-else :span="4" v-on:click="like(postDetail.id)">
            <el-icon style="margin-top: 2%; margin-right: 15%; margin-left: 15%">
              <Pointer/>
            </el-icon>
            {{postDetail.likeNum}}
          </el-col>
          <el-divider direction="vertical"/>
          <el-col :span="4">
            <el-icon style="margin-top: 2%; margin-right: 15%; margin-left: 15%">
              <ChatDotSquare/>
            </el-icon>
            {{postDetail.commentNum}}
          </el-col>
          <el-divider direction="vertical"/>
          <el-col v-if="collectFlag" :span="4" v-on:click="disCollect(postDetail.id)">
            <el-icon style="margin-top: 2%; margin-right: 15%; margin-left: 15%;color: #88b0ef">
              <StarFilled/>
            </el-icon>
            {{postDetail.collectNum}}
          </el-col>
          <el-col v-else :span="4" v-on:click="collect(postDetail.id)">
            <el-icon style="margin-top: 2%; margin-right: 15%; margin-left: 15%">
              <Star/>
            </el-icon>
            {{postDetail.collectNum}}
          </el-col>
          <el-divider direction="vertical"/>
          <el-col :span="4" v-on:click="doCopy('ecampus.chat/index/community/detail/' + postDetail.id, postDetail.id)">
            <el-icon style="margin-top: 2%; margin-right: 15%; margin-left: 15%">
              <Share/>
            </el-icon>
            {{postDetail.forwardNum}}
          </el-col>
        </el-row>
      </el-card>


      <el-card style="margin-top: 1%">
        <el-row >评论</el-row>
        <el-row style="margin-top: 2%">
          <el-col :span="20">
            <el-input
                v-model="commentText"
                :rows="2"
                type="textarea"
                placeholder="请发表评论"
            />
          </el-col>
          <el-col :span="3" :offset="1">
            <el-button style="margin-top: 20%" v-on:click="doComment">发送</el-button>
          </el-col>

          <el-card style="margin-top: 2%; width: 100%">
            <div v-for="(item, index) in commentDetail" style="margin-top: 2%">
              <el-row>
                <el-col :span="8">{{item.floor.userName}}</el-col>
                <el-col :span="4" :offset="12">{{item.floor.date.split("T")[0]}}</el-col>
              </el-row>
              <el-row style="margin-top: 2%" >
                <el-container style="margin-bottom: 2%">
                  <el-main style="padding: 0">
                    <el-link style="word-wrap:break-word; word-break:break-all;" v-on:click="doReplyShowFunc(index)">{{item.floor.content}}</el-link>

                  </el-main>
                  <el-aside width="10%">
                    <el-icon v-if="item.floor.likeFlag" style="color: #88b0ef" v-on:click="doFloorDislike(item.floor.id)"><Pointer /></el-icon>
                    <el-icon v-else v-on:click="doFloorLike(item.floor.id)"><Pointer /></el-icon>
                    {{item.floor.likeNum}}
                  </el-aside>
                </el-container>

              </el-row>
              <el-row v-if="item.doReplyLimit && doReplyShow" style="margin-top: 2%">
                <el-col :span="20"><el-input

                    v-model="replyText"
                    :rows="2"
                    type="textarea"
                    placeholder="请发表评论"
                /></el-col>
                <el-col :span="3" :offset="1">
                  <el-button style="margin-top: 20%" v-on:click="doReply(index)">发送</el-button>
                </el-col>
              </el-row>
              <el-row v-if="item.replies != ''">
                <el-col :span="3" :offset="21">
                  <el-icon v-on:click="repliesShow(index)">
                    <ArrowDown />
                  </el-icon>
                </el-col>
              </el-row>


            </div>
          </el-card>


        </el-row>
      </el-card>

    </el-main>
    <el-aside class="animate__animated animate__fadeInRight" width="16%" style="margin-right: 12%; margin-left: 2%">
      <el-card>
        <div style="font-weight: bold">
          我的
        </div>
        <el-row style="text-align: center; font-size: small; margin-top: 10%">
          <el-col :span="12">
            <el-iocn>
              <EditPen style="width: 30%"/>
            </el-iocn>
            <div>
              帖子
            </div>
          </el-col>
          <el-col :span="12">
            <el-iocn>
              <Document style="width: 30%"/>
            </el-iocn>
            <div>
              回帖
            </div>
          </el-col>
        </el-row>
        <el-row style="text-align: center; font-size: small; margin-top: 10%">
          <el-col :span="12">
            <el-iocn>
              <FolderOpened style="width: 30%"/>
            </el-iocn>
            <div>
              收藏夹
            </div>
          </el-col>

        </el-row>
      </el-card>

      <el-card style="margin-top: 10%">
        <el-row>
          <el-col :span="12">
            <div style="font-weight: bold">
              社区公告

            </div>
          </el-col>
          <el-col :span="8" :offset="4">
            <div style="color: #919191">
              进入
              <el-icon style="margin-right: 0; margin-left: 0">
                <ArrowRightBold/>
              </el-icon>
            </div>

          </el-col>
        </el-row>

      </el-card>
    </el-aside>


  </el-container>
  <Footer></Footer>
  <el-drawer v-model="callUserFlag" :direction="'ltr'">
    <template #header>

      <h4>{{callUserData.username}}</h4>
    </template>
    <template #default>
      <el-avatar :src="callUserData.avatar"></el-avatar>
      <el-card>
        <el-row>
          联系方式：{{callUserData.email?callUserData.email:'暂未绑定'}}
        </el-row>
      </el-card>
    </template>
    <template #footer>
      <div style="flex: auto">
        <el-button type="primary" @click="callUser(callUserData.id)">私聊</el-button>
      </div>
    </template>
  </el-drawer>
  <el-drawer v-model="showRepliesFlag" :direction="'rtl'">
    <template #header>
      <h4>{{originComment}}</h4>
    </template>
    <template #default>

        <div v-for="(reply, index) in currentReplies" style="margin-top: 2%; padding: 0">

          <el-row>
            <el-col :span="8" v-if="reply.others == null">{{reply.userName}}</el-col>
            <el-col :span="8" v-else>
              {{reply.userName}}
              <div style="margin-left: 1%;margin-right: 1%;color: #919191">回复</div>
              {{reply.othersName}}
            </el-col>
            <el-col :span="6" :offset="10">{{reply.date.split("T")[0]}}</el-col>
          </el-row>
          <el-row style="margin-top: 2%" >
            <el-container>
              <el-container >
                <el-main style="padding: 0">
                  <el-link style="word-wrap:break-word; word-break:break-all;" >{{reply.content}}</el-link>

                </el-main>
                <el-aside width="10%">
                  <el-icon v-if="reply.likeFlag" style="color: #88b0ef" v-on:click="doReplyDislike(reply.id)"><Pointer /></el-icon>
                  <el-icon v-else v-on:click="doReplyLike(reply.id)"><Pointer /></el-icon>
                  {{reply.likeNum}}
                </el-aside>

              </el-container>
              <el-footer style="padding: 0;margin-bottom: 5%" height="100%">
                <el-row v-if="reply.others == null">
                  <el-col :offset="21" :span="3">
                    <el-button style="border-color: transparent;color: grey;background-color: transparent" v-on:click="doOthersShowFunc(reply, index)">回复</el-button>
                  </el-col>
                </el-row>


                <el-row v-if="reply.doOthersShow && othersShow" style="margin-top: 2%">
                  <el-col :span="18"><el-input
                      v-model="othersText"
                      :rows="2"
                      type="textarea"
                      placeholder="请发表回复"
                  /></el-col>
                  <el-col :span="3" :offset="1">
                    <el-button style="margin-top: 20%" v-on:click="doOthers(index)">发送</el-button>
                  </el-col>
                </el-row>

              </el-footer>
            </el-container>

          </el-row>
          <el-divider style="padding: 0;margin: 0"></el-divider>
        </div>

    </template>
    <template #footer>
      <el-row style="margin-top: 2%">
        <el-col :span="20"><el-input

            v-model="replyText"
            :rows="2"
            type="textarea"
            placeholder="请发表评论"
        /></el-col>
        <el-col :span="3" :offset="1">
          <el-button style="margin-top: 20%" v-on:click="doReply(currentIndex)">发送</el-button>
        </el-col>
      </el-row>
    </template>
  </el-drawer>
  <Chat :show="userChatDrawer" :chatInit="chatProp" @handleClose="chatHandleClose"></Chat>


</template>

<script>
import Header from "@/components/Header";
import Footer from "@/components/Footer";
import Chat from "@/components/Chat";
import {ElNotification} from "element-plus";
export default {
  name: "communitydetail",
  components: {
    Header,
    Footer,
    Chat
  },
  data() {
    return {
      othersText: '',
      likeFlag: false,
      collectFlag: false,
      callUserFlag: false,
      callUserData: {},
      userChatDrawer: false,
      chatProp: {
        from: 0,
        to: 0,
        tradeId: false,
        toUser: {
        },
        detail: []
      },
      doReplyShow: false,
      doReplyLimit: -1,

      repliesShowLimit: -1,

      communityId: 0,
      postDetail: {},
      commentDetail: [],
      srcList: [],
      userId: localStorage.getItem("userId"),
      commentText: '',
      replyText: '',

      othersShow: false,
      othersIndex: -1,
      othersIndex2: -1,

      showRepliesFlag: false,
      originComment: '',
      currentReplies: [],
      currentIndex: -1
    }
  },
  created() {
    this.initParams()
    this.init()
    this.getComment()

  },
  methods: {
    goOff(){this.$router.go(-1)},
    callUserInfo(userId){
      let _this = this
      _this.$axios.post("user/index", {id: userId}).then(res=>{
        _this.callUserData = res.data.data
        _this.callUserFlag = true
      }).catch(res=>{
        ElNotification({
          title: 'Error',
          message: '此人不存在',
          type: 'error',
        })
      })
    },
    doCopy(url, id){
      let _this = this
      _this.$copyText(url).then(function (e) {
        ElNotification({
          title: 'Success',
          message: '已复制分享内容到剪贴板',
          type: 'success',
        })
        _this.$axios.post("community/doForward", {cid: id}).then(res =>{
          _this.init()
        })
      }, function (e) {
        ElNotification({
          title: 'Error',
          message: '分享失败',
          type: 'error',
        })
      })

    },
    chatHandleClose(res){
      this.userChatDrawer = false
    },
    callUser(userId){
      let _this = this
      _this.chatProp.from = localStorage.getItem("userId")
      _this.chatProp.to = userId
      _this.chatProp.toUser = _this.callUserData
      _this.$axios.post("chat/getAllContent", {current: 1,send: localStorage.getItem("userId"),recv: userId}).then(res=>{
        _this.chatProp.detail = res.data.data.records.reverse()
      })
      _this.callUserFlag = false
      _this.drawer = false
      _this.$axios.post("chat/establishContact", {owner: localStorage.getItem("userId"),others: userId})
      _this.userChatDrawer = true
    },
    //点赞
    like(id) {

      let _this = this
      let temp = {
        uid: _this.userId,
        objectId: id,
        type: 0,
        flag: true
      }
      _this.$axios.post('/community/doLike', temp).then(res => {
        _this.init()
        console.log(_this.likeFlag)
        _this.likeFlag = !_this.likeFlag
      })
    },
    dislike(id) {
      let _this = this
      let temp = {
        uid: _this.userId,
        objectId: id,
        type: 0,
        flag: false
      }
      _this.$axios.post('/community/doLike', temp).then(res => {
        _this.init()
        console.log(_this.likeFlag)
        _this.likeFlag = !_this.likeFlag

      })
    },
    //收藏
    collect(id){
      let _this = this
      let temp = {
        uid: _this.userId,
        pid: id
      }
      _this.$axios.post("/community/doCollect", temp).then(res => {

        ElNotification({
          title: 'Success',
          message: '收藏成功',
          type: 'success',
        })
        _this.init()
        _this.collectFlag = !_this.collectFlag
      }).catch(res =>{
        ElNotification({
          title: 'Error',
          message: '收藏失败',
          type: 'error',
        })
      })
    },
    disCollect(id){
      let _this = this
      let temp = {
        uid: _this.userId,
        objectIds: [id]
      }
      _this.$axios.post("/community/deleteCollectByIds", temp).then(res => {
        if (res.data.code == 200) {
          ElNotification({
            title: 'Success',
            message: '取消收藏成功',
            type: 'success',
          })

          _this.init()
          _this.collectFlag = !_this.collectFlag
        }

      }).catch(res => {
        ElNotification({
          title: 'Error',
          message: '取消收藏失败',
          type: 'error',
        })
      })
    },
    initParams(){
      let _this = this
      _this.communityId = _this.$route.params.communityId
      _this.likeFlag = _this.$route.query.likeFlag
      _this.collectFlag = _this.$route.query.collectFlag
      if(_this.likeFlag == 'true'){
        _this.likeFlag = true
      } else{
        _this.likeFlag = false
      }
      if(_this.collectFlag == 'true'){
        _this.collectFlag = true
      } else{
        _this.collectFlag = false
      }
    },
    init() {
      let _this = this

      console.log(_this.likeFlag)
      let temp = {
        id: _this.communityId
      }
      _this.$axios.post('/community/postDetail', temp).then(res=>{
        _this.postDetail = res.data.data

        _this.postDetail.pic = _this.postDetail.pic.split(",")

        let temp1 = {
          id: parseInt(_this.postDetail.owner)
        }
        let temp2 = {
          id: res.data.data.university
        }
        _this.$axios.post('/user/index', temp1).then(res =>{
          _this.postDetail.authorname = res.data.data.username
          _this.postDetail.avatar = res.data.data.avatar
          _this.$axios.post('/school/get', temp2).then(res =>{
            _this.postDetail.universityName = res.data.data.schoolname
          })
        })
      })
    },
    getComment(){
      let _this = this
      let temp = {
        uid: _this.userId,
        pid: _this.communityId,
        current: 1,
        order: 0
      }
      _this.$axios.post("/community/getPostInfo", temp).then(res =>{
        _this.commentDetail = res.data.data
        if(_this.showRepliesFlag){
          _this.repliesShow(_this.currentIndex)
        }
        console.log(_this.commentDetail)
      })
    },
    //一级评论
    doComment() {
      let _this = this
      let temp = {
        content: _this.commentText,
        pid: _this.postDetail.id,
        owner: _this.userId
      }
      _this.$axios.post("/community/doComment", temp).then(res =>{
        _this.commentText = ''
        _this.getComment()
      })
    },
    doReplyShowFunc(index){


      let _this = this

      if(_this.doReplyLimit == -1){
        _this.doReplyLimit = index
        _this.doReplyShow = !_this.doReplyShow
      } else{
        _this.doReplyShow = !_this.doReplyShow

        _this.commentDetail[_this.doReplyLimit].doReplyLimit = false
        _this.doReplyLimit = index
      }
      _this.commentDetail[index].doReplyLimit = true


    },
    //二级评论
    doReply(index){
      let _this = this
      let temp = {
        content: _this.replyText,
        owner: _this.userId,
        fid: _this.commentDetail[index].floor.id,
        reference: _this.showRepliesFlag?_this.commentDetail[_this.currentIndex].reply[index].id:_this.commentDetail[index].floor.id
      }
      _this.$axios.post("/community/doReply", temp).then(res =>{
        _this.replyText = ''
        _this.getComment()
        _this.repliesShow(index)
      })
    },
    doOthers(index){
      let _this = this
      let temp = {
        content: _this.othersText,
        owner: _this.currentReplies[index].owner,
        fid: _this.commentDetail[_this.currentIndex].floor.id,
        others: _this.userId,
        reference: _this.userId
      }
      _this.$axios.post("/community/doReply", temp).then(res =>{
        _this.othersText = ''
        _this.getComment()

      })
    },
    repliesShow(index) {
      let _this = this
      _this.showRepliesFlag = true
      _this.currentIndex = index
      _this.currentReplies = _this.commentDetail[index].replies
      _this.originComment = _this.commentDetail[index].floor.content
    },

    doOthersShowFunc(reply, index){
      let _this = this
      _this.othersShow = !_this.othersShow
      if(_this.othersIndex == -1){
        _this.othersIndex = index
      }
      _this.currentReplies[_this.othersIndex].doOthersShow = false
      _this.othersIndex = index
      _this.currentReplies[_this.othersIndex].doOthersShow = true


    },

    doFloorLike(id){
      let _this = this
      let temp = {
        uid: _this.userId,
        objectId: id,
        type: 1,
        flag: true
      }
      _this.$axios.post("community/doLike", temp).then(res=>{
        _this.getComment()
      })
    },
    doFloorDislike(id){
      let _this = this
      let temp = {
        uid: _this.userId,
        objectId: id,
        type: 1,
        flag: false
      }
      _this.$axios.post("community/doLike", temp).then(res=>{
        _this.getComment()
      })
    },
    doReplyLike(id){
      let _this = this
      let temp = {
        uid: _this.userId,
        objectId: id,
        type: 2,
        flag: true
      }
      _this.$axios.post("community/doLike", temp).then(res=>{
        _this.getComment()
      })
    },
    doReplyDislike(id){
      let _this = this
      let temp = {
        uid: _this.userId,
        objectId: id,
        type: 2,
        flag: false
      }
      _this.$axios.post("community/doLike", temp).then(res=>{
        _this.getComment()
      })
    }
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