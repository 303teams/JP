<template>
  <div style="padding: 20px">
    <el-row v-if="items.length > 0">
      <el-col
          v-for="(item, index) in items"
          :key="index"
          :span="7"
      >
        <div class="course_info">
          <img
              src="@/assets/img.png"
              class="image"
              @click="courseEnter(item.cno,item.cname)"
          />
          <div class="info-content">
            <span class="course_name" @click="courseEnter(item.cno,item.cname)">{{item.cname}}</span>
            <span class="course_id">课程号: {{item.cno}}</span>
          </div>

          <el-button class="button" @click="courseEnter(item.cno,item.cname)">进入课程</el-button>
        </div>

        <el-divider />
      </el-col>
    </el-row>

    <div v-else>
      <el-empty description="暂无已授课程"></el-empty>
    </div>
  </div>
</template>

<script setup>
import {onMounted, ref} from 'vue'
import {useRouter} from "vue-router";
import http from "@/api/http";

const items = ref([]);
const router = useRouter();

const fetchData = async () => {
  http.getCourseList()
      .then((res) => {
        if (res.data.code === 200) {
          if(res.data.data){
            items.value = res.data.data;
          }else{
            items.value = [];
          }
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

const courseEnter = (cno,cname) => {
  router.push({
    name: 'ViewMyHomework',
    params: {
      cno,
      courseName: cname
    }
  });
};

onMounted(() => {
  fetchData();
});
</script>

<style>
.el-col {
  margin-left: 30px;
}

.course_info {
  display: flex;
  flex-direction: column; /* 将内容垂直排列 */
  align-items: flex-start;
}

.info-content {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin-top: 15px;
}

.course_name{
  font-size: 25px;
  font-weight: bold;
  text-decoration: underline;
  cursor: pointer; /* 添加手型光标，表示可以点击 */
}

.course_id{
  font-size: 16px;
}

.teacher_name{
  font-size: 16px;
}

.button{
  margin-top: 20px;
}

.image {
  width: 350px;
  height: 200px;
  display: block;
  cursor: pointer; /* 添加手型光标，表示可以点击 */
}
</style>
