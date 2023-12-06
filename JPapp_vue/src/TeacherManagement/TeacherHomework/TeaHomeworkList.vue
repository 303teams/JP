<template>
  <div class="homeListMain">
    <el-icon class="icon" @click="Back"><ArrowLeft /></el-icon>
    <div class="base_title">
      <span style="font-size: 30px; font-weight: bold;">{{ courseName}}</span>
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
                v-loading = "loading"
                element-loading-text = "拼命加载中"
                size="large"
                stripe
                :header-cell-style="{background:'#cde2ee',color:'#000'}">
        <el-table-column label="作业名称" width="120px" sortable prop="name" />
        <el-table-column label="作业截止时间" width="170px" sortable prop="submitDdl" />
        <el-table-column label="互评截止时间" width="170px" sortable prop="scoreDdl" />
        <el-table-column label="作业内容" width="100px" prop="content" >
          <template v-slot="scope">
            <el-link
                :href="scope.row.blobUrl"
                :download="scope.row.fileName"
                style="color: dodgerblue; text-decoration: underline;"
            >
              查看作业
            </el-link>
          </template>
        </el-table-column>
        <el-table-column label="提交情况" width="150px" align="center">
          <template v-slot="scope">
          <el-tooltip class="item" effect="dark" content="查看详情" placement="top">
          <span @click="handleClick(scope.row)" style="cursor: pointer; color:dodgerblue">
            {{ scope.row.submitAmount }} / {{ scope.row.totalAmount }}
            <el-icon><Search /></el-icon>
          </span>
          </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column label="上传答案" align="center" width="160px">
          <template v-slot="scope">
          <el-upload
              v-if="scope.row.afilename === null"
              class="upload-demo"
              action="http://localhost:8081/homework/setAnswer"
              :http-request="(params) => uploadAnswer(params, scope.row)"
              :before-upload="beforeUpload"
              :on-success="successHandle"
              show-file-list="false"
              limit="1"
          >
            <el-button type="primary">上传答案</el-button>
            <template #tip>
            <div class="el-upload__tip">
              文件大小不超过100Mb
            </div>
            </template>
          </el-upload>
          <span v-else>已上传</span>
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

    <!-- 上传作业的弹出框 -->
    <el-dialog title="上传作业" :close-on-click-modal="false" :lock-scroll="false" v-model="dialogTableVisible" @close="closeDia" width="50%" >
      <div style = "flex: 1; display: flex; align-items: center; justify-content: center">
        <el-form ref="HomeworkFormRef" :model="homeworkData" :rules="homeFormRules" label-width="130px">
          <el-form-item label="作业名字:" prop="name" >
            <el-input style="width: 220px" v-model="homeworkData.name" ></el-input>
          </el-form-item>
          <el-form-item label="提交截止日期:" prop="submitDdl" >
            <el-date-picker
                v-model="homeworkData.submitDdl"
                type="datetime"
                value-format="YYYY-MM-DD HH:mm:ss"
                format="YYYY-MM-DD HH:mm:ss"
                :disabled-date="disabledSubmitDate"
                placeholder="选择日期和时间"/>
          </el-form-item>
          <el-form-item label="互评截止日期:" prop="scoreDdl" >
            <el-date-picker
                v-model="homeworkData.scoreDdl"
                type="datetime"
                value-format="YYYY-MM-DD HH:mm:ss"
                format="YYYY-MM-DD HH:mm:ss"
                :disabled-date="disabledScoreDate"
                placeholder="选择日期和时间"/>
          </el-form-item>
          <el-form-item label="上传附件" prop="content">
            <el-upload
                class="upload-demo"
                drag
                action="#"
                :auto-upload="false"
                :on-change="handleChange"
                :on-remove="handleRemove"
                :on-exceed="handleExceed"
                :before-upload="beforeUpload"
                :file-list="homeworkData.content"
                limit="1"
            >
              <el-icon class="el-icon--upload"><upload-filled /></el-icon>
              <div class="el-upload__text">
                拖动文件到这或者 <em>点击上传</em>
              </div>
              <template #tip>
              <div class="el-upload__tip">
                文件大小不超过100Mb
              </div>
              </template>
            </el-upload>
          </el-form-item>
          <el-form-item  label="作业内容" prop="info">
            <div style="width: 400px">
              <el-input type="textarea" resize="none" :rows="5" v-model="homeworkData.info" placeholder="请输入作业内容"/>
            </div>
          </el-form-item>
        </el-form>
      </div>
      <span class="dialog-footer">
        <el-button @click="assignHomework">提交</el-button>
        <el-button @click="closeDia">取消</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script setup>
import { ref, computed, reactive, onMounted,defineProps } from 'vue';
import axios from 'axios';
import {ElConfigProvider, ElMessage} from 'element-plus';
import zhCn from 'element-plus/es/locale/lang/zh-cn';
import {useRoute, useRouter} from "vue-router";

const loading = ref(true);
const currentPage = ref(1); // 从第一页开始
const pageSize = ref(10); //每页展示多少条数据
const search = ref('');  // 搜索关键字
const tableData = reactive({ data: [] });  //储存后端传来的数据
const filteredData = ref([]); // 新的变量用于存储过滤后的数据
const dialogTableVisible = ref(false);
const token = localStorage.getItem('token');
const props = defineProps(['cno']);
const HomeworkFormRef =ref();
const router = useRouter();
const route =useRoute();
const courseName = ref('');
const homeworkData = reactive({
  name: '',
  content: [],
  submitDdl: '',
  scoreDdl: '',
  info: '',
});

const homeFormRules = reactive({
  name: [
    { required: true, message: '请输入作业名字', trigger: 'blur' },
  ],
  content: [
    { required: true, message: '请上传附件', trigger: 'blur' },
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

// 禁用日期
const disabledSubmitDate = (time) => {
  if(homeworkData.scoreDdl === '') {
    return time.getTime() < new Date() - 8.64e7;
  }else{
    return time.getTime() > new Date(homeworkData.scoreDdl).getTime() || time.getTime() < new Date() - 8.64e7;
  }
};

const disabledScoreDate = (time) => {
  if(homeworkData.submitDdl === ''){
    return time.getTime() < new Date() - 8.64e7;
  }else{
    return time.getTime() < new Date(homeworkData.submitDdl).getTime();
  }

};



const fetchData = () => {
  return new Promise((resolve, reject) => {
    axios
        .post(
            'http://localhost:8081/teacher/findHWbyCno',
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
        .then(res1 => {
          if (res1.data.code === 200) {
            // courseName.value = res1.data.data[0].courseName;
            if(res1.data.data !==null){
              console.log(props.cno);
              tableData.data = res1.data.data;
              console.log(res1);

              // 使用promise实现多个接口的调用
              const promises = tableData.data.map(item => {
                return axios.post(
                    'http://localhost:8081/homework/downloadHW',
                    null,
                    {
                      params: {
                        homeworkId: item.homeworkID,
                      },
                      headers: {
                        'Content-Type': 'application/json',
                        'token': token,
                      },
                      responseType: 'blob',
                    }
                ).then(res2 => {
                  console.log(res2)
                  const blob = new Blob([res2.data], {type: 'application/octet-stream'});
                  const blobUrl = URL.createObjectURL(blob);

                  // 给每项作业分配url用来下载
                  item.blobUrl = blobUrl;
                  updateFilteredData(); // 更新过滤后的数据
                });
              });

              console.log(tableData.data)
              // 使用Promise.all来执行promises数组里的所有promise
              return Promise.all(promises);
            }
          } else {
            console.log(res1.data.msg)
          }
        })
        .then(() => {
          resolve({success: true, message: 'Data fetched successfully'});
        })
        .catch(error => {
          console.error("发生未知错误！");
          console.log(error);

          reject("发生未知错误！");
        });
  });
};

const updateFilteredData = () => {
  filteredData.value = tableData.data.filter(
      (data) =>
          !search.value ||
          data.name.toLowerCase().includes(search.value.toLowerCase())
  );
};

const handleClick = (row) => {
  router.push(`/teacherHome/ViewHomeworkSubmit/${props.cno}/${row.homeworkID}`);
};

const uploadHomework = () => {
  resetFormData();
  dialogTableVisible.value = true;
};

const clickSearch = () => {
  updateFilteredData();
};

const handleChange = (file,fileList) => {
  homeworkData.content = fileList;
};

const handleRemove = (file,fileList) => {
  homeworkData.content = fileList;  // 移除文件
};

const handleExceed = () => {
  ElMessage.warning(`最多只能上传1个附件`);
};

const beforeUpload = (file) => {
  const isLt100M = file.size / 1024 / 1024 < 100;
  if (!isLt100M) {
    ElMessage.error('上传文件大小不能超过 100MB!');
  }
  return isLt100M;
}
const assignHomework = () => {
  HomeworkFormRef.value.validate((valid) => {
    const formData = new FormData();
    if(homeworkData.content){
      for(const file of homeworkData.content){
        if(file.raw){
          formData.append('file', file.raw);
        }
      }
    }
    formData.set('cno', props.cno)
    formData.set('HWName', homeworkData.name);
    formData.set('scoreDdl', homeworkData.scoreDdl);
    formData.set('submitDdl', homeworkData.submitDdl);
      if (valid) {
        console.log(formData.get('scoreDdl'))
        axios
            .post(
                'http://localhost:8081/homework/uploadHW',
                formData,
                {
                  headers: {
                    'Content-Type': 'multipart/form-data',
                    'token': token,
                  },
                }
            )
            .then((res) => {
              if (res.data.code === 200) {
                console.log(res)
                resetFormData();
                fetchData();
              } else {
                window.alert("上传失败:" + res.data.msg);
                resetFormData();
              }
            })
            .catch((err) => {
              console.error("发生未知错误！");
              resetFormData();
              console.log(err);
            });
      }
    });
};
const closeDia = () => {
  dialogTableVisible.value = false;
  resetFormData();
  HomeworkFormRef.value.resetFields();
};

const form = new FormData();
const uploadAnswer = (params,row) =>{

  form.set('file', params.file);
  form.set('homeworkID', row.homeworkID)
  return axios({
    url: 'http://localhost:8081/homework/setAnswer',
    method: 'post',
    data: form,
    headers: {
      'Content-Type': 'multipart/form-data',
      'token': token,
    }
  })
};

const successHandle = () => {
  ElMessage.success('上传成功');
  fetchData();
  console.log(form.homeworkID)
};

const resetFormData = () => {
  homeworkData.name = '';
  homeworkData.content = [];
  homeworkData.submitDdl = '';
  homeworkData.scoreDdl = '';
  dialogTableVisible.value = false;
};

onMounted(() => {
  fetchData();
  loading.value = false;
  courseName.value=route.params.courseName;
  console.log(route.params.courseName)
});

const Back = () => {
  router.back();
};

</script>

<style scoped>
.homeListMain{
  margin-top: 50px;
  position: relative;
  display: flex;
  justify-content: center
}

.icon{
  position: absolute;
  top: -40px;
  left: 50px;
  font-size: 30px;
  color: #3796EC;
  cursor: pointer;
}

.base_title {
  position: absolute;
  top: -40px;
  left: 170px;
}

.title {
  position: relative;
  padding-left: 13px;
  font-size: 20px;
  font-weight: bold;
  margin-top: 40px;
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
  margin-top: 80px;
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
  right: 170px;
  margin-top: 80px;
  margin-bottom: 10px;
}

.demo-pagination-block{
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  margin-top: 20px;
  right: 170px;
}

.HomeworkList{
  width: 100vh;
}

</style>
