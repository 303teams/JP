<template>

  <div>
    <h2>{{sex}}</h2>

  </div>

</template>


<script>

import {mapState} from 'vuex'

export default{

//
  computed:{
    ...mapState(['sex'])
}
}

</script>