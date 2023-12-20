
<template>
  <el-table :data="tableData" style="width: 100%">
    <el-table-column label="ID" width="180">
      <template #default="scope">
        <i class="el-icon-time"></i>
        <span style="margin-left: 10px">{{ scope.row.date }}</span>
      </template>
    </el-table-column>
    <el-table-column label="姓名" width="180">
      <template #default="scope">
        <i class="el-icon-time"></i>
        <span style="margin-left: 10px">{{ scope.row.name }}</span>
<!--        <el-popover effect="light" trigger="hover" placement="top">-->
<!--          <template #default>-->
<!--            <p>姓名: {{ scope.row.name }}</p>-->
<!--            <p>住址: {{ scope.row.address }}</p>-->
<!--          </template>-->
<!--          <template #reference>-->
<!--            <div class="name-wrapper">-->
<!--              <el-tag size="medium">{{ scope.row.name }}</el-tag>-->
<!--            </div>-->
<!--          </template>-->
<!--        </el-popover>-->
      </template>
    </el-table-column>
    <el-table-column label="年龄" width="180">
      <template #default="scope">
        <i class="el-icon-time"></i>
        <span style="margin-left: 10px">{{ scope.row.date }}</span>
      </template>
    </el-table-column>
    <el-table-column label="邮箱" width="180">
      <template #default="scope">
        <i class="el-icon-time"></i>
        <span style="margin-left: 10px">{{ scope.row.date }}</span>
      </template>
    </el-table-column>
    <el-table-column label="操作">
      <template #default="scope">
        <el-button size="mini" @click="handleEdit(scope.$index, scope.row)"
        >编辑</el-button
        >
        <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)"
        >删除</el-button
        >
      </template>
    </el-table-column>
  </el-table>
</template>

<script setup>
import { ref, reactive, onMounted,defineProps } from 'vue';
import axios from 'axios';
// import { ElConfigProvider } from 'element-plus';
// import zhCn from 'element-plus/es/locale/lang/zh-cn';
//import {useRouter} from "vue-router";

// const currentPage = ref(1); // 从第一页开始
// const pageSize = ref(10); //每页展示多少条数据
const search = ref('');  // 搜索关键字
const tableData = reactive({ data: [] });  //储存后端传来的数据
const filteredData = ref([]); // 新的变量用于存储过滤后的数据
const token = localStorage.getItem('token');
const props = defineProps(['cno']);
// const router = useRouter();

// 将表格中的数据按pageSize切片
// const filterTableData = computed(() =>
//     filteredData.value.slice(
//         (currentPage.value - 1) * pageSize.value,
//         currentPage.value * pageSize.value
//     )
// );

//点击提交按钮
// const handleSubmit = (cno,homeworkID) => {
//   router.push(`/studentHome/HomeworkSubmit/${cno}/${homeworkID}`);
// };

const fetchData = () => {
  axios
      .post(
          'http://localhost:8081/student/findCTByCno',
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
      .then((res) => {
        if (res.data.code === 200) {
          console.log(props.cno);
          tableData.data = res.data.data;
          console.log(res)
          updateFilteredData(); // 更新过滤后的数据
        } else {
          window.alert("获取信息失败:" + res.data.msg);
        }
      })
      .catch((err) => {
        console.error("发生未知错误！");
        console.log(err);
      });
};

const updateFilteredData = () => {
  filteredData.value = tableData.data.filter(
      (data) =>
          !search.value ||
          data.cno.toLowerCase().includes(search.value.toLowerCase())
  );
};

// const clickSearch = () => {
//   updateFilteredData();
// };
//
// const Back = () => {
//   router.back();
// };

onMounted(() => {
  fetchData();
});


</script>

<style>

</style>