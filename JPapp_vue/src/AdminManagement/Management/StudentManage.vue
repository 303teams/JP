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
      <el-table :data="filterTableData" style="width: 100%" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" />
        <el-table-column label="学号" prop="id" />
        <el-table-column label="姓名" prop="name" />
        <el-table-column label="性别" prop="sex" />
        <el-table-column label="年龄" prop="age" />
        <el-table-column label="邮箱" prop="email" />
        <el-table-column align="center" label="操作">
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

      <el-config-provider :locale="zhCn">
        <div class="demo-pagination-block">
          <el-pagination
              v-model:current-page="currentPage"
              v-model:page-size="pageSize"
              :page-sizes="[2, 5, 10, 15, 30, 50, 100]"
              background
              layout="total, sizes, prev, pager, next"
              :total="filteredData.length"
          />
        </div>
      </el-config-provider>

    </div>

  </div>

</template>

<script setup>
import {computed, onMounted, reactive, ref} from 'vue'
import {Delete} from "@element-plus/icons-vue";
import http from "@/api/http";
import zhCn from "element-plus/es/locale/lang/zh-cn";
import {ElConfigProvider} from "element-plus";

const tableData = reactive({data:[]});
const filteredData = ref([]);
const multipleSelection = ref([]);
const currentPage = ref(1); // 从第一页开始
const pageSize = ref(10); //每页展示多少条数据
const search = ref('')

// 将表格中的数据按pageSize切片
const filterTableData = computed(() =>
    filteredData.value.slice(
        (currentPage.value - 1) * pageSize.value,
        currentPage.value * pageSize.value
    )
);

const clickSearch = () => {
  filteredData.value = tableData.data.filter(
      (data) =>
          !search.value ||
          data.name.toLowerCase().includes(search.value.toLowerCase())
  );
};

const handleSelectionChange = (val) => {
  multipleSelection.value = val;
};

const fetchData = () => {
  http.getAllStudent()
      .then(res => {
        if(res.data.code === 200){
          tableData.data = res.data.data;
          clickSearch();
        }else{
          window.alert("获取信息失败:" + res.data.msg);
        }
      }).catch(err => {
        console.log(err);
  })


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