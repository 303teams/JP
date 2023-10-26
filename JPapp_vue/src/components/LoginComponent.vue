<template>
  <div class = "login" style = "height: 100vh; display: flex; align-items: center; justify-content: center; background-color: #2c3e50">
    <div style = "display: flex; background-color: white; height:50%; width: 50%; border-radius: 5px; box-shadow: 10px 10px 5px #888888; overflow: hidden">
      <div style= "flex: 1; display: flex; align-items: center">
        <img src="@/assets/sanguosha.png" alt="" style="width: 100%">
      </div>
        <div style = "flex: 1; display: flex; align-items: center; justify-content: center">
          <el-form ref="LoginRef" :label-position="right" label-width="80px" :model="user" :rules="rules">
            <h3>欢迎登录!</h3>
            <el-form-item label="用户名:" label-width="5em" prop="username">
              <el-input prefix-icon="user" v-model="user.username" placeholder="请输入用户名"></el-input>
            </el-form-item>
            <el-form-item label="密 码:" label-width="5em" prop="password">
              <el-input prefix-icon="lock" v-model="user.password" placeholder="请输入密码"></el-input>
            </el-form-item>
            <el-radio-group v-model="user.role" @change = "clickChange">
              <el-radio label="1">管理员</el-radio>
              <el-radio label="2">学生</el-radio>
              <el-radio label="3">老师</el-radio>
            </el-radio-group>
            <el-button type="primary" style = "width: 80%; margin: 15px" @click="login">登录</el-button>
            <div style="flex: 1; font-size: 12px;">
              <span style="letter-spacing: 2px;">密码忘记了？点此处</span>
              <span style="color: #4682B4; cursor: pointer" @click="EmailVerify">
              找回密码
            </span>
            </div>
          </el-form>
        </div>
    </div>

    <!--输入用户名、邮箱和验证码的对话框-->
    <el-dialog title = "忘记密码" v-model = "EmailVerifyDialogVis" :width="'40%'">
      <div style = "flex: 1; display: flex; align-items: center; justify-content: center">
        <el-form ref="UserEmailVerifyRef" label-width="80px" style = "padding-right: 20px" :model="UserEmailVerifyForm" :rules="EmailRules">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="UserEmailVerifyForm.username" placeholder="请输入用户名"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="UserEmailVerifyForm.email" placeholder="请输入邮箱"></el-input>
          </el-form-item>
          <el-form-item label="验证码" prop="code">
            <div style="display: flex; align-items: center;">
              <el-input v-model="UserEmailVerifyForm.code" placeholder="请输入验证码" style="flex: 1;"></el-input>
              <el-button type="text" @click="sendVerificationCode" :disabled="disableSend" style="margin-left: 15px">发送验证码</el-button>
            </div>
          </el-form-item>
        </el-form>
      </div>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="EmailVerifyDialogVis = false">取 消</el-button>
        <el-button type="primary" @click="confirmEmail">确 认</el-button>
      </span>
      </template>
    </el-dialog>

    <!--修改密码的对话框-->
    <el-dialog title="修改密码" v-model="resetPasswordDialogVis" :width="'40%'">
      <div style = "flex: 1; display: flex; align-items: center; justify-content: center">
        <el-form :model="resetPasswordForm" ref="ResetPasswordRef" label-width="80px" :rules="passwordResetRules">
          <el-form-item label="新密码" prop="newPassword">
            <el-input v-model="resetPasswordForm.newPassword" placeholder="请输入新密码" type="password"></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="confirmPassword">
            <el-input v-model="resetPasswordForm.confirmPassword" placeholder="确认新密码" type="password"></el-input>
          </el-form-item>
        </el-form>
        </div>
        <span class="dialog-footer">
        <el-button @click="resetPasswordDialogVis = false">取消</el-button>
        <el-button type="primary" @click="changePassword">确认</el-button>
        </span>
    </el-dialog>
  </div>
</template>

<script>
// import qs from 'qs'

import {right} from "core-js/internals/array-reduce";

export default {

  name: "LoginComponent",
  data() {
    return {
      disableSend: false,
      user: {              // 登录表单
        username: '',
        password: '',
        role: '2',
      },
      EmailVerifyDialogVis: false,
      UserEmailVerifyForm: {     // 忘记密码表单
        username: '',
        email: '',
        code: '',
      },
      resetPasswordDialogVis: false,
      resetPasswordForm: {
        newPassword: '',                // 修改密码表单
        confirmPassword: '',
      },
      rules: {
        username: [
          {required: true, message: "请输入用户名", trigger: "blur"},
        ],
        password: [
          {required: true, message: "请输入密码", trigger: "blur"},
        ],
      },
      EmailRules: {
        username: [
          {required: true, message: "请输入用户名", trigger: "blur"},
        ],
        email: [
          {required: true, message: "请输入邮箱", trigger: "blur"},
        ],
        code: [
          {required: true, message: "请输入验证码", trigger: "blur"},
        ],
      },
      passwordResetRules: {
        newPassword: [{ required: true, message: '请输入新密码', trigger: 'blur' }],
        confirmPassword: [
          { required: true, message: '请输入确认密码', trigger: 'blur' },
          {
            validator: (rule, value, callback) => {
              if (value !== this.resetPasswordForm.newPassword) {
                callback(new Error('两次密码输入不一致'));
              } else {
                callback();
              }
            },
            trigger: 'blur',
          },
        ],
      },
    }
  },
  methods: {
    right,
    clickChange: function () {
      console.log(this.role);
    },
    login: function () {
      this.$refs.form.validate((valid) => {
        if (valid) {
          console.log("开始登录！")
          let vm = this;
          console.log("开始发送请求：" + vm.user.username + " " + vm.user.password);
          this.axios({
            url: 'http://localhost:8081/user/login',
            method: 'post',
            data: {
              'username': vm.user.username,
              'password': vm.user.password,
              'role': vm.user.role
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
    },

    EmailVerify: function () {
      if(this.$refs.UserEmailVerifyRef !== undefined){
        this.$refs.UserEmailVerifyRef.resetFields();
      }
      this.EmailVerifyDialogVis = true;
    },
    changePassword: function () {
    },
    sendVerificationCode: function () {
      this.disableSend = true
      this.axios.post('/email', { email: this.UserEmailVerifyForm.email }).then(res => {
        if (res.data.status === 'success') {
          this.$message.success(res.data.msg)
        } else {
          this.$message.error(res.data.msg)
        }
        this.disableSend = false
      }).catch(error => {
        console.log(error)
        this.$message.error('发送验证码失败')
        this.disableSend = false
      })
    }
    },
    confirmEmail: function () {
      if(this.$refs.ResetPasswordRef !== undefined){
        this.$refs.ResetPasswordRef.resetFields();
      }

      this.EmailVerifyDialogVis = false;
      this.resetPasswordDialogVis = true;
    },

}
</script>

<style scoped>
.login {
  margin: 0;
  padding: 0;
  border: 0
}

</style>