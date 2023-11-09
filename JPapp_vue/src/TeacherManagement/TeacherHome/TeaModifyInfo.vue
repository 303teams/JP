<template>
  <div>
    <el-dialog title="修改个人信息" :close-on-click-modal="false" v-model="dialogVisible" width="60%" :before-close="handleClose">
      <div style = "flex: 1; display: flex; align-items: center; justify-content: center">
        <el-form :model="form" :rules="rules" ref="form" label-width="150px">
          <div class="updateinfo">
            <el-form-item label="名字" prop="name">
              <el-input v-model="form.name"></el-input>
            </el-form-item>
            <el-form-item label="性别" prop="sex">
              <el-radio-group v-model="form.sex">
                <el-radio label="男">男</el-radio>
                <el-radio label="女">女</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="年龄" prop="age">
              <el-input v-model="form.age"></el-input>
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

<script>

import qs from 'qs';
import {mapState} from "vuex";

export default {
  data() {
    return {
      dialogVisible: false,
      form: {
        id: Number,
        name: "",
        sex: "",
        age: Number,
      },
      rules: {
        name: [
          { required: true, message: "名字不能为空", trigger: "blur" },
        ],
        sex: [
          { required: true, message: "性别不能为空", trigger: "blur" },
        ],
        age: [
          { required: true, message: "年龄不能为空", trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    load(){
      Object.assign(this.form, this.$store.state)
    },
    open() {
      this.dialogVisible = true;
      this.load()
    },
    handleClose() {
      this.dialogVisible = false;
      this.$emit("flesh");
    },

    submitForm() {
      // 验证表单是否合法
      this.$refs.form.validate((valid) => {
        if (valid) {
          let vm = this;
          // 把form对象的数据转换成URL编码的格式
          let data = qs.stringify(vm.form);
          this.axios({
            url: 'http://localhost:8081/student/modifyInfo',
            method: 'post',
            data: data,
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded',
              'token': localStorage.getItem('token') // 添加token到请求头中
            }
          }).then(res => {
            // 根据返回的数据来判断请求的结果
            if (res.data.code === 200) {
              // 修改成功，显示成功提示信息
              this.$message.success('修改成功');
              // 使用Object.assign来复制form对象的值到Vuex的状态上
              Object.assign(this.$store.state, this.form)
              // 调用mutation来更新状态
              this.$store.commit('updateInfo', this.$store.state)
              // 关闭对话框
              this.handleClose()
            } else {
              // 修改失败，显示失败提示信息
              this.$message.error('修改失败：' + res.data.msg);
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
    ...mapState([ 'id', 'name', 'sex', 'age'])
  },
};
</script>

<style scoped>
.updateinfo {
  overflow: auto;
}

</style>
