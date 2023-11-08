<template>
  <div class="common-layout">
    <el-aside class="el-side" width="200px">
      <el-menu router active-text-color="#00c3ff" class="el-menu-vertical-demo" >
        <el-menu-item index="/studenthome" :route="{ name: 'StudentInfoPage' }">
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
</template>

<script>
export default {
  props: ['isCollapse'],
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