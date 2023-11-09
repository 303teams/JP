<template>
  <div style = "flex: 1; display: flex; align-items: center; justify-content: center">
    <!--修改密码的对话框-->
    <el-dialog title="修改密码" :close-on-click-modal="false" v-model="modifyPasswordDialogVis" :label-position="right" :width="'40%'">
      <div style = "flex: 1; display: flex; align-items: center; justify-content: center">
        <el-form :model="modifyPasswordForm" ref="ModifyPasswordRef" label-width="80px" :rules="rules">
          <el-form-item label="旧密码" prop="oldPassword">
            <el-input show-password v-model="modifyPasswordForm.oldPassword" style="width: 200px" placeholder="请输入旧密码" type="password"></el-input>
          </el-form-item>
          <el-form-item label="新密码" prop="newPassword">
            <el-input show-password v-model="modifyPasswordForm.newPassword" style="width: 200px" placeholder="请输入新密码" type="password"></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="confirmPassword">
            <el-input show-password v-model="modifyPasswordForm.confirmPassword" style="width: 200px" placeholder="请再次确认新密码" type="password"></el-input>
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
import {right} from "core-js/internals/array-reduce";

export default {
  data() {
    const validatePass2 = (rule, value, callback) => {
      if (value === "") {
        // 验证第二次是否输入密码
        callback(new Error("请再次输入密码"));
      } else if (value !== this.modifyPasswordForm.newPassword) {
        // 验证两次密码是否一致
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };

    return {
      modifyPasswordDialogVis: false,
      modifyPasswordForm: {
        oldPassword: "",
        newPassword: "",
        confirmPassword: "",
      },
      rules: {
        oldPassword: [{ required: true, message: '请输入旧密码', trigger: 'blur' }],
        newPassword: [{ required: true, message: '请输入新密码', trigger: 'blur' }],
        confirmPassword: [
          { required: true, validator: validatePass2, trigger: 'blur' },
        ],
      },
    };
  },
  methods: {
    right,
    open() {
      if(this.$refs.ResetPasswordRef !== undefined){
        this.$refs.ResetPasswordRef.resetFields();
      }
      this.modifyPasswordDialogVis = true;
    },
    handleClose() {
      this.modifyPasswordDialogVis = false;
      this.$emit("flesh");
    },

    submitForm() {
      if(this.modifyPasswordForm.newPassword === this.modifyPasswordForm.oldPassword){
        this.$message.error('新密码不能与旧密码相同');
        this.modifyPasswordForm.newPassword = "";
        this.modifyPasswordForm.confirmPassword = "";
        return false;
      }
      // 验证表单是否合法
      this.$refs.ModifyPasswordRef.validate((valid) => {
        if (valid) {
          let vm = this;

          // 把form对象的数据转换成URL编码的格式
          let data = qs.stringify(vm.modifyPasswordForm);
          this.axios({
            url: 'http://localhost:8081/student/modifypassword',
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
