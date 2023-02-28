<template>
  <div class="page_back page_this"></div>

  <img src="../assets/images/wave.png" alt="" class="ourpage" style="position: fixed;background-color: transparent">
  <Header></Header>

  <el-container v-if="!isPhone" style="margin-right: 20%;margin-left: 20%">
    <el-main>
      <el-card>
        <el-row v-on:click="goOff" style="margin-bottom: 2%">
          <el-icon style="margin-top: 0.5%"><ArrowLeftBold /></el-icon>
          返回
        </el-row>
        <el-row>
          <el-col :span="6">
            <h3>{{activityDetail.title}}</h3>
          </el-col>
          <el-col :span="4" :offset="14">
            {{activityDetail.date}}
          </el-col>
        </el-row>
        <el-row style="margin-top: 5%;margin-bottom: 5%;font-weight: lighter;color: #919191">
          介绍：{{activityDetail.des}}
        </el-row>
        <el-row>
          <el-carousel indicator-position="outside" style="width: 100%" height="200px">
            <el-carousel-item v-for="item in activityDetail.pic" :key="item">
              <el-image :src="item" v-on:click="handlePictureCardPreview(item)" fit="contain"></el-image>
            </el-carousel-item>
          </el-carousel>
        </el-row>
        <el-row style="word-wrap:break-word; word-break:break-all;margin-bottom: 6%;margin-right: 5%;margin-left: 5%;font-size: large;line-height: initial">
          {{activityDetail.content}}
        </el-row>
        <el-row style="margin-bottom: 3%">
          <h3>注意事项</h3>
        </el-row>
        <el-row style="word-wrap:break-word; word-break:break-all;">
          {{activityDetail.concern}}
        </el-row>
        <el-row style="margin-top: 3%;margin-bottom: 4%">
          参与人数：{{activityDetail.pnum+" "}}人
        </el-row>
        <el-row>
          <el-col :offset="20">
            <el-button style="background-color: #88b0ef" v-on:click="submit">点击报名</el-button>
          </el-col>
        </el-row>


      </el-card>
    </el-main>
  </el-container>
  <el-container v-else style="margin-right: 2%;margin-left: 2%">
    <el-main>
      <el-card>
        <el-row v-on:click="goOff" style="margin-bottom: 2%">
          <el-icon style="margin-top: 0.5%"><ArrowLeftBold /></el-icon>
          返回
        </el-row>
        <el-row>
          <el-col :span="6">
            <h3>{{activityDetail.title}}</h3>
          </el-col>
          <el-col :span="4" :offset="14">
            {{activityDetail.date}}
          </el-col>
        </el-row>
        <el-row style="margin-top: 5%;margin-bottom: 5%;font-weight: lighter;color: #919191">
          介绍：{{activityDetail.des}}
        </el-row>
        <el-row>
          <el-carousel indicator-position="outside" style="width: 100%" height="200px">
            <el-carousel-item v-for="item in activityDetail.pic" :key="item">
              <el-image :src="item" v-on:click="handlePictureCardPreview(item)" fit="contain"></el-image>
            </el-carousel-item>
          </el-carousel>
        </el-row>
        <el-row style="word-wrap:break-word; word-break:break-all;margin-bottom: 6%;margin-right: 5%;margin-left: 5%;font-size: large;line-height: initial">
          {{activityDetail.content}}
        </el-row>
        <el-row style="margin-bottom: 3%">
          <h3>注意事项</h3>
        </el-row>
        <el-row style="word-wrap:break-word; word-break:break-all;">
          {{activityDetail.concern}}
        </el-row>
        <el-row style="margin-top: 3%;margin-bottom: 4%">
          参与人数：{{activityDetail.pnum+" "}}人
        </el-row>
        <el-row>
          <el-col :offset="17">
            <el-button style="background-color: #88b0ef" v-on:click="submit">点击报名</el-button>
          </el-col>
        </el-row>


      </el-card>
    </el-main>
  </el-container>
  <el-dialog v-if="!isPhone" v-model="dialogVisible" style="width: 100%">
    <img w-full :src="dialogImageUrl" alt="Preview Image" style="width: 100%"/>
  </el-dialog>
  <el-dialog v-if="isPhone" v-model="dialogVisible" style="width: 98%">
    <img w-full :src="dialogImageUrl" alt="Preview Image" style="width: 100%"/>
  </el-dialog>
  <el-drawer v-if="!isPhone" v-model="drawer" :direction="'rtl'">
    <template #header>
      <h4>报名须知</h4>
    </template>
    <template #default>
      <h4>参与须知</h4>
      <div style="word-wrap:break-word; word-break:break-all;margin-top: 4%">{{activityDetail.concern}}</div>
      <h4>联系方式</h4>
      <div style="margin-top: 4%">{{activityDetail.connectWay}}</div>
      <h4>活动群</h4>
      <el-image :src="activityDetail.grouppic" v-on:click="handlePictureCardPreview(activityDetail.grouppic)"></el-image>
    </template>
    <template #footer>
      <div style="flex: auto">
        <el-button type="primary" @click="doClick(activityDetail.id)">报名</el-button>
      </div>
    </template>
  </el-drawer>
  <el-drawer v-if="isPhone" v-model="drawer" size="88%" :direction="'btt'">
    <template #header>
      <h4>报名须知</h4>
    </template>
    <template #default>
      <h4>参与须知</h4>
      <div style="word-wrap:break-word; word-break:break-all;margin-top: 4%">{{activityDetail.concern}}</div>
      <h4>联系方式</h4>
      <div style="margin-top: 4%">{{activityDetail.connectWay}}</div>
      <h4>活动群</h4>
      <el-image :src="activityDetail.grouppic" v-on:click="handlePictureCardPreview(activityDetail.grouppic)"></el-image>
    </template>
    <template #footer>
      <div style="flex: auto">
        <el-button type="primary" @click="doClick(activityDetail.id)">报名</el-button>
      </div>
    </template>
  </el-drawer>
  <Footer></Footer>
</template>

<script>
import Header from "@/components/Header";
import Footer from "@/components/Footer";
import {ElNotification} from "element-plus";
import {inject} from "vue";

export default {
  name: "activitydetail",
  components:{
    Header,
    Footer
  },
  data(){
    return{
      isPhone: inject('isPhone'),
      activityDetail: {},
      activityId: 0,
      dialogImageUrl: '',
      dialogVisible: false,
      drawer: false
    }
  },
  methods:{
    goOff(){this.$router.go(-1)},
    doClick(id){
      let _this = this
      _this.$axios.post("activity-collect/do", {uid: localStorage.getItem("userId"), aid: id}).then(res=>{
        ElNotification({
          title: 'Success',
          message: '报名成功',
          type: 'success',
        })
      }).catch(res=>{
        ElNotification({
          title: 'Error',
          message: '报名失败',
          type: 'error',
        })
      })
    },
    submit(){
      let _this = this
      _this.drawer = true
    },
    handlePictureCardPreview(uploadFile) {
      this.dialogImageUrl = uploadFile
      this.dialogVisible = true
    },
    init(){
      let _this = this
      _this.activityId = _this.$route.params.activityId
      _this.$axios.post("activity/detail", {cid: _this.activityId}).then(res=>{
        _this.activityDetail = res.data.data
        _this.activityDetail.date = _this.activityDetail.date.split("T")[0]

        _this.activityDetail.pic = _this.activityDetail.pic.split(",")

      })
    }
  },
  created() {
    this.init()
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