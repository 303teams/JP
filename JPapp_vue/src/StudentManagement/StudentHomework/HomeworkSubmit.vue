<template>
  <div style="display: flex; flex-direction: column; justify-content: center">
    <div class="title" style="text-align: left">
      <h1 style="font-size: 30px; margin: 0; display: inline-block;">{{ name }}</h1>
      <span style="font-size: 15px;color: red; display: inline-block; margin-left: 40px;">截止时间 {{submitDdl}}</span>
    </div>

    <div class="content-container">
      <span style="font-size: 20px;font-weight: bold">作业内容:</span>
      <a style="font-size: 15px;margin-top:50px" :href="blobUrl" :download="fileName">{{fileName}}</a>
    </div>

    <el-divider></el-divider>

    <div style = "flex: 1; display: flex; align-items: center; justify-content: start">
      <el-form :model="submitHomeworkForm" ref="SubmitHomeworkRef" :rules="rules">
        <el-form-item label="作业附件" prop="files">
          <el-upload
              class="upload-demo"
              action="#"
              :auto-upload="false"
              :on-change="handleChange"
              :on-remove="handleRemove"
              :on-exceed="handleExceed"
              :before-upload="beforeUpload"
              limit="1"
          >
            <el-button type="primary">上传附件</el-button>
            <template #tip>
            <div class="el-upload__tip">
              文件大小不超过100Mb
            </div>
            </template>
          </el-upload>
        </el-form-item>
        <el-form-item style="margin-top: 100px">
          <div style="width: 1100px" label="内容" prop="info">
            <el-input type="textarea" resize="none" :rows="10" v-model="submitHomeworkForm.info" placeholder="请输入作业内容"/>
          </div>
        </el-form-item>
      </el-form>
    </div>
    <span class="dialog-footer">
        <el-button @click="closeDia">取消</el-button>
        <el-button type="primary" @click="submitHomework">确认</el-button>
    </span>
  </div>

</template>

<script setup>

import {defineProps, onMounted, reactive, ref} from "vue";
import axios from "axios";
import http from "@/api/http";
import {useRouter} from "vue-router";
import {ElMessage} from "element-plus";

const token = localStorage.getItem('token');
const props = defineProps(['homeworkID','cno']);
const router = useRouter();
const SubmitHomeworkRef = ref();
const blobUrl = ref();
const fileName = ref();
const name = history.state.name;
const submitDdl = history.state.submitDdl;
const submitHomeworkForm = reactive({
  files: null,
  info: null,
});

const rules = {
  files: [
    { required: true, message: '请上传作业附件', trigger: 'change' },
  ],
};


const handleChange = (file,fileList) => {
  const fileName = fileList.length > 0 ? fileList[0].name : '';
  submitHomeworkForm.files = fileList;
  submitHomeworkForm.info = fileName;
};

const handleRemove = (file,fileList) => {
  submitHomeworkForm.files = fileList;  // 移除文件
};

const handleExceed = () => {
  ElMessage.warning(`最多只能上传1个附件`);
};

const beforeUpload = (file) => {
  const isLt10M = file.size / 1024 / 1024 < 100;
  if (!isLt10M) {
    ElMessage.error('上传文件大小不能超过 100MB!');
  }
  return isLt10M;
}

const submitHomework = () => {
  SubmitHomeworkRef.value.validate((valid) => {
    if (valid) {
      const formData = new FormData();
      if(submitHomeworkForm.files){
        for(const file of submitHomeworkForm.files){
          if(file.raw){
            formData.append('file', file.raw);
          }
        }
      }
      formData.set('cno', props.cno)
      formData.set('homeworkID', props.homeworkID)
      axios
          .post(
              'http://localhost:8081/content/uploadCT',
              formData,
              {
                headers: {
                  'Content-Type': 'multipart/form-data',
                  'token': token,
                },
              }
          )
          .then((res) => {
            if (res.data.code === 200) {
              console.log(res)
              resetFormData();
              Back();
            } else {
              window.alert("上传失败:" + res.data.msg);
              resetFormData();
              Back();
            }
          })
          .catch((err) => {
            console.error("发生未知错误！");
            resetFormData();
            Back();
            console.log(err);
          });
    } else {
      console.log("error submit!!");
      resetFormData();
      return false;
    }
  });
};

const resetFormData = () => {
  submitHomeworkForm.files = null;
};

const closeDia= () => {
  resetFormData();
};

const Back = () => {
  router.back();
};

const fetchData = () => {
  const data = {
    homeworkId: props.homeworkID,
  }
  http.downloadHomework(data).then((res) => {
    const blob = new Blob([res.data], { type: 'application/octet-stream' });

    // 提取 filename 的方法
    function extractFilename(contentDisposition) {
      const matches = contentDisposition.match(/filename="(.+?)"/);
      return matches ? decodeURIComponent(matches[1]) : null;
    }

    const contentDisposition = res.headers['content-disposition'];
    fileName.value = extractFilename(contentDisposition);
    console.log(res)
    console.log(fileName.value)

    blobUrl.value = URL.createObjectURL(blob);

  })
      .catch((err) => {
        console.error("发生未知错误！");
        console.log(err);
      });
};

onMounted(() => {
   fetchData();
});
</script>

<style scoped>
.title {
  height: 100px;
  width: 1100px;
  padding: 10px;
}

.content-container {
  text-align: left;
  margin-top: 30px;
  margin-bottom: 150px;
  padding: 10px;
  display: flex;
  flex-direction: column;
}
</style>
