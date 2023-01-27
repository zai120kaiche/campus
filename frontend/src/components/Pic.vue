<template>
  <el-upload
      v-model:file-list="fileList"
      action="http://49.232.222.169:8081/api/pri/file/upload"
      list-type="picture-card"
      class="upload-demo"
      :on-preview="handlePictureCardPreview"
      :on-remove="handleRemove"
      :on-success="uploadSucc"
      :limit="3"
  >
    <el-icon><Plus /></el-icon>
  </el-upload>

  <el-dialog v-model="dialogVisible">
    <img w-full :src="dialogImageUrl" alt="Preview Image" style="width: 100%" />
  </el-dialog>
</template>

<script>
import { ref } from 'vue'
import { Plus } from '@element-plus/icons-vue'

import type ,{ UploadProps, UploadUserFile } from 'element-plus'

export default {
  name: "Pic",
  data() {
    return {
      dialogImageUrl :'',
      dialogVisible :false,
      fileList: []
    }
  },
  methods: {
    uploadSucc(res) {
      console.log(res)
      this.$emit('handleSelect', res.data.returnImgeUrl)
    },
    handlePictureCardPreview (uploadFile) {
      console.log(uploadFile)
          this.dialogImageUrl = uploadFile.response.data.returnImgeUrl
          this.dialogVisible = true
    },
    handleRemove() {

    }
  }
}
</script>

<style scoped>

</style>