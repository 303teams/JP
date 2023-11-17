<template>
  <div class="homeListMain" style="position: relative">
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

        <el-button @click="uploadHomework" size="large" class="upload-button">上传作业</el-button>
      </div>
      <el-table :data="filterTableData"
                class="HomeworkList"
                size="large" column_width="60px"
                stripe
                :header-cell-style="{background:'#cde2ee',color:'#000'}">
        <el-table-column label="id" align="center">
          <template v-slot="{ $index }">{{ $index + 1 }}</template>
        </el-table-column>
        <el-table-column label="作业名称" sortable prop="name" />
        <el-table-column label="课程名称" sortable prop="courseName" />
        <el-table-column label="发布人" sortable prop="teacherName" />
        <el-table-column label="截止时间" sortable prop="submitDdl" />
        <el-table-column label="作业内容" prop="content" />
        <el-table-column align="right">
          <template v-slot="scope">
          <el-button size="large" @click="handleSubmit(scope.row.homeworkID)">更改</el-button>
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

    <!-- 上传文件的弹出框 -->
    <el-dialog title="上传文件" :close-on-click-modal="false" v-model="dialogTableVisible" width="30%" center>
        <el-form ref="HomeworkFormRef" :model="homeworkData" :rules="homeFormRules" label-width="130px">
          <el-form-item label="作业名字:" prop="kgCode" >
            <el-input v-model="homeworkData.name" ></el-input>
          </el-form-item>
          <el-form-item label="提交截止日期:" prop="targetUrl" >
            <el-date-picker v-model="homeworkData.submitDdl" type="datetime" placeholder="选择日期和时间"/>
          </el-form-item>
          <el-form-item label="互评截止日期:" prop="targetUsername" >
            <el-date-picker v-model="homeworkData.scoreDdl" type="datetime" placeholder="选择日期和时间"/>
          </el-form-item>
          <el-form-item label="上传文件" prop="content">
            <el-upload
                class="upload-demo"
                drag
                action
                :auto-upload="false"
                :http-request="httpRequest"
                multiple
            >
              <template #trigger>
              <el-button icon="el-icon-upload">选择文件</el-button>
              </template>
            </el-upload>
          </el-form-item>
        </el-form>
      <el-button @click="sumitHomework">提交</el-button>
      <el-button @click="closeDia">取消</el-button>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, computed, reactive, onMounted,defineProps } from 'vue';
import axios from 'axios';
import { ElConfigProvider } from 'element-plus';
import zhCn from 'element-plus/es/locale/lang/zh-cn';
import {useRouter} from "vue-router";

const currentPage = ref(1); // 从第一页开始
const pageSize = ref(10); //每页展示多少条数据
const search = ref('');  // 搜索关键字
const tableData = reactive({ data: [] });  //储存后端传来的数据
const filteredData = ref([]); // 新的变量用于存储过滤后的数据
const dialogTableVisible = ref(false);
const token = localStorage.getItem('token');
const props = defineProps(['cno']);
const router = useRouter();
const HomeworkFormRef =ref();
const homeworkData = reactive({
  name: '',
  content: '',
  submitDdl: '',
  scoreDdl: '',
});

const homeFormRules = reactive({
  name: [
    { required: true, message: '请输入作业名字', trigger: 'blur' },
  ],
  content: [
    { required: true, message: '请输入作业内容', trigger: 'blur' },
  ],
  submitDdl: [
    { required: true, message: '请选择提交截止日期', trigger: 'blur' },
  ],
  scoreDdl: [
    { required: true, message: '请选择互评截止日期', trigger: 'blur' },
  ],
});

// 将表格中的数据按pageSize切片
const filterTableData = computed(() =>
    filteredData.value.slice(
        (currentPage.value - 1) * pageSize.value,
        currentPage.value * pageSize.value
    )
);

//点击提交按钮
const handleSubmit = (homeworkID) => {
  router.push(`/studentHome/HomeworkSubmit/${homeworkID}`);
};


const fetchData = () => {
  axios
      .post(
          'http://localhost:8081/homework/findById',
          {
            cno: props.cno,
          },

          {
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded',
              'token': token,
            },
          }
      )
      .then((res) => {
        if (res.data.code === 200) {
          console.log(props.cno);
          tableData.data = res.data.data;
          console.log(res)
          updateFilteredData(); // 更新过滤后的数据
        } else {
          window.alert("获取信息失败:" + res.data.msg);
        }
      })
      .catch((err) => {
        console.error("发生未知错误！");
        console.log(err);
      });
};

const updateFilteredData = () => {
  filteredData.value = tableData.data.filter(
      (data) =>
          !search.value ||
          data.cno.toLowerCase().includes(search.value.toLowerCase())
  );
};

const uploadHomework = () => {
  dialogTableVisible.value = true;
};

const clickSearch = () => {
  updateFilteredData();
};

const sumitHomework = () => {
  const formData = new FormData();
  formData.append('name', homeworkData.name);
  formData.append('content', homeworkData.content);
  formData.append('submitDdl', homeworkData.submitDdl);
  formData.append('scoreDdl', homeworkData.scoreDdl);


  HomeworkFormRef.value.validate((valid) => {
      if (valid) {
        axios
            .post(
                'http://localhost:8081/teacher/uploadHW',
                formData,
                {
                  headers: {
                    'Content-Type': 'application/form-data',
                    'token': token,
                  },
                }
            )
            .then((res) => {
              if (res.data.code === 200) {
                console.log(res)
                window.alert("上传成功");
                dialogTableVisible.value = false;
              } else {
                window.alert("上传失败:" + res.data.msg);
              }
            })
            .catch((err) => {
              console.error("发生未知错误！");
              console.log(err);
            });
      } else {
        console.log("error submit!!");
        return false;
      }
    });
  dialogTableVisible.value = false;
};
const closeDia = () => {
  dialogTableVisible.value = false;
};


onMounted(() => {
  fetchData();
});


</script>

<style scoped>
.homeListMain{
  margin-top: 50px;
}


.base_title {
  position: absolute;
  top: -40px;
  left: 0;
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

.upload-button {
  position: absolute;
  top: 0;
  right: 0;
  margin-top: 10px;
  margin-right: 10px;
}

.demo-pagination-block{
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  margin-top: 20px;
  right: 0;
}

.main_page{
  margin-top: 80px;
}

.HomeworkList{
  width: 100vh;
}



</style>
