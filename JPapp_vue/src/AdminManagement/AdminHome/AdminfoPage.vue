<template>
  <div>
    <el-card class="box-card">
      <el-descriptions class="margin-top" title="简介" :column="1" border>
        <el-descriptions-item label="账号">{{ id }}</el-descriptions-item>
        <el-descriptions-item label="名字">{{ name }}</el-descriptions-item>
        <el-descriptions-item label="性别">{{ sex }}</el-descriptions-item>
        <el-descriptions-item label="年龄">{{ age }}</el-descriptions-item>
      </el-descriptions>
      <el-button style="margin-top: 100px" type="primary" size="large" @click="Modifyinfo">修改</el-button>
    </el-card>
    <AdmModifyInfo ref="dia" @flesh="reload"></AdmModifyInfo>
  </div>
</template>

<script>
import {mapState} from "vuex";
import AdmModifyInfo from "@/AdminManagement/AdminHome/AdmModifyInfo.vue";
export default {
  components: {AdmModifyInfo},
  name: "AdminfoPage",
  data() {
    return {};
  },
  methods: {
    Modifyinfo() {
      this.$refs.dia.open();
    },
  },

  computed:{
    ...mapState(['id', 'name', 'sex', 'age'])
  },
};

</script>

<style scoped>

</style>