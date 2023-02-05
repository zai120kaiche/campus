<template>
  <div class="page_back page_this"></div>
  <img src="../assets/images/wave.png" alt="" class="ourpage" style="position: fixed;background-color: transparent">
  <Header></Header>

  <el-container style="margin-bottom: 5%">
    <el-aside class="animate__animated animate__fadeInLeft" width="14%" style="margin-left: 7%; margin-right: 4%">
      <el-card>
        <div style="font-weight: bold">
          收闲置
        </div>
        <div style="margin-top: 8%">
        </div>
        <div v-for="(type, index) in tradeTypeTableData" style="margin-top: 4%">
          <button v-if="type.id == currentTradeType"
                  style="color: #88b0ef; background-color: transparent;border-color: transparent; width: 100%; border-radius: 5px"
                  v-on:click="jumpTypeBlock(type.id, type.tradetypename)">{{ type.tradetypename }}
          </button>
          <button v-else style="background-color: transparent; border-color: transparent"
                  v-on:click="jumpTypeBlock(type.id, type.tradetypename)">{{ type.tradetypename }}
          </button>
        </div>
        <el-divider style="padding-bottom: 0; margin-top: 10px; margin-bottom: 0"></el-divider>
      </el-card>
      <el-card style="margin-top: 10%">

      </el-card>
    </el-aside>
    <el-main class="animate__animated animate__fadeInDown" style="padding: 0">
      <el-card>
        <el-input
            v-model="postData.content"
            clearable
            placeholder="请输入你需要的商品"
        />
        <el-row style="margin-top: 3%;">
          <el-input placeholder="请输入您期待的价格" v-model="postData.price">

          </el-input>
        </el-row>


        <el-row style="margin-top: 3%" :gutter="8">
          <el-col :span="5">


            <el-row style="font-weight: bold">

              <el-col>
                <el-select
                    v-model="tradeTypeSelect"
                    placeholder="# 类别"
                    style="width: 100%"
                    filterable
                    multiple
                >
                  <el-option
                      v-for="item in tradeTypeTableData"
                      :key="item.tradetypename"
                      :label="item.tradetypename"
                      :value="item.id"
                  />
                </el-select>
              </el-col>

            </el-row>


          </el-col>
          <el-col :span="5">


            <el-row style="font-weight: bold">

              <el-col>
                <el-select
                    v-model="postData.university"
                    placeholder="所属学校"
                    style="width: 100%"
                    filterable
                >
                  <el-option
                      v-for="item in schoolTableData"
                      :key="item.schoolname"
                      :label="item.schoolname"
                      :value="item.id"
                  />
                </el-select>
              </el-col>

            </el-row>


          </el-col>
          <el-col :span="4">


            <el-row style="font-weight: bold">


              <el-col>

                <el-button style="font-weight: lighter" @click="picDialogVisible = true">
                  <el-icon style="margin-right: 5%">
                    <Picture/>
                  </el-icon>
                  图片
                </el-button>
              </el-col>


            </el-row>


          </el-col>
          <el-col :span="4">
            <el-row style="font-weight: bold">

              <el-col>
                <el-select
                    v-model="postData.tradeFlag"
                    placeholder="出/收"
                    style="width: 100%"
                    filterable
                >
                  <el-option
                      v-for="item in butOrSaleList"
                      :key="item.name"
                      :label="item.name"
                      :value="item.value"
                  />
                </el-select>
              </el-col>

            </el-row>
          </el-col>
          <el-col :span="1">
            <el-popover trigger="hover" placement="bottom" :width="160">

              <el-row style="font-weight: bold; margin-top: 5%">
                <el-icon style="margin-top: 2%">
                  <Clock/>
                </el-icon>
                <div style="margin-left: 11%">
                  定时发布
                </div>
              </el-row>
              <el-row style="font-weight: bold; margin-top: 5%">
                <el-icon style="margin-top: 2%">
                  <Top/>
                </el-icon>
                <div style="margin-left: 11%">
                  积分发急收
                </div>
              </el-row>
              <template #reference>
                <el-button
                    style="background-color: transparent; border-color: transparent; font-size: large; color: #333333; padding: 0">
                  <el-icon>
                    <MoreFilled/>
                  </el-icon>
                </el-button>
              </template>
            </el-popover>
          </el-col>

          <el-col :span="3" :offset="1">
            <el-button style="background-color: #A2C9FC" v-on:click="doGoodPost">发送</el-button>
          </el-col>
        </el-row>
      </el-card>
      <el-card style="margin-top: 1%">
        <el-row>
          <el-col :span="6">
            <el-icon style="color: #88b0ef; margin-top: 2%">
              <Promotion/>
            </el-icon>
            物品查找
          </el-col>
          <el-col :span="12">
            <el-input placeholder="请输入您想查找的物品名称"
                      v-model="keyWord"
            >
              <template #append>
                <el-button style="background-color: transparent; border-color: transparent">
                  <el-icon v-on:click="getGoodList(1, 2)">
                    <Search/>
                  </el-icon>

                </el-button>
              </template>

            </el-input>
          </el-col>
        </el-row>
      </el-card>
      <el-row>

        <el-card v-for="(item, index) in goodList"
                 style="margin-top: 1%; width: 28%; margin-right: 2%; margin-left: 3%"
                 v-on:click="getTradeDetail(item.id)">


          <el-image
              v-if="item.pic != 'undefined' && item.pic != null"
              :src="item.pic.split(',')[0]"
              class="image"
              style="border-radius: 5px"
          />
          <div style="text-align: center;color: #333333;word-wrap:break-word; word-break:break-all;
              text-overflow:ellipsis;overflow:hidden;display:-webkit-box;
              -webkit-line-clamp:1;-webkit-box-orient:vertical;">{{ item.content }}
          </div>
          <el-row style="color: #333333">
            <el-col :span="12">￥ {{ item.price }}</el-col>
            <el-col v-if="item.tradeFlag" :span="3" :offset="9"
                    style="font-weight: bold; font-size: large; color: crimson">出
            </el-col>
            <el-col v-else :span="4" :offset="8" style="font-weight: bold; font-size: large; color: #7AC23C">收</el-col>
          </el-row>
          <el-row style="color: #333333">
            <el-col :span="12">{{ item.collectNum }}人想要</el-col>
            <el-col :span="12" style="margin-top: 2%">
              <el-icon style="margin-top: 2%; margin-right: 15%; margin-left: 15%">
                <View/>
              </el-icon>
              {{ item.viewNum }}
            </el-col>
          </el-row>

          <div :span="6" style="color: #919191; font-weight: lighter; font-size: xx-small">学校:
            {{ item.universityName }}
          </div>
          <div :span="14" style="color: #919191; font-weight: lighter; font-size: xx-small">时间:
            {{ item.date.split("T")[0] }}
          </div>


        </el-card>
      </el-row>

      <el-card style="margin-top: 1%; padding: 0">
        <el-pagination
            @current-change="currentPageChange"
            :current-page="currentPage"
            layout="prev, pager, next"
            :total="currentTotal"
            :page-size="9"
            style="width: 100%; margin: 0"
        />
      </el-card>


    </el-main>
    <el-aside class="animate__animated animate__fadeInRight" width="14%" style="margin-right: 7%; margin-left: 4%">

      <el-card>
        <div style="font-weight: bold">
          我的
        </div>
        <el-row style="text-align: center; font-size: small; margin-top: 10%">
          <el-col :span="12" v-on:click="toMyTrade">
            <el-iocn>
              <EditPen style="width: 30%"/>
            </el-iocn>
            <div>
              发闲置
            </div>
          </el-col>
          <el-col :span="12" v-on:click="toMyTrade">
            <el-iocn>
              <Bicycle style="width: 30%"/>
            </el-iocn>
            <div>
              收闲置
            </div>
          </el-col>
        </el-row>
        <el-row style="text-align: center; font-size: small; margin-top: 10%">
          <el-col :span="12"  v-on:click="toMyTradeCollect">
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
              平台公告

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
  <el-dialog
      v-model="picDialogVisible"
      title="添加图片"
      width="60%"
      :before-close="handleClose"
  >
    <span>请添加您要选择的图片（上限三张）</span>
    <template #footer>
      <el-row>
        <el-col :span="24">
          <Pic @handleSelect="picSelect"></Pic>
        </el-col>
      </el-row>
      <el-row style="margin-top: 2%">
        <el-col>
          <el-button v-on:click="picDialogVisible = false">确认上传</el-button>
        </el-col>
      </el-row>
    </template>
  </el-dialog>
  <Footer></Footer>
  <Tool></Tool>
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
    <template #footer>
      <div style="flex: auto">
        <el-button type="primary" @click="collectClick(tradeDetail.id)">收藏</el-button>
        <el-button type="primary" @click="callSaler(tradeDetail.id, tradeDetail.owner)">联系卖家</el-button>
        <el-button type="primary" @click="saleOnLine(tradeDetail.id, tradeDetail.owner)">线上交易</el-button>
      </div>
    </template>
  </el-drawer>
  <Chat :show="userChatDrawer" :chatInit="chatProp" @handleClose="chatHandleClose"></Chat>
</template>

<script>
import Header from "@/components/Header";
import Footer from "@/components/Footer";
import Pic from "@/components/Pic"
import {ElNotification} from "element-plus";
import Tool from "@/components/Tool";
import Chat from "@/components/Chat";

export default {
  name: "shop",
  components: {
    Header,
    Footer,
    Pic,
    Tool,
    Chat
  },
  created() {
    this.tradeTypeList(0)
    this.schoolList(0)
    this.$store.commit('SET_INDEX', 2)
    this.getGoodList(1, 0)
  },
  data() {
    return {
      keyWord: '',
      userChatDrawer: false,
      chatProp: {
        from: 0,
        to: 0,
        tradeId: 0,
        toUser: {
        }
      },
      drawer: false,
      tradeDetail: {},
      butOrSaleList: [{
        name: "出",
        value: true
      }, {
        name: "收",
        value: false
      }],
      picDialogVisible: false,
      orderSelectValue: '',
      orderSelect: [{
        value: 0,
        label: "按时间排序"
      }, {
        value: 1,
        label: "按浏览量排序"
      },],
      tradeTypeTableData: [],
      schoolTableData: [],
      currentTradeTypeName: '',
      currentTradeType: 0,
      textInput: '',
      tradeTypeSelect: [],
      schoolSelect: '',
      tradeTypeBlockSelect: '',
      goodList: [],
      picList: [],
      currentPage: 1,
      currentTotal: 0,


      selectData: {
        uid: localStorage.getItem("userId"),
        type: null,
        school: null,
        keyWord: [],
        current: 1,
        tradeFlag: null,

      },
      postData: {
        content: '',
        tradeFlag: null,
        owner: localStorage.getItem("userId"),
        kind: '',
        university: '',
        pic: '',
        price: null
      },
      visible: false,
      userId: localStorage.getItem("userId"),
    }
  },
  methods: {
    chatHandleClose(res){
      this.userChatDrawer = false
    },
    callSaler(id, owner){
      let _this = this
      _this.chatProp.from = localStorage.getItem("userId")
      _this.chatProp.to = owner
      _this.chatProp.tradeId = true
      _this.chatProp.tradeDetail = _this.tradeDetail
      _this.$axios.post("chat/getAllContent", {current: 1,send: localStorage.getItem("userId"),recv: owner}).then(res=>{
        _this.chatProp.detail = res.data.data.records.reverse()
      })
      _this.$axios.post("chat/establishContact", {owner: localStorage.getItem("userId"),others: owner})
      _this.$axios.post("user/index", {id: owner}).then(res=>{
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
    saleOnLine(id, owner){

    },
    toMyTrade() {
      let _this = this
      this.$store.commit('SET_INDEX', 3)
      _this.$router.push({
        name: 'user',
        params: {flag: 3}
      })
    },
    toMyTradeCollect() {
      let _this = this
      this.$store.commit('SET_INDEX', 3)
      _this.$router.push({
        name: 'user',
        params: {flag: 7}
      })
    },
    collectClick(id) {
      let _this = this
      let temp = {
        uid: _this.userId,
        cid: id
      }
      _this.$axios.post("/trade/doCollect", temp).then(res => {
        if (res.data.code == 200) {
          ElNotification({
            title: 'Success',
            message: '已加入收藏',
            type: 'success',
          })
        }
      }).catch(res => {
        ElNotification({
          title: 'Error',
          message: '收藏失败，请查看是否已加入收藏',
          type: 'error',
        })
      })
    },
    getTradeDetail(id) {
      let _this = this
      _this.$axios.post("/trade/view", {cid: id}).then(res => {
        _this.tradeDetail = res.data.data
        console.log(_this.tradeDetail)
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
    currentPageChange(current) {
      let _this = this
      _this.currentPage = current
      _this.selectData.current = current
      _this.$axios.post("/trade/getCommodity", _this.selectData).then(res => {
        _this.goodList = res.data.data.records
        console.log(_this.goodList)
      })
    },
    getGoodList(current, flag) {
      let _this = this
      if (flag == 0) {
        _this.selectData.type = null
        _this.selectData.school = null
        _this.selectData.keyWord = []
        _this.keyWord = ''

      } else if(flag == 2){

          _this.selectData.keyWord = []
          _this.selectData.keyWord.push(_this.keyWord)

      }
      _this.$axios.post("/trade/getCommodity", _this.selectData).then(res => {
        console.log(res.data.data)
        _this.goodList = res.data.data.records
        _this.currentTotal = res.data.data.total
      })

    },

    doGoodPost() {
      let _this = this
      let kindselect = ''
      let picselect = ''
      let i = 0
      for (i = 0; i < _this.tradeTypeSelect.length - 1; i++) {
        kindselect += _this.tradeTypeSelect[i]
        kindselect += ","
      }
      kindselect += _this.tradeTypeSelect[i]
      for (i = 0; i < _this.picList.length - 1; i++) {
        picselect += _this.picList[i]
        picselect += ","
      }
      picselect += _this.picList[i]
      _this.postData.kind = kindselect
      _this.postData.pic = picselect
      if (_this.postData.tradeFlag == null) {
        ElNotification({
          title: 'Error',
          message: '请选择卖出还是收入',
          type: 'error',
        })
      } else if (_this.postData.content == '') {
        ElNotification({
          title: 'Error',
          message: '请输入商品描述',
          type: 'error',
        })
      } else if (_this.tradeTypeSelect.length == 0) {
        ElNotification({
          title: 'Error',
          message: '请选择类别',
          type: 'error',
        })
      } else if (_this.postData.university == '') {
        ElNotification({
          title: 'Error',
          message: '请选择所属学校',
          type: 'error',
        })
      } else if(_this.postData.price == null){
        ElNotification({
          title: 'Error',
          message: '请输入期待价格',
          type: 'error',
        })
      } else if(isNaN(_this.postData.price)){
        ElNotification({
          title: 'Error',
          message: '请输入正确的价格形式（数字）',
          type: 'error',
        })
      }else {
        console.log(_this.postData)
        _this.$axios.post("/trade/addCommodity", _this.postData).then(res => {
          _this.postData.content = ''
          _this.tradeTypeSelect = []
          _this.postData.tradeFlag = null
          _this.postData.university = ''
          _this.postData.pic = ''
          _this.picList = []
          _this.postData.price = null
          _this.getGoodList(_this.selectData.current, 0)
          ElNotification({
            title: 'Success',
            message: '发布成功',
            type: 'success',
          })
        }).catch(res=>{
          ElNotification({
            title: 'Error',
            message: '发布失败，请登录',
            type: 'error',
          })
        })
      }


    },
    tradeTypeList(currentPage) {
      let _this = this
      _this.$axios.get("tradetype/list/all/?currentPage=" + currentPage).then(res => {
        _this.tradeTypeTableData = res.data.data.records

      })
    },
    schoolList(currentPage) {
      let _this = this
      _this.$axios.get("school/list/all/?currentPage=" + currentPage).then(res => {
        _this.schoolTableData = res.data.data.records

      })
    },
    jumpTypeBlock(index, name) {
      let _this = this
      _this.currentTradeType = index
      _this.currentTradeTypeName = name
      _this.selectData.type = index
      _this.getGoodList(1, 0)

    },
    picSelect(res) {
      let _this = this
      _this.picList.push(res)
      console.log(_this.picList)
    },
    handleClose() {
      let _this = this
      _this.picDialogVisible = false
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