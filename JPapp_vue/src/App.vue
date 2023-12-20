<template>
  <div id="app">
    <!-- 路由出口 -->

    <router-view/>
  </div>
</template>

<script setup>
// app.vue写在script里面  main.js写在app挂在完之后
const debounce = (fn, delay) => {
  let timer
  return (...args) => {
    if (timer) {
      clearTimeout(timer)
    }
    timer = setTimeout(() => {
      fn(...args)
    }, delay)
  }
}

const _ResizeObserver = window.ResizeObserver;
window.ResizeObserver = class ResizeObserver extends _ResizeObserver{
  constructor(callback) {
    callback = debounce(callback, 200);
    super(callback);
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
