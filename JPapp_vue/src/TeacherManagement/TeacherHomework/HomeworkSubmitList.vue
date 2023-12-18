<template>
  <div class="homeListMain" style="position: relative; display: flex; justify-content: center">
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

        <div style="margin-left: 130px">
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
          <el-link
              v-if="scope.row.contentID !== null"
              :href="scope.row.blobUrl"
              :download="scope.row.fileName"
              style="color: dodgerblue; text-decoration: underline"
          >
            下载作业
          </el-link>
          <span v-else>未提交</span>
          </template>
        </el-table-column>
        <el-table-column label="作业成绩" width="120px" align="center" sortable prop="score" />
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
          <el-input v-model="newScore" style="width: 150px;" placeholder="输入新的分数"></el-input>
        </div>

      <template #footer>
        <span>
          <el-button @click="modifyScoreDia = false">取 消</el-button>
          <el-button type="primary" @click="modiScoreSubmit">确 定</el-button>
        </span>
      </template>
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
</template>


<script setup>
import { ref, computed, reactive, onMounted,defineProps } from 'vue';
import {dayjs, ElConfigProvider, ElMessage} from 'element-plus';
import zhCn from 'element-plus/es/locale/lang/zh-cn';
 import {useRouter} from "vue-router";
import http from "@/api/http";

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


// 禁用日期
const disabledSubmitDate = (time) => {
  if(homeworkData.scoreDdl === '') {
    return time.getTime() <= new Date() - 8.64e7;
  }else{
    return time.getTime() >= new Date(homeworkData.scoreDdl).getTime() || time.getTime() < new Date() - 8.64e7;
  }
};

const disabledSubmitHours = () => {
  if(homeworkData.submitDdl !=='') {
    const selectedDate = new Date(homeworkData.submitDdl).getTime();
    const selectedDay = dayjs(selectedDate).format('YYYY-MM-DD');
    const currentDay = dayjs().format('YYYY-MM-DD');
    const currentHour = new Date().getHours();


    if(homeworkData.scoreDdl === ''){
      if(selectedDay === currentDay) {
        return Array.from({length: currentHour}, (_, index) => index);
      }else{
        return [];
      }
    }else{
      const scoreDay = dayjs(new Date(homeworkData.scoreDdl).getTime()).format('YYYY-MM-DD');
      const scoreDdlHours = new Date(homeworkData.scoreDdl).getHours();
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
  if(homeworkData.submitDdl !=='') {
    const selectedDate = new Date(homeworkData.submitDdl).getTime();
    const selectedDay = dayjs(selectedDate).format('YYYY-MM-DD');
    const currentDay = dayjs().format('YYYY-MM-DD');
    const currentHour = new Date().getHours();
    const currentMinutes = new Date().getMinutes();

    if(homeworkData.scoreDdl === ''){
      if(selectedDay === currentDay && selectedHour === currentHour) {
        return Array.from({length: currentMinutes}, (_, index) => index);
      }else{
        return [];
      }
    }else{
      const scoreDay = dayjs(new Date(homeworkData.scoreDdl).getTime()).format('YYYY-MM-DD');
      const scoreDdlHours = new Date(homeworkData.scoreDdl).getHours();
      const scoreDdlMinutes = new Date(homeworkData.scoreDdl).getMinutes();
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
  if(homeworkData.submitDdl !=='') {
    const selectedDate = new Date(homeworkData.submitDdl).getTime();
    const selectedDay = dayjs(selectedDate).format('YYYY-MM-DD');
    const currentDay = dayjs().format('YYYY-MM-DD');
    const currentHour = new Date().getHours();
    const currentMinutes = new Date().getMinutes();
    const currentSeconds = new Date().getSeconds();

    if(homeworkData.scoreDdl === ''){
      if(selectedDay === currentDay && selectedHour === currentHour && selectedMinute === currentMinutes) {
        return Array.from({length: currentSeconds}, (_, index) => index);
      }else{
        return [];
      }
    }else{
      const scoreDay = dayjs(new Date(homeworkData.scoreDdl).getTime()).format('YYYY-MM-DD');
      const scoreDdlHours = new Date(homeworkData.scoreDdl).getHours();
      const scoreDdlMinutes = new Date(homeworkData.scoreDdl).getMinutes();
      const scoreDdlSeconds = new Date(homeworkData.scoreDdl).getSeconds();
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
  if(homeworkData.submitDdl === ''){
    return time.getTime() <= new Date() - 8.64e7;
  }else{
    const submitDdlDate = new Date(homeworkData.submitDdl).setHours(0, 0, 0, 0);
    return time.getTime() < submitDdlDate;
  }
};

const disabledScoreHours = () => {
  if(homeworkData.scoreDdl !== ''){
    const selectedDate = new Date(homeworkData.scoreDdl).getTime();
    const selectedDay = dayjs(selectedDate).format('YYYY-MM-DD');
    const currentDay = dayjs().format('YYYY-MM-DD');
    const currentHour = new Date().getHours();

    if(homeworkData.submitDdl === ''){
      if(selectedDay === currentDay) {
        return Array.from({length: currentHour}, (_, index) => index);
      }else{
        return [];
      }
    }else{
      const submitDdlDay = dayjs(new Date(homeworkData.submitDdl).getTime()).format('YYYY-MM-DD');
      const submitDdlHours = new Date(homeworkData.submitDdl).getHours();
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
  if(homeworkData.scoreDdl !== ''){
    const selectedDate = new Date(homeworkData.scoreDdl).getTime();
    const selectedDay = dayjs(selectedDate).format('YYYY-MM-DD');
    const currentDay = dayjs().format('YYYY-MM-DD');
    const currentHour = new Date().getHours();
    const currentMinutes = new Date().getMinutes();

    if(homeworkData.submitDdl === ''){
      if(selectedDay === currentDay && selectedHour === currentHour) {
        return Array.from({length: currentMinutes}, (_, index) => index);
      }else{
        return [];
      }
    }else{
      const submitDdlDay = dayjs(new Date(homeworkData.submitDdl).getTime()).format('YYYY-MM-DD');
      const submitDdlHours = new Date(homeworkData.submitDdl).getHours();
      const submitDdlMinutes = new Date(homeworkData.submitDdl).getMinutes();
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
  if(homeworkData.scoreDdl !== ''){
    const selectedDate = new Date(homeworkData.scoreDdl).getTime();
    const selectedDay = dayjs(selectedDate).format('YYYY-MM-DD');
    const currentDay = dayjs().format('YYYY-MM-DD');
    const currentHour = new Date().getHours();
    const currentMinutes = new Date().getMinutes();
    const currentSeconds = new Date().getSeconds();

    if(homeworkData.submitDdl === ''){
      if(selectedDay === currentDay && selectedHour === currentHour && selectedMinute === currentMinutes) {
        return Array.from({length: currentSeconds}, (_, index) => index);
      }else{
        return [];
      }
    }else{
      const submitDdlDay = dayjs(new Date(homeworkData.submitDdl).getTime()).format('YYYY-MM-DD');
      const submitDdlHours = new Date(homeworkData.submitDdl).getHours();
      const submitDdlMinutes = new Date(homeworkData.submitDdl).getMinutes();
      const submitDdlSeconds = new Date(homeworkData.submitDdl).getSeconds();
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
  return new Promise((resolve, reject) => {
    const data1={
      homeworkID: props.homeworkID,
    }
    http.getStudentHomeworkList(data1)
        .then(res1 => {
          console.log(res1);
          if (res1.data.code === 200) {
            console.log(props.cno);
            tableData.data = res1.data.data;
            console.log(res1);

            // 使用promise实现多个接口的调用
            const promises = tableData.data.map(item => {
              const data2={
                contentID: item.contentID,
              }
              return http.downloadCT(data2)
                  .then(res2 => {
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
          } else {
            window.alert("获取信息失败:" + res1.data.msg);
            reject("获取信息失败:" + res1.data.msg);
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
          modifyDdlDia.value = false;
          submitDdl = newSubmitDdl.value;
          scoreDdl = newScoreDdl.value;
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
.homeListMain{
  margin-top: 50px;
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
  top: 0px;
  left: 170px;
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
