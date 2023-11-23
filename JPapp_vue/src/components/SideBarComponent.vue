<template>
<!--  学生导航栏-->
  <div v-show="role === 'student'" class="student">
    <el-aside class="el-side" width="200px">
      <el-menu router active-text-color="#00c3ff" class="el-menu-vertical-demo" >
        <a class="logo">
          <img :src="logo" alt="logo" class="image">
          <h1 class="header_1">简评</h1>
        </a>
        <el-menu-item index="/studentHome" :route="{ name: 'StudentInfoPage' }">
          <template #title>
            <el-icon><user /></el-icon>个人信息
          </template>
        </el-menu-item>
        <el-menu-item index="/viewHomework" :route="{ name: 'ViewHomework' }">
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
        <a class="logo">
          <img :src="logo" alt="logo" class="image">
          <h1 class="header_1">简评</h1>
        </a>
        <el-menu-item index="/teacherHome" :route="{ name: 'TeacherInfoPage' }">
          <template #title>
          <el-icon><user /></el-icon>个人信息
          </template>
        </el-menu-item>
        <el-menu-item index="/viewHomework" :route="{ name: 'ViewHomework' }">
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
        <a class="logo">
          <img :src="logo" alt="logo" class="image">
          <h1 class="header_1">简评</h1>
        </a>
        <el-menu-item index="/StuManagement" :route="{ name: 'StuManagement' }">
          <template #title>
            <div>
              <span>学生管理</span>
            </div>
          </template>
        </el-menu-item>
        <el-menu-item index="/TeaManagement" :route="{ name: 'TeaManagement' }">
          <template #title>
          <div>
            <span>老师管理</span>
          </div>
          </template>
        </el-menu-item >
        <el-menu-item index="/CouManagement" :route="{ name: 'CouManagement' }">
          <template #title>
          <div>
            <span>课程管理</span>
          </div>
          </template>
        </el-menu-item>
      </el-menu>
    </el-aside>
  </div>
</template>

<script>
import {mapState} from "vuex";
import logo from "@/assets/logo.png";

export default {
  data(){
    return{
      logo:logo
    }
  },

  computed:{
    ...mapState(['role'])
  },
  methods:{
    gotoPage(pageName){
      this.$router.push({path:pageName})
    }
  }
}



</script>

<style scoped>
/*整个侧边栏背景颜色*/
.el-side {
  background-color: rgba(101, 75, 65, 0.86);
  height: 100vh;
  width: 200px;
}

/*侧边菜单背景颜色*/
.el-menu {
  background-color: rgba(194, 193, 145, 0.87);
  width: auto;
}

/*侧边栏菜单项的背景颜色和字体颜色*/
.el-menu-item {
  background-color: rgba(101, 75, 65, 0.86);
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

.header_1{
  height: 50px;
  margin-top: 25px;
  font-size: 35px;
  background-image: -webkit-linear-gradient(
      left,
      rgb(42, 134, 141),
      rgba(121, 120, 82, 0.86) 20%,
      #3498db 40%,
      #e74c3c 60%,
      #09ff009a 80%,
      rgba(82, 196, 204, 0.281) 100%
  );
  -webkit-text-fill-color: transparent;
  -webkit-background-clip: text;
  -webkit-background-size: 200% 100%;
  -webkit-animation: masked-animation 4s linear infinite;
}

.logo {
  display: flex;
  justify-content: center; /* 新添加的属性，水平方向居中对齐 */
  border-bottom: 1px solid #f0f0f0;
}

.image {
  width: 70px; /* 根据实际Logo尺寸调整 */
  height: 70px; /* 保持宽高比例 */
  margin-top: 17px;
  margin-right: 10px; /* 调整Logo和文字之间的间距 */
}

</style>