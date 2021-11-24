<template>
  <div>
      <div class="div1">
        <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="主页" name="first"></el-tab-pane>
<!--          <el-tab-pane label="&#45;&#45;&#45;&#45;&#45;&#45;" name="second"></el-tab-pane>-->
<!--          <el-tab-pane label="&#45;&#45;&#45;&#45;&#45;&#45;" name="third"></el-tab-pane>-->
<!--          <el-tab-pane label="&#45;&#45;&#45;&#45;&#45;&#45;" name="fourth"></el-tab-pane>-->
        </el-tabs>
      </div>
      <div class="div2">
        <el-input
          placeholder=""
          v-model="input4">
          <i slot="suffix" class="el-input__icon el-icon-search"></i>
        </el-input>
      </div>
<!--      <div class="div3">-->
<!--        <router-link :to="{name: 'Login'}">-->
<!--          <el-avatar :size="40" :src="circleUrl">-->
<!--          </el-avatar>-->
<!--        </router-link>-->
<!--      </div>-->
      <div class="div4">
        <el-button @click="logout">退出登录</el-button>
      </div>
  </div>
</template>

<script>
export default {
  name: "Userdetail",
  data() {
    return {
      activeName: 'first',
      input4: '',
      circleUrl: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
    };
  },
  methods: {
    handleClick(tab, event) {
      if (this.activeName === 'first') this.$router.push("Home")
      console.log(tab, event);
    },
    logout(){
      this.$axios({
        method:"post",
        url:"/v2.0/users/logout"
      }).then(res=>{
        if (res.data.data === 0 ){
          this.$message({
            type:"success",
            message:"成功退出登录"
          })
          this.$store.commit("REMOVE_INFO")
          this.$router.push("login")
        }else {
          this.$message({
            type:"warning",
            message:res.data.msg
          })
          this.$store.commit("REMOVE_INFO")
          this.$router.push("login")
        }
      })
    }
  },
}
</script>

<style scoped>
.div1 {
  float: left;
  /*width: auto;*/
  width: 40%;
  height: 60px;
}
.div2 {
  margin-left: 70px;
  float: left;
  width: 30%;
  height: 60px;
}
/*.div3 {*/
/*  margin-right: 30px;*/
/*  float: right;*/
/*  align-content: center;*/
/*  width: 10%;*/
/*  height: 60px;*/
/*}*/
.div4{
  margin-right: 50px;
  float: right;
  align-content: center;
  width: 50px;
  height: 40px;
}
</style>
