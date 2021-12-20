
<template>
  <div id="app">
    <router-view v-if="isRouterAlive"/>
  </div>
</template>

<script>
export default {
  name: "APP",
  provide(){
    return{
      reload: this.reload
    }
  },
  data(){
    return{
      isRouterAlive: true
    }
  },methods:{
    reload(){
      this.isRouterAlive = false
      this.$nextTick(function () {
        this.isRouterAlive = true
      })
    }
  },
  mounted() {
    window.onload = function () {
      console.log(window.sessionStorage["userInfo"]);
      if (!window.sessionStorage["userInfo"]) {
        // 关闭浏览器
        window.localStorage.removeItem("token");
      } else {
        // 刷新
      }
    };
    // window.onunload = function () {
    //   window.sessionStorage[""] = true;
    // };
    // window.onbeforeunload = function () {
    //   window.sessionStorage["ISlogin"] = true;
    // };
  }
}
</script>

<style>
#app {
  max-width: 960px;
  margin: 0 auto;
}
page{
  background-color: #161622;
}
</style>
