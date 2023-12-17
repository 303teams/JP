<template>
  <el-table :data="filterTableData" style="width: 100%">
    <el-table-column label="Date" prop="date" />
    <el-table-column label="Name" prop="name" />
    <el-table-column align="right">
      <template #header>
      <el-input v-model="search" size="small" placeholder="Type to search" />
      </template>
      <template #default="scope">
      <el-button size="small" @click="handleEdit(scope.$index, scope.row)"
      >编辑</el-button
      >
      <el-button
          size="small"
          type="danger"
          @click="handleDelete(scope.$index, scope.row)"
      >删除</el-button
      >
      </template>
    </el-table-column>
  </el-table>
</template>

<script setup>
import {computed, onMounted, reactive, ref} from 'vue'
import http from "@/api/http";

const tableData = reactive([]);

const search = ref('')
const filterTableData = computed(() =>
    tableData.filter(
        (data) =>
            !search.value ||
            data.name.toLowerCase().includes(search.value.toLowerCase())
    )
)

const fetchData = () => {
  http.getAllStudent()
      .then(res => {
        if(res.data.code === 200){
          tableData.value = res.data.data;
        }
  })
}

onMounted(() => {
  fetchData();
})

</script>
