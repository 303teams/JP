<template>
  <div class="submitListMain">
    <el-icon class="icon" @click="Back"><ArrowLeft /></el-icon>
    <div class="base_title">
      <div class="title">提交情况</div>
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

        <div>
          <el-button @click="modifyContent" size="large" class="modify-ddl-button">修改作业内容</el-button>
          <el-button @click="modifyDdl" size="large" class="modify-ddl-button">修改截止时间</el-button>
        </div>
      </div>
      <el-table :data="filterTableData"
                class="HomeworkList"
                size="large"
                stripe
                :header-cell-style="{background:'#cde2ee',color:'#000'}">
        <el-table-column label="学生学号" width="120px" align="center" sortable prop="sno" />
        <el-table-column label="学生姓名" width="120px" align="center" sortable prop="sname" />
        <el-table-column label="提交时间" width="200px" align="center" sortable prop="submitTime" />
        <el-table-column label="作业提交内容" align="center" width="150px">
          <template v-slot="scope">
          <el-button type="text" v-if="scope.row.contentID !== null" @click="DownloadCT(scope.row)">查看作业</el-button>
          <span v-else>未提交</span>
          </template>
        </el-table-column>
        <el-table-column label="作业成绩" width="120px" align="center" sortable>
          <template v-slot="scope">
          <el-tooltip v-if="scope.row.similarAmount > 0" class="item" effect="dark" content="点击查看查重名单" placement="top">
          <span @click="ClickSimilar(scope.row)" style="cursor: pointer; color:red">
            {{ scope.row.score }}
            <el-icon><Search /></el-icon>
          </span>
          </el-tooltip>

          <span v-else>{{ scope.row.score }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="140px">
          <template v-slot="scope">
          <el-button size="large" @click="modifyScore(scope.row.contentID,scope.row.score)">修改成绩</el-button>
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

    <el-dialog title="修改成绩" :close-on-click-modal="false" :lock-scroll="false" v-model="modifyScoreDia" width="30%">
        <div>
          <p>当前分数：{{ currentScore }}</p>
          <el-input v-model="newScore" style="width: 150px;" @input="handleEdit" placeholder="输入新的分数"/>
        </div>

      <template #footer>
        <span>
          <el-button @click="modifyScoreDia = false">取 消</el-button>
          <el-button type="primary" @click="modiScoreSubmit">确 定</el-button>
        </span>
      </template>
    </el-dialog>

    <el-dialog title="上传作业" :close-on-click-modal="false" :lock-scroll="false" v-model="dialogTableVisible" @close="closeDia" width="50%" >
      <div style = "flex: 1; display: flex; align-items: center; justify-content: center">
        <el-form ref="HomeworkFormRef" :model="homeworkData" :rules="homeFormRules" label-width="130px">
          <el-form-item label="上传附件" prop="content">
            <el-upload
                class="upload-demo"
                drag
                action="#"
                :auto-upload="false"
                ref="uploadFile"
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
        <el-button @click="modifyHomework">提交</el-button>
        <el-button @click="closeDia">取消</el-button>
      </span>
    </el-dialog>
    <el-dialog title="修改截止时间" :close-on-click-modal="false" :lock-scroll="false" v-model="modifyDdlDia" width="30%">
      <div class="modify-ddl">
        <p>当前提交截止时间：{{submitDdl}}</p>
        <el-date-picker
            v-model="newSubmitDdl"
            type="datetime"
            value-format="YYYY-MM-DD HH:mm:ss"
            format="YYYY-MM-DD HH:mm:ss"
            :disabled-date="disabledSubmitDate"
            :disabled-hours="disabledSubmitHours"
            :disabled-minutes="disabledSubmitMinutes"
            :disabled-seconds="disabledSubmitSeconds"
            placeholder="选择日期和时间"/>

        <p>当前互评截止时间：{{scoreDdl }}</p>
        <el-date-picker
            v-model="newScoreDdl"
            type="datetime"
            value-format="YYYY-MM-DD HH:mm:ss"
            format="YYYY-MM-DD HH:mm:ss"
            :disabled-date="disabledScoreDate"
            :disabled-hours="disabledScoreHours"
            :disabled-minutes="disabledScoreMinutes"
            :disabled-seconds="disabledScoreSeconds"
            placeholder="选择日期和时间"/>
      </div>

      <template #footer>
        <span>
          <el-button @click="modifyDdlDia = false">取 消</el-button>
          <el-button type="primary" @click="modiDdlSubmit">确 定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>

  <review-details ref="ReviewDetailRef"></review-details>
</template>


<script setup>
import { ref, computed, reactive, onMounted,defineProps } from 'vue';
import {dayjs, ElConfigProvider, ElMessage} from 'element-plus';
import zhCn from 'element-plus/es/locale/lang/zh-cn';
 import {useRouter} from "vue-router";
import http from "@/api/http";
import ReviewDetails from "@/TeacherManagement/TeacherHomework/ReviewDetails.vue";

const currentPage = ref(1); // 从第一页开始
const pageSize = ref(10); //每页展示多少条数据
const search = ref('');  // 搜索关键字
const tableData = reactive({ data: [] });  //储存后端传来的数据
const filteredData = ref([]); // 新的变量用于存储过滤后的数据
const props = defineProps(['cno','homeworkID']);
const router = useRouter();
const modifyScoreDia = ref(false);
const modifyDdlDia = ref(false);
const currentContentID = ref(0);    // 当前作业ID
const currentScore = ref(0);       // 当前分数
const newScore = ref(0);           // 新的分数
let submitDdl = history.state.submitDdl;
let scoreDdl = history.state.scoreDdl;
const newSubmitDdl = ref('');
const newScoreDdl = ref('');
const ReviewDetailRef= ref();
const homeworkData = reactive({
  content: [],
  info: '',
});

// 在 Input 值改变时触发
const handleEdit = (e) => {
  let value = e.replace(/[^\d.]/g, '') // 只能输入数字和.
  value = value.replace(/^\./g, '')  //第一个字符不能是.
  value = value.replace(/\.{2,}/g, '.') // 不能连续输入.
  value = value.replace(/(\.\d+)\./g, '$1') // .后面不能再输入.
  value = value.replace(/^0+(\d)/, '$1') // 第一位0开头，0后面为数字，则过滤掉，取后面的数字
  value = value.replace(/(\d{15})\d*/, '$1') // 最多保留15位整数
  value = value.replace(/(\.\d{2})\d*/, '$1')// 最多保留2位小数

  newScore.value = value;
}

const ClickSimilar = (row) =>{
  ReviewDetailRef.value.openDetailDialog(row.contentID);
};

// 禁用日期
const disabledSubmitDate = (time) => {
  if(newScoreDdl.value === '') {
    return time.getTime() < new Date() - 8.64e7 || time.getTime() > new Date(scoreDdl).getTime();
  }else{
    return time.getTime() > new Date(newScoreDdl.value).getTime() || time.getTime() < new Date() - 8.64e7;
  }
};

const disabledSubmitHours = () => {
  if(newSubmitDdl.value !=='') {
    const selectedDate = new Date(newSubmitDdl.value).getTime();
    const selectedDay = dayjs(selectedDate).format('YYYY-MM-DD');
    const currentDay = dayjs().format('YYYY-MM-DD');
    const currentHour = new Date().getHours();


    if(newScoreDdl.value === ''){
      if(selectedDay === currentDay) {
        return Array.from({length: currentHour}, (_, index) => index);
      }else{
        return [];
      }
    }else{
      const scoreDay = dayjs(new Date(newScoreDdl.value).getTime()).format('YYYY-MM-DD');
      const scoreDdlHours = new Date(newScoreDdl.value).getHours();
      console.log("scoreDay:"+scoreDay)
      console.log("selectedDay:"+selectedDay)
      if(scoreDay === selectedDay) {
        if(selectedDay === currentDay){
          return Array.from({length: 24}, (_, index) => index < currentHour || index > scoreDdlHours ? index : null).filter(hour => hour !== null);
        }else{
          return Array.from({length: 24}, (_, index) => index > scoreDdlHours ? index : null).filter(hour => hour !== null);
        }
      }else{
        return [];
      }
    }
  }else{
    return [];
  }
};

const disabledSubmitMinutes = (selectedHour) => {
  if(newSubmitDdl.value !=='') {
    const selectedDate = new Date(newSubmitDdl.value).getTime();
    const selectedDay = dayjs(selectedDate).format('YYYY-MM-DD');
    const currentDay = dayjs().format('YYYY-MM-DD');
    const currentHour = new Date().getHours();
    const currentMinutes = new Date().getMinutes();

    if(newScoreDdl.value === ''){
      if(selectedDay === currentDay && selectedHour === currentHour) {
        return Array.from({length: currentMinutes}, (_, index) => index);
      }else{
        return [];
      }
    }else{
      const scoreDay = dayjs(new Date(newScoreDdl.value).getTime()).format('YYYY-MM-DD');
      const scoreDdlHours = new Date(newScoreDdl.value).getHours();
      const scoreDdlMinutes = new Date(newScoreDdl.value).getMinutes();
      if(scoreDay === selectedDay && selectedHour === scoreDdlHours) {
        if(selectedDay === currentDay && selectedHour === currentHour) {
          return Array.from({length: 60}, (_, index) => index < currentMinutes || index > scoreDdlMinutes ? index : null).filter(minute => minute !== null);
        }else{
          return Array.from({length: 60}, (_, index) => index > scoreDdlMinutes ? index : null).filter(minute => minute !== null);
        }
      }else{
        return [];
      }
    }
  }else{
    return [];
  }

};

const disabledSubmitSeconds = (selectedHour,selectedMinute) => {
  if(newSubmitDdl.value !=='') {
    const selectedDate = new Date(newSubmitDdl.value).getTime();
    const selectedDay = dayjs(selectedDate).format('YYYY-MM-DD');
    const currentDay = dayjs().format('YYYY-MM-DD');
    const currentHour = new Date().getHours();
    const currentMinutes = new Date().getMinutes();
    const currentSeconds = new Date().getSeconds();

    if(newScoreDdl.value === ''){
      if(selectedDay === currentDay && selectedHour === currentHour && selectedMinute === currentMinutes) {
        return Array.from({length: currentSeconds}, (_, index) => index);
      }else{
        return [];
      }
    }else{
      const scoreDay = dayjs(new Date(newScoreDdl.value).getTime()).format('YYYY-MM-DD');
      const scoreDdlHours = new Date(newScoreDdl.value).getHours();
      const scoreDdlMinutes = new Date(newScoreDdl.value).getMinutes();
      const scoreDdlSeconds = new Date(newScoreDdl.value).getSeconds();
      if(scoreDay === selectedDay && selectedHour === scoreDdlHours && selectedMinute === scoreDdlMinutes) {
        if(selectedDay === currentDay && selectedHour === currentHour && selectedMinute === currentMinutes) {
          return Array.from({length: 60}, (_, index) => index < currentSeconds || index > scoreDdlSeconds ? index : null).filter(second => second !== null);
        }else{
          return Array.from({length: 60}, (_, index) => index > scoreDdlSeconds ? index : null).filter(second => second !== null);
        }
      }else{
        return [];
      }
    }
  }else{
    return [];
  }
};

const disabledScoreDate = (time) => {
  if(newSubmitDdl.value === ''){
    return time.getTime() < new Date() - 8.64e7
  }else{
    return time.getTime() < new Date(newSubmitDdl.value).getTime();
  }
};

const disabledScoreHours = () => {
  if(newScoreDdl.value !== ''){
    const selectedDate = new Date(newScoreDdl.value).getTime();
    const selectedDay = dayjs(selectedDate).format('YYYY-MM-DD');
    const currentDay = dayjs().format('YYYY-MM-DD');
    const currentHour = new Date().getHours();

    if(newSubmitDdl.value === ''){
      if(selectedDay === currentDay) {
        return Array.from({length: currentHour}, (_, index) => index);
      }else{
        return [];
      }
    }else{
      const submitDdlDay = dayjs(new Date(newSubmitDdl.value).getTime()).format('YYYY-MM-DD');
      const submitDdlHours = new Date(newSubmitDdl.value).getHours();
      if(submitDdlDay === selectedDay) {
        if(selectedDay === currentDay){
          return Array.from({length: 24}, (_, index) => index < submitDdlHours || index < currentHour ? index : null).filter(hour => hour !== null);
        }else{
          return Array.from({length: 24}, (_, index) => index < submitDdlHours ? index : null).filter(hour => hour !== null);
        }
      }else{
        return [];
      }
    }
  }
};

const disabledScoreMinutes = (selectedHour) => {
  if(newScoreDdl.value !== ''){
    const selectedDate = new Date(newScoreDdl.value).getTime();
    const selectedDay = dayjs(selectedDate).format('YYYY-MM-DD');
    const currentDay = dayjs().format('YYYY-MM-DD');
    const currentHour = new Date().getHours();
    const currentMinutes = new Date().getMinutes();

    if(newSubmitDdl.value === ''){
      if(selectedDay === currentDay && selectedHour === currentHour) {
        return Array.from({length: currentMinutes}, (_, index) => index);
      }else{
        return [];
      }
    }else{
      const submitDdlDay = dayjs(new Date(newSubmitDdl.value).getTime()).format('YYYY-MM-DD');
      const submitDdlHours = new Date(newSubmitDdl.value).getHours();
      const submitDdlMinutes = new Date(newSubmitDdl.value).getMinutes();
      if(submitDdlDay === selectedDay && selectedHour === submitDdlHours) {
        if(selectedDay === currentDay && selectedHour === currentHour) {
          return Array.from({length: 60}, (_, index) => index < submitDdlMinutes || index < currentMinutes ? index : null).filter(minute => minute !== null);
        }else{
          return Array.from({length: 60}, (_, index) => index < submitDdlMinutes ? index : null).filter(minute => minute !== null);
        }
      }else{
        return [];
      }
    }
  }
};
const disabledScoreSeconds = (selectedHour,selectedMinute) => {
  if(newScoreDdl.value !== ''){
    const selectedDate = new Date(newScoreDdl.value).getTime();
    const selectedDay = dayjs(selectedDate).format('YYYY-MM-DD');
    const currentDay = dayjs().format('YYYY-MM-DD');
    const currentHour = new Date().getHours();
    const currentMinutes = new Date().getMinutes();
    const currentSeconds = new Date().getSeconds();

    if(newSubmitDdl.value === ''){
      if(selectedDay === currentDay && selectedHour === currentHour && selectedMinute === currentMinutes) {
        return Array.from({length: currentSeconds}, (_, index) => index);
      }else{
        return [];
      }
    }else{
      const submitDdlDay = dayjs(new Date(newSubmitDdl.value).getTime()).format('YYYY-MM-DD');
      const submitDdlHours = new Date(newSubmitDdl.value).getHours();
      const submitDdlMinutes = new Date(newSubmitDdl.value).getMinutes();
      const submitDdlSeconds = new Date(newSubmitDdl.value).getSeconds();
      if(submitDdlDay === selectedDay && selectedHour === submitDdlHours && selectedMinute === submitDdlMinutes) {
        if(selectedDay === currentDay && selectedHour === currentHour && selectedMinute === currentMinutes) {
          return Array.from({length: 60}, (_, index) => index < submitDdlSeconds || index < currentSeconds ? index : null).filter(second => second !== null);
        }else{
          return Array.from({length: 60}, (_, index) => index < submitDdlSeconds ? index : null).filter(second => second !== null);
        }
      }else{
        return [];
      }
    }
  }
};

// 将表格中的数据按pageSize切片
const filterTableData = computed(() =>
    filteredData.value.slice(
        (currentPage.value - 1) * pageSize.value,
        currentPage.value * pageSize.value
    )
);


const fetchData = () => {
  const data1 = {
    homeworkID: props.homeworkID,
  };

  http.getStudentHomeworkList(data1)
      .then(res1 => {
        console.log(res1);
        if (res1.data.code === 200) {
          console.log(props.cno);
          tableData.data = res1.data.data;
        }else {
            window.alert("获取信息失败:" + res1.data.msg);
          }
      }).then(() => {
        // 将已提交作业的学生放在列表前面排序
        tableData.data.sort((a, b) => {
          if (a.contentID !== null && b.contentID === null) {
            return -1;
          } else if (a.contentID === null && b.contentID !== null) {
            return 1;
          } else {
            return 0;
          }
        });

        updateFilteredData(); // 更新过滤后的数据
      }).catch(error => {
        console.error("发生未知错误！");
        console.log(error);
      });
};

const DownloadCT = (row) =>{
  const data = {
    contentID: row.contentID,
  };
  http.downloadCT(data)
      .then(res => {
        console.log(res);
        const blob = new Blob([res.data], { type: 'application/octet-stream' });
        const blobUrl = URL.createObjectURL(blob);

        const link = document.createElement('a');
        link.href = blobUrl;
        link.download = row.fileName;

        // 模拟点击下载
        link.click();

        // 释放 URL 对象
        URL.revokeObjectURL(blobUrl);
      })
      .catch(err => {
        console.error("发生未知错误！");
        console.log(err);
      });
}

const updateFilteredData = () => {
  filteredData.value = tableData.data.filter(
      (data) =>
          !search.value ||
          data.sno.toLowerCase().includes(search.value.toLowerCase()) ||
          data.sname.toLowerCase().includes(search.value.toLowerCase())
  );
};

const modifyScore = (contentId,score) => {
  modifyScoreDia.value = true;
  currentScore.value = score;
  newScore.value = score;
  currentContentID.value = contentId;
};

const modiScoreSubmit = () => {
  if(newScore.value === ''){
    ElMessage.error("分数不能为空");
    return;
  }

  if(newScore.value > 10){
    ElMessage.error("分数必须在0-10之间");
    return;
  }
  const data = {
    contentID: currentContentID.value,
    score: newScore.value,
  }
  http.changeScore(data)
      .then((res) => {
        if (res.data.code === 200) {
          console.log(res)
          modifyScoreDia.value = false;
          fetchData();
          ElMessage.success("修改成功");
        } else {
          window.alert("修改失败:" + res.data.msg);
        }
      })
      .catch((err) => {
        console.error("发生未知错误！");
        console.log(err);
      });
};

const modifyDdl = () => {
  modifyDdlDia.value = true;
};

const dialogTableVisible = ref(false);
const modifyContent = () => {
  dialogTableVisible.value = true;
};

const homeFormRules = reactive({
  content: [
    { required: true, message: "请上传作业附件", trigger: "change" },
  ],
});

const HomeworkFormRef = ref();
const modifyHomework = () => {
  HomeworkFormRef.value.validate((valid) => {
    const formData = new FormData();
    formData.set('homeworkID', props.homeworkID);
    if(homeworkData.content){
      for(const file of homeworkData.content){
        if(file.raw){
          formData.append('file', file.raw);
        }
      }
    }
    if(homeworkData.info){
      formData.set('info', homeworkData.info);
    }
    if (valid) {
      console.log(formData.get('scoreDdl'))
      http.changeHomework(formData)
          .then((res) => {
            if (res.data.code === 200) {
              console.log(res)
              ElMessage.success("作业修改成功");
              closeDia();
              fetchData();
            } else {
              window.alert("上传失败:" + res.data.msg);
              closeDia();
            }
          })
          .catch((err) => {
            console.error("发生未知错误！");
            closeDia();
            console.log(err);
          });
    }
  });
};
const closeDia = () => {
  dialogTableVisible.value = false;
  HomeworkFormRef.value.resetFields();
};

const modiDdlSubmit = () =>{
  const data = {
    homeworkID: props.homeworkID,
    submitDdl: newSubmitDdl.value,
    scoreDdl: newScoreDdl.value,
  }
  http.changeDeadline(data)
      .then((res) => {
        if (res.data.code === 200) {
          console.log(res)
          submitDdl = newSubmitDdl.value;
          scoreDdl = newScoreDdl.value;
          modifyDdlDia.value = false;
          fetchData();
          ElMessage.success("修改成功");
        } else {
          window.alert("修改失败:" + res.data.msg);
          modifyDdlDia.value = false;
        }
      })
      .catch((err) => {
        modifyDdlDia.value = false;
        console.error("发生未知错误！");
        console.log(err);
      });
}

const uploadFile= ref();
const handleChange = (file,fileList) => {
  const isLt100M = file.size / 1024 / 1024 < 100;
  console.log(file.size)
  if (!isLt100M) {
    ElMessage.error('上传文件大小不能超过 100MB!');
    uploadFile.value.handleRemove(file)
    return;
  }
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
  console.log(file.size)
  if (!isLt100M) {
    ElMessage.error('上传文件大小不能超过 100MB!');
  }
  return isLt100M;
}

const clickSearch = () => {
  updateFilteredData();
};

const Back = () => {
  router.back();
};

onMounted(() => {
  fetchData();
  newSubmitDdl.value = submitDdl;
  newScoreDdl.value = scoreDdl;
});


</script>


<style scoped>
.submitListMain{
  display: flex;
  justify-content: center;
  background: #fff;
  border-radius: 4px;
  overflow: hidden;
  padding: 20px;
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
  left: 420px;
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
  position: absolute;
  margin-top: 20px;
  right: 170px;
}

.modify-ddl {
  text-align: left;
  margin: 0 50px;
}

.modify-ddl-button{
  margin-left: 160px;
}

.HomeworkList{
  width: 100%;
}

</style>
