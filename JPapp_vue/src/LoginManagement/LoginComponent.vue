<template>
  <div class = "background">
    <div class = "login">
      <div style = "flex: 1; display: flex; align-items: center; justify-content: center">
        <el-form ref="LoginRef" :label-position="right" label-width="80px" :model="user" :rules="rules">
          <h3 style="color:white">欢迎登录!</h3>
          <el-form-item label="用户名:" label-width="5em" prop="username">
            <el-input prefix-icon="user" v-model="user.username" placeholder="请输入用户名"></el-input>
          </el-form-item>
          <el-form-item label="密 码:" label-width="5em" prop="password">
            <el-input type="password" prefix-icon="lock" v-model="user.password" placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-radio-group v-model="user.role" @change = "clickChange">
            <el-radio label="admin">管理员</el-radio>
            <el-radio label="student">学生</el-radio>
            <el-radio label="teacher">老师</el-radio>
          </el-radio-group>
          <el-button type="primary" style = "width: 80%; margin: 15px" @click="login">登录</el-button>
          <div style="flex: 1; font-size: 12px;">
            <span style="letter-spacing: 2px;color:white">密码忘记了？点此处</span>
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
            <el-input prefix-icon="user" v-model="UserEmailVerifyForm.username" placeholder="请输入用户名"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input prefix-icon="message" v-model="UserEmailVerifyForm.email" placeholder="请输入邮箱"></el-input>
          </el-form-item>
          <el-form-item label="验证码" prop="code">
            <div style="display: flex; align-items: center;">
              <el-input v-model="UserEmailVerifyForm.code" placeholder="请输入验证码" style="flex: 1;"></el-input>
              <el-button v-if="codeShow" type="text" @click="sendVerificationCode" style="margin-left: 15px; width: 80px;">
                发送验证码
              </el-button>
              <el-button v-else type="text" disabled style="margin-left: 15px; width: 80px;">{{count}}s</el-button>
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
// import axios from "axios";

export default {

  name: "LoginComponent",
  data() {
    const validatePass2 = (rule, value, callback) => {
      if (value === "") {
        // 验证第二次是否输入密码
        callback(new Error("请再次输入密码"));
      } else if (value !== this.ruleForm.password) {
        // 验证两次密码是否一致
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };

    return {
      codeShow: true,
      timer: null,
      count: '',
      user: {              // 登录表单
        username: '',
        password: '',
        role: 'student',
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
          { required: true, validator: validatePass2, message: '请输入确认密码', trigger: 'blur' },
        ],
      },
    }
  },
  methods: {
    right,
    clickChange: function () {
      console.log(this.role);
    },
    //登录
    login: function () {
      this.$refs.LoginRef.validate((valid) => {
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
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded'
            }
          }).then(res => {
            // 登录成功
            console.log(res.data);
            if (res.data.code === 200) {
              setTimeout(() => {
                localStorage.setItem("token",res.data.data.token);
                this.$store.commit('setRole', this.user.role);
                this.$store.commit('setId', res.data.data.id);
                this.$store.commit('setName', res.data.data.name);
                this.$store.commit('setEmail', res.data.data.email);
                this.$store.commit('setAge', res.data.data.age);
                if(this.user.role === "student"){
                  this.$store.commit('setSex', res.data.data.sex);
                  this.$router.push('/studentHome');
                }else if(this.user.role === "teacher"){
                  this.$router.push('/teacherHome');
                }else{
                  this.$router.push('/adminHome');
                }
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

    //弹出忘记密码窗口
    EmailVerify: function () {
      if(this.$refs.UserEmailVerifyRef !== undefined){
        this.$refs.UserEmailVerifyRef.resetFields();
      }
      this.EmailVerifyDialogVis = true;
    },
    //修改密码
    changePassword: function () {
      this.$refs.ResetPasswordRef.validate((valid) => {
        if (valid) {
          let vm = this;
          this.axios.post('http://localhost:8081/user/change',
              {
                'password': vm.resetPasswordForm.newPassword
              },
              {headers: {
                  'Content-Type': 'application/x-www-form-urlencoded'
                }}).then(res => {
            if (res.data.code === 200) {
              this.$message.success("密码修改成功！");
              this.resetPasswordDialogVis = false;
            } else {
              this.$message.warning("密码修改失败:" + res.data.msg)
            }
          }).catch(err => {
            this.$message.error("发生未知错误！");
            console.log(err);
          });
        }
      })
    },
    //发送验证码
    sendVerificationCode: function () {
      if(this.UserEmailVerifyForm.email === ""||this.UserEmailVerifyForm.username === ""){
        this.$message.warning("请填写完整！");
        return;
      }
      //邮箱格式验证
      if(!/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(this.UserEmailVerifyForm.email)){

        this.$message.warning("请输入正确的邮箱！");
        return;
      }

      let vm = this;
      //发送验证码
      this.axios.post('http://localhost:8081/user/email',
          {
            'username': vm.UserEmailVerifyForm.username,
            'email': vm.UserEmailVerifyForm.email
          },
          {headers: {
              'Content-Type': 'application/x-www-form-urlencoded'
            }}).then(res => {
        if (res.data.code === 200) {
          this.$message.success("验证码发送成功！");
          const TIME_COUNT = 60;
          if (!this.timer) {
            this.count = TIME_COUNT;
            this.codeShow = false;
            this.timer = setInterval(() => {
              if (this.count > 0 && this.count <= TIME_COUNT) {
                this.count--;
              } else {
                this.codeShow = true;
                clearInterval(this.timer);
                this.timer = null;
              }
            }, 1000)
          }
        } else {
          this.$message.warning("验证码发送失败:" + res.data.msg)
        }
      }).catch(err => {
        this.$message.error("发生未知错误！");
        console.log(err);
      });

    },
    //邮箱验证后
    confirmEmail: function () {
      if(this.$refs.ResetPasswordRef !== undefined){
        this.$refs.ResetPasswordRef.resetFields();
      }
      let vm = this;
      this.axios.post('http://localhost:8081/user/verify', {'code': vm.UserEmailVerifyForm.code},
          {headers: {
              'Content-Type': 'application/x-www-form-urlencoded'
            }}).then(res => {
        if (res.data.code === 200) {
          this.EmailVerifyDialogVis = false;
          this.resetPasswordDialogVis = true;
        } else {
          this.$message.warning("验证失败:" + res.data.msg)
        }
      }).catch(err => {
        this.$message.error("发生未知错误！");
        console.log(err);
      });

    },
  }
}
</script>

<style scoped>

/*背景图片*/
.background{
  background:url("../assets/background.jpg");
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
  background-color:rgba(29,45,59,0.7);
  height:50%;
  width: 50%;
  border-radius: 5px;
  box-shadow: 5px 5px 5px rgb(99,122,119);
  overflow: hidden;
  margin: 0;
  padding: 0;
  border: 0
}

</style>