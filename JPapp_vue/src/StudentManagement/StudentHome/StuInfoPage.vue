<template>
  <div class="stu-info">
    <el-card class="box-card">
      <el-descriptions class="margin-top" title="简介" :column="1" border>
        <el-descriptions-item label="账号">{{ id }}</el-descriptions-item>
        <el-descriptions-item label="名字">{{ name }}</el-descriptions-item>
        <el-descriptions-item label="性别">{{ sex }}</el-descriptions-item>
        <el-descriptions-item label="邮箱">{{ email }}</el-descriptions-item>
        <el-descriptions-item label="年龄">{{ age }}</el-descriptions-item>
      </el-descriptions>
      <el-button style="margin-top: 100px" type="primary" size="large" @click="Modifyinfo">修改</el-button>
    </el-card>
    <StuModifyInfo ref="stuModifyInfoRef"></StuModifyInfo>
  </div>
</template>


<script setup>
import StuModifyInfo from "./StuModifyInfo.vue";
import {computed, ref} from "vue";
import { useStore } from 'vuex';

const stuModifyInfoRef = ref();
const store = useStore();
const Modifyinfo = () => {
  stuModifyInfoRef.value.open();
};

const id = computed(() => store.state.id);
const name = computed(() => store.state.name);
const sex = computed(() => store.state.sex);
const email = computed(() => store.state.email);
const age = computed(() => store.state.age);
</script>

<style scoped>
.stu-info{
  display: flex;
  justify-content: center;
  align-items: center;
}
.box-card {
  width: 500px;
  height: 350px;
  background-color: #f0f0f0;
  padding: 20px;
  box-sizing: border-box;
}

</style>