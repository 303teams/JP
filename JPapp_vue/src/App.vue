<template>
  <div id="app">
    <!-- 路由出口 -->
    <router-view/>
  </div>
</template>

<script>
import DevicePixelRatio from './utils/devicePixelRatio';

export default {
  name: 'App',
  components: {},
  created() {
    new DevicePixelRatio().init();
  },

  methods: {
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 0px;
}


body{
  margin:0;
  padding:0;
  border:0;
  overflow: hidden;
}

html{
  overflow: hidden;
}

#pic1 {
  width: 150px;
  height: 150px;
  padding: 20px;
}

</style>
