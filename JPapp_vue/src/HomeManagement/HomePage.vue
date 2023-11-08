<template>
  <div class="common-layout">
    <el-container class="home-container">
      <!--侧边栏-->
      <SideBar></SideBar>
      <el-container>
        <el-header class="el-header">
          <header-component></header-component>
        </el-header>
        <!--头部-->
        <!--面包屑-->
        <!--          <el-breadcrumb separator="/">-->
        <!--            <el-breadcrumb-item v-show="role === 'administrator'" :to="{ path: '/adminHome' }">管理员首页</el-breadcrumb-item>-->
        <!--            <el-breadcrumb-item v-show="role === 'teacher'" :to="{ path: '/teacherHome' }">教师首页</el-breadcrumb-item>-->
        <!--            <el-breadcrumb-item v-show="role === 'student'" :to="{ path: '/studentHome' }">学生首页</el-breadcrumb-item>-->
        <!--            <el-breadcrumb-item>待改</el-breadcrumb-item>-->
        <!--            <el-breadcrumb-item>待改</el-breadcrumb-item>-->
        <!--          </el-breadcrumb>-->
        <!--下拉菜单-->
        <!--          <el-dropdown class="el-dropdown">-->
        <!--            <span class="el-dropdown-link">-->
        <!--              <el-avatar :size="40" src="src/assets/头像.jpg"/>-->
        <!--              <el-icon class="el-icon&#45;&#45;right">-->
        <!--                <arrow-down/>-->
        <!--              </el-icon>-->
        <!--            </span>-->
        <!--            <template #dropdown>-->
        <!--              <el-dropdown-menu>-->
        <!--                <el-dropdown-item>{{ showNumber }}</el-dropdown-item>-->
        <!--                <el-dropdown-item @click="changePassword">修改密码</el-dropdown-item>-->
        <!--                <el-dropdown-item @click="deleteAccount">注销账号</el-dropdown-item>-->
        <!--                <el-dropdown-item @click="logout">退出系统</el-dropdown-item>-->
        <!--              </el-dropdown-menu>-->
        <!--            </template>-->
        <!--          </el-dropdown>-->
        <el-main class = "main_page">
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<script>

import SideBar from "./SideBar.vue";
import HeaderComponent from "@/HomeManagement/HeaderComponent.vue";
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
    changeIsCollapse() {
      this.isCollapse = !this.isCollapse;
    }
  }
}

</script>

<style scoped>
.el-header{
  padding:0;
}

.home-container{
  height: 100%;
}

.main_page{
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
  height: 100%;
  width: 100%;
}

</style>