<template>
  <el-container class="page_back">
    <el-aside width="13%" style="background-color: #545c64">
      <el-menu
          active-text-color="#000000"
          background-color="#545c64"
          class="el-menu-vertical-demo"
          default-active="1"
          text-color="#fff"

      >
        <el-menu-item index="1" @click="select(1)">
          <el-icon>
            <icon-menu/>
          </el-icon>
          <span>社区分类</span>
        </el-menu-item>
        <el-menu-item index="2" @click="select(2)">
          <el-icon>
            <icon-menu/>
          </el-icon>
          <span>学校加盟</span>
        </el-menu-item>
        <el-menu-item index="3" @click="select(3)">
          <el-icon>
            <icon-menu/>
          </el-icon>
          <span>商品分类</span>
        </el-menu-item>
        <el-menu-item index="4" @click="select(4)">
          <el-icon>
            <icon-menu/>
          </el-icon>
          <span>发帖管理</span>
        </el-menu-item>
        <el-menu-item index="5" @click="select(5)">
          <el-icon>
            <icon-menu/>
          </el-icon>
          <span>商品管理</span>
        </el-menu-item>
        <el-menu-item index="6" @click="select(6)">
          <el-icon>
            <icon-menu/>
          </el-icon>
          <span>评论管理</span>
        </el-menu-item>
        <el-menu-item index="7" @click="select(7)">
          <el-icon>
            <icon-menu/>
          </el-icon>
          <span>活动管理</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-main>
      <div v-if="selectOption == 1">
        <div style="font-weight: bold; font-size: xx-large">社区分类</div>
        <el-input
            v-model="addType.typename"
            placeholder="请输入要添加的社区类目"
            class="input-with-select"
            style="margin-top: 3%; width: 50%"
        >

          <template #append>
            <el-button v-on:click="typeAdd">
              <el-icon>
                <Finished/>
              </el-icon>
            </el-button>
          </template>
        </el-input>
        <el-table :data="typeTableData"
                  border style="width: 100%"
                  :row-key="typeTableRow"
                  empty-text="NAN"
        >
          <el-table-column prop="id" label="编号" width="180"/>
          <el-table-column prop="typename" label="类目" width="180"/>
          <el-table-column prop="count" label="数目" width="180"/>
          <el-table-column fixed="right" label="操作" width="120">
            <template #default="scope">
              <el-popconfirm title="确定删除该社区类目吗?"
                             @confirm="typeDelete(scope.row)"
                             confirm-button-text="是的"
                             cancel-button-text="取消"
              >
                <template #reference>
                  <el-button link type="primary" size="small">删除</el-button>
                </template>
              </el-popconfirm>

              <el-button link type="primary" size="small" @click="typeEdit(scope.row)">编辑</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="example-pagination-block">
          <el-pagination
              @current-change="typeTableChange"
              :current-page="typeCurrentPage"
              layout="prev, pager, next"
              :total="typeTotal"
              :page-size="10"
          />
        </div>
      </div>
      <div v-if="selectOption == 2">
        <div style="font-weight: bold; font-size: xx-large">学校加盟</div>
        <el-input
            v-model="addSchool.schoolname"
            placeholder="请输入要加盟的学校名称"
            class="input-with-select"
            style="margin-top: 3%; width: 50%"
        >

          <template #append>
            <el-button v-on:click="schoolAdd">
              <el-icon>
                <Finished/>
              </el-icon>
            </el-button>
          </template>
        </el-input>
        <el-table :data="schoolTableData"
                  border style="width: 100%"
                  :row-key="schoolableRow"
                  empty-text="NAN"
        >
          <el-table-column prop="id" label="编号" width="180"/>
          <el-table-column prop="schoolname" label="类目" width="180"/>
          <el-table-column prop="count" label="数目" width="180"/>
          <el-table-column fixed="right" label="操作" width="120">
            <template #default="scope">
              <el-popconfirm title="确定删除该学校吗?"
                             @confirm="schoolDelete(scope.row)"
                             confirm-button-text="是的"
                             cancel-button-text="取消"
              >
                <template #reference>
                  <el-button link type="primary" size="small">删除</el-button>
                </template>
              </el-popconfirm>

              <el-button link type="primary" size="small" @click="schoolEdit(scope.row)">编辑</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="example-pagination-block">
          <el-pagination
              @current-change="schoolTableChange"
              :current-page="schoolCurrentPage"
              layout="prev, pager, next"
              :total="schoolTotal"
              :page-size="10"
          />
        </div>
      </div>
      <div v-if="selectOption == 3">
        <div style="font-weight: bold; font-size: xx-large">商品分类</div>
        <el-input
            v-model="addTradeType.tradetypename"
            placeholder="请输入要添加的商品类目"
            class="input-with-select"
            style="margin-top: 3%; width: 50%"
        >

          <template #append>
            <el-button v-on:click="tradeTypeAdd">
              <el-icon>
                <Finished/>
              </el-icon>
            </el-button>
          </template>
        </el-input>
        <el-table :data="tradeTypeTableData"
                  border style="width: 100%"
                  :row-key="tradeTypeTableRow"
                  empty-text="NAN"
        >
          <el-table-column prop="id" label="编号" width="180"/>
          <el-table-column prop="tradetypename" label="类目" width="180"/>
          <el-table-column prop="count" label="数目" width="180"/>
          <el-table-column fixed="right" label="操作" width="120">
            <template #default="scope">
              <el-popconfirm title="确定删除该商品类目吗?"
                             @confirm="tradeTypeDelete(scope.row)"
                             confirm-button-text="是的"
                             cancel-button-text="取消"
              >
                <template #reference>
                  <el-button link type="primary" size="small">删除</el-button>
                </template>
              </el-popconfirm>

              <el-button link type="primary" size="small" @click="tradeTypeEdit(scope.row)">编辑</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="example-pagination-block">
          <el-pagination
              @current-change="tradeTypeTableChange"
              :current-page="tradeTypeCurrentPage"
              layout="prev, pager, next"
              :total="tradeTypeTotal"
              :page-size="10"
          />
        </div>
      </div>
      <div v-if="selectOption == 4">
        <div style="font-weight: bold; font-size: xx-large">发帖管理</div>
        <el-input
            v-model="keyWord"
            placeholder="请输入要搜索的发帖内容"
            class="input-with-select"
            style="margin-top: 3%; width: 50%"
        >

          <template #append>
            <el-button v-on:click="postSearch">
              <el-icon>
                <Search/>
              </el-icon>
            </el-button>
          </template>
        </el-input>
        <el-table :data="postTableData"
                  border style="width: 100%"
                  :row-key="postTableRow"
                  empty-text="NAN"
        >
          <el-table-column prop="id" label="编号" width="180" show-overflow-tooltip="true"/>
          <el-table-column prop="title" label="标题" width="180" show-overflow-tooltip="true"/>
          <el-table-column prop="content" label="内容" width="180" show-overflow-tooltip="true"/>
          <el-table-column fixed="right" label="操作" width="120">
            <template #default="scope">
              <el-popconfirm title="确定删除该帖子吗?"
                             @confirm="postDelete(scope.row)"
                             confirm-button-text="是的"
                             cancel-button-text="取消"
              >
                <template #reference>
                  <el-button link type="primary" size="small">删除</el-button>
                </template>
              </el-popconfirm>

              <el-button link type="primary" size="small" @click="postDetail(scope.row)">查看详情</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="example-pagination-block">
          <el-pagination
              @current-change="postTableChange"
              :current-page="postCurrentPage"
              layout="prev, pager, next"
              :total="postTotal"
              :page-size="10"
          />
        </div>
      </div>
      <div v-if="selectOption == 5">
        <div style="font-weight: bold; font-size: xx-large">商品管理</div>
        <el-input
            v-model="goodKeyWord"
            placeholder="请输入要搜索的商品内容"
            class="input-with-select"
            style="margin-top: 3%; width: 50%"
        >

          <template #append>
            <el-button v-on:click="goodSearch">
              <el-icon>
                <Search/>
              </el-icon>
            </el-button>
          </template>
        </el-input>
        <el-table :data="goodTableData"
                  border style="width: 100%"
                  :row-key="goodTableRow"
                  empty-text="NAN"
        >
          <el-table-column prop="id" label="编号" width="180" show-overflow-tooltip="true"/>
          <el-table-column prop="content" label="内容" width="180" show-overflow-tooltip="true"/>
          <el-table-column fixed="right" label="操作" width="120">
            <template #default="scope">
              <el-popconfirm title="确定删除该商品吗?"
                             @confirm="goodDelete(scope.row)"
                             confirm-button-text="是的"
                             cancel-button-text="取消"
              >
                <template #reference>
                  <el-button link type="primary" size="small">删除</el-button>
                </template>
              </el-popconfirm>

              <el-button link type="primary" size="small" @click="goodDetail(scope.row)">查看详情</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="example-pagination-block">
          <el-pagination
              @current-change="goodTableChange"
              :current-page="goodCurrentPage"
              layout="prev, pager, next"
              :total="goodTotal"
              :page-size="9"
          />
        </div>
      </div>
      <div v-if="selectOption == 7">
        <div style="font-weight: bold; font-size: xx-large;margin-bottom: 3%">活动管理</div>
        <el-menu
            :default-active="activityActiveIndex"
            class="el-menu-demo"
            mode="horizontal"
            @select="activityHandleSelect"
        >
          <el-menu-item index="1">活动添加</el-menu-item>
          <el-menu-item index="2">活动调整</el-menu-item>

        </el-menu>
        <el-card v-if="activityActiveIndex == 1">
          <el-form :model="activityAddData" label-width="120px">
            <el-form-item label="活动名称">
              <el-input v-model="activityAddData.title"/>
            </el-form-item>
            <el-form-item label="活动类别">
              <el-select v-model="activityAddData.flag" placeholder="请选择活动所属类别">
                <el-option label="志愿服务" value="1"/>
                <el-option label="校园活动" value="2"/>
              </el-select>
            </el-form-item>
            <el-form-item label="活动时间">
              <el-col :span="11">
                <el-date-picker
                    v-model="date1"
                    type="date"
                    placeholder="请选择日期"
                    style="width: 100%"
                />
              </el-col>
              <el-col :span="2" class="text-center">
                <span class="text-gray-500">-</span>
              </el-col>
              <el-col :span="11">
                <el-time-picker
                    v-model="date2"
                    placeholder="请选择时间"
                    style="width: 100%"
                />
              </el-col>
            </el-form-item>
            <el-form-item label="活动简介">
              <el-input v-model="activityAddData.des" type="textarea"/>
            </el-form-item>
            <el-form-item label="活动内容">
              <el-input v-model="activityAddData.content" type="textarea"/>
            </el-form-item>
            <el-form-item label="活动注意事项">
              <el-input v-model="activityAddData.concern" type="textarea"/>
            </el-form-item>
            <el-form-item label="活动人数">
              <el-radio-group v-model="activityAddData.pnum">
                <el-radio label="1-10"/>
                <el-radio label="10-50"/>
                <el-radio label="50-100"/>
                <el-radio label="100-200"/>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="活动联系方式">
              <el-input v-model="activityAddData.connectWay" type="input"/>
            </el-form-item>
            <el-form-item label="活动群二维码">
              <el-upload
                  v-model:file-list="QRFileList"
                  action="http://49.232.222.169:8081/api/pri/file/upload"
                  list-type="picture-card"
                  class="upload-demo"
                  :on-preview="handlePictureCardPreview"
                  :on-remove="handleRemove"
                  :on-success="QRcodeHandleSucc"
                  :limit="1">
                <el-icon>
                  <Plus/>
                </el-icon>
              </el-upload>
            </el-form-item>
            <el-form-item label="活动展示图片">
              <el-upload
                  v-model:file-list="FileList"
                  action="http://49.232.222.169:8081/api/pri/file/upload"
                  list-type="picture-card"
                  class="upload-demo"
                  :on-preview="handlePictureCardPreview"
                  :on-remove="handleRemove"
                  :on-success="ActivityHandleSucc"
                  :limit="5">
                <el-icon>
                  <Plus/>
                </el-icon>
              </el-upload>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onSubmitActivity">发布</el-button>
              <el-button v-on:click="activityDataInit">取消</el-button>
            </el-form-item>
          </el-form>
        </el-card>
        <el-card v-if="activityActiveIndex == 2">
          <el-table :data="activityList"
                    border style="width: 100%"
                    empty-text="NAN"
          >
            <el-table-column prop="title" label="标题" width="120" show-overflow-tooltip="true"/>
            <el-table-column prop="des" label="简介" width="120" show-overflow-tooltip="true"/>
            <el-table-column prop="content" label="内容" width="120" show-overflow-tooltip="true"/>
            <el-table-column prop="pic"  width="120">
              <template #default="scope">
                <el-image :src="scope.row.grouppic" v-on:click="handlePictureCardPreview2(scope.row.grouppic)"></el-image>
              </template>
            </el-table-column>
            <el-table-column fixed="right" label="操作" width="180">
              <template #default="scope">
                <el-popconfirm title="确定删除该活动吗?"
                               @confirm="activityDelete(scope.row.id)"
                               confirm-button-text="是的"
                               cancel-button-text="取消"
                >
                  <template #reference>
                    <el-button link type="primary" size="small">删除</el-button>
                  </template>
                </el-popconfirm>

                <el-button link type="primary" size="small" @click="activityEdit(scope.row)">编辑</el-button>
                <el-button link type="primary" size="small" @click="activityDetail(scope.row)">查看详情</el-button>
              </template>
            </el-table-column>
          </el-table>
          <div class="example-pagination-block">
            <el-pagination
                @current-change="activityPageChange"
                :current-page="activityCurrentPage"
                layout="prev, pager, next"
                :total="activityPageTotal"
                :page-size="7"
            />
          </div>
        </el-card>

      </div>

    </el-main>
  </el-container>
  <el-drawer v-model="goodDrawer" :direction="'rtl'">
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
    </template>
  </el-drawer>
  <el-dialog v-model="dialogVisible">
    <img w-full :src="dialogImageUrl" alt="Preview Image" style="width: 100%"/>
  </el-dialog>
</template>

<script>
import {
  Document,
  Menu as IconMenu,
  Location,
  Setting,
} from '@element-plus/icons-vue'
import {ElMessage, ElMessageBox} from 'element-plus'
import type, {Action} from 'element-plus'
import {ElNotification} from 'element-plus'

export default {
  name: "control",
  data() {

    return {
      activityList: [],
      activityCurrentPage: 0,
      activityPageTotal: 0,
      QRFileList: [],
      FileList: [],
      ActivityFileList: [],
      date1: null,
      date2: null,
      activityActiveIndex: 1,
      activityAddData: {
        owner: localStorage.getItem("userId"),
        title: '',
        content: '',
        des: '',
        concern: '',
        pnum: 0,
        pic: [],
        flag: null,
        grouppic: '',
        connectWay: '',
        orgnisetime: ''
      },

      selectOption: 1,


      addType: {
        id: null,
        typename: '',
        count: null
      },
      typeTableData: [],
      typeCurrentPage: 0,
      typePages: 0,
      typeTotal: 0,


      addSchool: {
        id: null,
        schoolname: '',
        count: null
      },
      schoolTableData: [],
      schoolCurrentPage: 0,
      schoolPages: 0,
      schoolTotal: 0,


      addTradeType: {
        id: null,
        tradetypename: '',
        count: null
      },
      tradeTypeTableData: [],
      tradeTypeCurrentPage: 0,
      tradeTypePages: 0,
      tradeTypeTotal: 0,


      selectPost: {
        keyWord: [],
        current: 1,
        order: 1
      },
      keyWord: '',
      postTableData: [],
      postCurrentPage: 0,
      postTotal: 0,

      goodDrawer: false,
      tradeDetail: {},
      selectGood: {
        keyWord: [],
        current: 1,
        order: 1
      },
      goodKeyWord: '',
      goodTableData: [],
      goodCurrentPage: 0,
      goodTotal: 0,

      dialogImageUrl: '',
      dialogVisible: false,

    }
  },
  created() {
    this.typeList(0)
    this.schoolList(0)
    this.tradeTypeList(0)
    this.postList()
    this.goodList()
    this.getActivityList(0)
  },
  methods: {
    activityDelete(id){
      let _this = this
      _this.$axios.post("activity/delete", {uid: localStorage.getItem("userId"), aid: id}).then(res=>{
        _this.getActivityList(_this.activityCurrentPage)
      })
    },
    activityDetail(id){
      let _this = this
      console.log(id)
      _this.$router.push({
        name: 'activitydetail',
        params: {activityId: id.id}
      })
    },
    activityEdit(detail){
      console.log(detail)
      let _this = this
      _this.activityAddData = detail
      _this.activityActiveIndex = 1
    },
    getActivityList(currentPage){
      let _this = this
      _this.$axios.post("activity/search/?currentPage=" + currentPage,{order: 0}).then(res => {
        _this.activityPageTotal = res.data.data.total
        _this.activityList = res.data.data.records
      })
    },
    activityPageChange(currentPage){
      let _this = this
      _this.activityCurrentPage = currentPage
      _this.getActivityList(currentPage)
    },
    activityDataInit(){
      let _this = this
      let tem = {
        owner: localStorage.getItem("userId"),
        title: '',
        content: '',
        des: '',
        concern: '',
        pnum: 0,
        pic: [],
        flag: null,
        grouppic: '',
        connectWay: '',
        orgnisetime: ''
      }
      _this.activityAddData = tem
    },
    onSubmitActivity() {
      let _this = this
      let i = 0
      _this.activityAddData.pic = ''
      for (i = 0; i < _this.ActivityFileList.length - 1; i++) {
        _this.activityAddData.pic += _this.ActivityFileList[i]
        _this.activityAddData.pic += ','

      }
      _this.activityAddData.pic += _this.ActivityFileList[i]
      _this.activityAddData.orgnisetime = _this.date1 + _this.date2
      if (_this.activityAddData.title == '') {
        ElNotification({
          title: 'Error',
          message: '请输入活动标题',
          type: 'error',
        })
      } else if (_this.activityAddData.flag == null) {
        ElNotification({
          title: 'Error',
          message: '请选择活动类别',
          type: 'error',
        })
      } else if (_this.activityAddData.orgnisetime == '') {
        ElNotification({
          title: 'Error',
          message: '请选择活动预计时间',
          type: 'error',
        })
      } else if (_this.activityAddData.des == '') {
        ElNotification({
          title: 'Error',
          message: '请输入活动简介',
          type: 'error',
        })
      } else if (_this.activityAddData.content == '') {
        ElNotification({
          title: 'Error',
          message: '请输入活动内容',
          type: 'error',
        })
      } else if (_this.activityAddData.concern == '') {
        ElNotification({
          title: 'Error',
          message: '请输入活动注意事项',
          type: 'error',
        })
      } else if (_this.activityAddData.QRcode == '') {
        ElNotification({
          title: 'Error',
          message: '请添加活动二维码',
          type: 'error',
        })
      } else if (_this.activityAddData.pic == '') {
        ElNotification({
          title: 'Error',
          message: '请添加活动图片',
          type: 'error',
        })
      } else if (_this.activityAddData.connectWay == '') {
        ElNotification({
          title: 'Error',
          message: '请输入联系方式',
          type: 'error',
        })
      } else if (_this.activityAddData.pnum == '') {
        ElNotification({
          title: 'Error',
          message: '请选择活动预计人数',
          type: 'error',
        })
      } else {
        console.log(_this.activityAddData)
        _this.$axios.post("activity/add", _this.activityAddData).then(res => {
          let tem = {
            owner: localStorage.getItem("userId"),
            title: '',
            content: '',
            des: '',
            concern: '',
            pnum: 0,
            pic: [],
            flag: null,
            grouppic: '',
            connectWay: '',
            orgnisetime: ''
          }
          _this.date1 = ''
          _this.date2 = ''
          _this.activityAddData = tem
          _this.activityActiveIndex = 2
          ElNotification({
            title: 'Success',
            message: '发布成功',
            type: 'success',
          })
        })
      }

    },
    ActivityHandleSucc(res) {
      let _this = this
      _this.ActivityFileList.push(res.data.returnImgeUrl)
    },
    QRcodeHandleSucc(res) {
      let _this = this
      _this.activityAddData.grouppic = res.data.returnImgeUrl
    },
    handlePictureCardPreview(uploadFile) {
      this.dialogImageUrl = uploadFile.response.data.returnImgeUrl
      this.dialogVisible = true
    },
    handlePictureCardPreview2(uploadFile) {
      this.dialogImageUrl = uploadFile
      this.dialogVisible = true
    },
    handleRemove() {

    },
    activityHandleSelect(key, keyPath) {
      let _this = this
      _this.activityActiveIndex = key
    },
    select(key) {
      let _this = this
      _this.selectOption = key
    },

    typeAdd() {
      let _this = this
      _this.$axios.post("/type/add", _this.addType).then(res => {
        _this.addType.typename = ''
        _this.typeList(_this.typeCurrentPage)
        ElNotification({
          title: 'Success',
          message: '社区类目添加成功',
          type: 'success',
        })
      })
    },
    typeTableChange(currentPage) {
      let _this = this
      _this.typeCurrentPage = currentPage
      _this.typeList(currentPage)
    },
    typeList(currentPage) {
      let _this = this
      _this.$axios.get("type/list/?currentPage=" + currentPage).then(res => {
        _this.typeTableData = res.data.data.records
        _this.typePages = res.data.data.pages
        _this.typeTotal = res.data.data.total
      })
    },
    typeDelete(data) {
      let _this = this
      _this.$axios.post("/type/delete", data).then(res => {
        _this.typeList(_this.typeCurrentPage)
      })
    },
    typeEdit(type) {
      let changeType = type
      let _this = this
      ElMessageBox.prompt('请输入要修改的名称', '社区类目修改', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
      })
          .then(({value}) => {
            changeType.typename = value
            _this.$axios.post("/type/add", changeType).then(res => {
              _this.typeList(_this.typeCurrentPage)
              ElNotification({
                title: 'Success',
                message: '类目修改成功',
                type: 'success',
              })
            })
          })
          .catch(() => {
            ElMessage({
              type: 'info',
              message: '修改取消',
            })
          })
    },

    schoolAdd() {
      let _this = this
      _this.$axios.post("/school/add", _this.addSchool).then(res => {
        _this.addSchool.schoolname = ''
        _this.schoolList(_this.schoolCurrentPage)
        ElNotification({
          title: 'Success',
          message: '学校加盟成功',
          type: 'success',
        })
      })
    },
    schoolTableChange(currentPage) {
      let _this = this
      _this.schoolCurrentPage = currentPage
      _this.schoolList(currentPage)
    },
    schoolList(currentPage) {
      let _this = this
      _this.$axios.get("school/list/?currentPage=" + currentPage).then(res => {
        _this.schoolTableData = res.data.data.records
        _this.schoolPages = res.data.data.pages
        _this.schoolTotal = res.data.data.total
      })
    },
    schoolDelete(data) {
      let _this = this
      _this.$axios.post("/school/delete", data).then(res => {
        _this.schoolList(_this.schoolCurrentPage)
      })
    },
    schoolEdit(school) {
      let changeSchool = school
      let _this = this
      ElMessageBox.prompt('请输入要修改的名称', '学校名称修改', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
      })
          .then(({value}) => {
            changeSchool.schoolname = value
            _this.$axios.post("/school/add", changeSchool).then(res => {
              _this.schoolList(_this.schoolCurrentPage)
              ElNotification({
                title: 'Success',
                message: '名称修改成功',
                type: 'success',
              })
            })
          })
          .catch(() => {
            ElMessage({
              type: 'info',
              message: '修改取消',
            })
          })
    },

    tradeTypeAdd() {
      let _this = this
      _this.$axios.post("/tradetype/add", _this.addTradeType).then(res => {
        _this.addTradeType.tradetypename = ''
        _this.tradeTypeList(_this.tradeTypeCurrentPage)
        ElNotification({
          title: 'Success',
          message: '商品类目添加成功',
          type: 'success',
        })
      })
    },
    tradeTypeTableChange(currentPage) {
      let _this = this
      _this.tradeTypeCurrentPage = currentPage
      _this.tradeTypeList(currentPage)
    },
    tradeTypeList(currentPage) {
      let _this = this
      _this.$axios.get("tradetype/list/?currentPage=" + currentPage).then(res => {
        _this.tradeTypeTableData = res.data.data.records
        _this.tradeTypePages = res.data.data.pages
        _this.tradeTypeTotal = res.data.data.total
      })
    },
    tradeTypeDelete(data) {
      let _this = this
      _this.$axios.post("/tradetype/delete", data).then(res => {
        _this.tradeTypeList(_this.tradeTypeCurrentPage)
      })
    },
    tradeTypeEdit(type) {
      let changeType = type
      let _this = this
      console.log(changeType)
      ElMessageBox.prompt('请输入要修改的名称', '商品类目修改', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
      })
          .then(({value}) => {
            changeType.tradetypename = value
            _this.$axios.post("/tradetype/add", changeType).then(res => {
              _this.tradeTypeList(_this.tradeTypeCurrentPage)
              ElNotification({
                title: 'Success',
                message: '类目修改成功',
                type: 'success',
              })
            })
          })
          .catch(() => {
            ElMessage({
              type: 'info',
              message: '修改取消',
            })
          })
    },

    postList() {
      let _this = this
      _this.$axios.post("community/getPostList", _this.selectPost).then(res => {
        _this.postTableData = res.data.data.records
        _this.postTotal = res.data.data.total
      })
    },
    postTableChange(currentPage) {
      let _this = this
      console.log(currentPage)
      _this.selectPost.current = currentPage
      _this.$axios.post("community/getPostList", _this.selectPost).then(res => {
        console.log(res.data.data.records)
        _this.postTableData = res.data.data.records
        _this.postTotal = res.data.data.total
      })
    },
    postDelete(id) {

      let _this = this
      let temp = {
        uid: localStorage.getItem("userId"),
        objectIds: [id.id]
      }
      console.log(temp)
      _this.$axios.post("community/deletePostsByIds", temp).then(res => {
        ElNotification({
          title: 'Success',
          message: '删除成功',
          type: 'success',
        })
        _this.postList()
      }).catch(res => {
        ElNotification({
          title: 'Error',
          message: '没有删除权限',
          type: 'error',
        })
      })
    },
    postDetail(id) {
      let _this = this
      _this.$router.push({
        name: 'communitydetail',
        params: {communityId: id.id}
      })
    },
    postSearch() {
      let _this = this
      _this.selectPost.keyWord = []
      _this.selectPost.keyWord.push(_this.keyWord)
      _this.postList()
    },

    goodList() {
      let _this = this
      _this.$axios.post("trade/getCommodity", _this.selectGood).then(res => {
        _this.goodTableData = res.data.data.records
        _this.goodTotal = res.data.data.total
      })
    },
    goodTableChange(currentPage) {
      let _this = this
      _this.selectGood.current = currentPage
      _this.$axios.post("trade/getCommodity", _this.selectGood).then(res => {
        _this.goodTableData = res.data.data.records
        _this.goodTotal = res.data.data.total
      })
    },
    goodDelete(id) {

      let _this = this
      let temp = {
        uid: localStorage.getItem("userId"),
        objectIds: [id.id]
      }
      console.log(temp)
      _this.$axios.post("trade/deleteCommodity", temp).then(res => {
        ElNotification({
          title: 'Success',
          message: '删除成功',
          type: 'success',
        })
        _this.goodList()
      }).catch(res => {
        ElNotification({
          title: 'Error',
          message: '没有删除权限',
          type: 'error',
        })
      })
    },
    goodDetail(id) {
      let _this = this
      _this.$axios.post("/trade/view", {cid: id.id}).then(res => {
        _this.tradeDetail = res.data.data
        if (_this.tradeDetail.pic.search(",") != -1 && _this.tradeDetail.pic != null) {
          _this.tradeDetail.pic = _this.tradeDetail.pic.split(",")
        } else {
          let temp = []
          temp.push(_this.tradeDetail.pic)
          _this.tradeDetail.pic = temp
        }
        _this.goodDrawer = true
      })
    },
    goodSearch() {
      let _this = this
      _this.selectGood.keyWord = []
      _this.selectGood.keyWord.push(_this.goodKeyWord)
      _this.goodList()
    }
  }
}
</script>

<style scoped>

</style>