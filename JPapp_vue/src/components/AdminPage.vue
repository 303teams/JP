<template>
  <div class="common-layout">
    <el-container>
      <el-header>Header</el-header>
      <el-container>
        <el-aside width="200px">
          <div>
            <el-menu router active-text-color="#00c3ff" class="el-menu-vertical-demo">
              <el-menu-item index="info" :route="{ name: 'InfoPage' }">
                <template #title>
                  <div>
                    <span>个人信息</span>
                  </div>
                </template>
              </el-menu-item>
              <el-menu-item>
                <template #title>
                  <div>
                    <span>作业</span>
                  </div>
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
          </div>
        </el-aside>
        <el-main>Main</el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>

</script>

<style scoped>

</style>