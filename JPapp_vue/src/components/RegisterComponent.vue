<template>
  <div class="register">
    <el-form ref="form" :label-position='right' label-width="80px" :model="items" :rules="rules">
      <h3>欢迎注册!</h3>
      <el-form-item label="用户名" prop="username">
        <el-input v-model="items.username" placeholder="请输入用户名"></el-input>
      </el-form-item>

      <el-form-item label="密码" prop="password">
        <el-input v-model="items.password" placeholder="请输入密码"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="register">注册</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="toLog">登录</el-button>
      </el-form-item>

    </el-form>
  </div>
</template>

<script>
export default {
  name: "RegisterComponent",
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
    register: function () {
      this.$refs.form.validate((valid) => {
        if (valid) {
          console.log("开始注册！")
          let vm = this;
          console.log("开始发送请求：" + vm.items.username + " " + vm.items.password);
          this.axios({
            url: 'http://localhost:8080/user/register',
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
              this.$message.success("注册成功！欢迎新用户" + this.items.username + " !");
              setTimeout(() => {
                this.$router.push('/welcome/login');
              }, 2000)
            } else {
              // 注册不成功 提示错误信息
              // console.log("请求：" + vm.items.username + " " + vm.items.password);
              this.$message.warning("注册失败:" + res.data.msg)
            }
          }).catch(err => {
            this.$message.error("发生未知错误！");
            console.log(err);
          })
        }
      })
    },
    toLog() {
      this.$router.push("/welcome/login");
    }
  }
}
</script>

<style scoped>

.register {
  width: 40%;
  margin: 0 auto
}

</style>