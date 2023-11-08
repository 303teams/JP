<template>
  <div>
    <el-card class="box-card">
      <el-descriptions class="margin-top" title="简介" :column="1" border>
        <el-descriptions-item label="账户名">2112</el-descriptions-item>
        <el-descriptions-item label="昵称">我</el-descriptions-item>
        <el-descriptions-item label="年龄">13</el-descriptions-item>
        <el-descriptions-item label="性别">
          <el-tag size="small">{{ sex }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="邮箱">
          {{ email }}
        </el-descriptions-item>
      </el-descriptions>
      <el-row class="button-row">
        <el-col :span="24">
          <el-button type="primary" size="small">操作</el-button>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>


<script>
export default {
  name: "InfoPage",

  data() {
    return {
      id: Number,
      username: String,
      age: Number,
      email: String,
      mobilePhoneNumber: String,
      sex: String,
    };
  },
  mounted() {
    this.load();
  },
  methods: {
    load() {
    },
  },
};
</script>

<style scoped>
.box-card {
    width: 1200px; /* 卡片宽度占满父元素(main) */
    height: 700px; /* 卡片高度占满父元素(main) */
    background-color: #f0f0f0; /* 卡片背景颜色 */
    padding: 20px; /* 可以根据需要设置内边距 */
    box-sizing: border-box; /* 内边距和边框不会增加卡片的实际宽度 */
}

</style>
