<template>
  <div class="page_back page_this"></div>
  <img src="../assets/images/wave.png" alt="" class="ourpage" style="position: fixed;background-color: transparent">
  <Header></Header>
  <el-container style="margin-right: 20%;margin-left: 20%">
    <el-main>
      <el-card class="  animate__animated animate__fadeIn ">
        <el-row v-on:click="goOff" style="margin-bottom: 2%">
          <el-icon style="margin-top: 0.5%">
            <ArrowLeftBold/>
          </el-icon>
          返回
        </el-row>
        <el-row>


          <el-col :span="8">{{ whisperDetail.owner }}</el-col>

        </el-row>

        <div style="margin-top: 6%;margin-bottom: 5%; font-weight: bold; font-size: xx-large">
          {{ whisperDetail.title }}
        </div>
        <el-row style="margin-left: 5%">
          <el-image v-for="item in whisperDetail.pic" style="width: 150px; height: 150px;
                  margin-right: 5%" :src="item" :fit="'fill'" preview-teleported="true"
                    :preview-src-list="whisperDetail.pic"/>

        </el-row>

        <div style="word-wrap:break-word; word-break:break-all;
              margin-top: 5%;margin-left: 8%;margin-right: 8%;
              font-size: large">{{ whisperDetail.content }}
        </div>


      </el-card>
      <el-card style="margin-top: 1%">
        <el-row>评论</el-row>
        <el-row style="margin-top: 2%">
          <el-col :span="20">
            <el-input
                v-model="commentText"
                :rows="2"
                type="textarea"
                placeholder="请发表评论"
                style="z-index: 0"
            />
          </el-col>
          <el-col :span="3" :offset="1">
            <el-button style="margin-top: 20%" v-on:click="doComment">发送</el-button>
          </el-col>

          <el-card style="margin-top: 2%; width: 100%">
            <div v-for="(item, index) in commentDetail" style="margin-top: 2%">
              <el-row>
                <el-col :span="8">{{ item.floor.owner }}</el-col>
                <el-col :span="4" :offset="12">{{ item.floor.date.split("T")[0] }}</el-col>
              </el-row>
              <el-row style="margin-top: 2%">
                <el-container style="margin-bottom: 2%">
                  <el-main style="padding: 0">
                    <el-link style="word-wrap:break-word; word-break:break-all;" v-on:click="doReplyShowFunc(index)">
                      {{ item.floor.content }}
                    </el-link>

                  </el-main>

                </el-container>

              </el-row>
              <el-row v-if="item.doReplyLimit && doReplyShow" style="margin-top: 2%">
                <el-col :span="20">
                  <el-input

                      v-model="replyText"
                      :rows="2"
                      type="textarea"
                      placeholder="请发表评论"
                  />
                </el-col>
                <el-col :span="3" :offset="1">
                  <el-button style="margin-top: 20%" v-on:click="doReply(index)">发送</el-button>
                </el-col>
              </el-row>
              <el-row v-if="item.replies != ''">
                <el-col :span="3" :offset="21">
                  <el-icon v-on:click="repliesShow(index)">
                    <ArrowDown/>
                  </el-icon>
                </el-col>
              </el-row>


            </div>
            <el-pagination
                @current-change="getComment"
                :current-page="currentPage"
                layout="prev, pager, next"
                :total="currentTotal"
                :page-size="5"
                style="width: 100%; margin: 0"
            />

          </el-card>


        </el-row>
      </el-card>
    </el-main>
  </el-container>
  <el-drawer v-model="showRepliesFlag" :direction="'rtl'">
    <template #header>
      <h4>{{ originComment }}</h4>
    </template>
    <template #default>

      <div v-for="(reply, index) in currentReplies" style="margin-top: 2%; padding: 0">

        <el-row>
          <el-col :span="8" v-if="reply.others == ''">{{ reply.owner }}</el-col>
          <el-col :span="8" v-else>
            {{ reply.owner }}
            <div style="margin-left: 1%;margin-right: 1%;color: #919191">回复</div>
            {{ reply.others }}
          </el-col>
          <el-col :span="6" :offset="10">{{ reply.date.split("T")[0] }}</el-col>
        </el-row>
        <el-row style="margin-top: 2%">
          <el-container>
            <el-container>
              <el-main style="padding: 0">
                <el-link style="word-wrap:break-word; word-break:break-all;">{{ reply.content }}</el-link>

              </el-main>


            </el-container>
            <el-footer style="padding: 0;margin-bottom: 5%" height="100%">
              <el-row v-if="reply.others == ''">
                <el-col :offset="21" :span="3">
                  <el-button style="border-color: transparent;color: grey;background-color: transparent"
                             v-on:click="doOthersShowFunc(reply, index)">回复
                  </el-button>
                </el-col>
              </el-row>


              <el-row v-if="reply.doOthersShow && othersShow" style="margin-top: 2%">
                <el-col :span="18">
                  <el-input
                      v-model="othersText"
                      :rows="2"
                      type="textarea"
                      placeholder="请发表回复"
                  />
                </el-col>
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
        <el-col :span="20">
          <el-input

              v-model="replyText"
              :rows="2"
              type="textarea"
              placeholder="请发表评论"
          />
        </el-col>
        <el-col :span="3" :offset="1">
          <el-button style="margin-top: 20%" v-on:click="doReply(currentIndex)">发送</el-button>
        </el-col>
      </el-row>
    </template>
  </el-drawer>


  <Footer></Footer>
</template>

<script>
import Footer from "@/components/Footer";
import Header from "@/components/Header";
import {ElNotification} from "element-plus";

export default {
  name: "whiperdetail",
  components: {Footer, Header},
  data() {
    return {
      whisperId: 0,
      whisperDetail: {},
      currentPage: 1,
      currentTotal: 0,
      othersText: '',
      doReplyShow: false,
      doReplyLimit: -1,

      repliesShowLimit: -1,
      commentText: '',
      replyText: '',

      othersShow: false,
      othersIndex: -1,
      othersIndex2: -1,

      showRepliesFlag: false,
      originComment: '',
      currentReplies: [],
      currentIndex: -1,
      commentDetail: []
    }
  },
  methods: {
    //一级评论
    doComment() {
      let _this = this
      if (_this.commentText == '') {
        ElNotification({
          title: 'Error',
          message: '请填写评论内容',
          type: 'error',
        })
      } else {
        let temp = {
          content: _this.commentText,
          pid: _this.whisperDetail.id,
          uid: localStorage.getItem("userId")
        }

        _this.$axios.post("/whisper/doComment", temp).then(res => {
          _this.commentText = ''
          _this.getComment(_this.currentPage)
        })
      }

    },
    doReplyShowFunc(index) {
      let _this = this

      if (_this.doReplyLimit == -1) {
        _this.doReplyLimit = index
        _this.doReplyShow = !_this.doReplyShow
      } else {
        _this.doReplyShow = !_this.doReplyShow

        _this.commentDetail[_this.doReplyLimit].doReplyLimit = false
        _this.doReplyLimit = index
      }
      _this.commentDetail[index].doReplyLimit = true


    },
    //二级评论
    doReply(index) {

      let _this = this
      if (_this.replyText == '') {
        ElNotification({
          title: 'Error',
          message: '请填写评论内容',
          type: 'error',
        })
      } else {
        let temp = {
          content: _this.replyText,
          uid: localStorage.getItem("userId"),
          others: '',
          fid: _this.commentDetail[index].floor.id,
          reference: _this.showRepliesFlag ? _this.commentDetail[_this.currentIndex].replys[index].id : _this.commentDetail[index].floor.id
        }

        _this.$axios.post("/whisper/doReply", temp).then(res => {
          _this.replyText = ''
          _this.getComment(_this.currentPage)
          _this.repliesShow(index)
        })
      }

    },
    doOthers(index) {
      let _this = this
      if (_this.othersText == '') {
        ElNotification({
          title: 'Error',
          message: '请填写回复内容',
          type: 'error',
        })
      } else {
        let temp = {
          content: _this.othersText,
          fid: _this.commentDetail[_this.currentIndex].floor.id,
          uid: localStorage.getItem("userId"),
          others: _this.currentReplies[index].owner,
          reference: _this.currentReplies[index].id
        }

        _this.$axios.post("/whisper/doReply", temp).then(res => {
          _this.othersText = ''
          _this.getComment(_this.currentPage)

        })
      }

    },
    repliesShow(index) {
      let _this = this

      _this.showRepliesFlag = true
      _this.currentIndex = index
      _this.currentReplies = _this.commentDetail[index].replys

      _this.originComment = _this.commentDetail[index].floor.content
    },

    doOthersShowFunc(reply, index) {
      let _this = this
      _this.othersShow = !_this.othersShow
      if (_this.othersIndex == -1) {
        _this.othersIndex = index
      }
      _this.currentReplies[_this.othersIndex].doOthersShow = false
      _this.othersIndex = index
      _this.currentReplies[_this.othersIndex].doOthersShow = true


    },
    init() {
      let _this = this
      _this.whisperId = _this.$route.params.whisperId
      _this.$axios.post("whisper/getDetail", {cid: _this.whisperId}).then(res => {
        _this.whisperDetail = res.data.data

        _this.whisperDetail.pic = _this.whisperDetail.pic.split(",")

      })
    },
    getComment(currentPage) {
      let _this = this

      _this.$axios.post("whisper/getPostInfo", {pid: _this.whisperId, current: currentPage}).then(res => {
        _this.commentDetail = res.data.data

      })
    },
    goOff() {
      this.$router.go(-1)
    },
  },
  created() {
    this.init()
    this.getComment(1)
  }
}
</script>

<style scoped>

</style>