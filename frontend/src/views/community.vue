<template>
  <div class="page_back page_this"></div>
  <img src="../assets/images/wave.png" alt="" class="ourpage"  style="position: fixed;background-color: transparent">
  <Header></Header>
  <el-container>
    <el-aside width="14%" style="margin-left: 7%; margin-right: 4%">
      <el-card>
        <div style="font-weight: bold">
          分类
        </div>
      </el-card>
      <el-card style="margin-top: 10%">

      </el-card>
    </el-aside>
    <el-main style="padding: 0">
      <el-card>

      </el-card>

    </el-main>
    <el-aside width="14%" style="margin-right: 7%; margin-left: 4%">
      <el-card>
        <div style="font-weight: bold">
          我的
        </div>
        <el-row  style="text-align: center; font-size: small; margin-top: 10%">
          <el-col :span="12" >
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
        <el-row  style="text-align: center; font-size: small; margin-top: 10%">
          <el-col :span="12" >
            <el-iocn>
              <FolderOpened style="width: 30%"/>
            </el-iocn>
            <div>
              收藏夹
            </div>
          </el-col>
          <el-col :span="12" >
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
        <el-divider />
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
              <el-icon style="margin-right: 0; margin-left: 0"><ArrowRightBold /></el-icon>
            </div>

          </el-col>
        </el-row>

      </el-card>
    </el-aside>
  </el-container>
</template>

<script>
import Header from "@/components/Header";
export default {
  name: "community",
  components: {
    Header
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