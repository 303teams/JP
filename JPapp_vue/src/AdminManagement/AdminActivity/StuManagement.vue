<template>
  <div>
    <el-table :data="filterTableData" style="width: 100%">
      <el-table-column label="id" prop="id" />
      <el-table-column label="姓名" prop="name" />
      <el-table-column label="性别" prop="sex" />
      <el-table-column label="邮箱" prop="email" />
      <el-table-column label="年龄" prop="age" />
      <el-table-column label="密码" prop="password" />
      <el-table-column align="right">
        <template #header>
          <el-input v-model="search" size="small" placeholder="输入关键字搜索" />
        </template>
        <el-button size="small" @click="handleSubmit">提交</el-button >
      </el-table-column>
    </el-table>

  </div>
</template>

<script>
export default {
  name: "StuManagement"
}
</script>

<style scoped>

</style>