<template>
  <div>
    <el-dialog title="修改个人信息"
               :close-on-click-modal="false"
               v-model="dialogVisible"
               :lock-scroll="false"
               width="60%"
               :before-close="handleClose">
      <div style="flex: 1; display: flex; align-items: center; justify-content: center">
        <el-form :model="TeaForm" :rules="rules" ref="TeaFormRef" label-width="150px">
          <div class="updateinfo">
            <el-form-item label="名字" prop="name">
              <el-input v-model="TeaForm.name"></el-input>
            </el-form-item>
            <el-form-item label="性别" prop="sex">
              <el-radio-group v-model="TeaForm.sex">
                <el-radio label="男">男</el-radio>
                <el-radio label="女">女</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="年龄" prop="age">
              <el-input
                  style="width: 200px"
                  v-model="TeaForm.age"
                  type="text"
                  maxlength="2"
                  @input="handleInput" />
            </el-form-item>

          </div>
        </el-form>
      </div>

      <template #footer>
      <span class="dialog-footer">
          <el-button @click="handleClose">取 消</el-button>
          <el-button type="primary" @click="submitForm">提 交</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { defineExpose, reactive, ref } from 'vue';
import qs from 'qs';
import { useStore } from 'vuex';
import { ElMessage } from "element-plus";
import http from "@/api/http";

const dialogVisible = ref(false);
const TeaFormRef = ref();
const TeaForm = reactive({
  name: "",
  sex: "",
  age: Number,
});
const rules = reactive({
  name: [
    { required: true, message: "名字不能为空", trigger: "blur" },
  ],
  sex: [
    { required: true, message: "性别不能为空", trigger: "blur" },
  ],
  age: [
    { required: true, message: "年龄不能为空", trigger: "blur" },
  ],
});

const store = useStore();

const handleInput = (e) => {
  let value = e.replace(/[^\d]/g, ""); // 只能输入数字
  value = value.replace(/^0+(\d)/, "$1"); // 第一位0开头，0后面为数字，则过滤掉，取后面的数字
  value = value.replace(/(\d{2})\d*/, '$1') // 最多保留15位整数
  TeaForm.age = value
};

const load = () => {
  Object.assign(TeaForm, store.state);
};

const open = () => {
  dialogVisible.value = true;
  load();
};

const handleClose = () => {
  dialogVisible.value = false;
};

const submitForm = () => {
  // 验证表单是否合法
  TeaFormRef.value.validate((valid) => {
    if (valid) {
      // 把form对象的数据转换成URL编码的格式
      const data = qs.stringify(TeaForm);
      http.teaChangeInfo(data).then(res => {
        // 根据返回的数据来判断请求的结果
        if (res.data.code === 200) {
          // 修改成功，显示成功提示信息
          ElMessage.success('修改成功');
          // 使用Object.assign来复制form对象的值到Vuex的状态上
          Object.assign(store.state, TeaForm);
          // 调用mutation来更新状态
          store.commit('updateInfo', store.state);
          // 关闭对话框
          handleClose();
        } else {
          // 修改失败，显示失败提示信息
          ElMessage.error('修改失败：' + res.data.msg);
        }
      }).catch(err => {
        // 发生未知错误，显示错误提示信息
        ElMessage.error("发生未知错误！");
        console.log(err);
      })
    }
  });
};

defineExpose({
  open
});
</script>

<style scoped>
.updateinfo {
  overflow: auto;
}
</style>
