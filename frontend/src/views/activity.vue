<template>
  <div class="page_back page_this"></div>

  <img src="../assets/images/wave.png" alt="" class="ourpage" style="position: fixed;background-color: transparent">
  <Header></Header>


  <el-container v-if="!isPhone" style="margin-bottom: 5%" class="  animate__animated animate__fadeIn ">
    <el-aside class="animate__animated animate__fadeInLeft" width="14%" style="margin-left: 14%; margin-right: 4%">
      <el-card>
        <div style="font-weight: bold">
          分类
        </div>
        <div style="margin-top: 8%">
        </div>
        <div>
          <el-button v-if="typeSelect == 1" style="border-color: transparent;color: #88b0ef">校园活动</el-button>
          <el-button v-else style="border-color: transparent" v-on:click="changeTypeSelect(1)">校园活动</el-button>

        </div>
        <div style="margin-top: 3%">
          <el-button v-if="typeSelect == 2" style="border-color: transparent;color: #88b0ef">志愿活动</el-button>
          <el-button v-else style="border-color: transparent" v-on:click="changeTypeSelect(2)">志愿活动</el-button>

        </div>

      </el-card>

    </el-aside>
    <el-main class="animate__animated animate__fadeInDown" style="padding: 0;margin-right: 14%">

      <el-card style="margin-bottom: 3%">
        <el-input v-model="activitySelectData.keyWord" placeholder="请输入您要查找的内容">
          <template #append>
            <el-button style="background-color: transparent; border-color: transparent" v-on:click="searchActivity">
              <el-icon >
                <Search/>
              </el-icon>

            </el-button>
          </template>
        </el-input>
      </el-card>
      <el-card v-for="(item, index) in activityList" style="margin-bottom: 2%;">
        <el-container>
          <el-aside width="40%">


            <el-image v-if="item.pic.search(',')!=-1" :fit="'scale-down'" :src="item.pic.split(',')[0]"
                      v-on:click="handlePictureCardPreview(item.pic.split(',')[0])"></el-image>

            <el-image :fit="'scale-down'" v-else :src="item.pic"
                      v-on:click="handlePictureCardPreview(item.pic)"></el-image>

          </el-aside>
          <el-main v-on:click="toDetail(item.id)">
            <router-link :to="{name: 'activitydetail', params: {activityId: item.id}}">
              <h4 style="font-weight: bold; font-size: large;word-wrap:break-word; word-break:break-all">{{ item.title }}</h4>
              <div style="margin-top: 2%"></div>
              <div style="word-wrap:break-word; word-break:break-all;
              text-overflow:ellipsis;overflow:hidden;display:-webkit-box;
              -webkit-line-clamp:3;-webkit-box-orient:vertical;">{{ item.des }}
              </div>
            </router-link>


          </el-main>
        </el-container>
      </el-card>
      <div class="example-pagination-block">
        <el-pagination
            @current-change="activityPageChange"
            :current-page="activityCurrentPage"
            layout="prev, pager, next"
            :total="activityPageTotal"
            :page-size="7"
        />
      </div>

    </el-main>

  </el-container>
  <el-container v-else style="margin-left: 2%;margin-right: 2%;margin-bottom: 5%;" class="  animate__animated animate__fadeIn " >

    <el-main class="animate__animated animate__fadeInDown" style="">
      <el-card style="padding: 0;margin-top: 2%;margin-bottom: 2%">
        <el-dropdown>
          <el-button type="primary" style="background-color: white;color: #333333;border-color: transparent">
            分类<el-icon class="el-icon--right"><arrow-down /></el-icon>
          </el-button>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item  v-on:click="changeTypeSelect(1)">校园活动</el-dropdown-item>
              <el-dropdown-item  v-on:click="changeTypeSelect(2)">志愿活动</el-dropdown-item>

            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-card>
      <el-card style="margin-bottom: 3%">
        <el-input v-model="activitySelectData.keyWord" placeholder="请输入您要查找的内容">
          <template #append>
            <el-button style="background-color: transparent; border-color: transparent" v-on:click="searchActivity">
              <el-icon >
                <Search/>
              </el-icon>

            </el-button>
          </template>
        </el-input>
      </el-card>
      <el-card v-for="(item, index) in activityList" style="margin-bottom: 2%;">
        <el-container>
          <el-aside width="40%">


            <el-image v-if="item.pic.search(',')!=-1" :fit="'scale-down'" :src="item.pic.split(',')[0]"
                      v-on:click="handlePictureCardPreview(item.pic.split(',')[0])"></el-image>

            <el-image :fit="'scale-down'" v-else :src="item.pic"
                      v-on:click="handlePictureCardPreview(item.pic)"></el-image>

          </el-aside>
          <el-main v-on:click="toDetail(item.id)">
            <router-link :to="{name: 'activitydetail', params: {activityId: item.id}}">
              <h4 style="font-weight: bold; font-size: large;word-wrap:break-word; word-break:break-all">{{ item.title }}</h4>
              <div style="margin-top: 2%"></div>
              <div style="word-wrap:break-word; word-break:break-all;
              text-overflow:ellipsis;overflow:hidden;display:-webkit-box;
              -webkit-line-clamp:3;-webkit-box-orient:vertical;">{{ item.des }}
              </div>
            </router-link>


          </el-main>
        </el-container>
      </el-card>
      <div class="example-pagination-block">
        <el-pagination
            @current-change="activityPageChange"
            :current-page="activityCurrentPage"
            layout="prev, pager, next"
            :total="activityPageTotal"
            :page-size="7"
        />
      </div>

    </el-main>

  </el-container>
  <el-dialog v-model="dialogVisible">
    <img w-full :src="dialogImageUrl" alt="Preview Image" style="width: 100%"/>
  </el-dialog>
  <Footer></Footer>
</template>

<script>
import Header from "@/components/Header";
import Footer from "@/components/Footer";
import {inject} from "vue";

export default {
  name: "activity",
  components: {
    Header,
    Footer
  },
  data() {
    return {
      isPhone: inject('isPhone'),
      dialogImageUrl: '',
      dialogVisible: false,

      activityPageTotal: 0,
      activityList: [],
      activityCurrentPage: 0,

      postShow: [],
      flag: false,
      typeSelect: -1,

      activitySelectData: {
        order: 0,
        type: 0,
        keyWord: ''
      }
    }
  },
  methods: {
    toDetail(id){
      this.$router.push({
        name: 'activitydetail',
        params: {activityId: id}
      })
    },
    searchActivity(){
      let _this = this
      _this.activitySelectData.order = 2
      _this.getActivityList(1)
    },
    handlePictureCardPreview(uploadFile) {
      this.dialogImageUrl = uploadFile
      this.dialogVisible = true
    },
    changeTypeSelect(index) {
      let _this = this
      _this.typeSelect = index
      _this.activitySelectData.order = 1
      _this.activitySelectData.type = index
      _this.activitySelectData.keyWord = ''
      _this.getActivityList(_this.activityCurrentPage)

    },
    getActivityList(currentPage) {
      let _this = this

      _this.$axios.post("activity/search/?currentPage=" + currentPage, _this.activitySelectData).then(res => {
        _this.activityPageTotal = res.data.data.total
        _this.activityList = res.data.data.records
        if (!_this.flag) {
          _this.postShow = res.data.data.records
          _this.flag = true
        }

      })
    },
    activityPageChange(currentPage) {
      let _this = this
      _this.activityCurrentPage = currentPage
      _this.getActivityList(currentPage)
    },
  },
  created() {
    this.getActivityList(1)
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