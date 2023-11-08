<template>
  <div class="header-container">
    <div class="l-content">

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
            <el-dropdown-item>修改密码</el-dropdown-item>
            <el-dropdown-item>注销账号</el-dropdown-item>
            <el-dropdown-item>退出系统</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import male from "@/assets/male.png";
import female from "@/assets/female.png";
import {mapState} from "vuex";

export default {
  data(){
    return{
      male:male,
      female:female
    }
  },

  computed:{
    ...mapState(['sex'])
  }
}
</script>

<style scoped>
.header-container {
  background-color: #000000;
  height: 80px;
  display: flex; /* 使用 Flexbox 布局 */
  justify-content: space-between; /* 将 "l-content" 和 "r-content" 分散对齐 */
}

.l-content {
  /* 样式 "l-content"， */
}

.r-content {
  /* 样式 "r-content"，并使其靠在右侧 */
  display: flex;
  align-items: center; /* 垂直居中 */
}
</style>