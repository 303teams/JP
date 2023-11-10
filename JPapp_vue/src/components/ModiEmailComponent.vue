<template>
  <div style = "flex: 1; display: flex; align-items: center; justify-content: center">
    <!--修改邮箱的对话框-->
    <el-dialog title="修改邮箱" :close-on-click-modal="false" v-model="modifyEmailDialogVis" :width="'40%'">
      <div style = "flex: 1; display: flex; align-items: center; justify-content: center">
        <el-form :model="modifyEmailForm" ref="ModifyEmailRef" label-width="80px" :rules="rules">
          <el-form-item label="新邮箱" prop="email">
            <el-input  v-model="modifyEmailForm.email" style="width: 200px" placeholder="请输入邮箱"></el-input>
          </el-form-item>
          <el-form-item label="验证码" prop="code">
            <div style="display: flex; align-items: center;">
              <el-input v-model="modifyEmailForm.code" placeholder="请输入验证码" style="flex: 1;width: 200px"></el-input>
              <el-button v-if="codeShow" type="text" @click="SendEmail" style="margin-left: 15px; width: 80px;">
                发送验证码
              </el-button>
              <el-button v-else type="text" disabled style="margin-left: 15px; width: 80px;">{{count}}s</el-button>
            </div>
          </el-form-item>
        </el-form>
      </div>
      <span class="dialog-footer">
        <el-button @click="handleClose">取消</el-button>
        <el-button type="primary" @click="submitForm">确认</el-button>
        </span>
    </el-dialog>
  </div>
</template>

<script>

import qs from 'qs';
import {mapState} from "vuex";

export default {
  data() {

    return {
      codeShow: true,
      timer: null,
      count: '',
      modifyEmailDialogVis: false,
      modifyEmailForm: {
        email: "",
        code: "",
      },
      rules: {
        email: [{ required: true, type: "email", trigger: 'blur' }],
        code: [{ required: true, message: '请输入新密码', trigger: 'blur' }],
      },
    };
  },
  methods: {
    open() {
      if(this.$refs.ModifyEmailRef !== undefined){
        this.$refs.ModifyEmailRef.resetFields();
      }
      this.modifyEmailDialogVis = true;
    },
    handleClose() {
      this.modifyEmailDialogVis = false;
      this.$emit("flesh");
    },

    //验证码倒计时
    CountDown: function () {
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
    },

    SendEmail: function () {
      if(this.modifyEmailForm.email === ""){
        this.$message.warning("请填写邮箱！");
        return;
      }
      //邮箱格式验证
      if(!/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(this.modifyEmailForm.email)){
        this.$message.warning("请输入正确的邮箱！");
        return;
      }
      let vm = this;
      //发送验证码
      this.axios.post('http://localhost:8081/user/sendEmail',
          {
            // 'username': vm.modifyEmailForm.username,
            'email': vm.modifyEmailForm.email
          },
          {headers: {
              'Content-Type': 'application/x-www-form-urlencoded',
              'token': localStorage.getItem('token')// 添加token到请求头中
            }}).then(res => {
        if (res.data.code === 200) {
          this.$message.success("验证码发送成功！");
          this.CountDown();
        } else {
          this.$message.warning("验证码发送失败:" + res.data.msg)
        }
      }).catch(err => {
        this.$message.error("发生未知错误！");
        console.log(err);
      });

    },

    submitForm() {
      // 验证表单是否合法
      this.$refs.ModifyEmailRef.validate((valid) => {
        if (valid) {
          let vm = this;

          // 把form对象的数据转换成URL编码的格式
          let data = qs.stringify(vm.modifyEmailForm);
          this.axios({
            url: 'http://localhost:8081/user/modifyEmail',
            method: 'post',
            data: data,
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded',
              'token': localStorage.getItem('token')// 添加token到请求头中
            }
          }).then(res => {
            // 根据返回的数据来判断请求的结果
            if (res.data.code === 200) {
              // 修改成功，显示成功提示信息
              this.$message.success('修改成功');
              // 关闭对话框
              this.handleClose()
            } else {
              // 修改失败，显示失败提示信息
              this.$message.error('修改失败：' + res.data.message);
            }
          }).catch(err => {
            // 发生未知错误，显示错误提示信息
            this.$message.error("发生未知错误！");
            console.log(err);
          })
        } else {
          // 表单不合法，提示错误
          this.$message.error('请检查表单是否填写正确')
          return false;
        }
      });
    }
  },

  computed:{
    ...mapState([ 'id', 'name', 'sex', 'email', 'age'])
  },
};
</script>

<style scoped>
.updateinfo {
  height: 350px;
  overflow: auto;
}

</style>
