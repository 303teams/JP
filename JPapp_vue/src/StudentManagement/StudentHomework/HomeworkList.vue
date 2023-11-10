<template>
  <el-table :data="filterTableData" style="width: 100%">
    <el-table-column label="id" prop="id" />
    <el-table-column label="课程号" prop="cno" />
    <el-table-column label="截止时间" prop="ddl" />
    <el-table-column label="作业名称" prop="hname" />
    <el-table-column label="作业内容" prop="content" />
    <el-table-column align="right">
      <template #header>
        <el-input v-model="search" size=“small” placeholder=“输入关键字搜索” />
      </template>
      <template #default="scope">
        <el-button size=“small” @click="handleEdit(scope.$index, scope.row)">提交</el-button >
      </template>
    </el-table-column>
  </el-table>
</template>

<script setup>
import {ref, computed, reactive, onMounted} from 'vue'
import axios from 'axios';

const search = ref('')
const tableData = reactive({ data: [] })

const filterTableData = computed(() =>
    tableData.data.filter(
        (data) =>
            !search.value ||
            data.hname.toLowerCase().includes(search.value.toLowerCase()) ||
            data.content.toLowerCase().includes(search.value.toLowerCase())
    )
)

const handleEdit = (index, row) => {
  console.log(index, row)
}

onMounted(() => {
  axios
      .post(
          'http://localhost:8081/homework/findById',
          null,
          {
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded',
              'token': localStorage.getItem('token'),
            },
          }
      )
      .then((res) => {
        if (res.data.code === 200) {
          // 使用 .data 将数据保存在 tableData 的响应式属性中
          tableData.data = res.data.data;
        } else {
          console.log(res.data.data + "hhh");
          // 在 setup 中，直接使用提示函数，而不是 this.$message
          window.alert("获取信息失败:" + res.data.msg);
        }
      })
      .catch((err) => {
        // 在 setup 中，直接使用 console.log
        console.error("发生未知错误！");
        console.log(err);
      });
  })
</script>
