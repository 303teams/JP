<template>
  <div class="my-message-content">
    <div style="width: 1150px" v-if="visibleMessages.length">
      <div class="message-main" style="width: 1150px">
        <div
            class="message-item"
            v-for="(item, index) in visibleMessages"
            :key="item.id"
            :class="{ 'active': cur === index }"
            @click="goRead(item)"
        >
          <div class="item-dot" v-if="item.status === 1"></div>
          <div class="item-content">
            <p class="time">{{ item.createTime }}</p>
            <p class="title">{{ item.title }}</p>
          </div>
        </div>
      </div>


      <div class="pagination">
        <el-pagination
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-size="pageSize"
            :total="messageList.length"
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

const messageList = ref([
  { id: 1, title: '测试消息1', createTime: '2021-06-01 12:00:00', status: 1 },
  { id: 2, title: '测试消息2', createTime: '2021-06-02 12:30:00', status: 0 },
  { id: 2, title: '测试消息2', createTime: '2021-06-02 12:30:00', status: 0 },
  { id: 2, title: '测试消息2', createTime: '2021-06-02 12:30:00', status: 0 },
  { id: 2, title: '测试消息2', createTime: '2021-06-02 12:30:00', status: 0 },
  { id: 2, title: '测试消息2', createTime: '2021-06-02 12:30:00', status: 0 },
  { id: 2, title: '测试消息2', createTime: '2021-06-02 12:30:00', status: 0 },
  { id: 2, title: '测试消息2', createTime: '2021-06-02 12:30:00', status: 0 },
  { id: 2, title: '测试消息2', createTime: '2021-06-02 12:30:00', status: 0 },
  { id: 2, title: '测试消息2', createTime: '2021-06-02 12:30:00', status: 0 },
  { id: 2, title: '测试消息2', createTime: '2021-06-02 12:30:00', status: 0 },
  { id: 2, title: '测试消息2', createTime: '2021-06-02 12:30:00', status: 0 },
  { id: 2, title: '测试消息2', createTime: '2021-06-02 12:30:00', status: 0 },
  { id: 2, title: '测试消息2', createTime: '2021-06-02 12:30:00', status: 0 },
]);

const cur = ref(0);
const pageSize = 10;
const currentPage = ref(1);


const visibleMessages = computed(() => {
  const start = (currentPage.value - 1) * pageSize;
  const end = start + pageSize;
  return messageList.value.slice(start, end);
});

const handleCurrentChange = (newPage) => {
  currentPage.value = newPage;
};

onMounted(() => {
  console.log('messageList', messageList);
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
  width: 10px;
  height: 10px;
  background-color: red; /* Change the color to red */
  margin-right: 10px; /* Add some spacing between dot and content */
  border-radius: 50%; /* Make it a circle */
}

.title{
  font-size: 17px;
}

.item-content {
  flex-grow: 1;
  display: flex;
  justify-content: space-between;
  flex-direction: row-reverse; /* Reverse the order of the items */
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

