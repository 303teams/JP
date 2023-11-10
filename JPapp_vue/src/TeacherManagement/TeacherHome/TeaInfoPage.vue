<template>
  <div>
    <el-card class="box-card">
      <el-descriptions class="margin-top" title="简介" :column="1" border>
        <el-descriptions-item label="账号">{{ id }}</el-descriptions-item>
        <el-descriptions-item label="名字">{{ name }}</el-descriptions-item>
        <el-descriptions-item label="性别">{{ sex }}</el-descriptions-item>
        <el-descriptions-item label="邮箱">{{ email }}</el-descriptions-item>
        <el-descriptions-item label="年龄">{{ age }}</el-descriptions-item>
      </el-descriptions>
      <el-row class="button-row">
        <el-col :span="24">
          <el-button style="margin-top: 100px" type="primary" size="large" @click="Modifyinfo">修改</el-button>
        </el-col>
      </el-row>
    </el-card>
    <TeaModifyInfo ref="dia" @flesh="reload"></TeaModifyInfo>
  </div>
</template>


<script>
import {mapState} from "vuex";
import TeaModifyInfo from "@/TeacherManagement/TeacherHome/TeaModifyInfo.vue";

export default {
  components: {TeaModifyInfo},
  name: "InfoPage",

  data() {
    return {};
  },
  methods: {
    Modifyinfo() {
      this.$refs.dia.open();
    },
  },

  computed:{
     ...mapState(['id', 'name', 'sex', 'email', 'age'])
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