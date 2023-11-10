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

const fetchData = async () => { // 定义fetchData函数，用于向后端发送请求，并将返回的数据赋值给tableData变量
   try {
     const response = await axios.get('/api/homework') // 你可以根据你的后端接口的具体情况，修改请求的URL和参数
      tableData.value = response.data
   } catch (error) {
     console.error(error)
   }
}

fetchData() // 在<script>标签的最后，调用fetchData函数，以便在进入页面时自动获取数据
</script>