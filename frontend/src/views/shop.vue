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
                  style="background-color: #88b0ef; border-color: transparent; width: 100%; border-radius: 5px"
                  v-on:click="jumpTypeBlock(type.id, type.tradetypename)">{{ type.tradetypename }}
          </button>
          <button v-else style="background-color: transparent; border-color: transparent"
                  v-on:click="jumpTypeBlock(type.id, type.tradetypename)">{{ type.tradetypename }}
          </button>
        </div>
        <el-divider style="padding-bottom: 0; margin-top: 10px; margin-bottom: 0"></el-divider>
        <div style="font-weight: bold; margin-top: 5%">
          收闲置
        </div>
      </el-card>
      <el-card style="margin-top: 10%">

      </el-card>
    </el-aside>
    <el-main class="animate__animated animate__fadeInDown" style="padding: 0">
      <el-card>
        <el-input
            v-model="textInput"
            clearable
            placeholder="请输入你需要的商品"
        />


        <el-row style="margin-top: 3%" :gutter="20">
          <el-col :span="5">


            <el-row style="font-weight: bold">

              <el-col>
                <el-select
                    v-model="tradeTypeSelect"
                    placeholder="# 类别"
                    style="width: 100%"
                    filterable
                >
                  <el-option
                      v-for="item in tradeTypeTableData"
                      :key="item.tradetypename"
                      :label="item.tradetypename"
                      :value="item.tradetypename"
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
                    v-model="tradeTypeBlockSelect"
                    placeholder="发布至"
                    style="width: 100%"
                    filterable
                    multiple
                >
                  <el-option
                      v-for="item in tradeTypeTableData"
                      :key="item.tradetypename"
                      :label="item.tradetypename"
                      :value="item.tradetypename"
                  />
                </el-select>
              </el-col>

            </el-row>


          </el-col>
          <el-col :span="2">
            <el-popover :visible="visible" placement="bottom" :width="160">

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
          <el-col :span="3" :offset="4">
            <el-button style="background-color: #A2C9FC">发送</el-button>
          </el-col>
        </el-row>
      </el-card>
      <el-row>
        <el-card v-for="(item, index) in goodList"
                 style="margin-top: 1%; width: 28%; margin-right: 2%; margin-left: 3%">

          <el-image
              src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png"
              class="image"
              style="border-radius: 5px"
          />
          <div style="text-align: center">标题</div>
          <el-row>
            <el-col :span="12">￥ 1</el-col>
            <el-col :span="3" :offset="9">收</el-col>
          </el-row>
          <el-row>
            <el-col :span="12">0人想要</el-col>
            <el-col :span="12" style="margin-top: 2%">
              <el-icon style="margin-top: 2%; margin-right: 15%; margin-left: 15%">
                <View/>
              </el-icon>
              0
            </el-col>
          </el-row>

          <div :span="6" style="color: #919191; font-weight: lighter; font-size: xx-small">学校: 北京邮电大学</div>
          <div :span="14" style="color: #919191; font-weight: lighter; font-size: xx-small">时间</div>
        </el-card>
      </el-row>


    </el-main>
    <el-aside class="animate__animated animate__fadeInRight" width="14%" style="margin-right: 7%; margin-left: 4%">
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
              发闲置
            </div>
          </el-col>
          <el-col :span="12">
            <el-iocn>
              <Bicycle style="width: 30%"/>
            </el-iocn>
            <div>
              收闲置
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
        <el-select v-model="orderSelectValue" class="m-2" placeholder="请选择排序方式">
          <el-option
              v-for="item in orderSelect"
              :key="item.value"
              :label="item.label"
              :value="item.value"
          />
        </el-select>
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
  <Footer></Footer>
</template>

<script>
import Header from "@/components/Header";
import Footer from "@/components/Footer";

export default {
  name: "shop",
  components: {
    Header,
    Footer
  },
  created() {
    this.tradeTypeList(0)
    this.schoolList(0)
    this.$store.commit('SET_INDEX', 2)
  },
  data() {
    return {
      orderSelectValue: '',
      orderSelect: [{
        value: 0,
        label: "按时间排序"
      },{
        value: 1,
        label: "按浏览量排序"
      },],
      tradeTypeTableData: [],
      schoolTableData: [],
      currentTradeTypeName: '',
      currentTradeType: 0,
      textInput: '',
      tradeTypeSelect: '',
      schoolSelect: '',
      tradeTypeBlockSelect: '',
      goodList: [
        {
          name: 1
        },
        {
          name: 2
        },
        {
          name: 2
        },
        {
          name: 2
        },
        {
          name: 2
        }
      ]
    }
  },
  methods: {
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
      this.currentTradeType = index
      this.currentTradeTypeName = name
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