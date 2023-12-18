<template>
  <div class="homeListMain">
    <div class = "score">
      <el-table
          :data="scoreList"
          style="width: 100%"
      >
        <el-table-column prop="score" label="学生评分" width="180" />
        <el-table-column prop="content" label="评语" width="500"/>
      </el-table>
      <span>我的分数：{{score}}</span>
      <div class="appeal">
        <div style="width: 500px;margin-top: 30px" label="内容" prop="info">
          <el-input type="textarea" resize="none" :rows="8" v-model="info" placeholder="请输入申诉内容"/>
        </div>
        <el-button style="margin-top: 50px; width: 50px" @click="HandleAppeal">申诉</el-button>
      </div>
    </div>
    <div class="echart" id="mychart" :style="myChartStyle"></div>
  </div>
</template>

<script setup>
import { useRouter } from "vue-router";
import {reactive, onMounted, defineProps, ref} from "vue";
import * as echarts from "echarts";
import {ElMessage} from "element-plus";
import http from "@/api/http";

const router = useRouter();
const tableData = reactive([]);
const scoreList = reactive([]);
const props = defineProps(["homeworkID"]);
const score= history.state.score;
const contentID = history.state.contentID;
const info = ref();
// const expandComments = reactive({});

const myChart = ref({});
const myChartStyle = ref({ margin: "50px 20px 50px auto", width: "50%", height: "400px" });

const initEcharts = (chart, data) => {
  const option = {
    legend: {
      data: ["0", "1-3", "4-6", "7-9", "10"],
      right: "20px", // Adjusted right position
      top: "30%",
      orient: "vertical",
    },
    title: {
      text: "分数分布图示",
      top: "0%",
      left: "center",
    },
    series: [
      {
        type: "pie",
        label: {
          show: true,
          formatter: "{b} : {c} ({d}%)",
        },
        radius: "30%",
        data: [
          {
            value: data.score_0,
            name: "0分",
          },
          {
            value: data.score_1_3,
            name: "1-3分",
          },
          {
            value: data.score_4_6,
            name: "4-6分",
          },
          {
            value: data.score_7_9,
            name: "7-9分",
          },
          {
            value: data.score_10,
            name: "10分",
          },
        ],
      },
    ],
  };

  chart.value = echarts.init(document.getElementById("mychart"));
  chart.value.setOption(option);

  window.addEventListener("resize", () => {
    chart.value.resize();
  });
};

const HandleAppeal = () =>{
  const data = {
    contentID: contentID,
    appealContent: info.value,
  }
  http.submitAppeal(data)
     .then((res) => {
        if (res.data.code === 200) {
          ElMessage.success("申诉提交成功")
          router.back();
        } else {
          window.alert("提交失败:" + res.data.msg);
        }
      })
      .catch((err) => {
        console.error("发生未知错误！");
        console.log(err);
      });
}

const fetchData = () => {
  const data1 = {
    homeworkID: props.homeworkID,
  };
  const data2 = {
    contentID: contentID,
  }

  Promise.all([
      http.getScoreInfo(data1),
      http.getScoreList(data2)
  ])
      .then(([res1, res2]) => {
        if (res1.data.code === 200) {
          console.log(res1)
          Object.assign(tableData, res1.data.data);
          initEcharts(myChart, tableData);
        } else {
          window.alert("获取信息失败:" + res1.data.msg);
        }

        if(res2.data.code === 200) {
          console.log(res2)
          Object.assign(scoreList, res2.data.data);

          //  初始化 expandComments
          // scoreList.forEach((item, index) => {
          //   expandComments[index] = item.content.split('\n').length > 2;
          // });
        }else{
          window.alert("获取信息失败:" + res2.data.msg);
        }
      })
      .catch(error => {
    // 处理错误
    console.error('Error:', error);
  });
};

// const toggleExpand = (index) => {
//   expandComments[index] = !expandComments[index];
// };


onMounted(() => {
  fetchData();
});
</script>

<style scoped>
.homeListMain{
  display: flex;
  flex-direction: row;
  justify-content: center;
}

.appeal{
  margin-top: 80px;
  display: flex;
  justify-content: start;
  flex-direction: column;
}

.score{
  display: flex;
  justify-content: start;
  flex-direction: column;
  text-align: left;
}

.score .el-table{
  margin-top: 50px;
}

.toggle{
  color: #3796EC;
  cursor: pointer;
}

.score-list div {
  overflow-wrap: break-word;
  word-wrap: break-word;
  max-width: 100%;
}

</style>
