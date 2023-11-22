<template>
  <div class="homeListMain" style="position: relative; display: flex; justify-content: center">
    <div class="base_title">
      <div class="title">课程作业</div>
    </div>
    <div class="main">
      <div class="search-container">
        <div class="search_input">
          <el-input v-model="search" size="large" placeholder="输入关键字搜索" />
        </div>
        <el-button size="large" class="search_button" @click="clickSearch">
          <el-icon style="vertical-align: middle">
            <Search />
          </el-icon>
          <span style="vertical-align: middle"> 查询 </span>
        </el-button>

        <el-button @click="uploadHomework" size="large" class="upload-button">上传作业</el-button>
      </div>
      <el-table :data="filterTableData"
                class="HomeworkList"
                size="large" column_width="60px"
                stripe
                :header-cell-style="{background:'#cde2ee',color:'#000'}">
        <el-table-column label="作业名称" sortable prop="name" />
        <el-table-column label="作业截止时间" sortable prop="submitDdl" />
        <el-table-column label="互评截止时间" sortable prop="scoreDdl" />
        <el-table-column label="作业内容" prop="content" >
          <template v-slot="scope">
          <el-link :href="blobUrl" :download="scope.row.fileName">下载</el-link>
          </template>
        </el-table-column>
        <el-table-column label="提交情况" align="center">
          <template v-slot="scope">
          <el-tooltip class="item" effect="dark" content="查看详情" placement="top">
          <span @click="handleClick(scope.row)" style="cursor: pointer;">
            20 / 30
            <i class="el-icon-search" style="margin-left: 5px;"></i>
          </span>
          </el-tooltip>
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

    <!-- 上传文件的弹出框 -->
    <el-dialog title="上传文件" :close-on-click-modal="false" v-model="dialogTableVisible" width="50%" >
      <div style = "flex: 1; display: flex; align-items: center; justify-content: center">
        <el-form ref="HomeworkFormRef" :model="homeworkData" :rules="homeFormRules" label-width="130px">
          <el-form-item label="作业名字:" prop="name" >
            <el-input style="width: 220px" v-model="homeworkData.name" ></el-input>
          </el-form-item>
          <el-form-item label="提交截止日期:" prop="submitDdl" >
            <el-date-picker v-model="homeworkData.submitDdl" type="datetime" placeholder="选择日期和时间"/>
          </el-form-item>
          <el-form-item label="互评截止日期:" prop="scoreDdl" >
            <el-date-picker v-model="homeworkData.scoreDdl" type="datetime" placeholder="选择日期和时间"/>
          </el-form-item>
          <el-form-item label="上传文件" prop="content">
            <el-upload
                class="upload-demo"
                drag
                action="#"
                :auto-upload="false"
                :on-change="onChange"
                :before-remove="beforeRemove"
                multiple
                :file-list="fileList"
                limit="1"
            >
              <el-icon class="el-icon--upload"><upload-filled /></el-icon>
              <div class="el-upload__text">
                拖动文件到这或者 <em>点击上传</em>
              </div>
              <template #tip>
              <div class="el-upload__tip">
                文件大小不超过10Mb
              </div>
              </template>
            </el-upload>
          </el-form-item>
        </el-form>
      </div>
      <span class="dialog-footer">
        <el-button @click="submitHomework">提交</el-button>
        <el-button @click="closeDia">取消</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script setup>
import zhCn from "element-plus/es/locale/lang/zh-cn";
import {ElConfigProvider} from "element-plus";
</script>