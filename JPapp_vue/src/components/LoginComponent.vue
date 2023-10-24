<template>
  <div class = "login" style = "height: 100vh; display: flex; align-items: center; justify-content: center; background-color: #2c3e50">
    <div style = "display: flex; background-color: white; height:50%; width: 50%; border-radius: 5px; box-shadow: 10px 10px 5px #888888; overflow: hidden">
      <div style= "flex: 1; display: flex; align-items: center">
        <img src="@/assets/sanguosha.png" alt="" style="width: 100%">
      </div>
        <div style = "flex: 1; display: flex; align-items: center; justify-content: center">
          <el-form ref="form" :label-position="right" label-width="80px" :model="items" :rules="rules">
            <h3>欢迎登录!</h3>
            <el-form-item label="用户名:" label-width="5em" prop="username">
              <el-input prefix-icon="user" v-model="items.username" placeholder="请输入用户名"></el-input>
            </el-form-item>
            <el-form-item label="密 码:" label-width="5em" prop="password">
              <el-input prefix-icon="lock" v-model="items.password" placeholder="请输入密码"></el-input>
            </el-form-item>
            <el-radio-group v-model="items.radio" @change = "clickChange">
              <el-radio label="1">管理员</el-radio>
              <el-radio label="2">学生</el-radio>
              <el-radio label="3">老师</el-radio>
            </el-radio-group>
            <el-form-item>
              <el-button type="primary" style = "width: 80%; margin: 15px" @click="login">登录</el-button>
            </el-form-item>
            <div style="flex: 1; font-size: 12px;">
              <span style="letter-spacing: 2px;">密码忘记了？点此处</span><span style="color: #4682B4; cursor: pointer">找回密码</span>
            </div>
          </el-form>

        </div>
    </div>

  </div>
</template>

<script>
// import qs from 'qs'

import {right} from "core-js/internals/array-reduce";

export default {

  name: "LoginComponent",
  data() {
    return {
      items: {
        username: '',
        password: '',
        radio: '2',
      },
      rules: {
        username: [
          {required: true, message: "请输入用户名", trigger: "blur"},
        ],
        password: [
          {required: true, message: "请输入密码", trigger: "blur"},
        ],
      },
    }
  },
  methods: {
    right,
    clickChange: function () {
      console.log(this.radio);
    },
    login: function () {
      this.$refs.form.validate((valid) => {
        if (valid) {
          console.log("开始登录！")
          let vm = this;
          console.log("开始发送请求：" + vm.items.username + " " + vm.items.password);
          this.axios({
            url: 'http://localhost:8081/user/login',
            method: 'post',
            data: {
              'username': vm.items.username,
              'password': vm.items.password
            },
            // transformResquest: [function (data) {
            //     return qs.stringify(data);
            //   }],
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded'
              // 'Content-Type': 'application/json'
            }
          }).then(res => {
            // 登录成功
            console.log(111);
            if (res.data.code == 200) {
              this.$message.success("登录成功！欢迎" + res.data.data.username);
              setTimeout(() => {
                this.$router.push('/home');
              }, 1000)
            } else {
              // 登录不成功 提示错误信息
              // console.log("请求：" + vm.items.username + " " + vm.items.password);
              this.$message.warning("登录失败:" + res.data.msg)
            }
          }).catch(err => {
            this.$message.error("发生未知错误！");
            console.log(err);
          })
        }
      })
    }
  }
}
</script>

<style scoped>
.login {
  margin: 0;
  padding: 0;
  border: 0
}

</style>