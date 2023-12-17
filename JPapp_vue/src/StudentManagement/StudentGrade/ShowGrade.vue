<template>
  <div class="demo-collapse">
    <el-collapse v-model="activeNames" @change="handleChange" v-if="tableData.length > 0">
      <el-collapse-item v-for="item in tableData" :key="item.cno" :title="item.cname" :name="item.cno">
        <canvas id="`lineChart-${item.cno}`"></canvas>
      </el-collapse-item>
    </el-collapse>
    <el-empty v-else />
  </div>
</template>

<script setup>
import {onBeforeUnmount, onMounted, reactive, ref} from 'vue';
import http from "@/api/http";
import { ElMessage } from "element-plus";
import * as echarts from "echarts";

const activeNames = ref(['1']);
const tableData = reactive([]);
const charts = ref({});
const handleChange = () => {
  console.log();
};

const fetchData = () => {
  http.getTotalScore().then((res) => {
    if (res.data.code === 200) {
      Object.assign(tableData, res.data.data);
      initialEcharts(charts, tableData);
    } else {
      ElMessage.error("获取信息失败:" + res.data.msg);
    }
  }).catch((err) => {
    console.error("发生未知错误！");
    console.log(err);
  });
};

onBeforeUnmount(() => {
  for (const chart of Object.values(charts.value)) {
    chart.dispose();
  }
});
const initialEcharts = (charts, data) => {
  data.forEach(item => {
    const option = {
      xAxis: {
        type: 'category',
        data: item.ranks.map((_, index) => index)
      },
      yAxis: {
        type: 'value',
        min: 0,
        max: Math.max(...item.ranks)
      },
      series: [{
        data: item.ranks,
        type: 'line',
        smooth: true
      }]
    };

    const currentChart = echarts.init(document.getElementById(`lineChart-${item.cno}`));
    currentChart.setOption(option);

    // Save the chart instance
    charts[item.cno] = currentChart;
  });
};


onMounted(() => {
  fetchData();
});
</script>
