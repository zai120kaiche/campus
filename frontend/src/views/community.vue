<template>

  <div class="page_back page_this"></div>

  <img src="../assets/images/wave.png" alt="" class="ourpage" style="position: fixed;background-color: transparent">
  <Header></Header>
  <el-container style="margin-bottom: 5%" class="  animate__animated animate__fadeIn ">
    <el-aside class="animate__animated animate__fadeInLeft" width="14%" style="margin-left: 7%; margin-right: 4%">
      <el-card >
        <div style="font-weight: bold">
          分类
        </div>
        <div style="margin-top: 8%">
        </div>
        <div v-for="(type, index) in typeTableData" style="margin-top: 4%;">
          <div v-if="type.id == currentType"

                  style="color: #88b0ef; border-color: transparent; width: 100%; border-radius: 5px;text-align: center"
                  v-on:click="jumpTypeBlock(type.id, type.typename)">{{ type.typename }}
          </div>
          <div v-else style="background-color: transparent; border-color: transparent"
                  v-on:click="jumpTypeBlock(type.id, type.typename)">{{ type.typename }}
          </div>
        </div>
      </el-card>
      <el-card style="margin-top: 10%">

      </el-card>
    </el-aside>
    <el-main class="animate__animated animate__fadeInDown" style="padding: 0">

      <el-card>
        <el-input
            placeholder="请输入发帖主题"
            v-model="title"
        ></el-input>
        <el-input
            v-model="textarea"
            clearable
            type="textarea"
            style="margin-top: 3%"
            :autosize="{ minRows: 3, maxRows: 7 }"
            placeholder="有什么想法要分享给大家？"
        />


        <el-row style="margin-top: 3%" :gutter="20">
          <el-col :span="5">


            <el-row style="font-weight: bold">

              <el-col>
                <el-select
                    v-model="typeSelect"
                    multiple
                    placeholder="# 类别"
                    style="width: 100%"
                    filterable
                >
                  <el-option
                      v-for="item in typeTableData"
                      :key="item.typename"
                      :label="item.typename"
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
                    v-model="schoolBlockSelect"
                    placeholder="发布至"
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
          <el-col :span="5">


            <el-row style="font-weight: bold">


              <el-col>

                <el-button style="font-weight: lighter" @click="picDialogVisible = true">
                  <el-icon style="margin-right: 10%">
                    <Picture/>
                  </el-icon>
                  图片
                </el-button>
              </el-col>


            </el-row>



          </el-col>
          <el-col :span="2">
            <el-popover  placement="bottom" :width="160" trigger="hover">
              <el-row style="font-weight: bold; margin-top: 5%">
                <el-icon style="margin-top: 2%">
                  <Avatar/>
                </el-icon>
                <div style="margin-left: 11%">
                  匿名发送
                </div>
              </el-row>
              <el-row style="font-weight: bold; margin-top: 5%">
                <el-icon style="margin-top: 2%">
                  <Clock/>
                </el-icon>
                <div style="margin-left: 11%">
                  定时发送
                </div>
              </el-row>
              <el-row style="font-weight: bold; margin-top: 5%">
                <el-icon style="margin-top: 2%">
                  <Top/>
                </el-icon>
                <div style="margin-left: 11%">
                  积分置顶
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
          <el-col :span="3" :offset="2">
            <el-button style="background-color: #A2C9FC" v-on:click="sendCommunity">发送</el-button>
          </el-col>
        </el-row>
      </el-card>
      <el-card v-if="currentTypeName == '校园吧'" style="margin-top: 1%">
        <el-row>
          <el-col :span="6">
            <el-icon style="color: #88b0ef; margin-top: 2%">
              <Promotion/>
            </el-icon>
            热门校园吧
          </el-col>
          <el-col :offset="6" :span="12">
            <el-input placeholder="请输入您想参与的校园吧名称"
                      v-model="schoolInput"
            >
              <template #prepend>
                <el-button style="background-color: transparent; border-color: transparent">
                  <el-icon>
                    <Search/>
                  </el-icon>

                </el-button>
              </template>
              <template #append>
                <el-button style="background-color: transparent; border-color: transparent">
                  <el-icon>
                    <Camera/>
                  </el-icon>

                </el-button>
              </template>
            </el-input>
          </el-col>
        </el-row>
        <el-row>
          <el-card v-for="(item, index) in schoolTableData"
                   style="margin-top: 3%; width: 30%; margin-right: 2%; margin-right: 3%">
            <el-row :gutter="20">
              <el-col :span="8">
                <el-avatar></el-avatar>
              </el-col>
              <el-col :span="16" style="color: #919191; font-size: small; font-weight: lighter">
                <el-row>{{ item.schoolname }}</el-row>
                <el-row>
                  <el-icon style="margin-top: 2%; margin-right: 5%">
                    <User/>
                  </el-icon>
                  {{ item.count }}
                </el-row>
                <el-row>
                  <el-icon style="margin-top: 2%; margin-right: 5%">
                    <ChatSquare/>
                  </el-icon>
                  {{ item.contentcount }}
                </el-row>
              </el-col>
            </el-row>
          </el-card>
        </el-row>

      </el-card>
      <el-card v-else-if="currentTypeName == '失物招领'" style="margin-top: 1%">
        <el-row>
          <el-col :span="6">
            <el-icon style="color: #88b0ef; margin-top: 2%">
              <Promotion/>
            </el-icon>
            失物查找
          </el-col>
          <el-col :span="12">
            <el-input placeholder="请输入您想寻找的物品名称"
                      v-model="keyWord"
            >
              <template #prepend>
                <el-button style="background-color: transparent; border-color: transparent">
                  <el-icon v-on:click="getList(1, 2)">
                    <Search/>
                  </el-icon>

                </el-button>
              </template>
              <template #append>
                <el-button style="background-color: transparent; border-color: transparent">
                  <el-icon>
                    <Camera/>
                  </el-icon>

                </el-button>
              </template>
            </el-input>
          </el-col>
        </el-row>
      </el-card>
      <el-card v-else style="margin-top: 1%">
        <el-row>
          <el-col :span="6">
            <el-icon style="color: #88b0ef; margin-top: 2%">
              <Promotion/>
            </el-icon>
            话题查找
          </el-col>
          <el-col :span="12">
            <el-input placeholder="请输入您想查找的话题名称"
                      v-model="keyWord"
            >
              <template #prepend>
                <el-button style="background-color: transparent; border-color: transparent">
                  <el-icon v-on:click="getList(1, 2)">
                    <Search/>
                  </el-icon>

                </el-button>
              </template>
              <template #append>
                <el-button style="background-color: transparent; border-color: transparent">
                  <el-icon>
                    <Camera/>
                  </el-icon>

                </el-button>
              </template>
            </el-input>
          </el-col>
        </el-row>
      </el-card>
      <el-card style="margin-top: 1%">
        <el-select  style="margin-left: 55%" v-model="orderSelectValue" class="m-2" placeholder="请选择排序方式">
          <el-option v-on:click="getList(1, 3)"
                     v-for="item in orderSelect"
                     :key="item.value"
                     :label="item.label"
                     :value="item.value"
          />
        </el-select>

      </el-card>
      <el-card v-for="(item, index) in contentList" style="margin-top: 1%">
        <el-container>
          <el-header>
            <el-row>
              <el-col :span="3">
                <el-avatar :src="item.avatar"></el-avatar>
              </el-col>
              <el-col :span="21">
                <el-row>
                  <el-col :span="8">{{item.authorname}}</el-col>
                  <el-col :span="8" style="color: #919191; font-weight: lighter; font-size: xx-small">类别: {{item.kindName}}</el-col>
                  <el-col :span="8" style="color: #919191; font-weight: lighter; font-size: xx-small">学校: {{item.universityName}}
                  </el-col>
                </el-row>

              </el-col>
            </el-row>

          </el-header>
          <router-link :to="{name: 'communitydetail', params: {communityId: item.id}}">
            <el-container>
              <el-aside width="40%">
                <el-image v-if="item.pic != '' && item.pic != 'undefined'" style="width: 90%; height: 80%; border-radius: 5px" :src="item.pic" :fit="'fill'"></el-image>
              </el-aside>
              <el-main style="padding: 0; margin-right: 15%">
                <div style="font-weight: bold; font-size: large">{{item.title}}</div>
                <div style="margin-top: 2%"></div>
                <div style="word-wrap:break-word; word-break:break-all;
              text-overflow:ellipsis;overflow:hidden;display:-webkit-box;
              -webkit-line-clamp:3;-webkit-box-orient:vertical;">{{ item.content }}
                </div>
              </el-main>
            </el-container>
          </router-link>

          <el-divider style="padding-bottom: 0; margin-top: 5%; margin-bottom: 0"></el-divider>
          <el-footer height="10%">
            <el-row :gutter="20" style="margin-top: 1%;">
              <el-col :span="4">
                <el-icon style="margin-top: 2%; margin-right: 15%; margin-left: 15%">
                  <View/>
                </el-icon>
                {{item.viewNum}}
              </el-col>
              <el-divider direction="vertical"/>

              <el-col v-if="item.likeFlag" :span="4" v-on:click="dislike(item.id)">
                <el-icon style="margin-top: 2%; margin-right: 15%; margin-left: 15%; color: #88b0ef">
                  <Pointer />
                </el-icon>
                {{item.likeNum}}
              </el-col>
              <el-col v-else :span="4" v-on:click="like(item.id)">
                <el-icon style="margin-top: 2%; margin-right: 15%; margin-left: 15%">
                  <Pointer/>
                </el-icon>
                {{item.likeNum}}
              </el-col>
              <el-divider direction="vertical"/>
              <el-col :span="4">
                <el-icon style="margin-top: 2%; margin-right: 15%; margin-left: 15%">
                  <ChatDotSquare/>
                </el-icon>
                {{item.commentNum}}
              </el-col>
              <el-divider direction="vertical"/>
              <el-col :span="4" v-on:click="collect(item.id)">
                <el-icon style="margin-top: 2%; margin-right: 15%; margin-left: 15%">
                  <Star/>
                </el-icon>
                {{item.collectNum}}
              </el-col>
              <el-divider direction="vertical"/>
              <el-col :span="4">
                <el-icon style="margin-top: 2%; margin-right: 15%; margin-left: 15%">
                  <Share/>
                </el-icon>
                {{item.forwardNum}}
              </el-col>
            </el-row>
          </el-footer>
        </el-container>
      </el-card>
      <el-card style="margin-top: 1%; padding: 0">
        <el-pagination
            @current-change="currentPageChange"
            :current-page="currentPage"
            layout="prev, pager, next"
            :total="currentTotal"
            :page-size="5"
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
          <el-col :span="12" v-on:click="toMyPost">
            <el-iocn>
              <EditPen style="width: 30%"/>
            </el-iocn>
            <div>
              帖子
            </div>
          </el-col>
          <el-col :span="12" >
            <el-iocn>
              <Document style="width: 30%"/>
            </el-iocn>
            <div>
              回帖
            </div>
          </el-col>
        </el-row>
        <el-row style="text-align: center; font-size: small; margin-top: 10%">
          <el-col :span="12" v-on:click="toMyPostCollet">
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
    </template>
  </el-dialog>
  <Footer></Footer>
  <Tool></Tool>

</template>

<script>
import {ref} from 'vue'
import {Plus} from '@element-plus/icons-vue'

import type, {ElNotification, UploadProps, UploadUserFile} from 'element-plus'

import Header from "@/components/Header";
import Footer from "@/components/Footer";
import Pic from "@/components/Pic"
import Tool from "@/components/Tool";

export default {
  name: "community",
  filters: {
    ellipsis(value) {
      if (!value) return "";
      if (value.length > 30) {
        return value.slice(0, 30) + "...";
      }
      return value;
    }
  },
  data() {
    return {
      visible: false,
      keyWord: '',
      userInfo: {

      },
      userId: localStorage.getItem("userId"),
      getListTable: {
        uid: localStorage.getItem("userId"),
        order: 1,
        type: null,
        school: null,
        keyWord: [],
        current: 1
      },
      orderSelectValue: '',
      orderSelect: [{
        value: 0,
        label: "按点赞量排序"
      },{
        value: 1,
        label: "按时间排序"
      },{
        value: 2,
        label: "按浏览量排序"
      },{
        value: 3,
        label: "按评论数排序"
      },{
        value: 4,
        label: "按收藏数排序"
      }],
      picDialogVisible: false,
      title: '',
      findInput: '',
      topicInput: '',
      schoolInput: '',

      typeTableData: [],
      schoolTableData: [],
      currentTypeName: '',
      currentType: 0,
      textarea: '',
      typeSelect: '',
      schoolSelect: '',
      schoolBlockSelect: null,
      contentList: [],
      picList: [],
      //  分页
      currentTotal: 6,
      currentPage: 1,
    }
  },
  components: {
    Header,
    Footer,
    Pic,
    Tool
  },
  created() {
    this.typeList(0)
    this.schoolList(0)
    this.$store.commit('SET_INDEX', 1)
    this.getList(1, 0)
  },
  methods: {
    toMyPost() {
      let _this = this
      this.$store.commit('SET_INDEX', 3)
      _this.$router.push({
        name: 'user',
        params: {flag: 2}
      })
    },
    toMyPostCollet() {

      let _this = this
      this.$store.commit('SET_INDEX', 3)
      _this.$router.push({
        name: 'user',
        params: {flag: 6}
      })
    },
    created() {
      this.typeList(0)
      this.schoolList(0)
      this.$store.commit('SET_INDEX', 1)
      this.getList(0)
    },
    getList(index, flag) {
      let _this = this
      if(flag == 0){
        _this.getListTable.keyWord = []
        _this.getListTable.type = null
        _this.getListTable.school = null
        _this.getListTable.order = 1
      }
      else if(flag == 2){
        let temp = _this.keyWord.split(' ')
        for(let i = 0; i < temp.length; i ++){
          _this.getListTable.keyWord.push(temp[i])
        }
      } else if(flag == 3){
        _this.getListTable.order = _this.orderSelectValue
      }
      _this.$axios.post('/community/getPost' ,_this.getListTable).then(res => {
        console.log(res.data.data)
        _this.contentList = res.data.data.records
        _this.currentTotal = res.data.data.total
        _this.dealPic()
      })
    },
    dealPic() {
      let _this = this
      for (let i = 0; i < _this.contentList.length; i++) {
        if(_this.contentList[i].pic != null){
          if(_this.contentList[i].pic.search(",") != -1){
            _this.contentList[i].pic = _this.contentList[i].pic.split(",")[0]
          }
        }


        let temp = {
          id: parseInt(_this.contentList[i].owner)
        }
        _this.$axios.post('/user/index', temp).then(res =>{

          _this.contentList[i].authorname = res.data.data.username
          _this.contentList[i].avatar = res.data.data.avatar
        })

      }
    },
    currentPageChange(currentPage){
      let _this = this
      _this.getListTable.current = currentPage
      _this.currentPage = currentPage
      _this.$axios.post('/community/getPost' ,_this.getListTable).then(res => {
        console.log(res.data.data)
        _this.contentList = res.data.data.records
        _this.dealPic()
        console.log(_this.contentList)
        _this.currentTotal = res.data.data.total
      })
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
        _this.getList(_this.currentPage, 0)

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
    picSelect(res) {
      let _this = this
      _this.picList.push(res)
      console.log(_this.picList)
    },
    typeList(currentPage) {
      let _this = this
      _this.$axios.get("type/list/all/?currentPage=" + currentPage).then(res => {
        _this.typeTableData = res.data.data.records

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
      _this.currentType = index
      _this.currentTypeName = name
      _this.getListTable.type = index
      _this.getList(1, 1)
    },
    sendCommunity() {
      let _this = this
      let typeselect = ''
      let picselect = ''
      let i = 0
      for (i = 0; i < _this.typeSelect.length - 1; i++) {
        typeselect += _this.typeSelect[i]
        typeselect += ","
      }
      typeselect += _this.typeSelect[i]
      for (i = 0; i < _this.picList.length - 1; i++) {
        picselect += _this.picList[i]
        picselect += ","
      }
      picselect += _this.picList[i]
      let temp = {
        owner: _this.userId,
        title: _this.title,
        content: _this.textarea,
        kind: typeselect,
        university: _this.schoolBlockSelect,
        pic: picselect
      }
      console.log(temp)
      _this.$axios.post("/community/doPost", temp).then(res => {
        if(res.data.code == 200){
          _this.created()
          _this.title = ''
          _this.textarea = ''
          _this.schoolBlockSelect = ''
          _this.typeSelect = []
          ElNotification({
            title: 'Success',
            message: '发布成功',
            type: 'success',
          })
        } else {
          ElNotification({
            title: 'Error',
            message: '发布失败',
            type: 'error',
          })
        }

      }).catch(res=>{
        ElNotification({
          title: 'Error',
          message: '发布失败，请登录',
          type: 'error',
        })
      })
    },
    handleClose(){
      let _this = this
      _this.picDialogVisible = false
    }
  },




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