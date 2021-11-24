<template>
  <el-container>
    <el-header>
      <div class="div1">
        <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="商品主页" name="first"></el-tab-pane>
<!--          <el-tab-pane label="&#45;&#45;&#45;&#45;&#45;&#45;" name="second"></el-tab-pane>-->
<!--          <el-tab-pane label="&#45;&#45;&#45;&#45;&#45;&#45;" name="third"></el-tab-pane>-->
<!--          <el-tab-pane label="&#45;&#45;&#45;&#45;&#45;&#45;" name="fourth"></el-tab-pane>-->
        </el-tabs>
      </div>
      <div class="div2">
        <el-input

          v-model="input4">
          <i slot="suffix" class="el-input__icon el-icon-search"></i>
        </el-input>
      </div>

    </el-header>
    <div class="link">
      <router-link :to="{name: 'Login'}">
        登录
      </router-link>
      <el-divider direction="vertical"></el-divider>
      <router-link :to="{name: 'Register'}">
        注册
      </router-link>
    </div>
    <el-container>
      <el-main >
        <el-form :model="form"  status-icon ref="form" label-width="80px" class="form1">
          <el-form-item label="账号" class="item" prop="username">
            <el-input v-model="form.username" style="width: 300px"></el-input>
          </el-form-item>
          <el-form-item label="密码" class="item" prop="pwd">
            <el-input type="password" v-model="form.pwd" style="width: 300px"></el-input>
          </el-form-item>
          <el-form-item class="item" style="margin-left: 100px">
            <el-button type="primary" @click="onSubmit" class="el-button">登录</el-button>
          </el-form-item>
        </el-form>
      </el-main>
    </el-container>
  </el-container>
</template>
<script>
export default {
  data() {
    // var validateusername = (rule, value, callback) => {
    //   if (value === '') {
    //     callback(new Error("请输入账号"))
    //   } else
    //     callback();
    // };
    // var validatepwd = (rule, value, callback) => {
    //   if (value === '') {
    //     callback(new Error("请输入密码"))
    //   } else
    //     callback();
    // };
    return {
      BASE64: '',
      activeName: '',
      input4: '',
      circleUrl: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
      form: {
        username: '',
        pwd: ''
      },
    };
  },
  methods: {
    handleClick(tab, event) {
      if (this.activeName === 'first') this.$router.push("Home")
      console.log(tab, event);
    },
    onSubmit() {
      this.$axios({
        method: 'post',
        url: '/v2.0/users/check',
        data: this.form
      }).then((res) => {//登录失败
        if (res.data.code !== 200) {
          alert(res.data.msg)
        } else {//登录成功

          this.$store.commit("SET_TOKEN", res.data.msg)//改变SET_TOKEN中的值，用res.data.msg传回store.js中代替原本store中的token值
          this.$store.commit("SET_USERINF", res.data.data)
          this.$axios({
            method:"get",
            url:"/v2.0/role/" + this.$store.getters.getUser.uid
          }).then(res=> {
            if (res.data.data.role === 0 ){//返回的role值是1说明应该进入卖家页面
              this.$message({
                type:"success",
                message:"尊敬的管理员,登陆成功"
              })
              console.log(res.data.data)
              localStorage.setItem("uid",res.data.data.uid)
              localStorage.setItem("role",res.data.data.role)
              this.$router.push("Userhome")
            }else if (res.data.data.role === 1){//普通用户
              this.$message({
                type:"success",
                message:"登陆成功"
              })
              localStorage.setItem("uid",res.data.data.uid)
              localStorage.setItem("role",res.data.data.role)
              console.log(res.data.data)
              this.$router.push("Custhistory")
            }
          })
        }
      })
    },
    created(){
      this.reload()
    }
  }
};
</script>


<style>
.div1 {
  float: left;
  width: 450px;
}

.div2 {
  margin-left: 30px;
  width: 300px;
  float: left;
}

.div3 {
  margin-left: 30px;
  float: right;
  align-content: center;
}

.div4 {
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
  width: 900px;
  margin: 0 auto;
  max-height: 600px;
}

.form1 {
  margin: 0px auto;

  max-width: 500px;
}

.item {
  margin: 40px auto;
}

.el-button {
  margin: 0 auto;
}

.link {
  margin: 60px auto;
  align-content: center;
  max-width: max-content;
}

</style>
