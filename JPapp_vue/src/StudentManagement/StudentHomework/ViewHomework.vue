<template>
  <div style="display: flex; flex-direction: column; justify-content: center; margin-left: 50px">
    <el-icon class="icon" @click="Back"><ArrowLeft /></el-icon>
    <div class="progress-boxes">
      <div :class="{ 'active-box': currentStage === 'submission' }" class="progress-box">
        <div class="progress-container">
          <el-progress :show-text="false" :percentage="Progress.submissionProgress" :stroke-width="20" />
          <p :class="{ 'active-hint': currentStage === 'submission' }"  class="hint">
            提交作业
            {{ currentStage === 'submission' ? `（剩余${remainDays}天）` : '' }}
          </p>
        </div>

        <el-divider style="margin-top: 5px"/>

        <div class="custom-text-with-checkbox">
          <p class="custom-text">已完成作业</p>
          <input type="checkbox" id="checkbox1" name="checkbox1" v-model="ifSubmit" disabled>
        </div>
      </div>

      <div :class="{ 'active-box': currentStage === 'peerReview' }" class="progress-box">
        <div class="progress-container">
          <el-progress :show-text="false" :percentage="Progress.peerReviewProgress" :stroke-width="20" />
          <p :class="{ 'active-hint': currentStage === 'peerReview' }" class="hint">
            互评阶段
            {{ currentStage === 'peerReview' ? `（剩余${remainDays}天）` : '' }}
          </p>
        </div>

        <el-divider style="margin-top: 5px"/>

        <div class="custom-text-with-checkbox">
          <p class="custom-text">已完成互评</p>
          <input type="checkbox" id="checkbox1" name="checkbox1" v-model="isMutual" disabled>
        </div>
      </div>

      <div :class="{ 'active-box': currentStage === 'resultPublish' }" class="progress-box">
        <div class="progress-container">
          <el-progress :show-text="false" :percentage="resultPublishProgress" :stroke-width="20" />
          <p :class="{ 'active-hint': currentStage === 'submission' }" class="hint">成绩公布</p>
        </div>

        <el-divider style="margin-top: 5px"/>
      </div>
    </div>

    <homework-submit :homeworkID="props.homeworkID" :submitTime="submitTime" :cno="props.cno" v-if="currentStage === 'submission'"/>
    <evaluation-list :homeworkID="props.homeworkID" :contentID="contentID" @MutualNum="ifMutual" v-show="currentStage === 'peerReview'"/>
    <grade-detail :homeworkID="props.homeworkID" v-if = "currentStage === 'resultPublish'"/>
  </div>

</template>

<script setup>

import {computed, defineProps, onMounted, ref} from "vue";
import {useRouter} from "vue-router";
import homeworkSubmit from "@/StudentManagement/StudentHomework/HomeworkSubmit.vue";
import EvaluationList from "@/StudentManagement/StudentHomework/EvaluationList.vue";
import GradeDetail from "@/StudentManagement/StudentHomework/GradeDetail.vue";

const router = useRouter();
const submitDdl = history.state.submitDdl;
const scoreDdl = history.state.scoreDdl;
const contentID = history.state.contentID;
const submitTime = history.state.submitTime;
const props = defineProps(['homeworkID','cno']);
const remainDays = ref();
const currentStage = ref();
const resultPublishProgress = ref();
const currentTimestamp = new Date().getTime();
const isMutual = ref(false);

const calculateRemainingDays = () => {
  const currentDate = new Date(); // 当前日期
  const submitDate = new Date(submitDdl); // 提交截止日期
  const scoreDate = new Date(scoreDdl); // 评分截止日期
  const remainingDays = ref();

  if (currentStage.value === 'submission') {
    // 计算剩余天数（向上取整）
    remainingDays.value = Math.ceil((submitDate - currentDate) / (24 * 60 * 60 * 1000));
  } else if (currentStage.value === 'peerReview') {
    remainingDays.value = Math.ceil((scoreDate - currentDate) / (24 * 60 * 60 * 1000));
  } else {
    remainingDays.value = 0;
  }

  return remainingDays.value;
};

const Progress = computed(() => {
  if (currentStage.value === 'submission') {
    // 在提交作业阶段，进度条的进度由剩余天数决定
    return{
      submissionProgress: 100 - Math.floor((remainDays.value / 20) * 100),
      peerReviewProgress: 0,
    } ;
  } else if (currentStage.value === 'peerReview') {
    // 在互评阶段，提交作业框的进度条填满，互评框的进度条由剩余互评时间决定
    return {
      submissionProgress: 100,
      peerReviewProgress: 100 - Math.floor((remainDays.value / 20) * 100),
    };
  } else {
    // 其他阶段，进度为 0
    return {
      submissionProgress: 100,
      peerReviewProgress: 100,
    };
  }
});

const ifSubmit = computed(() => {
  if(contentID === null){
    return false;
  }else {
    return true;
  }
});

//是否互评
const ifMutual = (value) => {
  console.log(value);
  if(value === 6){
    isMutual.value = true;
  }else {
    isMutual.value = false;
  }
};

const Back = () => {
  router.back();
};

const JudgeState = () =>{
  if(currentTimestamp < new Date(submitDdl).getTime()) {
    currentStage.value = 'submission';
  }else if( currentTimestamp >= new Date(submitDdl).getTime() &&
      currentTimestamp <= new Date(scoreDdl).getTime()){
    currentStage.value = 'peerReview';
  }else{
    currentStage.value = 'resultPublish';
  }

  if(contentID === null &&
      currentTimestamp > new Date(submitDdl).getTime()){
    ifSubmit.value = false;
  }
}

onMounted(() => {
  JudgeState();
  remainDays.value = calculateRemainingDays();
});
</script>

<style scoped>
.icon{
  position: absolute;
  top: 100px;
  left: 278px;
  font-size: 30px;
  color: #3796EC;
  cursor: pointer;
}

.progress-boxes {
  height: 180px;
  width: 800px;
  display: flex;
  margin-top: 40px;
  justify-content: space-around;
}

.progress-box {
  width: 200px;
  padding: 20px;
  border: 1px solid #ddd;
  text-align: start;
}

.active-box {
  border-color: #3498db;
}

.progress-container {
  width: 100%;
  overflow: hidden;
}

.hint{
  font-size: 18px;
}

.active-hint{
  color: #3498db;
}

.custom-text-with-checkbox {
  height: 20px;
  display: flex;
  align-items: center;
  font-size: 14px;
}

</style>
