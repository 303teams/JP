<template>
  <div class="my-message-content">
    <div style="width: 1150px" v-if="tableData">
      <div class="message-main">
        <div
            class="message-item"
            v-for="(item) in visibleMessages"
            :key="item.appealID"
            @click="goRead(item)"
        >
          <div class="item-content">
            <div class="appeal-info">
              <p class="title" :class="{ 'active': item.status === 0 }">
                课程：{{ item.cname }}
                &nbsp;&nbsp;&nbsp;&nbsp;
                作业名：{{item.hname}}
                &nbsp;&nbsp;&nbsp;&nbsp;
                申诉请求
                <span style="color:#3bb43b" v-if="item.status === 2">(已处理)</span>
              </p>
              <div class="content">
                <div class="item-dot" v-if="item.status === 0"></div>
                <div class="text-content">
                  <p class="sname" style="margin-left: 12px; white-space: nowrap; color: #3498db">{{item.sname}}：</p>
                  <p class="appeal-content" style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
                    {{ item.appealContent }}
                  </p>
                </div>
              </div>
            </div>
            <div style="margin-top: 40px">{{ item.time }}</div>
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
import http from "@/api/http";
import {ElMessage} from "element-plus";
import {useRouter} from "vue-router";


const pageSize = 10;
const currentPage = ref(1);
const token = localStorage.getItem('token');
const tableData = ref([]);  //储存后端传来的数据
const router = useRouter();


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
  if(item.status === 0){
    const data = {
      appealID: item.appealID,
    }
    http.ClickAppeal(data).then((res) => {
      if (res.data.code === 200) {
        console.log(res)
      } else {
        ElMessage.error("获取信息失败:" + res.data.msg);
      }
    })
        .catch((err) => {
          console.error("发生未知错误！");
          console.log(err);
        });
  }

  router.push({
    path: 'MessageDetail',
    state: {
      contentID: item.contentID,
      appealID: item.appealID,
      sno: item.sno,
      sname: item.sname,
    }
  })
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
  margin-top: 6px;
  border-radius: 50%;
}

.title{
  font-size: 19px;
  margin-left: 20px;

}

.content {
  display: flex;
  align-items: start;
}

.text-content{
  font-size: 14px;
  display: flex;
  justify-content: start;
  margin-top: -13px;
  margin-left: 10px;
  max-width: 1000px;

}

.item-content {
  flex-grow: 1;
  display: flex;
  justify-content: space-between;
  flex-direction: row;
}


.appeal-info{
  display: flex;
  flex-direction: column;
  align-items: flex-start;
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

