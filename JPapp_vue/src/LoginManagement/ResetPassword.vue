<template>
  <div>
    <!--修改密码的对话框-->
    <el-dialog title="修改密码" :close-on-click-modal="false" :lock-scroll="false" v-model="resetPasswordDialogVis" @close="resetData" width="40%">
      <div style = "flex: 1; display: flex; align-items: center; justify-content: center">
        <el-form :model="resetPasswordForm" ref="ResetPasswordRef" label-width="80px" :rules="passwordResetRules">
          <el-form-item label="新密码" prop="newPassword">
            <el-input
                show-password
                @keydown.enter="changePassword"
                v-model="resetPasswordForm.newPassword"
                style="width: 200px"
                placeholder="请输入新密码"
                type="password"/>
          </el-form-item>
          <el-form-item label="确认密码" prop="confirmPassword">
            <el-input
                show-password
                @keydown.enter="changePassword"
                v-model="resetPasswordForm.confirmPassword"
                style="width: 200px"
                placeholder="确认新密码"
                type="password"/>
          </el-form-item>
        </el-form>
      </div>
      <span class="dialog-footer">
        <el-button @click="resetData">取消</el-button>
        <el-button type="primary" @click="changePassword">确认</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script setup>
import {reactive, ref, defineExpose, defineProps} from "vue";
import http from "@/api/http";
import {ElMessage} from "element-plus";

const props = defineProps(['username']);
const resetPasswordDialogVis = ref(false);
const ResetPasswordRef = ref();
const resetPasswordForm = reactive({
  newPassword: '',
  confirmPassword: '',
});
const validatePass2 = (rule, value, callback) => {
  if (value === '') {
    // 验证第二次是否输入密码
    callback(new Error('请再次输入密码'));
  } else if (value !== resetPasswordForm.newPassword) {
    // 验证两次密码是否一致
    callback(new Error('两次输入密码不一致!'));
  } else {
    callback();
  }
};


const passwordResetRules = reactive({
  newPassword: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 7, message: '新密码长度必须大于6位', trigger: 'blur' },
  ],
  confirmPassword: [{ required: true, validator: validatePass2, trigger: 'blur' }],
});

const changePassword = () => {
  ResetPasswordRef.value.validate((valid) => {
    if (valid) {
      const data = {
        username: props.username,
        password: resetPasswordForm.newPassword,
      };

      console.log(props.username)
      http.changePassword(data)
          .then((res) => {
            if (res.data.code === 200) {
              ElMessage.success('密码修改成功！');
              resetPasswordDialogVis.value = false;
            } else {
              ElMessage.warning(`密码修改失败:${res.data.msg}`);
            }
          })
          .catch((err) => {
            ElMessage.error('发生未知错误！');
            console.log(err);
          });
    }
  });
};

const resetData = () =>{
  resetPasswordForm.newPassword = '';
  resetPasswordForm.confirmPassword = '';
  resetPasswordDialogVis.value = false;
  ResetPasswordRef.value.clearValidate();
};

const openResetPasswordDialog = () => {
  resetPasswordDialogVis.value = true;
};

defineExpose({
  openResetPasswordDialog,
});
</script>