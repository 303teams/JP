<template>
  <div class="homeListMain">
    <el-icon class="icon" @click="Back"><ArrowLeft /></el-icon>
    <div class="main">
      <div style="display: flex;flex-direction: column">
        <span style="font-size: 20px;font-weight: bold">学生学号:</span>
        <p style="font-size: 16px">{{sno}}</p>
        <span style="font-size: 20px;font-weight: bold">学生姓名:</span>
        <p style="font-size: 16px">{{sname}}</p>
        <span style="font-size: 20px;font-weight: bold">当前分数:</span>
        <p style="font-size: 16px">{{HisScore}}</p>
      </div>
      <el-divider/>
      <div class="content-container">
        <span style="font-size: 20px;font-weight: bold">学生申诉内容:</span>
        <p style="font-size: 16px; white-space: pre-wrap">{{appealContent}}</p>
      </div>
      <el-divider/>
      <div class="content-container">
        <span style="font-size: 20px;font-weight: bold">学生答案:</span>
        <br/>
        <a style="font-size: 15px; margin-top:30px; display: inline-block" :href="ContentBlobUrl" :download="ContentFileName">{{ContentFileName}}</a>
      </div>
      <el-divider style="margin-top: 50px"></el-divider>

      <div style="margin-top: 50px;">
        <label style="font-size: 16px; font-weight: bold;">打分:</label>
        <div class="rating-container">
          <div v-for="score in [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10]" :key="score" class="rating-item">
            <input type="radio" :id="'score' + score" :name="'score'" :value="score" v-model="selectedScore" />
            <label :for="'score' + score">{{ score }}</label>
          </div>
        </div>
      </div>

    </div>

    <div class="button_class">
      <el-button type="primary" @click="handleAppeal">提交</el-button>
    </div>


  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import http from "@/api/http";
import {ElMessage} from "element-plus";

const router = useRouter();
const contentID = history.state.contentID;
const appealID = history.state.appealID;
const sno = history.state.sno;
const sname = history.state.sname;
const ContentBlobUrl = ref();
const ContentFileName = ref();
const selectedScore = ref();
const appealContent = ref();
const HisScore = ref();

const fetchData = () => {
  const data1 = {
    contentID: contentID,
  }

  const data2 = {
    appealID: appealID,
  }

  Promise.all([
      http.downloadCT(data1),
      http.readAppeal(data2),
  ])
      .then(([res1, res2]) => {
        console.log(res1);
        const contentDisposition = res1.headers['content-disposition'];
        const filename = extractFilename(contentDisposition);
        const blob = new Blob([res1.data], { type: 'application/octet-stream' });
        const blobUrl = URL.createObjectURL(blob);
        ContentBlobUrl.value = blobUrl;
        ContentFileName.value = filename;

        console.log(res2);
        if(res2.data.code === 200){
          appealContent.value = res2.data.data.appealContent;
          HisScore.value = res2.data.data.score;
        }else{
          ElMessage.error(res2.data.msg);
        }
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

const handleAppeal = () => {
  if(selectedScore.value === undefined){
    selectedScore.value = HisScore.value
  }
  const data = {
    appealID: appealID,
    contentID: contentID,
    score: selectedScore.value,
  }
  http.handleAppeal(data)
      .then((res) => {
        if (res.data.code === 200) {
          console.log(res);
          Back();
          ElMessage.success("处理成功")
        } else {
          ElMessage.error("处理失败:" + res.data.msg);
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
}

.icon{
  top: 0px;
  left: 0px;
  font-size: 30px;
  color: #3796EC;
  cursor: pointer;
}

.main{
  margin-top: 50px;
  margin-left: 20px;
}

.content-container {
  text-align: left;
  padding: 10px;
  display: inline-block;
}

.rating-container {
  display: flex;
  align-items: start;
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

.button_class{
  display: flex;
  justify-content: center;
  margin-top: 80px;
}

</style>
