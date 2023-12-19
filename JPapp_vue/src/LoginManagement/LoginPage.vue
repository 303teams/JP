<template>
  <div class = "background">
    <div class = "login">
      <div style = "flex: 1; display: flex; align-items: center; justify-content: center">
        <el-form ref="LoginRef" label-width="80px" :model="user" :rules="rules">
          <div style="display:flex;justify-content:start;">
            <img :src="logo" class="image">
            <p class="header_1">简评</p>
          </div>

          <h3 style="color:white">欢迎登录!</h3>
          <el-form-item label="用户名:" label-width="5em" prop="username">
            <el-input
                prefix-icon="user"
                @keydown.enter="login"
                v-model="user.username"
                placeholder="请输入用户名"/>
          </el-form-item>
          <el-form-item label="密 码:" label-width="5em" prop="password">
            <el-input
                show-password
                type="password"
                @keydown.enter="login"
                prefix-icon="lock"
                v-model="user.password"
                placeholder="请输入密码"
            />
          </el-form-item>
          <el-radio-group v-model="user.role">
            <el-radio label="admin">管理员</el-radio>
            <el-radio label="student">学生</el-radio>
            <el-radio label="teacher">老师</el-radio>
          </el-radio-group>
          <el-button type="primary" style = "width: 80%; margin: 15px" @click="login">登录</el-button>
          <div style="flex: 1; font-size: 12px;">
            <span style="letter-spacing: 2px;color:black">密码忘记了？点此处</span>
            <span style="color: #4682B4; cursor: pointer" @click="EmailVerify">
              找回密码
            </span>
          </div>
        </el-form>
      </div>
    </div>

    <forget-password ref="EmailVerifyRef"></forget-password>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { useStore } from 'vuex';
import http from '@/api/http';
import ForgetPassword from '@/LoginManagement/ForgetPassword.vue';
import {useRouter} from "vue-router";
import {ElMessage} from "element-plus";
import logo from "@/assets/logo.png";

const LoginRef = ref();
const EmailVerifyRef = ref();
const store = useStore();
const router = useRouter();
const user = reactive({
  username: '',
  password: '',
  role: 'student',
});

const rules = reactive({
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
});


const login = () => {
  LoginRef.value.validate((valid) => {
    if (valid) {
      console.log('开始登录！');
      const data = {
        username: user.username,
        password: user.password,
        role: user.role,
      };
      http.login(data)
          .then((res) => {
            console.log(res);
            if (res.data.code === 200) {
              setTimeout(() => {
                localStorage.setItem('token', res.data.data.token);
                localStorage.setItem('role', res.data.data.role);
                store.commit('setRole', user.role);
                store.commit('setId', res.data.data.id);
                store.commit('setName', res.data.data.name);
                store.commit('setEmail', res.data.data.email);
                store.commit('setAge', res.data.data.age);
                store.commit('setSex', res.data.data.sex);
                if (user.role === 'student') {
                  router.push('/studentHome');
                } else if (user.role === 'teacher') {
                  router.push('/teacherHome');
                } else {
                  router.push('/adminHome');
                }
              }, 1000);
            } else {
              ElMessage.warning(`登录失败:${res.data.msg}`);
            }
          })
          .catch((err) => {
            ElMessage.error('发生未知错误！');
            console.log(err);
          });
    }
  });
};

const EmailVerify = () => {
  EmailVerifyRef.value.openEmailVerifyDialog();
};

</script>


<style scoped>

/*背景图片*/
.background{
  background:url("../assets/1.jpg");
  width:100%;
  height:100%;
  display: flex;
  align-items: center;
  justify-content: center;
  position:fixed;
  background-size:100% 100%;
}

.login {
  display: flex;
  background-color: rgba(247, 250, 252, 0.6);
  height:50%;
  width: 50%;
  border-radius: 5px;
  box-shadow: 5px 5px 5px rgb(99, 122, 100);
  overflow: hidden;
  margin: 0;
  padding: 0;
  border: 0
}

.image{
  width: 120px;
  height: 120px;
  margin-left: 20px;
}

.header_1{
  height: 50px;
  margin-top: 35px;
  font-size: 43px;
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

</style>