<template>
  <el-icon class="icon" @click="Back"><ArrowLeft /></el-icon>
  <div class="echart" id="mychart" :style="myChartStyle"></div>
</template>

<script setup>
import { useRouter } from "vue-router";
import { ref, onMounted } from 'vue';
import * as echarts from 'echarts';

const router = useRouter();
const myChart = ref({});
const pieData = [
  { value: 463, name: '江苏' },
  { value: 395, name: '浙江' },
  { value: 157, name: '山东' },
  { value: 149, name: '广东' },
  { value: 147, name: '湖南' }
];
const pieName = pieData.map(item => item.name);
const myChartStyle = ref({ float: 'left', width: '100%', height: '400px' });

const initEcharts = (chart, data, names) => {
  const option = {
    legend: {
      data: names,
      right: '10%',
      top: '30%',
      orient: 'vertical'
    },
    title: {
      text: '国内院士前五省份图示',
      top: '0%',
      left: 'center'
    },
    series: [
      {
        type: 'pie',
        label: {
          show: true,
          formatter: '{b} : {c} ({d}%)'
        },
        radius: '30%',
        data: data
      }
    ]
  };

  chart.value = echarts.init(document.getElementById('mychart'));
  chart.value.setOption(option);

  window.addEventListener('resize', () => {
    chart.value.resize();
  });
};
const Back = () => {
  router.back();
};

onMounted(() => {
  initEcharts(myChart, pieData, pieName);
});
</script>

<style scoped>
.icon{
  position: absolute;
  top: 0px;
  left: 20px;
  font-size: 30px;
  color: #3796EC;
  cursor: pointer;
}

.echart{
  margin-top: 50px;
}
</style>