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


<script>

import SideBar from "./SideBarComponent.vue";
import HeaderComponent from "@/components/HeaderComponent.vue";
export default {
  components: { SideBar , HeaderComponent},
  data() {
    return {
      isCollapse: false,
      isChange: false,
      isDelete: false,
    }
  },
  methods: {
  }
}

</script>

<style scoped>
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
  height: 100%;
  width: 100%;
}

</style>