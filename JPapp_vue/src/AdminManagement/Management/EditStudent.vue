<template>
  <div class="modify-main-page">
    <div class="basic-info">
      <el-form >
        <el-form-item label="学号:" label-width="5em">
          <el-input v-model="new_id"/>
        </el-form-item>
        <el-form-item label="姓名:" label-width="5em">
          <el-input v-model="new_name" />
        </el-form-item>
        <el-form-item label="性别:" label-width="5em">
          <el-input v-model="new_sex"/>
        </el-form-item>
        <el-form-item label="年龄:" label-width="5em">
          <el-input v-model="new_age"/>
        </el-form-item>
        <el-form-item label="邮箱:" label-width="5em">
          <el-input v-model="new_email"/>
        </el-form-item>
      </el-form>
    </div>
    <div class="chosen-course">
      <el-table :data="filterTableData" style="width: 100%" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" />
        <el-table-column label="课程号" prop="cno" width="180"/>
        <el-table-column label="课程名称" prop="cname" width="150"/>
        <el-table-column label="授课教师" prop="teacherName" width="150"/>
        <el-table-column align="center" label="操作" width="200">
          <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)"
          >编辑</el-button
          >
          <el-button
              size="small"
              type="danger"
              @click="handleDelete(scope.row)"
          >删除</el-button
          >
          </template>
        </el-table-column>
      </el-table>

      <el-config-provider :locale="zhCn">
        <div class="demo-pagination-block">
          <el-pagination
              v-model:current-page="currentPage"
              v-model:page-size="pageSize"
              :page-sizes="[2, 3, 5]"
              background
              layout="total, sizes, prev, pager, next"
              :total="CourseList.length"
          />
        </div>
      </el-config-provider>
    </div>
  </div>
</template>

<script setup>
import {computed, onMounted, ref} from "vue";
import {ElConfigProvider, ElMessage} from "element-plus";
import http from "@/api/http";
import zhCn from "element-plus/es/locale/lang/zh-cn";

const id = history.state.id;
const name = history.state.name;
const sex = history.state.sex;
const age = history.state.age;
const email = history.state.email;
const new_id = ref('');
const new_name = ref('');
const new_sex = ref('');
const new_age = ref('');
const new_email = ref('');
const CourseList= ref([]);
const currentPage = ref(1); // 从第一页开始
const pageSize = ref(5); //每页展示多少条数据

// 将表格中的数据按pageSize切片
const filterTableData = computed(() =>
    CourseList.value.slice(
        (currentPage.value - 1) * pageSize.value,
        currentPage.value * pageSize.value
    )
);

const initData = () =>{
  new_id.value = id;
  new_name.value = name;
  new_sex.value = sex;
  new_age.value = age;
  new_email.value = email;
}
const fetchData = () => {
  const data = {
    id: id,
  }
  http.getStudentCourse(data).then((res) => {
    if (res.data.code === 200) {
      CourseList.value = res.data.data;
    } else {
      ElMessage.error("获取课程列表失败:" + res.data.msg);
    }
  }).catch(err => {
    console.error("发生未知错误！"+err);
  });
};

onMounted(() => {
  initData();
  fetchData();
});
</script>

<style>
.modify-main-page {
  background: #fff;
  border-radius: 4px;
  overflow: hidden;
  padding: 20px;
  display: flex;
  justify-content: space-between;
}

.chosen-course{
  display: flex;
  flex-direction: column;
  align-items: center;
}

.basic-info {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.demo-pagination-block{
  margin-top: 20px;
}
</style>