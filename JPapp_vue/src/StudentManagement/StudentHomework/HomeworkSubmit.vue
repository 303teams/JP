<template>
  <div style="display: flex; flex-direction: column; justify-content: center;">
    <div class="title" style="text-align: left">
      <h1 style="font-size: 30px; margin: 0; display: inline-block;">实验报告</h1>
      <span style="font-size: 15px;color: red; display: inline-block; margin-left: 40px;">截止时间</span>
    </div>

    <div class="content-container">
      <span style="font-size: 20px;font-weight: bold">作业内容:</span>
      <a style="font-size: 15px;margin-top:50px" :href="blobUrl" :download="fileName">{{fileName}}</a>
    </div>


    <el-divider></el-divider>

    <div>
      <el-button @click="UploadHomework">提交作业</el-button>
      <el-button @click="Back">返回</el-button>
    </div>


    <el-dialog title="提交作业" :close-on-click-modal="false" v-model="dialogTableVisible" width="40%">
      <div style = "flex: 1; display: flex; align-items: center; justify-content: center">
        <el-form :model="submitHomeworkForm" ref="SubmitHomeworkRef" label-width="80px" :rules="rules">
          <el-form-item label="作业附件" prop="file">
            <el-upload
                class="upload-demo"
                drag
                action="#"
                :auto-upload="false"
                :on-change="onChange"
                :before-remove="beforeRemove"
                multiple
                :file-list="fileList"
                limit="1"
            >
              <el-icon class="el-icon--upload"><upload-filled /></el-icon>
              <div class="el-upload__text">
                拖动文件到这或者 <em>点击上传</em>
              </div>
              <template #tip>
                <div class="el-upload__tip">
                  文件大小不超过10Mb
                </div>
              </template>
            </el-upload>
          </el-form-item>
        </el-form>
      </div>
      <span class="dialog-footer">
        <el-button @click="closeDia">取消</el-button>
        <el-button type="primary" @click="submitHomework">确认</el-button>
      </span>
      </el-dialog>
  </div>

</template>

<script setup>

import {defineProps, onMounted, reactive, ref} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";

const dialogTableVisible = ref(false);
const token = localStorage.getItem('token');
const props = defineProps(['homeworkID','cno']);
const router = useRouter();
const SubmitHomeworkRef = ref();
const fileList = ref([]);
const blobUrl = ref();
const fileName = ref();
const submitHomeworkForm = reactive({
  file: null,
});

const rules = {
  file: [
    { required: true, message: '请上传作业附件', trigger: 'change' },
  ],
};
const UploadHomework = () => {
  dialogTableVisible.value = true;
}

const onChange = (file) => {
  submitHomeworkForm.file = file.raw;
};

const beforeRemove = () => {
  submitHomeworkForm.file = null;  // 取消选择文件，清空 content
  return true;  // 返回 true 表示继续移除
};

const submitHomework = () => {
  const formData = new FormData();
  formData.set('file', submitHomeworkForm.file);
  formData.set('cno', props.cno)
  formData.set('homeworkID', props.homeworkID)

  // formData.set('submit_ddl', homeworkData.submitDdl);
  // formData.set('score_ddl', homeworkData.scoreDdl);


  console.log(formData)

  SubmitHomeworkRef.value.validate((valid) => {
    if (valid) {
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
              dialogTableVisible.value = false;
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
  dialogTableVisible.value = false;
};

const resetFormData = () => {
  submitHomeworkForm.file = null;
  fileList.value = [];
};

const closeDia= () => {
  dialogTableVisible.value = false;
  resetFormData();
};

const Back = () => {
  router.back();
};

const fetchData = () => {
  axios
      .post(
          'http://localhost:8081/homework/downloadHW',
          null, // 空的请求体
          {
            params: {
              homeworkId: props.homeworkID, // 将homeworkID作为查询参数添加
            },
            headers: {
              'Content-Type': 'application/json',
              'token': token,
            },
            responseType: 'blob',
          })

      .then((res) => {
        const blob = new Blob([res.data], { type: 'application/octet-stream' });

        // 提取 filename 的方法
        function extractFilename(contentDisposition) {
          const matches = contentDisposition.match(/filename="(.+?)"/);
          return matches ? matches[1] : null;
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

        console.log(err.response);
      });
};

onMounted(() => {
   fetchData();
});
</script>

<style scoped>
.title {
  height: 100px;
  width: 1150px;
  padding: 10px;
  background-color: rgb(233, 233, 233);
  border-radius: 10px;
}

.content-container {
  text-align: left;
  margin-top: 30px;
  margin-bottom: 200px;
  display: flex;
  flex-direction: column;
}
</style>
