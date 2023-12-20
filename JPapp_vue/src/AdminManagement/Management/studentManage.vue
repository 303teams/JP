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
          <el-input v-model="search" size="large" placeholder="输入学号或者名字进行搜索" />
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
        <el-table-column type="selection" width="55" />
        <el-table-column label="学号" prop="id"/>
        <el-table-column label="姓名" prop="name"/>
        <el-table-column label="性别" prop="sex"/>
        <el-table-column label="年龄" prop="age" show-overflow-tooltip/>
        <el-table-column label="密码" prop="password" show-overflow-tooltip/>
        <el-table-column label="邮箱" prop="email" show-overflow-tooltip/>
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

  <el-dialog title="添加学生" :close-on-click-modal="false" :lock-scroll="false" v-model="AddDialogVis" @close="resetData" width="40%">
    <el-form ref="Ref" label-width="80px" :model="form" :rules="rules">
      <el-form-item label="学号" prop="id">
        <el-input v-model.number="form.id" autocomplete="off" />
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input v-model.trim="form.name" autocomplete="off" />
      </el-form-item>
      <el-form-item label="性别" prop="sex">
        <el-radio-group v-model="form.sex">
          <el-radio label="男">男</el-radio>
          <el-radio label="女">女</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="年龄" prop="age">
        <el-input
            style="width: 200px"
            v-model="form.age"
            type="text"
            maxlength="2"
            @input="handleInput" />
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model.trim="form.password" autocomplete="off" type="password" />
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model.trim="form.email" autocomplete="off" />
      </el-form-item>
    </el-form>
    <div class="dialog-footer">
      <el-button @click="resetData">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>

</template>

<script setup>
import {computed, onMounted, reactive, ref} from 'vue'
import {Delete} from "@element-plus/icons-vue";
import http from "@/api/http";
import zhCn from "element-plus/es/locale/lang/zh-cn";
import {ElConfigProvider, ElMessage, ElMessageBox} from "element-plus";
import {useRouter} from "vue-router";

const tableData = reactive({data:[]});
const filteredData = ref([]);
const multipleSelection = ref([]);
const currentPage = ref(1); // 从第一页开始
const pageSize = ref(15); //每页展示多少条数据
const search = ref('')
const router = useRouter();
const AddDialogVis = ref(false);
const Ref = ref();
const form = reactive({
  id: '',
  name:'',
  sex:'男',
  age:'',
  password: '',
  email: '',
})

const rules = reactive({
  id: [{ required: true, trigger: 'blur', message: '学号不能为空' },
    { type: "number", message: "学号必须为整数" },],
  name: [{ required: true, trigger: 'blur', message: '姓名不能为空' }],
  sex: [{ required: true, trigger: 'blur', message: '性别不能为空' }],
  age:[{ required: true, trigger: 'blur', message: '年龄不能为空' }],
  password: [{ required: true, trigger: 'blur', message: '密码不能为空' },
    { min: 7, message: '密码长度必须大于6位', trigger: 'blur' },],
});

const handleInput = (e) => {
  let value = e.replace(/[^\d]/g, ""); // 只能输入数字
  value = value.replace(/^0+(\d)/, "$1"); // 第一位0开头，0后面为数字，则过滤掉，取后面的数字
  value = value.replace(/(\d{2})\d*/, '$1') // 最多保留15位整数
  form.age = value
};

const resetData = () => {
  AddDialogVis.value = false;
  Ref.value.resetFields();
  Ref.value.clearValidate();
}

const save =() =>{
  http.addStudent(form).then(res =>{
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
};

// 将表格中的数据按pageSize切片
const filterTableData = computed(() =>
    filteredData.value.slice(
        (currentPage.value - 1) * pageSize.value,
        currentPage.value * pageSize.value
    )
);

const clickSearch = () => {
  filteredData.value = tableData.data.filter(
      (data) =>
          !search.value ||
          data.id.toLowerCase().includes(search.value.toLowerCase())||
          data.name.toLowerCase().includes(search.value.toLowerCase())
  );
};

const handleSelectionChange = (val) => {
  multipleSelection.value = val;
};

const handleAdd = () =>{
  AddDialogVis.value = true;
}

const handleDelete = (row) => {
  if (row.id) {
    ElMessageBox.confirm('确定要删除这个学生吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }).then(() => {
      // 用户点击了确定后的逻辑
      const data = {
        ids: row.id,
      }
      http.deleteStudent(data).then((res) => {
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
      ElMessageBox.confirm('确定要删除这些学生吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        // 用户点击了确定后的逻辑
        const data = {
          ids: multipleSelection.value.map((item) => item.id).join()
        }
        http.deleteStudent(data).then((res) => {
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

const handleEdit = (row) =>{
  router.push({
    path: '/adminHome/EditStudent',
    state:{
      id:row.id,
      name:row.name,
      sex:row.sex,
      email:row.email,
      age:row.age
    }
  })
}
const fetchData = () => {
  http.getAllStudent()
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
</style>