<template>
  <el-table :data="filterTableData" style="width: 100%">
    <el-table-column label="发布时间" prop=“time” />
    <el-table-column label="截止时间" prop=“deadline” />
    <el-table-column label="课程名称" prop=“course” />
    <el-table-column label="作业内容" prop=“content” />
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
import { computed, ref } from 'vue'
import axios from 'axios'// 导入axios库

const search = ref('')
const tableData = ref([]) // 定义tableData变量，初始值为空数组
const filterTableData = computed(() =>
    tableData.value.filter(
        (data) =>
            !search.value ||
            data.course.toLowerCase().includes(search.value.toLowerCase()) ||
            data.content.toLowerCase().includes(search.value.toLowerCase()) ) )
const handleEdit = (index, row) => {
  console.log(index, row)
}

const fetchData = async () => {
  try {
    // 修改为 POST 请求，并设置请求头
    const response = await axios.post('http://localhost:8081/homework/findById', null, {
      headers: {
        Authorization: localStorage.getItem('token'), // 将 yourToken 替换为实际的 Token
        'Content-Type': 'application/json', // 根据你的后端接口要求设置合适的 Content-Type
      },
    })

    tableData.value = response.data
  } catch (error) {
    console.error(error)
  }
}

fetchData() // 在<script>标签的最后，调用fetchData函数，以便在进入页面时自动获取数据
</script>