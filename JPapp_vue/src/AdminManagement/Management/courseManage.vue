<template>
  <div class="main-page">
    <div class="search-delete-add">
      <div>
        <el-button type="primary" size="large" @click="handleAdd">
          <el-icon><plus/></el-icon>添加
        </el-button>
        <el-button type="danger" size="large" @click="handleDelete">
          <el-icon><delete/></el-icon>批量删除
        </el-button>
      </div>
      <div class="search-container">
        <div class="search_input">
          <el-input v-model="search" size="large" placeholder="输入课程号或者课程名进行搜索" />
        </div>
        <el-button type="primary" size="large" class="search_button" @click="clickSearch">
          <el-icon style="vertical-align: middle">
            <Search />
          </el-icon>
          <span style="vertical-align: middle"> 查询 </span>
        </el-button>
      </div>
    </div>
    <div class="table-data">
      <el-table :data="filterTableData" style="width: 100%" @selection-change="handleSelectionChange">
        <el-table-column type="selection"/>
        <el-table-column label="课程号" prop="cno" sortable/>
        <el-table-column label="课程名" prop="cname"/>
        <el-table-column label="授课教师" prop="tname" />
        <el-table-column align="center" label="操作">
          <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)"
          >编辑</el-button
          >
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
              v-model:current-page="currentPage"
              v-model:page-size="pageSize"
              :page-sizes="[2, 5, 10, 15, 30, 50, 100]"
              background
              layout="total, sizes, prev, pager, next"
              :total="filteredData.length"
          />
        </div>
      </el-config-provider>

    </div>

  </div>

  <el-dialog title="添加课程" :close-on-click-modal="false" :lock-scroll="false" v-model="AddDialogVis" @close="resetData" width="40%">
    <el-form ref="Ref" label-width="80px" :model="form" :rules="rules">
      <el-form-item label="课程号" prop="cno">
        <el-input v-model="form.cno" autocomplete="off" />
      </el-form-item>
      <el-form-item label="课程名" prop="cname">
        <el-input v-model.trim="form.cname" autocomplete="off" />
      </el-form-item>
    </el-form>
    <div class="dialog-footer">
      <el-button @click="resetData">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>

  <el-dialog title="更改授课教师" :close-on-click-modal="false" :lock-scroll="false" v-model="EditDialogVis" @close="resetEditData" width="40%">
    <el-select v-model="new_teacher" placeholder="请选择一个教师">
      <el-option
          v-for="item in teachers"
          :key="item.id"
          :label="item.name"
          :value="item.id"
      />
    </el-select>
    <div class="dialog-footer">
      <el-button @click="resetEditData">取 消</el-button>
      <el-button type="primary" @click="HandleSubmit">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script setup>
import {computed, onMounted, reactive, ref, watch} from 'vue'
import {Delete} from "@element-plus/icons-vue";
import http from "@/api/http";
import zhCn from "element-plus/es/locale/lang/zh-cn";
import {ElConfigProvider, ElMessage, ElMessageBox} from "element-plus";

const tableData = reactive({data:[]});
const filteredData = ref([]);
const multipleSelection = ref([]);
const currentPage = ref(1); // 从第一页开始
const pageSize = ref(15); //每页展示多少条数据
const search = ref('')
const AddDialogVis = ref(false);
const Ref = ref();
const EditDialogVis = ref(false);
const new_teacher= ref('');
const chosenCno= ref('');
const form = reactive({
  cno: '',
  cname:'',
})

const rules = reactive({
  cno: [{ required: true, trigger: 'blur', message: '课程号不能为空' },
    {
      message: '请输入5-10位数字或大小写字母组成的课程号',
      pattern: /^[a-zA-Z0-9]{4,10}$/,
      trigger: ['blur'],
    },],
  cname: [{ required: true, trigger: 'blur', message: '课程名不能为空' }],
});

const resetData = () => {
  AddDialogVis.value = false;
  Ref.value.resetFields();
  Ref.value.clearValidate();
}

const resetEditData = () => {
  new_teacher.value='';
  EditDialogVis.value = false;
}

const HandleSubmit = () =>{
  if(new_teacher.value === '') {
    ElMessage.warning("请选择一个教师!");
  }else{
    const data = {
      id: new_teacher.value,
      cno: chosenCno.value
    }
    http.updateTeacher(data).then(res =>{
      if(res.data.code === 200){
        ElMessage.success("更改成功！")
        resetEditData();
        fetchData();
      }else{
        ElMessage.error("更改失败:" + res.data.msg);
      }
    })
        .catch(err => {
          console.log(err);
        });
  }
}

const save =() =>{
  Ref.value.validate((valid) => {
    if (valid) {
      http.addCourse(form).then(res =>{
        if(res.data.code === 200){
          ElMessage.success("添加成功！")
          resetData();
          fetchData();
        }else{
          ElMessage.error("添加失败:" + res.data.msg);
        }
      })
          .catch(err => {
            console.log(err);
          });
    } else {
      console.log('error submit!!');
      return false;
    }
  });

};

// 将表格中的数据按pageSize切片
const filterTableData = computed(() =>
    filteredData.value.slice(
        (currentPage.value - 1) * pageSize.value,
        currentPage.value * pageSize.value
    )
);

watch( search, () => {
  if(search.value === ''){
    clickSearch();
  }
});

const clickSearch = () => {
  filteredData.value = tableData.data.filter(
      (data) =>
          !search.value ||
          data.cno.toLowerCase().includes(search.value.toLowerCase())||
          data.cname.toLowerCase().includes(search.value.toLowerCase())
  );
};

const handleSelectionChange = (val) => {
  multipleSelection.value = val;
};

const handleAdd = () =>{
  AddDialogVis.value = true;
}

const handleDelete = (row) => {
  if (row.cno) {
    ElMessageBox.confirm('该操作无法撤销，所有与该门课相关的信息将被删除！确定要删除这门课程吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }).then(() => {
      // 用户点击了确定后的逻辑
      const data = {
        cnos: row.cno,
      }
      http.deleteCourse(data).then((res) => {
        if (res.data.code === 200) {
          ElMessage.success("删除成功");
          fetchData();
          console.log(res)
        } else {
          ElMessage.error("获取信息失败:" + res.data.msg);
        }
      })
          .catch((err) => {
            console.error("发生未知错误！");
            console.log(err);
          });
    }).catch(() => {
    });
  } else {
    if (multipleSelection.value.length > 0) {
      ElMessageBox.confirm('确定要删除这些课程吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        // 用户点击了确定后的逻辑
        const data = {
          cnos: multipleSelection.value.map((item) => item.cno).join()
        }
        http.deleteCourse(data).then((res) => {
          if (res.data.code === 200) {
            ElMessage.success("删除成功");
            fetchData();
            console.log(res)
          } else {
            ElMessage.error("删除失败:" + res.data.msg);
          }
        })
            .catch((err) => {
              console.error("发生未知错误！");
              console.log(err);
            });
      }).catch(() => {
      });
    }else {
      ElMessage.warning("未选中任何行!");
    }
  }
};

const teachers= ref([]);
const handleEdit = (row) =>{
  chosenCno.value = row.cno;
  const data = {
    cno: chosenCno.value,
  }
  http.getTeacher(data).then(res =>{
    if(res.data.code === 200){
      teachers.value = res.data.data;
      EditDialogVis.value = true;
    }else{
      ElMessage.error("获取信息失败:" + res.data.msg);
    }
  })
      .catch(err => {
        console.log(err);
      });
}
const fetchData = () => {
  http.getAllCourse()
      .then(res => {
        if(res.data.code === 200){
          tableData.data = res.data.data;
          clickSearch();
        }else{
          window.alert("获取信息失败:" + res.data.msg);
        }
      }).catch(err => {
    console.log(err);
  })


}

onMounted(() => {
  fetchData();
})

</script>

<style scoped>
.main-page {
  background: #fff;
  border-radius: 4px;
  overflow: hidden;
  padding: 20px;
}

.search-delete-add{
  display: flex;
  justify-content: space-between;
  padding: 10px;
}

.search-container {
  display: flex;
  width: auto;
  margin-bottom: 10px;
}

.search_input{
  width: 350px;
  margin-right: 10px;
}

.table-data{
  display: flex;
  flex-direction: column;
  align-items: center;
}

.demo-pagination-block{
  margin-top: 20px;
}

.dialog-footer{
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 50px;
}
</style>