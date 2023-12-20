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
          <el-input v-model="search" size="large" placeholder="输入学号或者名字进行搜索" />
        </div>
        <el-button type="primary" size="large" class="search_button" @click="clickSearch">
          <el-icon style="vertical-align: middle">
            <Search />
          </el-icon>
          <span style="vertical-align: middle"> 查询 </span>
        </el-button>
      </div>
    </div>
    <div class="table-data">
      <el-table :data="filterTableData" style="width: 100%" @selection-change="handleSelectionChange">
        <el-table-column type="selection"/>
        <el-table-column label="课程号" prop="cno"/>
        <el-table-column label="课程名" prop="cname"/>
        <el-table-column label="授课教师" prop="tname" />
        <el-table-column align="center" label="操作">
          <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)"
          >编辑</el-button
          >
          <el-button
              size="small"
              type="danger"
              @click="handleDelete(scope.row)"
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
import {ElConfigProvider, ElMessage, ElMessageBox} from "element-plus";
import {useRouter} from "vue-router";

const tableData = reactive({data:[]});
const filteredData = ref([]);
const multipleSelection = ref([]);
const currentPage = ref(1); // 从第一页开始
const pageSize = ref(15); //每页展示多少条数据
const search = ref('')
const router = useRouter();

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
          data.id.toLowerCase().includes(search.value.toLowerCase())||
          data.name.toLowerCase().includes(search.value.toLowerCase())
  );
};

const handleSelectionChange = (val) => {
  multipleSelection.value = val;
};

const handleDelete = (row) => {
  if (row.id) {
    ElMessageBox.confirm('确定要删除这个学生吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }).then(() => {
      // 用户点击了确定后的逻辑
      const data = {
        ids: row.id,
      }
      http.deleteStudent(data).then((res) => {
        if (res.data.code === 200) {
          ElMessage.success("删除成功");
          fetchData();
          console.log(res)
        } else {
          ElMessage.error("获取信息失败:" + res.data.msg);
        }
      })
          .catch((err) => {
            console.error("发生未知错误！");
            console.log(err);
          });
    }).catch(() => {
    });
  } else {
    if (multipleSelection.value.length > 0) {
      ElMessageBox.confirm('确定要删除这些学生吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        // 用户点击了确定后的逻辑
        const data = {
          ids: multipleSelection.value.map((item) => item.id).join()
        }
        http.deleteStudent(data).then((res) => {
          if (res.data.code === 200) {
            ElMessage.success("删除成功");
            fetchData();
            console.log(res)
          } else {
            ElMessage.error("删除失败:" + res.data.msg);
          }
        })
            .catch((err) => {
              console.error("发生未知错误！");
              console.log(err);
            });
      }).catch(() => {
      });
    }else {
      ElMessage.warning("未选中任何行!");
    }
  }
};

const handleEdit = (row) =>{
  router.push({
    path: '/adminHome/EditStudent',
    state:{
      id:row.id,
      name:row.name,
      sex:row.sex,
      email:row.email,
      age:row.age
    }
  })
}
const fetchData = () => {
  http.getAllCourse()
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

.search_input{
  width: 350px;
  margin-right: 10px;
}

.table-data{
  display: flex;
  flex-direction: column;
  align-items: center;
}

.demo-pagination-block{
  margin-top: 20px;
}

.el-table__header, .el-table__body, .el-table__footer{
  width:100% !important;
  table-layout: fixed !important;
}
</style>