<template>
  <div class="header-container">
    <div class="l-content">
      <el-text class="topInfo" v-if="role === 'student'">学生端</el-text>
      <el-text class="topInfo" v-else-if="role === 'teacher'">教师端</el-text>
      <el-text class="topInfo" v-else>管理员端</el-text>
    </div>
    <div class="r-content">
      <!--下拉菜单-->
      <el-dropdown class="el-dropdown">
            <span class="el-dropdown-link">
              <el-avatar :size="40" :src="sex === '男' ? male : female"/>
              <el-icon class="el-icon--right">
                <arrow-down/>
              </el-icon>
            </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="ModifyPass">修改密码</el-dropdown-item>
            <el-dropdown-item @click="ModifyEmail">修改邮箱</el-dropdown-item>
            <el-dropdown-item @click="LeaveSystem">退出系统</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
  <ModifyPassword ref="dia1" @flesh="reload"></ModifyPassword>
  <ModifyEmail ref="dia2" @flesh="reload"></ModifyEmail>
</template>

<script>
import male from "@/assets/male.png";
import female from "@/assets/female.png";
import ModifyPassword from "@/components/ModiPassComponent.vue";
import ModifyEmail from "@/components/ModiEmailComponent.vue";
import {mapState} from "vuex";

export default {
  components: { ModifyPassword, ModifyEmail},
  data(){
    return{
      male:male,
      female:female
    }
  },

  methods: {
    ModifyPass() {
      this.$refs.dia1.open();
    },

    ModifyEmail() {
      this.$refs.dia2.open();
    },

    LeaveSystem(){
      localStorage.removeItem('token');
      this.$store.commit('RESET_STATE');
      this.$router.push('/login');
    }
  },

  computed:{
    ...mapState(['role', 'sex'])
  }
}
</script>

<style scoped>
.header-container {
  background-color: #442607;
  height: 60px;
  display: flex; /* 使用 Flexbox 布局 */
  justify-content: space-between; /* 将 "l-content" 和 "r-content" 分散对齐 */
}

.l-content {
  /* 样式 "l-content"， */
  display: flex;
  align-items: center; /* 垂直居中 */
}

.r-content {
  /* 样式 "r-content"，并使其靠在右侧 */
  display: flex;
  align-items: center; /* 垂直居中 */
}

.topInfo {
  color: #ffffff;
  font-size: 25px;
  margin-left: 40px;
}

.el-dropdown{
  margin-right: 20px;
}
</style>