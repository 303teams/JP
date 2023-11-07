<template>
  <div class="common-layout">
    <el-container>
      <el-header><header-component /></el-header>
      <el-container>
        <el-aside width="200px">

        </el-aside>
        <el-main>
          <router-view></router-view>
        </el-main>
        <personal-dia ref="dia" @flesh="reload" />
      </el-container>
    </el-container>
  </div>
</template>

<script>
import PersonalDia from "./ModifyInfo.vue";
import HeaderComponent from "./HeaderComponent.vue"
export default {
  components: { PersonalDia,HeaderComponent },
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
      if (to.path == `/student`) {
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
  computed: {
    menuData(){
      return this.$store.state.tab.menu;
    }
  }
};
</script>

<style scoped>
.el-header{
  padding:0;
}

</style>
