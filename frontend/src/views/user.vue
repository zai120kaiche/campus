<template>
  <div class="page_back page_this"></div>
  <img src="../assets/images/wave.png" alt="" class="ourpage" style="position: fixed;background-color: transparent">
  <Header></Header>
  <el-container style="margin-left: 10%; margin-right: 10%; height: 100%">
    <el-aside width="30%" style="margin-right: 5%">
      <el-card style="text-align: center">
        <el-row>
          <el-col>
            <el-avatar size="large" :src="userInfo.avatar"></el-avatar>
          </el-col>
        </el-row>
        <el-row style="margin-top: 5%">
          <el-col style="font-weight: bold">
            {{ userInfo.username }}
          </el-col>
        </el-row>
        <!--帖子收藏和交易收藏-->
        <br>
        <el-row v-if="selectFlag == 1" style="color: #88b0ef">我的收藏</el-row>
        <el-row v-else v-on:click="changeSelectFlag(1)">我的收藏</el-row>
        <el-divider style="margin-top: 3%;margin-bottom: 3%"></el-divider>
        <el-row v-if="selectFlag == 2" style="color: #88b0ef">我的发帖</el-row>
        <el-row v-else v-on:click="changeSelectFlag(2)">我的发帖</el-row>
        <el-divider style="margin-top: 3%;margin-bottom: 3%"></el-divider>
        <el-row v-if="selectFlag == 3" style="color: #88b0ef">我的交易</el-row>
        <el-row v-else v-on:click="changeSelectFlag(3)">我的交易</el-row>
        <el-divider style="margin-top: 3%;margin-bottom: 3%"></el-divider>
        <el-row v-if="selectFlag == 4" style="color: #88b0ef">个人信息</el-row>
        <el-row v-else v-on:click="changeSelectFlag(4)">个人信息</el-row>
      </el-card>
    </el-aside>
    <el-main style="padding: 0">
      <el-card v-if="selectFlag == 1" style="min-height: 80%; position: absolute; width: 55%">
        <el-menu
            :default-active="activeIndex"
            class="el-menu-demo"
            mode="horizontal"
            :ellipsis="false"
            @select="handleSelect"
        >
          <el-menu-item index="0">帖子</el-menu-item>
          <div class="flex-grow"/>
          <el-menu-item index="1">二手易物</el-menu-item>
        </el-menu>
        <div v-if="activeIndex == 0">
          <div v-for="item in collectPostData">
            <div>
              <el-row gutter="20">
                <el-col :span="17">
                  <router-link :to="{name: 'communitydetail', params: {communityId: item.id}}">
                    <el-row>
                      <el-col :span="14">{{ item.title }}</el-col>
                      <el-col :span="10">{{ item.date.split("T")[0] }}</el-col>
                    </el-row>
                  </router-link>
                </el-col>
                <el-col :span="4">
                  <el-popconfirm title="确定取消收藏吗?"
                                 @confirm="collectPostDelete(item.id)"
                                 confirm-button-text="是的"
                                 cancel-button-text="取消"
                  >
                    <template #reference>
                      <el-link style="padding: 0; border-color: transparent;font-size: small;">取消收藏</el-link>
                    </template>
                  </el-popconfirm>
                </el-col>
              </el-row>

            </div>
          </div>
          <el-pagination
              @current-change="collectPostCurrentPageChange"
              :current-page="collectPostCurrentPage"
              layout="prev, pager, next"
              :total="collectPostTotal"
              :page-size="17"
              style="width: 100%; margin: 0; bottom: 0; position: absolute"
          />
        </div>
        <div v-if="activeIndex == 1">
          <div v-for="item in collectTradeData">
            <div>
              <el-row gutter="20">
                <el-col :span="16">
                  <div>
                    <el-row v-on:click="getTradeDetail(item.id)" style="color: #88b0ef">
                      <el-col :span="17">{{ item.content }}</el-col>
                      <el-col :span="7">{{ item.date.split("T")[0] }}</el-col>
                    </el-row>
                  </div>
                </el-col>
                <el-col :span="1">

                  <div v-if="item.tradeFlag" style="color: crimson">出</div>
                  <div v-else style="color: #7AC23C">收</div>
                </el-col>
                <el-col :span="3">
                  <el-link style="padding: 0; border-color: transparent;font-size: small;" v-on:click="getTradeDetail(item.id)">详情</el-link>
                </el-col>
                <el-col :span="4">
                  <el-popconfirm title="确定取消收藏吗?"
                                 @confirm="collectTradeDelete(item.id)"
                                 confirm-button-text="是的"
                                 cancel-button-text="取消"
                  >
                    <template #reference>
                      <el-link style="padding: 0; border-color: transparent;font-size: small;">取消收藏</el-link>
                    </template>
                  </el-popconfirm>
                </el-col>

              </el-row>

            </div>
          </div>
          <el-pagination
              @current-change="collectTradeCurrentPageChange"
              :current-page="collectTradeCurrentPage"
              layout="prev, pager, next"
              :total="collectTradeTotal"
              :page-size="17"
              style="width: 100%; margin: 0; bottom: 0; position: absolute"
          />
        </div>


      </el-card>
      <el-card v-if="selectFlag == 2"  style="min-height: 80%; position: absolute; width: 55%">
        <div v-for="item in postData">
          <div>
            <el-row gutter="20">
              <el-col :span="17">
                <router-link :to="{name: 'communitydetail', params: {communityId: item.id}}">
                  <el-row>
                    <el-col :span="14">{{ item.title }}</el-col>
                    <el-col :span="10">{{ item.date.split("T")[0] }}</el-col>
                  </el-row>
                </router-link>
              </el-col>
              <el-col :span="3">
                <el-popconfirm title="确定删除该发帖吗?"
                               @confirm="postDelete(item.id)"
                               confirm-button-text="是的"
                               cancel-button-text="取消"
                >
                  <template #reference>
                    <el-link style="padding: 0; border-color: transparent;font-size: small;">删除</el-link>
                  </template>
                </el-popconfirm>
              </el-col>
            </el-row>

          </div>
        </div>
        <el-pagination
            @current-change="postCurrentPageChange"
            :current-page="postCurrentPage"
            layout="prev, pager, next"
            :total="postTotal"
            :page-size="20"
            style="width: 100%; margin: 0; bottom: 0; position: absolute"
        />
      </el-card>
      <el-card v-if="selectFlag == 3" style="min-height: 80%; position: absolute; width: 55%">
        <div v-for="item in tradeData">
          <div>
            <el-row gutter="20">
              <el-col :span="16">
                <div>
                  <el-row v-on:click="getTradeDetail(item.id)" style="color: #88b0ef">
                    <el-col :span="17">{{ item.content }}</el-col>
                    <el-col :span="7">{{ item.date.split("T")[0] }}</el-col>
                  </el-row>
                </div>
              </el-col>
              <el-col :span="1">

                <div v-if="item.tradeFlag" style="color: crimson">出</div>
                <div v-else style="color: #7AC23C">收</div>
              </el-col>
              <el-col :span="3">
                    <el-link style="padding: 0; border-color: transparent;font-size: small;" v-on:click="getTradeDetail(item.id)">详情</el-link>
              </el-col>
              <el-col :span="3">
                <el-popconfirm title="确定删除该物品吗?"
                               @confirm="tradeDelete(item.id)"
                               confirm-button-text="是的"
                               cancel-button-text="取消"
                >
                  <template #reference>
                    <el-link style="padding: 0; border-color: transparent;font-size: small;">删除</el-link>
                  </template>
                </el-popconfirm>
              </el-col>

            </el-row>

          </div>
        </div>
        <el-pagination
            @current-change="tradeCurrentPageChange"
            :current-page="tradeCurrentPage"
            layout="prev, pager, next"
            :total="tradeTotal"
            :page-size="20"
            style="width: 100%; margin: 0; bottom: 0; position: absolute"
        />
      </el-card>
      <el-card v-if="selectFlag == 4"></el-card>
    </el-main>
  </el-container>
  <el-drawer v-model="drawer" :direction="'rtl'">
    <template #header>
      <h4 v-if="tradeDetail.tradeFlag">出</h4>
      <h4 v-else>收</h4>
    </template>
    <template #default>
      <el-container style="height: 100%">
        <el-main style="padding: 0">
          <el-carousel height="150px" indicator-position="outside" v-if="tradeDetail.pic != 'undefined'">
            <el-carousel-item v-for="item in tradeDetail.pic" :key="item">
              <el-image :src="item"></el-image>
            </el-carousel-item>
          </el-carousel>
        </el-main>
        <el-aside width="60%">
          <el-row
              style="word-wrap:break-word; word-break:break-all;margin-left: 10%; margin-right: 10%;font-size: large">
            {{ tradeDetail.content }}
          </el-row>
        </el-aside>
      </el-container>
    </template>

  </el-drawer>

</template>

<script>
import Header from "@/components/Header";
import {ElNotification} from "element-plus";

export default {
  name: "user",
  created() {
    this.getUserInfo(localStorage.getItem("userId"))
    this.init()
  },
  components: {
    Header
  },
  data() {
    return {
      userInfo: {
        id: localStorage.getItem("userId")
      },
      selectFlag: 4,
      activeIndex: 0,

      postCurrentPage: 1,
      postTotal: 0,
      postData: [],

      collectPostCurrentPage: 1,
      collectPostTotal: 0,
      collectPostData: [],

      tradeCurrentPage: 1,
      tradeTotal: 0,
      tradeData: [],

      collectTradeCurrentPage: 1,
      collectTradeTotal: 0,
      collectTradeData: [],

      send: {
        uid: 0,
        current: 1,
        order: 1,
      },
      collectSend: {
        uid: 0,
        current: 1,
        order: 1,
      },
      tradeDetail: {},
      drawer: false

    }
  },
  methods: {
    init() {
      let _this = this
      _this.selectFlag = _this.$route.params.flag
      if(_this.selectFlag == 2 || _this.selectFlag == 3){
        _this.changeSelectFlag(_this.selectFlag)
      } else if(_this.selectFlag == 6){
        _this.changeSelectFlag(1)
        _this.handleSelect(0)
      } else if(_this.selectFlag == 7){
        _this.changeSelectFlag(1)
        _this.handleSelect(1)
      }


    },
    deleteCollectClick(id) {
      let _this = this
    },
    handleSelect(res) {
      console.log(res)
      let _this = this
      _this.activeIndex = res
      let temp = {
        uid: _this.userInfo.id,
        current: res == 0 ? _this.collectPostCurrentPage : _this.collectTradeCurrentPage,
        order: 1,
      }
      _this.collectSend = temp
      if (res == 0) {
        _this.$axios.post("community/getCollect", temp).then(res => {
          console.log(res.data.data)
          _this.collectPostData = res.data.data.records
          _this.collectPostTotal = res.data.data.total
        })
      } else {
        _this.$axios.post("trade/getCollect", temp).then(res => {
          console.log(res.data.data)
          _this.collectTradeData = res.data.data.records
          _this.collectTradeTotal = res.data.data.total
        })
      }
    },
    postCurrentPageChange(currentPage) {
      let _this = this
      _this.send.current = currentPage
      _this.postCurrentPage = currentPage
      _this.$axios.post("community/getPostById", _this.send).then(res => {
        console.log(res.data.data)
        _this.postData = res.data.data.records
      })
    },
    collectPostCurrentPageChange(currentPage) {
      let _this = this
      _this.collectSend.current = currentPage
      _this.collectPostCurrentPage = currentPage
      _this.$axios.post("community/getCollect", _this.collectSend).then(res => {
        console.log(res.data.data)
        _this.collectPostData = res.data.data.records
      })
    },
    tradeCurrentPageChange(currentPage) {
      let _this = this
      _this.send.current = currentPage
      _this.tradeCurrentPage = currentPage
      _this.$axios.post("trade/getCommodityById", _this.send).then(res => {
        console.log(res.data.data)
        _this.tradeData = res.data.data.records
      })
    },
    collectTradeCurrentPageChange(currentPage) {
      let _this = this
      _this.collectSend.current = currentPage
      _this.collectTradeCurrentPage = currentPage
      _this.$axios.post("trade/getCollect", _this.collectSend).then(res => {
        console.log(res.data.data)
        _this.collectTradeData = res.data.data.records
      })
    },
    getUserInfo(index) {
      let _this = this
      let temp = {
        id: localStorage.getItem("userId")
      }
      _this.$axios.post("user/index", temp).then(res => {
        _this.userInfo = res.data.data
      })
    },
    changeSelectFlag(index) {
      let _this = this
      _this.selectFlag = index
      if (index == 2) {
        _this.search(0)
      } else if(index == 3){
        _this.search(1)
      }
    },
    search(res) {
      let _this = this

      let temp = {
        uid: _this.userInfo.id,
        current: res == 0 ? _this.postCurrentPage : _this.tradeCurrentPage,
        order: 1,
      }
      console.log(temp)
      _this.send = temp
      if (res == 0) {
        _this.$axios.post("community/getPostById", temp).then(res => {
          console.log(res.data.data)
          _this.postData = res.data.data.records
          _this.postTotal = res.data.data.total
        })
      } else {
        _this.$axios.post("trade/getCommodityById", temp).then(res => {
          console.log(res.data.data)
          _this.tradeData = res.data.data.records
          _this.tradeTotal = res.data.data.total
        })
      }
    },
    postDelete(res) {
      console.log(res)
      let _this = this
      let temp = {
        uid: _this.userInfo.id,
        objectIds: [res]
      }
      _this.$axios.post("/community/deletePostsByIds", temp).then(res=>{
        if(res.data.code == 200){
          ElNotification({
            title: 'Success',
            message: '删除成功',
            type: 'success',
          })
          _this.postCurrentPageChange(_this.postCurrentPage)
        }

      }).catch(res=>{
        ElNotification({
          title: 'Error',
          message: '删除失败',
          type: 'error',
        })
      })
    },
    tradeDelete(res) {
      let _this = this
      let temp = {
        uid: _this.userInfo.id,
        objectIds: [res]
      }
      _this.$axios.post("/trade/deleteCommodity", temp).then(res=>{
        if(res.data.code == 200){
          ElNotification({
            title: 'Success',
            message: '删除成功',
            type: 'success',
          })
          _this.tradeCurrentPageChange(_this.tradeCurrentPage)
        }
      }).catch(res=>{
        ElNotification({
          title: 'Error',
          message: '删除失败',
          type: 'error',
        })
      })
    },
    collectPostDelete(res) {
      let _this = this
      let temp = {
        uid: _this.userInfo.id,
        objectIds: [res]
      }
      _this.$axios.post("/community/deleteCollectByIds", temp).then(res=>{
        if(res.data.code == 200){
          ElNotification({
            title: 'Success',
            message: '取消收藏成功',
            type: 'success',
          })
          _this.collectPostCurrentPageChange(_this.collectPostCurrentPage)
        }

      }).catch(res=>{
        ElNotification({
          title: 'Error',
          message: '取消收藏失败',
          type: 'error',
        })
      })
    },
    collectTradeDelete(res) {
      let _this = this
      let temp = {
        uid: _this.userInfo.id,
        objectIds: [res]
      }
      _this.$axios.post("/trade/deleteCollect", temp).then(res=>{
        if(res.data.code == 200){
          ElNotification({
            title: 'Success',
            message: '取消收藏成功',
            type: 'success',
          })
          _this.collectTradeCurrentPageChange(_this.collectTradeCurrentPage)
        }
      }).catch(res=>{
        ElNotification({
          title: 'Error',
          message: '取消收藏失败',
          type: 'error',
        })
      })
    },
    getTradeDetail(id) {
      let _this = this
      _this.$axios.post("/trade/view", {cid: id}).then(res => {
        _this.tradeDetail = res.data.data
        console.log(_this.tradeDetail.pic)
        if (_this.tradeDetail.pic.search(",") != -1 && _this.tradeDetail.pic != null) {
          _this.tradeDetail.pic = _this.tradeDetail.pic.split(",")
        } else {
          let temp = []
          temp.push(_this.tradeDetail.pic)
          _this.tradeDetail.pic = temp
        }

        console.log(_this.tradeDetail)
        _this.drawer = true
      })
    },


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