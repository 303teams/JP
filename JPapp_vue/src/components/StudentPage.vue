<template>
  <div>
    <div class="person_body">
      <div class="person_body_left">
        <el-card class="box-card" style="width: 100%; height: 100%; overflow: auto;" >
          <template #header>
            <div>
              <span class="person_body_list" style="border-bottom: none">个人中心</span>
            </div>
          </template>
            <el-menu router active-text-color="#00c3ff" class="el-menu-vertical-demo">
              <el-menu-item index="info" :route="{ name: 'InfoPage' }">
                <template #title>
                  <div>
                    <span>个人信息</span>
                  </div>
                </template>
              </el-menu-item>
              <el-menu-item>
                <template #title>
                  <div>
                    <span>作业</span>
                  </div>
                </template>
              </el-menu-item>
              <el-menu-item>
                <template #title>
                  <div>
                    <span>分数</span>
                  </div>
                </template>
              </el-menu-item>
              <el-menu-item>
                <template #title>
                  <div>
                    <span>其他</span>
                  </div>
                </template>
              </el-menu-item>
            </el-menu>
        </el-card>
      </div>
      <div class="person_body_right">
        <router-view></router-view>
      </div>
    </div>
    <personal-dia ref="dia" @flesh="reload" />
  </div>
</template>


<script>
import PersonalDia from "./ModifyInfo.vue";

export default {
  components: { PersonalDia },
  name: "StudentPage",
  inject: ["reload"],
  data() {
    return {
      avatar: "",
      nickname: "",
      v: 1,
    };
  },
  watch: {
    $route(to) {
      if (to.path == `/info`) {
        this.reload();
      } else if (to.path == `/newsuser/personal/${this.$route.params.id}`) {
        this.reload();
      }
    },
  },
  methods: {
    edit() {
      this.$refs.dia.open();
    },
  },
};
</script>

<style scoped>

.user_text {
  width: 60%;
  height: 100%;
  line-height: 30px;
}

.user_name {
  font-weight: bold;
}
.user-v {
  margin-bottom: -5px;
}
.user-v-img {
  width: 15px;
  height: 15px;
}
.user-v-font {
  font-size: 15px;
  color: #00c3ff;
}
.user_qianming {
  font-size: 14px;
  color: #999;
}

.user_num {
  width: 40%;
  height: 100%;
  display: flex;
  align-items: center;
}

.user_num > div {
  text-align: center;
  border-right: 1px dotted #999;
  box-sizing: border-box;
  width: 80px;
  height: 40px;
  line-height: 20px;
}

.num_text {
  color: #999;
}

.num_number {
  font-size: 20px;
  color: #333;
}
.el-menu-item>span {
  font-size: 16px;
  color: #999;
}

/*下面部分样式*/
.person_body {
  width: 70%;
  height: 6000px;
  margin-top: 500px;
  display: flex;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  border-radius: 5px;
}

.person_body_left {
  width: 30%;
  height: 100%;
  border-radius: 5px;
  margin-right: 3%;
  text-align: center;
}

.person_body_list {
  width: 30%;
  height: 50px;
  margin-top: 25px;
  font-size: 200px;
  border-bottom: 1px solid #f0f0f0;
  background-image: -webkit-linear-gradient(
      left,
      rgb(42, 134, 141),
      #e9e625dc 20%,
      #3498db 40%,
      #e74c3c 60%,
      #09ff009a 80%,
      rgba(82, 196, 204, 0.281) 100%
  );
  -webkit-text-fill-color: transparent;
  -webkit-background-clip: text;
  -webkit-background-size: 200% 100%;
  -webkit-animation: masked-animation 4s linear infinite;
}

.el-menu-item {
  margin-top: 22px;
}

.person_body_right {
  width: 70%;
  height: 100%;
  /* height: 500px; */
  border-radius: 5px;
  background-color: white;
}

.box-card {
  height: 500px;
}

/*ui样式*/
.el-button {
  width: 84px;
}
</style>
