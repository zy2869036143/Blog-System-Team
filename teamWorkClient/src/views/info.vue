<template>
  <div class="add_container">
    <el-upload
      ref="upload"
      class="avatar-uploader"
      action=""
      :auto-upload="false"
      :show-file-list="false"
      :on-change="changePictureUpload"
    >
      <img v-if="tempPic" :src="tempPic" class="avatar">
      <i v-else class="el-icon-plus avatar-uploader-icon"></i>
    </el-upload>
    <el-col :span="24">
      <span class="text_tip">允许上传图片格式：png/jpg，且图片大小不能超过2MB</span>
    </el-col>

    <el-form ref="form" :model="form"  label-width="55px">
      <el-form-item label="昵称">
        <el-input v-model="form.name" placeholder=""></el-input>
      </el-form-item>
      <el-form-item label="邮箱" >
        <el-input v-model="form.email" placeholder=""></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="dialogVisible = true">修改</el-button>
      </el-form-item>
    </el-form>

    <el-dialog
      title="修改"
      :visible.sync="dialogVisible"
      width="30%"
      :append-to-body="true"
      center>
      <span>确认修改？</span>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click.prevent="onSubmit">确 定</el-button>
  </span>
    </el-dialog>
  </div>

</template>
<style scoped>
.center /deep/.el-dialog__body{
  display: inline-block;
}
.add_container {
  width: 70%;
  height: auto;
  border-radius: 3px;
  position: relative;
  text-align: center;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
<script>

export default {
  data(){
    return{
      picInfo:{
        name:'',
        type:'',
      },
      tempPic:'http://localhost:8080/95f31340-baf4-409a-96ea-e87d7276c',
      imageUrl: '',
      dialogVisible:false,
      data:{},
      form:{
        name: '',
        classNum: '',
        major: '',
        number: '',
        email:'',
        phone:'',
        address: '',
      }
    }

  },
  created() {
  },
  methods: {
    onSubmit() {
      let flag = true
      if(this.form.phone === ''){
        this.$message({
          type: 'error',
          message:'手机号不能为空'
        })
        flag = false;
        this.dialogVisible = false;
      }
      if(this.form.email === ''){
        this.$message({
          type: 'error',
          message:'邮箱不能为空'
        })
        flag = false;
        this.dialogVisible = false;
      }
      if(!(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(this.form.email)) && this.form.email !== ''){
        this.$message({
          type: 'error',
          message:'邮箱格式错误'
        })
        flag = false;
        this.dialogVisible = false;
      }
      if((!/^1\d{10}$/.test(this.form.phone)) && this.form.phone !== ''){
        this.$message({
          type: 'error',
          message:'手机号格式错误'
        })
        flag = false;
        this.dialogVisible = false;
      }
    },
    changePictureUpload(file,fileList) {
      if (fileList.length > 1) {
        fileList.splice(0,1)
      }
      // this.$refs.upload.clearFiles();
      this.picInfo.name = file.raw.name.split(".")[0];
      this.picInfo.type = file.raw.name.split(".")[1];
      const isJPG = file.raw.type === 'image/jpeg';
      const isPNG = file.raw.type === 'image/png';
      const isLt2M = file.raw.size / 1024 / 1024 < 2;
      this.tempPic = URL.createObjectURL(file.raw);
      console.log(file)
      if (!isJPG && !isPNG) {
        this.$message.error('上传图片格式不符合条件!');
        this.tempPic = ''
        this.picInfo.name = ''
        this.picInfo.type = ''
      }
      if (!isLt2M) {
        this.$message.error('上传图片大小不能超过 2MB!');
        this.tempPic = ''
        this.picInfo.name = ''
        this.picInfo.type = ''
      }
      // 返回图是jpg或png格式并且大小不超过2MB
      return (isJPG || isPNG) && isLt2M;
    },
  }
}

</script>
