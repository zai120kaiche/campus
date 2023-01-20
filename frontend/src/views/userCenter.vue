+<template>
  <div class="page_back page_this"></div>
  <img src="../assets/images/wave.png" alt="" class="ourpage" style="position: fixed;background-color: transparent">

  <el-container style="margin-bottom: 5%">
    <el-header></el-header>
    <el-main>
      <el-row>
        <el-col :span="18" :offset="3">
          <el-card>
            <el-row>
              <el-col :span="2">
                <el-avatar :size="80" :src="circleUrl" style="margin-left: 10%"/>
              </el-col>
              <el-col :span="4">
                <div style="font-weight:400; font-size: xx-large; margin-top: 14%;">用户名</div>
                <div style="font-size: small; margin-top: 5%;">
                  <SuccessFilled style="width: 1em; height: 1em; margin-right: 2%; margin-top: 5%; color: green;"/>
                  学生已认证
                </div>
              </el-col>
              <el-col :span="2" :offset="7">
                <div style="font-size: large; margin-top: 30%; text-align:center">收藏</div>
                <div style="text-align:center; margin-top: 5%;">0</div>
              </el-col>
              <el-col :span="2">
                <div style="font-size: large; margin-top: 30%; text-align:center">历史浏览</div>
                <div style="text-align:center; margin-top: 5%;">0</div>
              </el-col>
              <el-col :span="2">
                <div style="font-size: large; margin-top: 30%; text-align:center">关注</div>
                <div style="text-align:center; margin-top: 5%;">0</div>
              </el-col>
              <el-col :span="2">
                <div style="font-size: large; margin-top: 30%; text-align:center">粉丝</div>
                <div style="text-align:center; margin-top: 5%;">0</div>
              </el-col>
              <el-col :span="2">
                <div style="font-size: large; margin-top: 30%; text-align:center">帖子</div>
                <div style="text-align:center; margin-top: 5%;">0</div>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="8">
                <el-link style="margin-top: 1%; margin-left: 2%;">
                  点击添加个人简介
                  <el-icon><Edit/></el-icon>
                </el-link>
              </el-col>
              <el-col :span="6" :offset="10">
                <div style="display: inline" >个人资料待完善&emsp;</div>
                <el-link type="primary">去编辑&nbsp;></el-link>
              </el-col>
            </el-row>
          </el-card>

        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="14" :offset="3">
          <el-card style="margin-top:20px">
            <!-- 标签页导航卡片 -->
            <div style="display: inline; font-size: large; font-weight: 600;">我的交易</div>
            <div style="display: inline; font-size: large; font-weight: 300; margin-left: 5%;">收藏</div>
            <div style="display: inline; font-size: large; font-weight: 300; margin-left: 5%;">草稿箱</div>
            <div style="display: inline; font-size: large; font-weight: 300; margin-left: 5%;">浏览记录</div>
            <div style="display: inline; font-size: large; font-weight: 300; margin-left: 5%;">AI绘画</div>
          </el-card>
          <el-card style="margin-top:20px">
            <el-tabs v-model="activeName" @tab-click="handleClick">
              <el-tab-pane label="我发布的 (0)" name="first">我发布的</el-tab-pane>
              <el-tab-pane label="我卖出的 (0)" name="second">我卖出的</el-tab-pane>
              <el-tab-pane label="我买到的 (0)" name="third">我买到的</el-tab-pane>
            </el-tabs>
          </el-card>
        </el-col>
        <el-col :span="4">
          <el-card class="box-card" style="margin-top:20px">
            <div slot="header" class="clearfix">
              <span>学生社区</span>
              <el-button style="float: right; padding: 3px 0" type="text">进入</el-button>
            </div>
          </el-card>
          <el-card class="box-card" style="margin-top:20px">
            <div slot="header" class="clearfix">
              <span>消息中心</span>
              <el-button style="float: right; padding: 3px 0" type="text">进入</el-button>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </el-main>
  </el-container>
</template>

<script>
import { Edit } from '@element-plus/icons-vue';

export default {
  components: { Edit },
  data() {
    return {
      activeName: 'second'
    };
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    }
  }
}


import { ref } from 'vue';

const activeName = ref('first')

// const handleClick = (tab: TabsPaneContext, event: Event) => {
//   console.log(tab, event)
// }
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
.demo-tabs > .el-tabs__content {
  padding: 32px;
  color: #6b778c;
  font-size: 32px;
  font-weight: 600;
}
</style>