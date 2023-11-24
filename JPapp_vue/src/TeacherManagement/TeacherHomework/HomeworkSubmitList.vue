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
          <el-button @click="modifyContent" size="large" class="upload-button">修改作业内容</el-button>
          <el-button @click="modifyDdl" size="large" class="upload-button">修改截止时间</el-button>
        </div>
      </div>
      <el-table :data="filterTableData"
                class="HomeworkList"
                size="large"
                stripe
                :header-cell-style="{background:'#cde2ee',color:'#000'}">
        <el-table-column label="学生学号" width="120px" sortable prop="sno" />
        <el-table-column label="学生姓名" width="120px" sortable prop="sname" />
        <el-table-column label="提交时间" width="200px" sortable prop="submitTime" />
        <el-table-column label="作业提交内容" width="150px">
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
        <el-table-column label="作业成绩" width="120px" sortable prop="score" />
        <el-table-column label="操作" width="130px">
          <template v-slot="scope">
          <el-button size="large" v-if="scope.row.contentID !== null" @click="modifyScore(scope.row.contentID,scope.row.score)">修改成绩</el-button>
          <span v-else>未提交</span>
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
const token = localStorage.getItem('token');
const props = defineProps(['cno','homeworkID']);
const router = useRouter();
const modifyScoreDia = ref(false);
const currentContentID = ref(0);    // 当前作业ID
const currentScore = ref(0);       // 当前分数
const newScore = ref(0);           // 新的分数


// 将表格中的数据按pageSize切片
const filterTableData = computed(() =>
    filteredData.value.slice(
        (currentPage.value - 1) * pageSize.value,
        currentPage.value * pageSize.value
    )
);


const fetchData = () => {

  return new Promise((resolve, reject) => {
    axios
        .post(
            'http://localhost:8081/teacher/findCTByHId',
            {
              homeworkID: props.homeworkID,
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
            console.log(props.cno);
            tableData.data = res1.data.data;
            console.log(res1);

            // 使用promise实现多个接口的调用
            const promises = tableData.data.map(item => {
              return axios.post(
                  'http://localhost:8081/content/downloadCT',
                  null,
                  {
                    params: {
                      contentID: item.contentID,
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
  axios
      .post(
          'http://localhost:8081/teacher/setCTScore',
          {
            contentID: currentContentID.value,
            score: newScore.value,
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
          console.log(res)
          modifyScoreDia.value = false;
          fetchData();
        } else {
          window.alert("修改失败:" + res.data.msg);
        }
      })
      .catch((err) => {
        console.error("发生未知错误！");
        console.log(err);
      });
};


const clickSearch = () => {
  updateFilteredData();
};

const Back = () => {
  router.back();
};

onMounted(() => {
  fetchData();
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
  top: -40px;
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

.main_page{
  margin-top: 80px;
}

.HomeworkList{
  width: 100%;
}



</style>
