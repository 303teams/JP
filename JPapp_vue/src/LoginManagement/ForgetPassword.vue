<template>
  <div>
    <!--输入用户名、邮箱和验证码的对话框-->
    <el-dialog title = "忘记密码" :close-on-click-modal="false" :lock-scroll="false" v-model="EmailVerifyDialogVis" @close="resetData" width="40%">
      <div style = "flex: 1; display: flex; align-items: center; justify-content: center">
        <el-form ref="UserEmailVerifyRef" label-width="80px" style = "padding-right: 20px" :model="UserEmailVerifyForm" :rules="EmailRules">
          <el-form-item label="用户名" prop="username">
            <el-input
                prefix-icon="user"
                @keydown.enter="confirmEmail"
                v-model="UserEmailVerifyForm.username"
                placeholder="请输入用户名"/>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input
                prefix-icon="message"
                @keydown.enter="confirmEmail"
                v-model="UserEmailVerifyForm.email"
                placeholder="请输入邮箱"/>
          </el-form-item>
          <el-form-item label="验证码" prop="code">
            <div style="display: flex; align-items: center;">
              <el-input
                  @keydown.enter="confirmEmail"
                  v-model="UserEmailVerifyForm.code"
                  placeholder="请输入验证码"
                  style="flex: 1;"/>
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
        <el-button @click="resetData">取 消</el-button>
        <el-button type="primary" @click="confirmEmail">确 认</el-button>
      </span>
      </template>
    </el-dialog>

    <reset-password ref="ResetRef"></reset-password>
  </div>

</template>

<script setup>
import http from "@/api/http";
import {reactive, ref, defineExpose} from "vue";
import {ElMessage} from "element-plus";
import ResetPassword from "@/LoginManagement/ResetPassword.vue";

const UserEmailVerifyRef = ref();
const EmailVerifyDialogVis = ref(false);
const ResetRef = ref();
const codeShow = ref(true);
const timer = ref(null);
const count = ref();
const UserEmailVerifyForm = ref({
  username: '',
  email: '',
  code: ''
});
const EmailRules = reactive({
  username: [
    {required: true, message: "请输入用户名", trigger: "blur"},
  ],
  email: [
    {required: true, type: "email", trigger: "blur",message: "请输入正确的邮箱"},
  ],
  code: [
    {required: true, message: "请输入验证码", trigger: "blur"},
  ],
});
//验证码倒计时
const CountDown = () =>{
  const TIME_COUNT = 60;

  if (!timer.value) {
    count.value = TIME_COUNT;
    codeShow.value = false;
    timer.value = setInterval(() => {
      if (count.value > 0 && count.value <= TIME_COUNT) {
        count.value--;
      } else {
        codeShow.value = true;
        clearInterval(timer.value);
        timer.value = null;
      }
    }, 1000)
  }
};

//发送验证码
const sendVerificationCode = () => {
  if(UserEmailVerifyForm.value.username === ""){
    ElMessage.warning("请填写用户名！");
    return;
  }

  if(UserEmailVerifyForm.value.email === "") {
    ElMessage.warning("请填写邮箱！");
    return;
  }
  //邮箱格式验证
  if(!/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(UserEmailVerifyForm.value.email)){

    ElMessage.warning("请输入正确的邮箱！");
    return;
  }

  const data = {
    'username': UserEmailVerifyForm.value.username,
    'email': UserEmailVerifyForm.value.email
  };

  //发送验证码
  http.sendCode(data).then(res => {
    if (res.data.code === 200) {
      ElMessage.success("验证码已发送")
      CountDown();
    } else {
      ElMessage.warning("验证码发送失败:" + res.data.msg)
    }
  }).catch(err => {
    ElMessage.error("发生未知错误！");
    console.log(err);
  });
};
const confirmEmail = () =>{
  UserEmailVerifyRef.value.validate((valid) => {
    if(valid){
      const data = {
        'username': UserEmailVerifyForm.value.username,
        'email': UserEmailVerifyForm.value.email,
        'code': UserEmailVerifyForm.value.code
      };
      http.confirmEmail(data).then(res => {
        if (res.data.code === 200) {
          EmailVerifyDialogVis.value = false;
          reset();
          ElMessage.success("验证成功！");
        } else {
          ElMessage.warning("验证失败:" + res.data.msg)
        }
      }).catch(err => {
        ElMessage.error("发生未知错误！");
        console.log(err);
      });
    }
  })
};

const openEmailVerifyDialog = () =>{
  EmailVerifyDialogVis.value = true;
};

const reset = () => {
  ResetRef.value.openResetPasswordDialog();
};

const resetData = () =>{
  UserEmailVerifyForm.value.username = '';
  UserEmailVerifyForm.value.email = '';
  UserEmailVerifyForm.value.code = '';
  EmailVerifyDialogVis.value = false;
  codeShow.value = true;
  clearInterval(timer.value);
  timer.value = null;
  UserEmailVerifyRef.value.clearValidate();
};

defineExpose({
  openEmailVerifyDialog
})
</script>