<template>
  <div>
    <el-card class="box-card">
      <el-descriptions class="margin-top" title="简介" :column="2" border>
        <template v-slot:extra>
          <el-button type="primary" size="small">操作</el-button>
        </template>
        <el-descriptions-item>
          <template v-slot:label>
            账户名
          </template>
          {2112}
        </el-descriptions-item >
        <el-descriptions-item>
          <template v-slot:label>
            昵称
          </template>
          {我}
        </el-descriptions-item>
        <el-descriptions-item >
          <template v-slot:label>
            年龄
          </template>
          {13}
        </el-descriptions-item>
        <el-descriptions-item>
          <template v-slot:label>
            性别
          </template>
          <el-tag size="small">{{ sex }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item>
          <template v-slot:label>
            邮箱
            Email
          </template>
          {{ email }}
        </el-descriptions-item>
      </el-descriptions>
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
  height: 6000px;
}

</style>
