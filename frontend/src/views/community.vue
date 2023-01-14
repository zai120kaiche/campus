<template>
  <div class="page_back page_this"></div>
  <img src="../assets/images/wave.png" alt="" class="ourpage" style="position: fixed;background-color: transparent">
  <Header></Header>
  <el-container style="margin-bottom: 5%">
    <el-aside width="14%" style="margin-left: 7%; margin-right: 4%">
      <el-card>
        <div style="font-weight: bold">
          分类
        </div>
        <div style="margin-top: 8%">
        </div>
        <div v-for="(type, index) in typeTableData" style="margin-top: 4%">
          <button v-if="type.id == currentType"
                  style="background-color: #88b0ef; border-color: transparent; width: 100%; border-radius: 5px"
                  v-on:click="jumpTypeBlock(type.id)">{{ type.typename }}
          </button>
          <button v-else style="background-color: transparent; border-color: transparent"
                  v-on:click="jumpTypeBlock(type.id)">{{ type.typename }}
          </button>
        </div>
      </el-card>
      <el-card style="margin-top: 10%">

      </el-card>
    </el-aside>
    <el-main style="padding: 0">
      <el-card>
        <el-input
            v-model="textarea"
            clearable
            type="textarea"
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
                      :value="item.typename"
                  />
                </el-select>
              </el-col>

            </el-row>



          </el-col>
          <el-col :span="5">


            <el-row style="font-weight: bold">

              <el-col>
                <el-select
                    v-model="schoolSelect"
                    placeholder="所属学校"
                    style="width: 100%"
                    filterable
                >
                  <el-option
                      v-for="item in schoolTableData"
                      :key="item.schoolname"
                      :label="item.schoolname"
                      :value="item.schoolname"
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
                    multiple
                >
                  <el-option
                      v-for="item in schoolTableData"
                      :key="item.schoolname"
                      :label="item.schoolname"
                      :value="item.schoolname"
                  />
                </el-select>
              </el-col>

            </el-row>



          </el-col>
          <el-col :span="2">
            <el-popover :visible="visible" placement="bottom" :width="160">
              <el-row style="font-weight: bold; margin-top: 5%">
                <el-icon style="margin-top: 2%">
                  <Avatar />
                </el-icon>
                <div style="margin-left: 11%">
                  匿名发送
                </div>
              </el-row>
              <el-row style="font-weight: bold; margin-top: 5%">
                <el-icon style="margin-top: 2%">
                  <Clock />
                </el-icon>
                <div style="margin-left: 11%">
                  定时发送
                </div>
              </el-row>
              <el-row style="font-weight: bold; margin-top: 5%">
                <el-icon style="margin-top: 2%">
                  <Top />
                </el-icon>
                <div style="margin-left: 11%">
                  积分置顶
                </div>
              </el-row>
              <template #reference>
                <el-button style="background-color: transparent; border-color: transparent; font-size: large; color: #333333; padding: 0">
                  <el-icon >
                    <MoreFilled />
                  </el-icon>
                </el-button>
              </template>
            </el-popover>
          </el-col>
          <el-col :span="3" :offset="4">
            <el-button style="background-color: #A2C9FC">发送</el-button>
          </el-col>
        </el-row>
      </el-card>
      <el-card v-for="(item, index) in contentList" style="margin-top: 1%">
        <el-container>
          <el-header>
            <el-row>
              <el-col :span="3">
                <el-avatar></el-avatar>
              </el-col>
              <el-col :span="21">
                <el-row>
                  <el-col :span="4">用户名</el-col>
                  <el-col :span="14" style="color: #919191; font-weight: lighter; font-size: xx-small">类别</el-col>
                  <el-col :span="6" style="color: #919191; font-weight: lighter; font-size: xx-small">学校: 北京邮电大学</el-col>
                </el-row>
                <el-row>2</el-row>
              </el-col>
            </el-row>

          </el-header>
          <el-container>
            <el-aside width="40%">
              <el-image style="width: 90%; height: 80%; border-radius: 5px" :src="url" :fit="fit" ></el-image>
            </el-aside>
            <el-main style="padding: 0; margin-right: 15%">
              <div style="font-weight: bold; font-size: large">正文标题</div>
              <div style="margin-top: 2%"></div>
              <div style="word-wrap:break-word; word-break:break-all;
              text-overflow:ellipsis;overflow:hidden;display:-webkit-box;
              -webkit-line-clamp:3;-webkit-box-orient:vertical;">{{context}}</div>
            </el-main>
          </el-container>
          <el-divider style="padding-bottom: 0; margin-top: 10px; margin-bottom: 0"></el-divider>
          <el-footer height="10%">
            <el-row :gutter="20" style="margin-top: 1%;">
              <el-col :span="4">
                <el-icon style="margin-top: 2%; margin-right: 15%; margin-left: 15%">
                  <View />
                </el-icon>
                0
              </el-col><el-divider direction="vertical" />

              <el-col :span="4">
                <el-icon style="margin-top: 2%; margin-right: 15%; margin-left: 15%">
                  <Pointer />
                </el-icon>
                0
              </el-col><el-divider direction="vertical" />
              <el-col :span="4">
                <el-icon style="margin-top: 2%; margin-right: 15%; margin-left: 15%">
                  <ChatDotSquare />
                </el-icon>
                0
              </el-col><el-divider direction="vertical" />
              <el-col :span="4">
                <el-icon style="margin-top: 2%; margin-right: 15%; margin-left: 15%">
                  <Star />
                </el-icon>
                0
              </el-col><el-divider direction="vertical" />
              <el-col :span="4">
                <el-icon style="margin-top: 2%; margin-right: 15%; margin-left: 15%">
                  <Share />
                </el-icon>
                0
              </el-col>
            </el-row>
          </el-footer>
        </el-container>
      </el-card>

    </el-main>
    <el-aside width="14%" style="margin-right: 7%; margin-left: 4%">
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
          <el-col :span="12">
            <el-iocn>
              <FolderDelete style="width: 30%"/>
            </el-iocn>
            <div>
              草稿箱
            </div>
          </el-col>
        </el-row>
      </el-card>
      <el-card style="margin-top: 10%">
        <div style="font-weight: bold; font-size: small">
          最多人浏览
        </div>
        <el-divider/>
        <div style="font-weight: bold; font-size: small">
          最新发布
        </div>
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
</template>

<script>
import {ref} from 'vue'
import {Plus} from '@element-plus/icons-vue'

import type, {UploadProps, UploadUserFile} from 'element-plus'

import Header from "@/components/Header";
import Footer from "@/components/Footer";

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
      context: '1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111',
      typeTableData: [],
      schoolTableData: [],
      currentType: 0,
      textarea: '',
      typeSelect: '',
      schoolSelect: '',
      schoolBlockSelect: '',
      contentList: [
        {
          name: 1
        },
        {
          name: 2
        }
      ]
    }
  },
  components: {
    Header,
    Footer
  },
  created() {
    this.typeList(0)
    this.schoolList(0)
  },
  methods: {
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
    jumpTypeBlock(index) {
      this.currentType = index
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