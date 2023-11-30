<template>
  <div class="homeListMain">
    <div class="base_title">
      <p class="title">互评任务</p>
      <p>为保证成绩公平、公正，在提交作业后请至少为6份 作业进行评分；不评或者少评都会直接影响作业成绩。<br>
        为确保大家的作业都能够被评到，互评列表采取依次解锁的形式；在评完第一份后下一份作业自动解锁。</p>
    </div>
    <div class="main">
      <el-table :data="tableData" border stripe style="width: 500px">
        <el-table-column prop="date" label="作业列表" width="300px">
          <template v-slot="scope">
          <span>student{{ scope.$index+1 }}</span>
          </template>
        </el-table-column>
        <el-table-column label="你的评分" width="200px">
          <template v-slot="scope">
          <span v-if="scope.row.score === null">{{ scope.row.score }}</span>
          <el-button v-else type="text" style="color: #3796EC;" @click="MutualEvaluate(scope.row)">前往互评</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

  </div>
</template>

<script setup>
import { ref, onMounted,defineProps } from 'vue';
import axios from 'axios';
 import {useRouter} from "vue-router";


const tableData = ref( []);  //储存后端传来的数据
const token = localStorage.getItem('token');
const props = defineProps(['homeworkID', 'contentID']);
 const router = useRouter();

const MutualEvaluate = (row) =>{
  router.push(`/studentHome/MutualEva/${props.homeworkID}/${row.contentID}`);
}

const fetchData = () => {
  console.log(props.contentID);
  axios
      .post(
          'http://localhost:8081/student/findCTsByCID',
          {
            contentID: props.contentID,
          },

          {
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded',
              'token': token,
            },
          }
      )
      .then(res => {
        if (res.data.code === 200) {
          if (res.data.data !== null) {
            tableData.value = res.data.data;
            console.log(res);
          }
        }else {
          console.log(res)
        }
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
.homeListMain{
  margin-top: 50px;
  display: flex;
  justify-content: start;
  flex-direction: column;
  text-align: left;
}

.title {
  position: relative;
  padding-left: 13px;
  font-size: 20px;
  font-weight: bold;
}

.title:before {
  content: "";
  background-color: #3796EC;
  width: 4px;
  height: 23px;
  position: absolute;
  left: 0;
  top: 50%;
  margin-top: -10px;
}

.main{
  margin-top: 50px;
}




</style>
