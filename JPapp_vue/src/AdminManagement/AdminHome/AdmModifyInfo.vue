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
export default {
  name: "AdmModifyInfo"
}
</script>

<style scoped>

</style>