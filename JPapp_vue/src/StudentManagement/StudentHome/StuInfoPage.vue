<template>
  <div>
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
.box-card {
  width: 1200px; /* 卡片宽度占满父元素(main) */
  height: 754px; /* 卡片高度占满父元素(main) */
  background-color: #f0f0f0; /* 卡片背景颜色 */
  padding: 20px;
  box-sizing: border-box; /* 内边距和边框不会增加卡片的实际宽度 */
}

</style>