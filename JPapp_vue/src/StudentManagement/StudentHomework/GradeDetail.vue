<template>
  <div class="homeListMain" style="position: relative; display: flex; justify-content: center">
    <div class="Score">
      <span>我的分数：{{score}}</span>
      <div style="width: 500px;margin-top: 30px" label="内容" prop="info">
        <el-input type="textarea" resize="none" :rows="8" v-model="info" placeholder="请输入申诉内容"/>
      </div>
      <el-button style="margin-top: 50px; width: 50px" @click="HandleAppeal">申诉</el-button>
    </div>
    <div class="echart" id="mychart" :style="myChartStyle"></div>
  </div>
</template>

<script setup>
import { useRouter } from "vue-router";
import { reactive, onMounted, defineProps, ref } from "vue";
import * as echarts from "echarts";
import axios from "axios";
import {ElMessage} from "element-plus";

const router = useRouter();
const token = localStorage.getItem("token");
const tableData = reactive([]);
const props = defineProps(["homeworkID"]);
const score= history.state.score;
const contentID = history.state.contentID;
const info = ref();

const myChart = ref({});
const myChartStyle = ref({ margin: "50px 20px 50px auto", width: "50%", height: "400px" }); // Adjusted margin

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
  console.log(info.value)
  axios
      .post(
          "http://localhost:8081/student/sendAppeal",
          null,
          {
            params:{
              contentID: contentID,
              appealContent: info.value,
            },
            headers: {
              "Content-Type": "application/json",
              token: token,
            },
          }
      )
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
  axios
      .post(
          "http://localhost:8081/chart/getScoreDistribution",
          {
            homeworkID: props.homeworkID,
          },
          {
            headers: {
              "Content-Type": "application/x-www-form-urlencoded",
              token: token,
            },
          }
      )
      .then((res) => {
        if (res.data.code === 200) {
          console.log(res)
          Object.assign(tableData, res.data.data);
          console.log(tableData.score_0);
          initEcharts(myChart, tableData);
        } else {
          window.alert("获取信息失败:" + res.data.msg);
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
.Score{
  margin-top: 80px;
  margin-left: 100px;
  font-size: 30px;
  color: #3796ec;
  display: flex;
  justify-content: start;
  flex-direction: column;
}

.echart {
  margin-top: 50px;
}
</style>
