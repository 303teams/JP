<template>
  <div class="homeListMain">
    <div class="base_title">
      <p class="title">互评任务</p>
      <p>为保证成绩公平、公正，在提交作业后请为6份 作业进行评分；不评或者少评都会直接影响作业成绩。</p>
    </div>
    <div class="main">
      <el-table v-if="props.contentID" :data="tableData" border stripe style="width: 500px">
        <el-table-column prop="date" label="作业列表" width="300px">
          <template v-slot="scope">
          <span>student{{ scope.$index+1 }}</span>
          </template>
        </el-table-column>
        <el-table-column label="你的评分" width="200px">
          <template v-slot="scope">
          <span v-if="scope.row.score !== null">{{ scope.row.score }}</span>
          <el-button v-else type="text" style="color: #3796EC;" @click="MutualEvaluate(props.homeworkID,scope.row)">前往互评</el-button>
          </template>
        </el-table-column>
      </el-table>

      <span style="font-size: 25px" v-else>您未提交作业，无法参与互评!!!</span>
    </div>

  </div>
</template>

<script setup>
import {ref, onMounted, defineProps, defineEmits, computed} from 'vue';
import {useRouter} from "vue-router";
import http from "@/api/http";


const tableData = ref( []);  //储存后端传来的数据
const props = defineProps(['homeworkID', 'contentID']);
const router = useRouter();
const emit = defineEmits(['MutualNum']);

const MutualEvaluate = (homeworkID,row) =>{
  router.push(`/studentHome/MutualEva/${homeworkID}/${row.contentID}`);
  setValue();
}

const fetchData = () => {
  if(props.contentID !== null){
    const data = {
      contentID: props.contentID,
    };
    http.getMutualHomeworkList(data)
        .then(res => {
          if (res.data.code === 200) {
            if (res.data.data !== null) {
              tableData.value = res.data.data;
              setValue();
              console.log("tableData:", tableData.value);
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
  }

};


const scoredDataCount = computed(() => {
  // 如果 tableData 不为 null，则进行计算，否则返回 null 或其他默认值
  return tableData.value ? tableData.value.filter(item => item.score !== null).length : null;
});


const setValue = () => {
  emit('MutualNum',scoredDataCount.value);
}

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
