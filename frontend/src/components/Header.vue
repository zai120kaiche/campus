<template>
  <el-row style="background-color: transparent; font-weight: bold; font-size: x-large">
    <el-col :offset="2" :span="4">
      <el-image style="width: 80%; height: 80%; z-index: 1; background-color: transparent" :src="require('../assets/images/login_back.png')"
                :fit="'fill'"></el-image>
    </el-col>
    <el-col :span="6" style="margin-top: 3%">
      <el-button v-if="index == 1" style="background-color: transparent; border-color: transparent; font-size: large; color: #333333">
        <el-icon style="margin-right: 1%">
          <House />
        </el-icon>
        <div>
          学生社区
        </div>
      </el-button>
      <el-button v-if="index == 2" style="background-color: transparent; border-color: transparent; font-size: large; color: #919191" v-on:click="changeCommunity">
        <el-icon style="margin-right: 1%">
          <House />
        </el-icon>
        <div>
          学生社区
        </div>
      </el-button>
      <el-button v-if="index == 2" style="background-color: transparent; border-color: transparent; font-size: large; color: #333333">
        <el-icon style="margin-right: 1%">
          <Money />
        </el-icon>
        <div>
          二手易物
        </div>
      </el-button>
      <el-button v-if="index == 1" style="background-color: transparent; border-color: transparent; font-size: large; color: #919191" v-on:click="changeGood">
        <el-icon style="margin-right: 1%">
          <Money />
        </el-icon>
        <div>
          二手易物
        </div>
      </el-button>
    </el-col>
    <el-col :span="6" style="margin-top: 3%">
      <el-input
          v-model="searchInput"
          placeholder="搜索"
          class="input-with-select"

      >
        <template #prepend>
          <el-button style="background-color: transparent; border-color: transparent">
            <el-icon>
              <Search />
            </el-icon>

          </el-button>
        </template>
        <template #append>
          <el-button style="background-color: transparent; border-color: transparent">
            <el-icon>
              <Camera />
            </el-icon>

          </el-button>
        </template>

      </el-input>
    </el-col>
    <el-col :span="5" :offset="1" style="margin-top: 2%">
      <el-button style="background-color: transparent; border-color: transparent; font-size: large; color: #333333; padding: 0">
        <el-icon >
          <Service />
        </el-icon>
      </el-button>
      <el-popover :visible="visible" placement="top" :width="160">
        <el-row style="font-weight: bold">
          <el-icon style="margin-top: 2%">
            <Grid />
          </el-icon>
          <div style="margin-left: 11%">
            签到
          </div>
        </el-row>
        <template #reference>
          <el-button style="background-color: transparent; border-color: transparent; font-size: large; color: #333333; padding: 0">
            <el-icon >
              <Ticket />
            </el-icon>
          </el-button>
        </template>
      </el-popover>

      <el-popover :visible="visible" placement="top" :width="160">
        <el-row style="font-weight: bold">
          <el-icon style="margin-top: 2%">
            <Message />
          </el-icon>
          <div style="margin-left: 11%">
            通知中心
          </div>
        </el-row>
        <el-row style="font-weight: bold; margin-top: 5%">
          <el-icon style="margin-top: 2%">
            <ChatRound />
          </el-icon>
          <div style="margin-left: 11%">
            私信
          </div>
        </el-row>
        <el-divider style="padding-bottom: 0; margin-top: 10px; margin-bottom: 0"></el-divider>
        <el-row>
          <el-col :span="24">
            <el-button style="background-color: transparent; border-color: transparent">
              全部已读
            </el-button>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-button style="background-color: transparent; border-color: transparent">
              查看所有私信
            </el-button>
          </el-col>
        </el-row>
        <template #reference>
          <el-button style="background-color: transparent; border-color: transparent; font-size: large; color: #333333; padding: 0">
            <el-icon >
              <Bell />
            </el-icon>
          </el-button>
        </template>
      </el-popover>


      <el-avatar :size="40" :src="circleUrl" style="margin-left: 12%"/>

    </el-col>
  </el-row>

</template>

<script>
import { Search } from '@element-plus/icons-vue'
export default {
  name: "Header",
  data() {
    let index = this.getIndexed()
    return {
      index: index,
      searchInput: ''
    }
  },
  methods: {
    getIndexed() {
      return this.$store.getters.getIndex
    },
    changeCommunity() {
      this.$store.commit('SET_INDEX', 1)

      this.$router.push("/index/community")
    },
    changeGood() {
      this.$store.commit('SET_INDEX', 2)

      this.$router.push("/index/shop")
    }
  }
}
</script>

<style scoped>

</style>