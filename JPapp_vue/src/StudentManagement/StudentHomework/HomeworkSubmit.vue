<template>
  <div>
    <WangEditor :id="props.homeworkID"></WangEditor>
  </div>
</template>

<script setup>
import WangEditor from "@/components/wangEditor.vue";

import {defineProps} from "vue";

const props = defineProps(["homeworkID"]);


</script>