<template>
  <div class="info_main">
    <el-card class="box-card">
      <el-descriptions class="margin-top" title="简介" :column="1" border>
        <el-descriptions-item label="账号">{{ id }}</el-descriptions-item>
        <el-descriptions-item label="名字">{{ name }}</el-descriptions-item>
        <el-descriptions-item label="邮箱">{{ email }}</el-descriptions-item>
      </el-descriptions>
    </el-card>
  </div>
</template>


<script setup>
import {computed} from "vue";
import { useStore } from 'vuex';


const store = useStore();


const id = computed(() => store.state.id);
const name = computed(() => store.state.name);
const email = computed(() => store.state.email);
</script>

<style scoped>
.info_main {
  display: flex;
  justify-content: center;
  align-items: center;
  background: url("../../assets/Info.png") no-repeat center/cover;
}

.box-card {
  width: 500px;
  height: 350px;
  background-color: rgba(240, 240, 240, 0.44);
  padding: 20px;
  box-sizing: border-box;
}

</style>