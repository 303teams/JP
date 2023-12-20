<template>
  <div>
    <el-dialog title="重复列表" :close-on-click-modal="false" :lock-scroll="false" v-model="DetailsDialogVis">
      <el-table :data="tableData" style="width: 100%">
        <el-table-column label="学号" prop="id" width="180"/>
        <el-table-column label="姓名" prop="name" width="150"/>
        <el-table-column label="重复率" prop="" width="150"/>
      </el-table>
    </el-dialog>
  </div>
</template>

<script setup>
import {defineExpose, ref} from "vue";
import http from "@/api/http";

const DetailsDialogVis = ref(false);
const tableData = ref([]);

const openDetailDialog = (value) =>{
  DetailsDialogVis.value = true;
  fetchData(value);
};

const fetchData = (value) => {
  const data = {
    contentID: value,
  }
  http.getCheckList(data)
      .then((res) => {
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

defineExpose({
  openDetailDialog
})
</script>