<template>
  <div id="app">

    <router-view/>
  </div>
</template>

<script>
// import card from "./component/card"
export default {
  name: 'App',
  // components: {
  //   card
  // }
}
</script>

<style>
#app {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}
.icon {
  width: 1px;
  height: 1px;
  vertical-align: -0.15em;
  fill: currentColor;
  overflow: hidden;
}
</style>
