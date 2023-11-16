<template>
  <div style="border: 1px solid #ccc;">
    <Toolbar
        style="border-bottom: 1px solid #ccc"
        :editor="editorRef"
        :defaultConfig="toolbarConfig"
        :mode="mode"
    />
    <Editor
        style="height: 500px; overflow-y: hidden;"
        v-model="valueHtml"
        :defaultConfig="editorConfig"
        :mode="mode"
        @onCreated="handleCreated"
    />
  </div>
</template>

<script>
import '@wangeditor/editor/dist/css/style.css'; // Import CSS

import { onBeforeUnmount, ref, shallowRef, onMounted, defineProps } from 'vue';
import { Editor, Toolbar } from '@wangeditor/editor-for-vue';
import { Boot } from '@wangeditor/editor';
import attachmentModule from '@wangeditor/plugin-upload-attachment';
import axios from "axios";

export default {
  components: { Editor, Toolbar },
  setup() {

    const editorRef = shallowRef();
    const valueHtml = ref('<p></p>');
    const props = defineProps(['id']);

    onMounted(() => {
      // console.log(props.id)
      setTimeout(() => {
        valueHtml.value = '<p></p>';
      }, 1500);
    });

    // Register the attachmentModule plugin
    if (Boot.plugins.length < 13) {
      Boot.registerModule(attachmentModule);
    }

    const toolbarConfig = {
      insertKeys: {
        index: 0, // 自定义插入的位置
        keys: ['uploadAttachment'],
      },
    };

    const editorConfig = {
      placeholder: '请输入内容...',
      MENU_CONF: {
        // 图片上传设置
        uploadImage: {
          // 小于该值就插入 base64 格式（而不上传），默认为 0
          base64LimitSize: 5 * 1024, // 5kb
          // 单个文件的最大体积限制，默认为 2M
          // maxFileSize: 1 * 1024 * 1024, // 1M
          // // 最多可上传几个文件，默认为 100
          // maxNumberOfFiles: 5,
          // 选择文件时的类型限制，默认为 ['image/*'] 。如不想限制，则设置为 []
          allowedFileTypes: ['image/*'],
          // 自定义上传
          async customUpload(file, insertFn) { // 文件上传
            const formData = new FormData();
            formData.set('file', file)
            formData.set('id', props.id)

            let result = await axios.post(
                'http://localhost:8081/teacher/uploadHW',
                formData,
                {
                  headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                    'token': localStorage.getItem('token')
                  },

                }
            )
            // 插入到富文本编辑器中，主意这里的三个参数都是必填的，要不然控制台报错：typeError: Cannot read properties of undefined (reading 'replace')
            insertFn(result.data.data.url, result.data.data.name, result.data.data.name)
          }
        },
        // 附件上传
        uploadAttachment: {
          async customUpload(file, insertFn) {
            console.log(file, 'uploadAttachment');
            if (file.size / 1024 / 1024 > 200) {
              return console.error('请上传200M以内的附件！');
            }
            const formData = new FormData();
            formData.set('file', file)
            formData.set('Id', "10000")
            formData.set('name', "qq")
            formData.set('cno', "1001")

            console.log(formData)
            try {
              await axios.post(
                  'http://localhost:8081/teacher/uploadHW',
                  formData,
                  {
                    headers: {
                      'Content-Type': 'application/x-www-form-urlencoded',
                      'token': localStorage.getItem('token')
                    },
                  }
              ).then((res) =>{
                if (res.data.code === 200) {
                  insertFn(res.data.url, res.data.name);
                  console.log(res)
                } else {
                  window.alert("获取信息失败:" + res.data.msg);
                  console.log(res)
                }
              }).catch((err) => {
                console.error("发生未知错误！");
                console.log(err);
              });
              console.log("hh")
            } catch (error) {
              console.error(error);
            }
          },
        },
      }
    };

    onBeforeUnmount(() => {
      const editor = editorRef.value;
      if (editor == null) return;
      editor.destroy();
    });

    const handleCreated = (editor) => {
      editorRef.value = editor;
    };

    return {
      editorRef,
      valueHtml,
      mode: 'default',
      toolbarConfig,
      editorConfig,
      handleCreated,
    };
  },
};
</script>

<style src="@wangeditor/editor/dist/css/style.css"></style>
