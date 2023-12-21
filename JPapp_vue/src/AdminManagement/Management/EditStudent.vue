<template>
  <div class="modify-main-page">
    <div style="display: flex; justify-content: space-between">
      <div class="basic-info">
        <el-form ref="InfoRef" :model="Info" :rules="rules">
          <el-form-item label="姓名:" label-width="5em" prop="new_name">
            <el-input v-model="Info.new_name" />
          </el-form-item>
          <el-form-item label="性别:" label-width="5em" prop="new_sex">
            <el-radio-group v-model="Info.new_sex">
              <el-radio label="男">男</el-radio>
              <el-radio label="女">女</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="年龄:" label-width="5em" prop="new_age">
            <el-input v-model.number="Info.new_age"/>
          </el-form-item>
          <el-form-item label="密码:" label-width="5em" prop="new_password">
            <el-input v-model="Info.new_password"/>
          </el-form-item>
          <el-form-item label="邮箱:" label-width="5em" prop="new_email">
            <el-input v-model="Info.new_email"/>
          </el-form-item>
        </el-form>
        <el-button style="margin-top: 20px" type="primary" @click="Modifyinfo">提交</el-button>
      </div>
      <div class="chosen-course">
        <div style="display: flex;font-size: 20px;margin-bottom: 20px">已选课程</div>
        <div class="basic-info">
          <el-table :data="filterTableData1" style="width: 100%">
            <el-table-column label="课程号" prop="cno" width="180"/>
            <el-table-column label="课程名称" prop="cname" width="150"/>
            <el-table-column label="授课教师" prop="teacherName" width="150"/>
            <el-table-column align="center" label="操作" width="150">
              <template #default="scope">
              <el-button
                  size="small"
                  type="danger"
                  @click="handleDelete(scope.row)"
              >删除</el-button
              >
              </template>
            </el-table-column>
          </el-table>

          <el-config-provider :locale="zhCn">
            <div class="demo-pagination-block">
              <el-pagination
                  v-model:current-page="currentPage1"
                  v-model:page-size="pageSize1"
                  :page-sizes="[2, 3, 5]"
                  background
                  layout="total, sizes, prev, pager, next"
                  :total="CourseList.length"
              />
            </div>
          </el-config-provider>
        </div>
      </div>
    </div>
    <div class="optional-course">
      <div class="chosen-course">
        <div style="display: flex;font-size: 20px;margin-bottom: 20px">可选课程</div>
        <div class="basic-info">
          <el-table :data="filterTableData2">
            <el-table-column label="课程号" prop="cno" width="180"/>
            <el-table-column label="课程名称" prop="cname" width="150"/>
            <el-table-column label="授课教师" prop="teacherName" width="150"/>
            <el-table-column align="center" label="添加选课" width="150">
              <template #default="scope">
              <el-button
                  size="small"
                  type="primary"
                  @click="handleAdd(scope.row)"
              >添加</el-button
              >
              </template>
            </el-table-column>
          </el-table>

          <el-config-provider :locale="zhCn">
            <div class="demo-pagination-block">
              <el-pagination
                  v-model:current-page="currentPage2"
                  v-model:page-size="pageSize2"
                  :page-sizes="[2, 3, 5]"
                  background
                  layout="total, sizes, prev, pager, next"
                  :total="UnCourseUnList.length"
              />
            </div>
          </el-config-provider>
        </div>
      </div>
    </div>
    <el-button style="margin-top: 100px" type="primary" @click="Back">返回</el-button>
  </div>
</template>

<script setup>
import {computed, onMounted, reactive, ref} from "vue";
import {ElConfigProvider, ElMessage, ElMessageBox} from "element-plus";
import http from "@/api/http";
import zhCn from "element-plus/es/locale/lang/zh-cn";
import {useRouter} from "vue-router";

const id = history.state.id;
const name = history.state.name;
const sex = history.state.sex;
const age = history.state.age;
const password = history.state.password
const email = history.state.email;
const CourseList= ref([]);
const UnCourseUnList= ref([]);
const currentPage1 = ref(1); // 从第一页开始
const pageSize1 = ref(5); //每页展示多少条数据
const currentPage2 = ref(1); // 从第一页开始
const pageSize2 = ref(5); //每页展示多少条数据
const InfoRef= ref();
const Info = reactive({
  new_id:'',
  new_name:'',
  new_sex:'',
  new_age:'',
  new_password:'',
  new_email:''
})
const router=useRouter()

const rules = reactive({
  new_name: [{ required: true, trigger: 'blur', message: '姓名不能为空' }],
  new_sex: [{ required: true, trigger: 'blur', message: '性别不能为空' }],
  new_age:[{ required: true, trigger: 'blur', message: '年龄不能为空' },
    { type: "number",min:1, max:99, message: "年龄必须为正整数且最多为两位" },],
  new_password: [{ required: true, trigger: 'blur', message: '密码不能为空' },
    { min: 7, message: '密码长度必须大于6位', trigger: 'blur' },],
});
// 将表格中的数据按pageSize切片
const filterTableData1 = computed(() =>
    CourseList.value.slice(
        (currentPage1.value - 1) * pageSize1.value,
        currentPage1.value * pageSize1.value
    )
);

const filterTableData2 = computed(() =>
    UnCourseUnList.value.slice(
        (currentPage2.value - 1) * pageSize2.value,
        currentPage2.value * pageSize2.value
    )
);



const initData = () =>{
  Info.new_id = parseInt(id);
  Info.new_name = name;
  Info.new_sex = sex;
  Info.new_age = age;
  Info.new_password=password;
  Info.new_email = email;
}

const Modifyinfo =() =>{
  InfoRef.value.validate((valid) =>{
    if(valid){
      ElMessageBox.confirm('确定要修改学生信息吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        http.modifyStudentInfo(Info).then(res =>{
          if(res.data.code === 200){
            ElMessage.success("修改成功！")
          }else{
            ElMessage.error("修改失败:" + res.data.msg);
          }
        }).catch(err => {
          console.log(err);
        });
      }).catch(()=>{
      });
    }else{
      ElMessage.error("请检查表单是否填写正确")
    }
  })
}

const handleDelete = (row) =>{
  ElMessageBox.confirm('确定要删除这门课程吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(() => {
    const data={
      id: id,
      cno: row.cno,
    }

    http.deleteStudentCourse(data).then(res =>{
      if(res.data.code === 200) {
        ElMessage.success("删除课程成功！")
        fetchData()
      }else{
        ElMessage.error("删除失败")
      }
    }).catch(err => {
      console.error("发送未知错误"+err)
    })
  }).catch(()=>{
  });

}

const handleAdd = (row) =>{
  ElMessageBox.confirm('确定要添加这门课程吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'info',
  }).then(() => {
    const data={
      id: id,
      cno: row.cno,
    }

    http.addStudentCourse(data).then(res =>{
      if(res.data.code === 200) {
        ElMessage.success("添加课程成功！")
        fetchData()
      }else{
        ElMessage.error("添加失败")
      }
    }).catch(err => {
      console.error("发送未知错误"+err)
    })
  }).catch(()=>{
  });
}
const fetchData = () => {
  const data = {
    id: id,
  }
  Promise.all([
    http.getStudentCourse(data),
    http.getStudentUnCourse(data)
  ]).then(([res1, res2]) =>{
    if (res1.data.code === 200) {
      console.log(res1)
      if(res1.data.data){
        CourseList.value = res1.data.data;
      }else{
        CourseList.value = [];
      }
    } else {
      ElMessage.error("获取信息失败:" + res1.data.msg);
    }
    if(res2.data.code === 200) {
      console.log(res2)
      if(res2.data.data){
        UnCourseUnList.value = res2.data.data;
      }else{
        UnCourseUnList.value = [];
      }
    }else{
      ElMessage.error("获取信息失败:" + res2.data.msg);
    }
  })
      .catch(err => {
        console.log(err);
      });
};

const Back = () =>{
  router.back()
}

onMounted(() => {
  initData();
  fetchData();
});
</script>

<style>
.modify-main-page {
  background: #fff;
  border-radius: 4px;
  overflow: hidden;
  padding: 20px;
}

.chosen-course{
  display: flex;
  flex-direction: column;
}

.basic-info {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.optional-course{
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
}

.demo-pagination-block{
  margin-top: 20px;
}
</style>