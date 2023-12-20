<template>
  <div>
    <el-dialog title="查重名单" :close-on-click-modal="false" :lock-scroll="false" v-model="DetailsDialogVis">
      <el-table :data="tableData" style="width: 100%">
        <el-table-column label="学号" prop="sno"/>
        <el-table-column label="姓名" prop="sname"/>
        <el-table-column label="重复率" prop="similar"/>
        <el-table-column label="作业提交内容" align="center" width="150px">
          <template v-slot="scope">
          <el-button type="text" @click="DownloadCT(scope.row)">查看作业</el-button>
          </template>
        </el-table-column>
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

const DownloadCT = (row) =>{
  const data = {
    contentID: row.contentID,
  };
  http.downloadCT(data)
      .then(res => {
        console.log(res);
        const blob = new Blob([res.data], { type: 'application/octet-stream' });
        const blobUrl = URL.createObjectURL(blob);

        const fileName = ref('');
        // 提取 filename 的方法
        function extractFilename(contentDisposition) {
          const matches = contentDisposition.match(/filename="(.+?)"/);
          return matches ? decodeURIComponent(matches[1]) : null;
        }

        const contentDisposition = res.headers['content-disposition'];
        fileName.value = extractFilename(contentDisposition);

        const link = document.createElement('a');
        link.href = blobUrl;
        link.download = fileName.value;

        // 模拟点击下载
        link.click();

        // 释放 URL 对象
        URL.revokeObjectURL(blobUrl);
      })
      .catch(err => {
        console.error("发生未知错误！");
        console.log(err);
      });
}

defineExpose({
  openDetailDialog
})
</script>