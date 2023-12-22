<template>
  <div class="homeListMain">
    <el-icon class="icon" @click="Back"><ArrowLeft /></el-icon>
    <div class="base_title">
      <div class="title">课程作业</div>
    </div>
    <div class="main">
      <div class="search-container">
        <div class="search_input">
          <el-input v-model="search" size="large" placeholder="输入关键字搜索" />
        </div>
        <el-button size="large" class="search_button" @click="clickSearch">
          <el-icon style="vertical-align: middle">
          <Search />
        </el-icon>
          <span style="vertical-align: middle"> 查询 </span>
        </el-button>
      </div>
      <el-table :data="filterTableData"
                class="HomeworkList"
                size="large"
                v-loading = "loading"
                element-loading-text = "拼命加载中"
                stripe
                :header-cell-style="{background:'#cde2ee',color:'#000'}">
        <el-table-column label="作业名称" width="150px" sortable prop="name" />
        <el-table-column label="课程名称" width="150px" sortable prop="courseName" />
        <el-table-column label="发布人" width="120px" prop="teacherName" />
        <el-table-column label="提交截止时间" width="180px" sortable prop="submitDdl" />
        <el-table-column label="互评截止时间" width="180px" sortable prop="scoreDdl" />
        <el-table-column label="查看作业" width="140px" align="center">
          <template v-slot="scope">
          <el-button
              @click="handleCheck(cno,scope.row)"
              size="large"
          >
            查看作业
          </el-button>
          </template>
        </el-table-column>
        <el-table-column label="作业成绩" width="120px" align="center">
          <template v-slot="scope">
          <el-tooltip v-if="scope.row.contentID !== null" class="item" effect="dark" content="查看详情" placement="top">
          <span @click="handleCheck(cno,scope.row)" style="cursor: pointer; color:dodgerblue">
            {{ scope.row.score }}
            <el-icon><Search /></el-icon>
          </span>
          </el-tooltip>

          <span v-else>--</span>
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
import { ref, computed, reactive, onMounted,defineProps } from 'vue';
import http from '@/api/http';
import { ElConfigProvider } from 'element-plus';
import zhCn from 'element-plus/es/locale/lang/zh-cn';
import {useRouter} from "vue-router";

const currentPage = ref(1); // 从第一页开始
const pageSize = ref(10); //每页展示多少条数据
const search = ref('');  // 搜索关键字
const tableData = reactive({ data: [] });  //储存后端传来的数据
const filteredData = ref([]); // 新的变量用于存储过滤后的数据
const props = defineProps(['cno']);
const router = useRouter();
const loading = ref(true);

// 将表格中的数据按pageSize切片
const filterTableData = computed(() =>
    filteredData.value.slice(
        (currentPage.value - 1) * pageSize.value,
        currentPage.value * pageSize.value
    )
);

const handleCheck = (cno,row) => {
  router.push({
    path:`/studentHome/viewHomework/${cno}/${row.homeworkID}`,
    state: {
      name:row.name,
      submitDdl:row.submitDdl,
      scoreDdl:row.scoreDdl,
      contentID:row.contentID,
      info:row.info,
      score:row.score,
      submitTime:row.submitTime
    }
  });
};



const fetchData = () => {
  const data = {
    cno: props.cno,
  }
  http.stuHomeworkList(data).then((res) => {
    if (res.data.code === 200) {
      console.log(props.cno);
      if(res.data.data) {
        tableData.data = res.data.data;
      }else{
        tableData.data = [];
      }
      console.log(res)
      updateFilteredData(); // 更新过滤后的数据
    } else {
      window.alert("获取信息失败:" + res.data.msg);
    }
  }).catch((err) => {
        console.error("发生未知错误！");
        console.log(err);
      });
};

const updateFilteredData = () => {
  filteredData.value = tableData.data.filter(
      (data) =>
          !search.value ||
          data.name.toLowerCase().includes(search.value.toLowerCase())
  );
};

const clickSearch = () => {
  updateFilteredData();
};

const Back = () => {
  router.back();
};

onMounted(() => {
  fetchData();
  loading.value = false;
});


</script>

<style scoped>
.homeListMain{
  display: flex;
  justify-content: center;
}

.icon{
  position: absolute;
  top: 95px;
  left: 268px;
  font-size: 30px;
  color: #3796EC;
  cursor: pointer;
}

.base_title {
  position: absolute;
  top: 100px;
  left: 330px;
}

.title {
  position: relative;
  padding-left: 13px;
  font-size: 20px;
  font-weight: bold;
}

.title:before {
  content: "";
  background-color: #3796EC;
  width: 4px;
  height: 23px;
  position: absolute;
  left: 0;
  top: 50%;
  margin-top: -10px;
}

.main{
  margin-top: 40px;
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

.search_button{
  color: #2176d7;
  margin-left: 10px;
}

.demo-pagination-block{
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 20px;
}


.HomeworkList{
  width: 100%;
}



</style>
