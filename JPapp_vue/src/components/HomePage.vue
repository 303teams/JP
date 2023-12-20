<template>
  <el-container class="layout-container-demo" style="height: 100vh">
    <SideBar></SideBar>

    <el-container>
      <el-header style="text-align: right; font-size: 12px">
        <header-component></header-component>
      </el-header>

      <el-main>
        <router-view class="app-main-height"/>
      </el-main>
    </el-container>
  </el-container>
</template>


<script setup>
import SideBar from "./SideBarComponent.vue";
import HeaderComponent from "@/components/HeaderComponent.vue";

</script>

<style scoped>
body {
  overflow-x: hidden;
}

.layout-container-demo {
  width: 100vw;
  height: 100vh;
}

.app-main-height {
  min-height: 83vh;
}

.layout-container-demo .el-header {
  height: 8vh;
  padding: 0;
  border-bottom: 1px solid #e5e7eb;
}

.layout-container-demo .el-main {
  background: #f6f8f9;
  padding: 20px;
  height: 92vh;
  width: 85vw;
  overflow: auto;
}

</style>