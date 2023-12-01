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

      <div style="width: 1100px;margin-top: 30px" label="内容" prop="info">
        <el-input type="textarea" resize="none" :rows="4" v-model="info" placeholder="请输入评语"/>
      </div>
    </div>

    <div class="button_class">
      <el-button type="primary" @click="submitScore">提交</el-button>
    </div>


  </div>
</template>

<script setup>
import { ref, onMounted,defineProps, } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

const router = useRouter();
const token = localStorage.getItem('token');
const props = defineProps(['homeworkID', 'contentID']);
const ContentBlobUrl = ref();
const AnswerBlobUrl = ref();
const ContentFileName = ref();
const AnswerFileName = ref();
const selectedScore = ref();
const info = ref();

const fetchData = () => {
  // 创建两个 axios 实例
  const axiosInstance1 = axios.create({
    baseURL: 'http://localhost:8081',
    headers: {
      'Content-Type': 'application/json',
      'token': token,
    },
    responseType: 'blob',
  });

  const axiosInstance2 = axios.create({
    baseURL: 'http://localhost:8081',
    headers: {
      'Content-Type': 'application/json',
      'token': token,
    },
    responseType: 'blob',
  });

  // 同时发起两个请求
  Promise.all([
    axiosInstance1.post('/content/downloadCT', null, {
      params: {
        contentID: props.contentID,
      },
    }),
    axiosInstance2.post('homework/downloadAns', null, {
      params: {
        homeworkID: props.homeworkID,
      },
    }),
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
  console.log(props.contentID);
  axios
      .post(
          'http://localhost:8081/student/score',
          {
            contentID: props.contentID,
            score: selectedScore.value,
          },
          {
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded',
              'token': token,
            },
          }
      )
      .then((res) => {
        if (res.data.code === 200) {
          console.log(res);
          window.alert("评分成功");
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

.button_class{
  display: flex;
  justify-content: center;
  margin-top: 80px;
}

</style>
