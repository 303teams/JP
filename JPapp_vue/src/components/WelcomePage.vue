<template>
  <div class="hello">
    <img id = "pic1" alt="app logo" src="../assets/pic.jpg">
    <h1>Welcome to my app!</h1>
    <router-view></router-view>
  </div>
</template>

<script>

export default {
  name: 'WelcomePage',
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
