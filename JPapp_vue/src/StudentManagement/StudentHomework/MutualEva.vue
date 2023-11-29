<template>
  <div class="homeListMain">
    <el-icon class="icon" @click="Back"><ArrowLeft /></el-icon>
    <div class="base_title">
      <div class="title">互评任务</div>
    </div>
    <div class="main">
      <el-table :data="filterTableData"
                class="HomeworkList"
                size="large"
                stripe
                :header-cell-style="{background:'#cde2ee',color:'#000'}">
        <el-table-column label="序号" width="100px" align="center">
          <template v-slot="{ $index }">{{ $index + 1 }}</template>
        </el-table-column>
        <el-table-column label="作业" width="150px" sortable>
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
        <el-table-column label="打分" width="120px"/>
        <el-table-column label="批注" width="200px" sortable/>
        <el-table-column label="操作" width="120px">
          <template>
          <el-button
              size="large"
              @click="handleSubmit"
          >
            提交
          </el-button>
<!--          <span v-else>已提交</span>-->
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
              :total="tableData.length"
          />
        </div>
      </el-config-provider>
    </div>

  </div>
</template>

<script setup>
import { ref, computed, onMounted,defineProps } from 'vue';
import axios from 'axios';
import { ElConfigProvider } from 'element-plus';
import zhCn from 'element-plus/es/locale/lang/zh-cn';
import {useRouter} from "vue-router";

const currentPage = ref(1); // 从第一页开始
const pageSize = ref(10); //每页展示多少条数据
const tableData = ref( []);  //储存后端传来的数据
const token = localStorage.getItem('token');
const props = defineProps(['contentID']);
const router = useRouter();

// 将表格中的数据按pageSize切片
const filterTableData = computed(() =>
    tableData.value.slice(
        (currentPage.value - 1) * pageSize.value,
        currentPage.value * pageSize.value
    )
);

//点击提交按钮
const handleSubmit = () => {

};

const fetchData = () => {

  return new Promise((resolve) => {
    axios
        .post(
            'http://localhost:8081/student/findCTsByCID',
            {
              contentID: props.contentID,
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
              tableData.value = res1.data.data;
              console.log(res1);

              // 使用promise实现多个接口的调用
              const promises = tableData.value.map(item => {
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
                });
              });

              console.log(tableData.value)
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

        });
  });
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
  top: 0px;
  left: 260px;
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
  margin-top: 50px;
}

.demo-pagination-block{
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  margin-top: 20px;
  right: 250px;
}



</style>
