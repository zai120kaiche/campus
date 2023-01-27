<template>
  <div class="page_back page_this"></div>
  <img src="../assets/images/wave.png" alt="" class="ourpage" style="position: fixed;background-color: transparent">
  <Header></Header>
  <el-container >
    <el-main style="padding: 0;margin-left: 12%; margin-right: 3%;">
      <el-card class="  animate__animated animate__fadeIn "  >
        <el-row>
          <el-col :span="3" v-on:click="callUser(postDetail.owner)">
            <el-avatar :src="postDetail.avatar"></el-avatar>
          </el-col>
          <el-col :span="21">
            <el-row>
              <el-col :span="8"  v-on:click="callUser(postDetail.owner)">{{postDetail.authorname}}</el-col>
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

          <el-col :span="4" v-on:click="like(postDetail.id)">
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
          <el-col :span="4" v-on:click="collect(postDetail.id)">
            <el-icon style="margin-top: 2%; margin-right: 15%; margin-left: 15%">
              <Star/>
            </el-icon>
            {{postDetail.collectNum}}
          </el-col>
          <el-divider direction="vertical"/>
          <el-col :span="4">
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
              <el-row style="margin-top: 1%" >
                <el-row>
                  <el-link style="word-wrap:break-word; word-break:break-all;" v-on:click="doReplyShowFunc(index)">{{item.floor.content}}</el-link>

                </el-row>




              </el-row>
              <el-row v-if="item.replies != ''">
                <el-col :span="3" :offset="21">
                  <el-icon v-on:click="repliesShow(index)">
                    <ArrowDown />
                  </el-icon>
                </el-col>
              </el-row>
              <el-card v-if="item.showReplies" style="margin-top: 2%">
                <div v-for="(reply, index) in item.replies" style="margin-top: 2%; padding: 0">
                  <el-row>
                    <el-col :span="8">{{reply.userName}}</el-col>
                    <el-col :span="6" :offset="10">{{reply.date.split("T")[0]}}</el-col>
                  </el-row>
                  <el-row style="margin-top: 1%" >
                    <el-row>
                      <el-link style="word-wrap:break-word; word-break:break-all;">{{reply.content}}</el-link>

                    </el-row>




                  </el-row>
                  <el-divider></el-divider>
                </div>
              </el-card>
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
      userChatDrawer: false,
      chatProp: {
        from: 0,
        to: 0,
        tradeId: false,
        toUser: {
        }
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
      replyText: ''
    }
  },
  created() {
    this.init()
    this.getComment()
  },
  methods: {
    chatHandleClose(res){
      this.userChatDrawer = false
    },
    callUser(userId){
      let _this = this
      _this.chatProp.from = localStorage.getItem("userId")
      _this.chatProp.to = userId
      _this.$axios.post("user/index", {id: userId}).then(res=>{
        _this.chatProp.toUser = res.data.data
      }).catch(res=>{
        ElNotification({
          title: 'Error',
          message: '此人不存在',
          type: 'error',
        })
      })
      _this.drawer = false
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
        console.log(res.data)
        _this.init()

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
        console.log(res)
      })

    },
    init() {
      let _this = this
      _this.communityId = _this.$route.params.communityId
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
          console.log(res.data.data)
          _this.postDetail.authorname = res.data.data.username
          _this.postDetail.avatar = res.data.data.avatar

          console.log(temp2)
          _this.$axios.post('/school/get', temp2).then(res =>{
            console.log(res.data.data)
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
        fid: _this.commentDetail[index].floor.id
      }
      _this.$axios.post("/community/doReply", temp).then(res =>{
        _this.replyText = ''
        _this.getComment()
      })
    },
    repliesShow(index) {
      let _this = this

      if(_this.repliesShowLimit == -1){
        _this.repliesShowLimit = index

      } else{

        _this.commentDetail[_this.repliesShowLimit].showReplies = false
        _this.repliesShowLimit = index
      }
      _this.commentDetail[index].showReplies = true
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