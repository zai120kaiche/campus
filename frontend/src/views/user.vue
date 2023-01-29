<template>
  <div class="page_back page_this"></div>
  <img src="../assets/images/wave.png" alt="" class="ourpage" style="position: fixed;background-color: transparent">
  <Header></Header>
  <el-container v-if="userInfo.id != 'null'" style="margin-left: 10%; margin-right: 10%; height: 100%">
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
          <el-table :data="collectPostData"
                    border style="width: 100%"
                    empty-text="NAN">
            <el-table-column prop="title" label="标题" width="180" show-overflow-tooltip="true"/>
            <el-table-column prop="date" label="时间" width="180" show-overflow-tooltip="true"/>
            <el-table-column fixed="right" label="操作" width="160">
              <template #default="scope">
                <el-popconfirm title="确定取消收藏吗?"
                               @confirm="collectPostDelete(scope.row.id)"
                               confirm-button-text="确定"
                               cancel-button-text="取消"
                >
                  <template #reference>
                    <el-button link type="primary" size="small">取消收藏</el-button>
                  </template>
                </el-popconfirm>
                <el-button link type="primary" size="small" v-on:click="forDetail(scope.row.id)">查看详情</el-button>
              </template>
            </el-table-column>
          </el-table>

          <el-pagination
              @current-change="collectPostCurrentPageChange"
              :current-page="collectPostCurrentPage"
              layout="prev, pager, next"
              :total="collectPostTotal"
              :page-size="8"
              style="width: 100%; margin: 0; bottom: 0; position: absolute"
          />
        </div>
        <div v-if="activeIndex == 1">
          <el-table :data="collectTradeData"
                    border style="width: 100%"
                    empty-text="NAN">
            <el-table-column prop="content" label="内容" width="180" show-overflow-tooltip="true"/>
            <el-table-column prop="date" label="时间" width="180" show-overflow-tooltip="true"/>
            <el-table-column fixed="right" label="操作" width="160">
              <template #default="scope">
                <el-popconfirm title="确定取消收藏吗?"
                               @confirm="collectTradeDelete(scope.row.id)"
                               confirm-button-text="确定"
                               cancel-button-text="取消"
                >
                  <template #reference>
                    <el-button link type="primary" size="small">取消收藏</el-button>
                  </template>
                </el-popconfirm>
                <el-button link type="primary" size="small" v-on:click="getTradeDetail(scope.row.id)">查看详情</el-button>
              </template>
            </el-table-column>
          </el-table>

          <el-pagination
              @current-change="collectTradeCurrentPageChange"
              :current-page="collectTradeCurrentPage"
              layout="prev, pager, next"
              :total="collectTradeTotal"
              :page-size="8"
              style="width: 100%; margin: 0; bottom: 0; position: absolute"
          />
        </div>


      </el-card>
      <el-card v-if="selectFlag == 2" style="min-height: 80%; position: absolute; width: 55%">
        <el-table :data="postData"
                  border style="width: 100%"
                  empty-text="NAN">
          <el-table-column prop="title" label="标题" width="180" show-overflow-tooltip="true"/>
          <el-table-column prop="date" label="时间" width="180" show-overflow-tooltip="true"/>
          <el-table-column fixed="right" label="操作" width="160">
            <template #default="scope">
              <el-popconfirm title="确定删除发帖吗?"
                             @confirm="postDelete(scope.row.id)"
                             confirm-button-text="确定"
                             cancel-button-text="取消"
              >
                <template #reference>
                  <el-button link type="primary" size="small">删除</el-button>
                </template>
              </el-popconfirm>
              <el-button link type="primary" size="small" v-on:click="forDetail(scope.row.id)">查看详情</el-button>
            </template>
          </el-table-column>
        </el-table>


        <el-pagination
            @current-change="postCurrentPageChange"
            :current-page="postCurrentPage"
            layout="prev, pager, next"
            :total="postTotal"
            :page-size="10"
            style="width: 100%; margin: 0; bottom: 0; position: absolute"
        />
      </el-card>
      <el-card v-if="selectFlag == 3" style="min-height: 80%; position: absolute; width: 55%">
        <el-table :data="tradeData"
                  border style="width: 100%"
                  empty-text="NAN">
          <el-table-column prop="content" label="内容" width="180" show-overflow-tooltip="true"/>
          <el-table-column prop="date" label="时间" width="180" show-overflow-tooltip="true"/>
          <el-table-column fixed="right" label="操作" width="160">
            <template #default="scope">
              <el-popconfirm title="确定删除商品吗?"
                             @confirm="tradeDelete(scope.row.id)"
                             confirm-button-text="确定"
                             cancel-button-text="取消"
              >
                <template #reference>
                  <el-button link type="primary" size="small">删除</el-button>
                </template>
              </el-popconfirm>
              <el-button link type="primary" size="small" v-on:click="getTradeDetail(scope.row.id)">查看详情</el-button>
            </template>
          </el-table-column>
        </el-table>

        <el-pagination
            @current-change="tradeCurrentPageChange"
            :current-page="tradeCurrentPage"
            layout="prev, pager, next"
            :total="tradeTotal"
            :page-size="10"
            style="width: 100%; margin: 0; bottom: 0; position: absolute"
        />
      </el-card>
      <el-card v-if="selectFlag == 4" style="text-align: center">
        <el-row>
          <el-col :offset="10">
            <el-link v-on:click="logout">
              登出
              <el-icon><Right /></el-icon>
            </el-link>

          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-avatar size="large" :src="userInfo.avatar" v-if="!changeInfoFlag">

            </el-avatar>
            <el-upload
                v-if="changeInfoFlag"
                v-model:file-list="fileList"
                action="http://49.232.222.169:8081/api/pri/file/upload"
                list-type="picture-card"
                class="upload-demo"
                :on-preview="handlePictureCardPreview"
                :on-remove="handleRemove"
                :on-success="handleSucc"
                :limit="1">
              <el-icon>
                <Plus/>
              </el-icon>
            </el-upload>
          </el-col>
        </el-row>
        <el-row style="margin-top: 5%">
          <el-col style="font-weight: bold" v-if="!changeInfoFlag">
            {{ userInfo.username }}
          </el-col>

        </el-row>
        <div style="font-weight: bold;text-align: left">
          <el-row style="margin-top: 2%; margin-left: 3%" v-if="changeInfoFlag">
            <el-row>
              <el-col :span="8">
                用户名：
              </el-col>
              <el-col :span="15">
                <el-input :placeholder="userInfo.username" v-model="changeUserInfo.username"
                          style="width: 180%"></el-input>
              </el-col>
            </el-row>
          </el-row>
          <el-row style="margin-top: 2%; margin-left: 3%">
            <div v-if="!changeInfoFlag">
              电子邮箱：{{ userInfo.email ? userInfo.email : '暂未绑定' }}
            </div>
            <div v-else>
              <el-row>
                <el-col :span="8">
                  电子邮箱：
                </el-col>
                <el-col :span="15">
                  <el-input :placeholder="userInfo.email" v-model="changeUserInfo.email" style="width: 180%"></el-input>
                </el-col>
              </el-row>
            </div>
          </el-row>

          <el-row style="margin-top: 2%; margin-left: 3%">
            <div v-if="!changeInfoFlag">
              联系方式：{{ userInfo.phone ? userInfo.phone : '暂未绑定' }}
            </div>
            <div v-else>
              <el-row>
                <el-col :span="8">
                  联系方式：
                </el-col>
                <el-col :span="15">
                  <el-input :placeholder="userInfo.phone" v-model="changeUserInfo.phone" style="width: 180%"></el-input>
                </el-col>
              </el-row>
            </div>
          </el-row>
          <el-row style="margin-top: 2%; margin-left: 3%" v-if="!changeInfoFlag">
            已有积分：{{ userInfo.score }}
          </el-row>
        </div>
        <el-row>
          <el-col :offset="10" >
            <el-button v-if="!changeInfoFlag" style="border-color: transparent" v-on:click="changeInfoFlag = true">
              修改信息
            </el-button>
            <el-button v-else v-on:click="sendSms">确认修改</el-button>
          </el-col>
        </el-row>
      </el-card>
    </el-main>
  </el-container>
  <el-card v-else style="text-align: center;width: 70%; margin-right: 15%;margin-left: 15%;height: 30%;position: absolute">
    <br>
    <h2>你还没有登录，点击前往</h2><el-link v-on:click="login"><h2>登录</h2></el-link>
  </el-card>
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
  <el-dialog v-model="dialog" title="验证码确认">
    <div v-if="changeUserInfo.phone != ''">我们将向手机号：{{ changeUserInfo.phone }}发送验证码，请注意查收</div>
    <div v-else-if="changeUserInfo.email != ''">我们将向邮箱：{{ changeUserInfo.email }}发送验证码，请注意查收</div>
    <div v-else>请至少填写手机号或邮箱中的一项</div>
    <el-input placeholder="请输入验证码" v-model="sms" style="margin-top: 5%"></el-input>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialog = false">关闭</el-button>
        <el-button type="primary" @click="checkSms">
          确认修改
        </el-button>
      </span>
    </template>
  </el-dialog>

</template>

<script>
import Header from "@/components/Header";
import {ElNotification} from "element-plus";

export default {
  name: "user",
  created() {
    if(localStorage.getItem("userId") != 'null'){
      this.getUserInfo(localStorage.getItem("userId"))
      this.init()
    }

  },
  components: {
    Header
  },
  data() {
    return {
      sendData: {
        identifier: 0,
        phoneOrEmail: ''
      },
      sms: '',
      dialog: false,
      fileList: [],
      showPic: false,
      changeInfoFlag: false,
      changeUserInfo: {
        id: localStorage.getItem("userId"),
        username: '',
        email: '',
        phone: '',
        password: '',
        avatar: ''
      },
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
    login(){
      this.$router.push("/")
    },
    logout(){
      localStorage.setItem("userId", null)
      this.$router.push("/")
    },
    forDetail(id){
      let _this = this
      _this.$router.push({
        name: 'communitydetail',
        params: {
          communityId: id
        }
      })
    },
    sendSms() {
      let _this = this
      _this.dialog = true
      if (_this.changeUserInfo.phone != '') {
        _this.sendData.phoneOrEmail = _this.changeUserInfo.phone
        console.log(_this.sendData)
        _this.$axios.post('/identify/send', _this.sendData).then(res => {
          ElNotification({
            title: 'Success',
            message: '验证码发送成功',
            type: 'success',
          })
        })
      } else if (_this.changeUserInfo.email != '') {
        _this.sendData.phoneOrEmail = _this.changeUserInfo.email
        _this.$axios.post('/identify/send', _this.sendData).then(res => {
          ElNotification({
            title: 'Success',
            message: '验证码发送成功',
            type: 'success',
          })
        })
      } else {
        ElNotification({
          title: 'Error',
          message: '请填写绑定手机号或邮箱',
          type: 'error',
        })
      }

    },
    checkSms() {
      let _this = this
      _this.sendData.identifier = _this.sms
      _this.$axios.post('/identify/check', _this.sendData).then(res => {
        if (res.data.code == 200) {
          _this.changeInfo()
        } else {
          ElNotification({
            title: 'Error',
            message: '验证码不正确',
            type: 'error',
          })
        }
      })
    },
    handlePictureCardPreview(uploadFile) {
      console.log(uploadFile)
      this.dialogImageUrl = uploadFile.response.data.returnImgeUrl
      this.dialogVisible = true
    },
    handleRemove() {

    },
    handleSucc(res) {
      let _this = this
      _this.changeUserInfo.avatar = res.data.returnImgeUrl
    },
    changeInfo() {
      let _this = this

      _this.$axios.post("/user/change", _this.changeUserInfo).then(res => {
        if (res.data.code == 200) {
          ElNotification({
            title: 'Success',
            message: '修改成功',
            type: 'success',
          })
          _this.changeInfoFlag = false
          _this.sms = ''
          _this.dialog = false
        }
      }).catch(res => {
        ElNotification({
          title: 'Error',
          message: '修改失败',
          type: 'error',
        })
      })
    },
    init() {
      let _this = this
      _this.selectFlag = _this.$route.params.flag
      if (_this.selectFlag == 2 || _this.selectFlag == 3) {
        _this.changeSelectFlag(_this.selectFlag)
      } else if (_this.selectFlag == 6) {
        _this.changeSelectFlag(1)
        _this.handleSelect(0)
      } else if (_this.selectFlag == 7) {
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
        _this.changeUserInfo = res.data.data
      })
    },
    changeSelectFlag(index) {
      let _this = this
      _this.selectFlag = index
      if (index == 2) {
        _this.search(0)
      } else if (index == 3) {
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
      _this.$axios.post("/community/deletePostsByIds", temp).then(res => {
        if (res.data.code == 200) {
          ElNotification({
            title: 'Success',
            message: '删除成功',
            type: 'success',
          })
          _this.postCurrentPageChange(_this.postCurrentPage)
        }

      }).catch(res => {
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
      _this.$axios.post("/trade/deleteCommodity", temp).then(res => {
        if (res.data.code == 200) {
          ElNotification({
            title: 'Success',
            message: '删除成功',
            type: 'success',
          })
          _this.tradeCurrentPageChange(_this.tradeCurrentPage)
        }
      }).catch(res => {
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
      _this.$axios.post("/community/deleteCollectByIds", temp).then(res => {
        if (res.data.code == 200) {
          ElNotification({
            title: 'Success',
            message: '取消收藏成功',
            type: 'success',
          })
          _this.collectPostCurrentPageChange(_this.collectPostCurrentPage)
        }

      }).catch(res => {
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
      _this.$axios.post("/trade/deleteCollect", temp).then(res => {
        if (res.data.code == 200) {
          ElNotification({
            title: 'Success',
            message: '取消收藏成功',
            type: 'success',
          })
          _this.collectTradeCurrentPageChange(_this.collectTradeCurrentPage)
        }
      }).catch(res => {
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