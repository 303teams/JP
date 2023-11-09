<template>
<!--  学生导航栏-->
  <div v-show="role === 'student'" class="student">
    <el-aside class="el-side" width="200px">
      <el-menu router active-text-color="#00c3ff" class="el-menu-vertical-demo" >
        <el-menu-item index="/studentHome" :route="{ name: 'StudentInfoPage' }">
          <template #title>
            <el-icon><user /></el-icon>个人信息
          </template>
        </el-menu-item>
        <el-menu-item>
          <template #title>
            <el-icon><message /></el-icon>作业
          </template>
        </el-menu-item>
        <el-menu-item>
          <template #title>
            <div>
              <span>分数</span>
            </div>
          </template>
        </el-menu-item>
        <el-menu-item>
          <template #title>
            <div>
              <span>其他</span>
            </div>
          </template>
        </el-menu-item>
      </el-menu>
    </el-aside>
  </div>

<!--  老师导航栏-->
  <div v-show="role === 'teacher'" class="teacher">
    <el-aside class="el-side" width="200px">
      <el-menu router active-text-color="#00c3ff" class="el-menu-vertical-demo" >
        <el-menu-item index="/studentHome" :route="{ name: 'StudentInfoPage' }">
          <template #title>
          <el-icon><user /></el-icon>个人信息
          </template>
        </el-menu-item>
        <el-menu-item>
          <template #title>
          <el-icon><message /></el-icon>作业
          </template>
        </el-menu-item>
        <el-menu-item>
          <template #title>
          <div>
            <span>学生列表</span>
          </div>
          </template>
        </el-menu-item>
        <el-menu-item>
          <template #title>
          <div>
            <span>其他</span>
          </div>
          </template>
        </el-menu-item>
      </el-menu>
    </el-aside>
  </div>

<!--  管理员导航栏-->
  <div v-show="role === 'admin'" class="admin">
    <el-aside class="el-side" width="200px">
      <el-menu router active-text-color="#00c3ff" class="el-menu-vertical-demo" >
        <el-menu-item>
          <template #title>
          <el-icon><message /></el-icon>学生管理
          </template>
        </el-menu-item>
        <el-menu-item>
          <template #title>
          <div>
            <span>老师管理</span>
          </div>
          </template>
        </el-menu-item>
        <el-menu-item>
          <template #title>
          <div>
            <span>成绩管理</span>
          </div>
          </template>
        </el-menu-item>
      </el-menu>
    </el-aside>
  </div>
</template>

<script>
import {mapState} from "vuex";

export default {
  props: ['isCollapse'],

  computed:{
    ...mapState(['role'])
  }
}


</script>

<style scoped>
/*整个侧边栏背景颜色*/
.el-side {
  background-color: #2c3e50;
  height: 100vh;
  width: 200px;
}

/*侧边菜单背景颜色*/
.el-menu {
  background-color: #2c3e50;
  width: auto;
}

/*侧边栏菜单项的背景颜色和字体颜色*/
.el-menu-item {
  background-color: #2c3e50;
  color: white; /* 设置字体颜色为白色 */
}

/*鼠标悬停时的背景颜色*/
.el-menu-item:hover {
  background-color: rgb(230, 230, 250);
}

/*侧边栏中的图标颜色*/
.el-menu-item el-icon {
  color: white; /* 设置图标颜色为白色 */
}

</style>