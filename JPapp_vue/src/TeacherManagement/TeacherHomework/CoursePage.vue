<template>
  <div class="common-layout">
    <el-container>
      <el-header class="CourseHeader">
        <div class="el-menu-item" href="#">作业列表</div>
        <div class="el-menu-item" href="#">学生列表</div>
        <div class="el-menu-item" href="#">互评列表</div>
      </el-header>
      <el-main><router-view></router-view></el-main>
    </el-container>
  </div>
</template>
<script setup>
</script>

<style>
.CourseHeader {
  display: flex;
  justify-content: space-around;
  align-items: center;
  height: 70px;
  width: 100%;
  padding: 0;
  border-bottom: 1px solid #e5e7eb;
}


</style>