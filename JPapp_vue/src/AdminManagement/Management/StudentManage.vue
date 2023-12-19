<template>
  <div class="main-page">
    <div class="search-delete-add">
      <div>
        <el-button type="primary" size="large" @click="handleEdit">
          <el-icon><plus/></el-icon>添加
        </el-button>
        <el-button type="danger" size="large" @click="handleDelete">
          <el-icon><delete/></el-icon>批量删除
        </el-button>
      </div>
      <div class="search-container">
        <div class="search_input">
          <el-input v-model="search" size="large" placeholder="输入用户名搜索" />
        </div>
        <el-button type="primary" size="large" class="search_button" @click="clickSearch">
          <el-icon style="vertical-align: middle">
            <Search />
          </el-icon>
          <span style="vertical-align: middle"> 查询 </span>
        </el-button>
      </div>
    </div>
    <div>
      <el-table :data="filterTableData" style="width: 100%">
        <el-table-column label="Date" prop="date" />
        <el-table-column label="Name" prop="name" />
        <el-table-column align="right">
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
    </div>

  </div>

</template>

<script setup>
import {computed, onMounted, reactive, ref} from 'vue'
import {Delete} from "@element-plus/icons-vue";
// import http from "@/api/http";

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
  // http.getAllStudent()
  //     .then(res => {
  //       if(res.data.code === 200){
  //         tableData.value = res.data.data;
  //       }
  // })


}

onMounted(() => {
  fetchData();
})

</script>

<style scoped>
.main-page {
  background: #fff;
  border-radius: 4px;
  overflow: hidden;
  padding: 20px;
}

.search-delete-add{
  display: flex;
  justify-content: space-between;
  padding: 10px;
}

.search-container {
  display: flex;
  width: auto;
  margin-bottom: 10px;
}
</style>