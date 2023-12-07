<template>
  <el-container class="layout-container-demo" style="height: 850px">
    <SideBar></SideBar>

    <el-container>
      <el-header style="text-align: right; font-size: 12px">
        <header-component></header-component>
      </el-header>

      <el-main>
        <router-view></router-view>
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
  width: 100%;
  height: 100%;
}

.layout-container-demo .el-header {
  height: 70px;
  width: 100%;
  padding: 0;
  border-bottom: 1px solid #e5e7eb;
}

.layout-container-demo .el-main {
  padding: 0 0 30px 0;
  height: 100%;
  width: 100%;
  overflow: auto;
}

</style>