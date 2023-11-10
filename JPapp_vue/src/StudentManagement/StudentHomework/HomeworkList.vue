<template>
  <el-table :data="filterTableData" style="width: 100%">
    <el-table-column label="id" prop="id" />
    <el-table-column label="课程号" prop="cno" />
    <el-table-column label="截止时间" prop="ddl" />
    <el-table-column label="作业名称" prop="hname" />
    <el-table-column label="作业内容" prop="content" />
    <el-table-column align="right">
      <template #header>
        <el-input v-model="search" size="small" placeholder="输入关键字搜索" />
      </template>
        <el-button size="small" @click="handleSubmit">提交</el-button >
    </el-table-column>
  </el-table>

  <!-- 上传文件的弹出框 -->
  <el-dialog
      title="上传文件"
      v-model="dialogTableVisible"
      width="30%"
      center
  >
    <el-upload
    class="upload-demo"
    drag
    action="http://localhost:8081/file/upload"
    :headers="{'token': token}"
    multiple
    >
    <el-icon class="el-icon--upload"><upload-filled /></el-icon>
    <div class="el-upload__text">
      拖动文件到这或者 <em>点击上传</em>
    </div>
    <template #tip>
    <div class="el-upload__tip">
      文件不能大于5mb
    </div>
    </template>

    </el-upload>

    <el-button
        type="primary"
        :loading="uploading"
        style="margin-top: 10px;"
        @click="submitUpload"
    >
      确认
    </el-button>
  </el-dialog>
</template>

<script setup>
import {ref, computed, reactive, onMounted} from 'vue'
import axios from 'axios';

const search = ref('')
const tableData = reactive({ data: [] })
const dialogTableVisible = ref(false);
const token = localStorage.getItem('token')

const filterTableData = computed(() =>
    tableData.data.filter(
        (data) =>
            !search.value ||
            data.hname.toLowerCase().includes(search.value.toLowerCase()) ||
            data.content.toLowerCase().includes(search.value.toLowerCase())
    )
)

const handleSubmit = () => {
  dialogTableVisible.value = true;
};

onMounted(() => {
  axios
      .post(
          'http://localhost:8081/homework/findById',
          null,
          {
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded',
              'token': token
            },
          }
      )
      .then((res) => {
        if (res.data.code === 200) {
          // 使用 .data 将数据保存在 tableData 的响应式属性中
          tableData.data = res.data.data;
        } else {
          console.log(res.data.data + "hhh");
          // 在 setup 中，直接使用提示函数，而不是 this.$message
          window.alert("获取信息失败:" + res.data.msg);
        }
      })
      .catch((err) => {
        // 在 setup 中，直接使用 console.log
        console.error("发生未知错误！");
        console.log(err);
      });
  })
</script>

<style scoped>
</style>