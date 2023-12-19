<template>
  <div class="homeListMain">
    <el-icon class="icon" @click="Back"><ArrowLeft /></el-icon>
    <div class="main">
      <div class="content-container">
        <span style="font-size: 20px;font-weight: bold">作业答案:</span>
        <a style="font-size: 15px;margin-top:50px" :href="AnswerBlobUrl" :download="AnswerFileName">{{AnswerFileName}}</a>
      </div>
      <div class="content-container">
        <span style="font-size: 20px;font-weight: bold">学生答案:</span>
        <a style="font-size: 15px;margin-top:50px" :href="ContentBlobUrl" :download="ContentFileName">{{ContentFileName}}</a>
      </div>

      <div style="margin-top: 20px;">
        <label style="font-size: 16px; font-weight: bold;">打分:</label>
        <div class="rating-container">
          <div v-for="score in [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10]" :key="score" class="rating-item">
            <input type="radio" :id="'score' + score" :name="'score'" :value="score" v-model="selectedScore" />
            <label :for="'score' + score">{{ score }}</label>
          </div>
        </div>
      </div>

      <div class="comment" label="内容" prop="info">
        <el-input type="textarea" resize="none" :rows="4" v-model="info" placeholder="请输入评语" :maxlength="20"/>
        <el-button type="primary" @click="submitScore">提交</el-button>
      </div>
    </div>


  </div>
</template>

<script setup>
import { ref, onMounted,defineProps, } from 'vue';
import { useRouter } from 'vue-router';
import {ElMessage} from "element-plus";
import http from "@/api/http";

const router = useRouter();
const props = defineProps(['homeworkID', 'contentID']);
const ContentBlobUrl = ref();
const AnswerBlobUrl = ref();
const ContentFileName = ref();
const AnswerFileName = ref();
const selectedScore = ref();
const info = ref();

const fetchData = () => {
  const data1 = {
    contentID: props.contentID,
  };

  const data2 = {
    homeworkID: props.homeworkID,
  };


  // 同时发起两个请求
  Promise.all([
      http.downloadCT(data1),
      http.downloadAnswer(data2),
  ])
      .then(([res1, res2]) => {
        // 处理第一个请求的响应
        console.log(res1);
        const blob1 = new Blob([res1.data], {type: 'application/octet-stream'});
        const contentDisposition1 = res1.headers['content-disposition'];
        ContentFileName.value = extractFilename(contentDisposition1);
        console.log(ContentFileName.value)
        ContentBlobUrl.value = URL.createObjectURL(blob1);
        console.log(ContentBlobUrl.value)

        // 处理第二个请求的响应
        console.log(res2);
        const blob2 = new Blob([res2.data], {type: 'application/octet-stream'});
        const contentDisposition2 = res2.headers['content-disposition'];
        AnswerFileName.value = extractFilename(contentDisposition2);
        AnswerBlobUrl.value = URL.createObjectURL(blob2);
        console.log(AnswerFileName.value)
        console.log(AnswerBlobUrl.value)

      })
      .catch(error => {
        // 处理错误
        console.error('Error:', error);
      });
};

const extractFilename = (contentDisposition) => {
  const matches = contentDisposition.match(/filename="(.+?)"/);
  return matches ? decodeURIComponent(matches[1]) : null;
};

const submitScore = () => {
  console.log(selectedScore.value);
  if(selectedScore.value === undefined){
    ElMessage.warning("请选择分数");
    return;
  }

  const data = {
    contentID: props.contentID,
    score: selectedScore.value,
    content: info.value,
  }
  http.submitScore(data)
      .then((res) => {
        if (res.data.code === 200) {
          console.log(res);
          ElMessage.success("评分成功");
          Back();
        } else {
          window.alert("评分失败:" + res.data.msg);
        }
      })
      .catch((err) => {
        console.error("发生未知错误！");
        console.log(err);
      });
};

const Back = () =>{
  router.back();
}

onMounted(() => {
  fetchData();
});


</script>

<style scoped>
.homeListMain{
  display: flex;
  justify-content: start;
  flex-direction: column;
  text-align: left;
  padding-left: 50px;
}

.icon{
  position: absolute;
  top: 100px;
  left: 278px;
  font-size: 30px;
  color: #3796EC;
  cursor: pointer;
}

.main{
  margin-top: 100px;
}

.content-container {
  text-align: left;
  margin-bottom: 100px;
  padding: 10px;
  display: flex;
  flex-direction: column;
}

.rating-container {
  display: flex;
  align-items: center;
  margin-top: 10px;
}

.rating-item {
  margin-left: 20px;
  text-align: center;
}


.rating-item label {
  cursor: pointer;
  font-size: 16px;
  color: #333;
  transition: color 0.3s;
}

.rating-item label:hover {
  color: #ff9800; /* 或者你选择的其他颜色 */
}

.comment{
  display: flex;
  justify-content: center;
  flex-direction: column;
  width: 500px;
  margin-top: 30px
}

.comment .el-button{
  margin-top: 30px;
  width:50px
}

</style>
