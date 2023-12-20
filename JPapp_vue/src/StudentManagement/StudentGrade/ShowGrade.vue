<template>
  <div class="demo-collapse">
    <el-collapse v-model="activeNames" @change="handleChange" v-if="tableData.length > 0">
      <el-collapse-item class="score_item" v-for="item in tableData" :key="item.cno" :title="item.cname" :name="item.cno">
        <div style="margin: 20px">
          <div :id="`lineChart-${item.cno}`" :style="{width: '800px', height: '400px'}"></div>
          <div>总成绩：{{item.score}}</div>
        </div>
      </el-collapse-item>
    </el-collapse>
    <el-empty v-else />
  </div>
</template>

<script setup>
import { onMounted, reactive, ref} from 'vue';
import http from "@/api/http";
import { ElMessage } from "element-plus";
import * as echarts from "echarts";

const activeNames = ref(['1']);
const tableData = reactive([]);
const charts = ref({});
const handleChange = () => {
  console.log();
};

const fetchData = async () => {
  http.getTotalScore().then((res) => {
    if (res.data.code === 200) {
      Object.assign(tableData, res.data.data);
      setTimeout(() => {
        initialEcharts(charts, tableData);
      }, 1)
      console.log(res)
    } else {
      ElMessage.error("获取信息失败:" + res.data.msg);
    }
  })
      .catch((err) => {
        console.error("发生未知错误！");
        console.log(err);
      })

};

const initialEcharts = (chartsObject, data) => {
  data.forEach(item => {
    console.log("item in initialEcharts:", item); // 检查数据
    const option = {
      xAxis: {
        type: 'category',
        data: item.ranks.map((_, index) => index+1),
        name:'作业次数'
      },
      yAxis: {
        type: 'value',
        min: 0,
        max: Math.max(...item.ranks),
        inverse: true ,// 反向设置
        name:'排名'
      },
      series: [{
        data: item.ranks,
        type: 'line',
        smooth: true
      }]
    };


    const currentChart = echarts.init(document.getElementById(`lineChart-${item.cno}`));
    currentChart.setOption(option);

    chartsObject[item.cno] = currentChart;

    window.addEventListener("resize", () => {
      currentChart.resize(); // 使用当前图表实例的 resize 方法
    });
  });
};

onMounted(() => {
  fetchData();
});

</script>

<style scoped>
.score_item{
  display: flex;
  justify-content: start;
  flex-direction: column;
  text-align: left;
}
</style>