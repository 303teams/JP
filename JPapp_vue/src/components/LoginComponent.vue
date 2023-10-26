<template>
  <div class="login">
    <el-form ref="form" :label-position="right" label-width="80px" :model="items" :rules="rules">
      <h3>欢迎登录!</h3>
      <el-form-item label="用户名" prop="username">
        <el-input v-model="items.username" placeholder="请输入用户名"></el-input>
      </el-form-item>

      <el-form-item label="密码" prop="password">
        <el-input v-model="items.password" placeholder="请输入密码"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="login">登录</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="toReg">注册</el-button>
      </el-form-item>

    </el-form>
  </div>
</template>

<script>
// import qs from 'qs'

export default {

  name: "LoginComponent",
  data() {
    return {
      items: {
        username: '',
        password: '',
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
    login: function () {
      this.$refs.form.validate((valid) => {
        if (valid) {
          console.log("开始登录！")
          let vm = this;
          console.log("开始发送请求：" + vm.items.username + " " + vm.items.password);
          this.axios({
            url: 'http://localhost:8080/user/login',
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
                this.$router.push('/market');
              }, 2000)
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
<<<<<<< Updated upstream
    toReg() {
      this.$router.push("/welcome/register");
    }
=======

    EmailVerify: function () {
      if(this.$refs.UserEmailVerifyRef !== undefined){
        this.$refs.UserEmailVerifyRef.resetFields();
      }
      this.EmailVerifyDialogVis = true;
    },
    changePassword: function () {
    },
    sendVerificationCode: function () {
      if(!this.UserEmailVerifyForm.email){
        this.$message.warning("请输入邮箱！");
        return;
      }
// //邮箱格式验证
//       if(!/^[a-z0-9A-Z]+[- | a-z0-9A-Z . _]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}$/.test(this.UserEmailVerifyForm.email)){
//         this.$message.warning("请输入正确的邮箱！");
//         return;
//       }

//发送验证码
      this.request.get("/user/email/", + this.UserEmailVerifyForm.email).then(res => {
        if(res.data.code === 200){
          this.$message.success("验证码发送成功！");
        }else{
          this.$message.error("验证码发送失败！");
        }
      })


    },
    confirmEmail: function () {
      if(this.$refs.ResetPasswordRef !== undefined){
        this.$refs.ResetPasswordRef.resetFields();
      }

      this.EmailVerifyDialogVis = false;
      this.resetPasswordDialogVis = true;
    },
>>>>>>> Stashed changes
  }
}
</script>

<style scoped>
.login {
  width: 40%;
  margin: 0 auto
}

</style>