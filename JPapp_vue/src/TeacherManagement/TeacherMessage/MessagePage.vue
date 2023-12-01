<template>
  <div class="my-message-content">
    <div style="width: 1150px" v-if="visibleMessages.length">
      <div class="message-main" style="width: 1150px">
        <div
            class="message-item"
            v-for="(item) in visibleMessages"
            :key="item.contentID"
            :class="{ 'active': item.status === 0 }"
            @click="goRead(item)"
        >
          <div class="item-content">
            <div class="appeal-info">
              <p class="title">课程：{{ item.cname }}  作业名：{{item.name}}申诉请求</p>
              <div class="content">
                <div class="item-dot" v-if="item.status === 0"/>
                <span></span>
                <p style="margin-left: 20px">{{ item.appealContent }}</p>
              </div>
            </div>
            <p style="margin-top: 40px">{{ item.time }}</p>
          </div>
        </div>
      </div>


      <div class="pagination">
        <el-pagination
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-size="pageSize"
            :total="tableData.length"
            layout="prev, pager, next"
        />
      </div>
    </div>
    <div class="course-empty" v-else>
      <el-empty description="暂无消息"></el-empty>
    </div>
  </div>
</template>

<script setup>
import {computed, onMounted, ref} from 'vue';
import axios from "axios";


const pageSize = 10;
const currentPage = ref(1);
const token = localStorage.getItem('token');
const tableData = ref([]);  //储存后端传来的数据


const visibleMessages = computed(() => {
  const start = (currentPage.value - 1) * pageSize;
  const end = start + pageSize;
  return tableData.value.slice(start, end);
});

const handleCurrentChange = (newPage) => {
  currentPage.value = newPage;
};

const fetchData = () => {
  axios
      .post(
          'http://localhost:8081/teacher/findAppeal',
          null,
          {
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded',
              'token': token,
            },
          }
      ).then((res) => {
    if (res.data.code === 200) {
      tableData.value = res.data.data;
      console.log(res)
    } else {
      window.alert("获取信息失败:" + res.data.msg);
    }
  })
      .catch((err) => {
        console.error("发生未知错误！");
        console.log(err);
      });
};

onMounted(() => {
  fetchData()
});

const goRead = (item) => {
  console.log('Read message:', item);
  // 在这里处理点击消息后的逻辑，例如跳转到消息详情页面等
};
</script>


<style scoped>
.message-main {
  width: 100%;
}

.active{
  font-weight: bold;
}

.message-item {
  width: 100%;
  display: flex;
  align-items: center;
  border-bottom: 1px solid #ccc;
  padding: 10px;
  cursor: pointer;
}

.message-item:hover {
  background-color: rgba(88, 165, 196, 0.25); /* Change the background color on hover */
}

.item-dot {
  width: 7px;
  height: 7px;
  background-color: red;
  margin-top: 15px;
  margin-left: -10px;
  border-radius: 50%;
}

.title{
  font-size: 17px;
  margin-left: 20px;
}

.content{
  font-size: 12px;
  display: flex;
  justify-content: left;
  margin-top: -13px;
  margin-left: 10px;
}

.item-content {
  flex-grow: 1;
  display: flex;
  justify-content: space-between;
  flex-direction: row;
}


.appeal-info{
  display: flex;
  justify-content: start;
  flex-direction: column;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}

.full-row {
  width: 100%;
}
</style>

