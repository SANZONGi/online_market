<template>

  <el-container>
    <el-header>
      <div>
        <!--主页标签栏-->
        <div class="div1">
          <el-tabs v-model="activeName" @tab-click="handleClick" stretch="false">
            <el-tab-pane label="主页" name="first"></el-tab-pane>
            <el-tab-pane label="" name="second"></el-tab-pane>
            <el-tab-pane label="" name="third"></el-tab-pane>
            <el-tab-pane label="" name="fourth"></el-tab-pane>
          </el-tabs>
        </div>
        <!--搜索框-->
        <div class="div2">
          <span style="font-family: Algerian">尊敬的{{this.$store.getters.getUser.username}}，欢迎</span>
        </div>

        <!--登录按钮-->
        <div class="div3">

          <el-button @click="logout" type="danger" round>退出登录</el-button>
        </div>
      </div>
    </el-header>
    <el-container>
      <el-aside width="200px" >
        <Custside></Custside>
      </el-aside>
    <el-main>
      <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="form1">
      <el-form-item label="电话" prop="phone" class="item">
        <el-input v-model="ruleForm.phone" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item label="默认交易地点" prop="address" class="item">
        <el-input v-model="ruleForm.address"></el-input>
      </el-form-item>
      <el-form-item style="margin-left: 100px">
        <el-button type="primary" @click="submitForm('ruleForm')">确认修改</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
      </el-form>
    </el-main>
    </el-container>
  </el-container>
</template>

<script>
import Userdetail from "@/components/Userdetail";
import Custside from "@/components/Custside";
export default {
  name: "Changecust",
  components:{Userdetail,Custside},
  data(){

    var validateusername = (rule,value,callback)=>{
      if (value === '') {
        callback(new Error("请输入用户名"))
      } else
        callback();
    };

    var validateaddress = (rule,value,callback)=>{
      if (value === '') {
        callback(new Error("请输入地址"))
      } else
        callback();
    };

    var validatepassword = (rule,value,callback)=>{
      if (value === '') {
        callback(new Error("请输入密码"))
      } else
        callback();
    };

    var validatecheckpassword = (rule,value,callback)=>{
      if (value === '') {
        callback(new Error("请确认密码"))
      } else
        callback();
    };

    var validatephone = (rule,value,callback)=>{
      if (value === '') {
        callback(new Error("请输入电话"))
      } else
        callback();
    };

    return{
      ruleForm:{
        username: '',
        password: '',
        checkPass: '',
        phone: '',
        address: '',
        activeName:'first'
      },
      rules:{
        username: [{validator:validateusername,trigger:'blur'}],
        password: [{validator:validatepassword,trigger:'blur'}],
        checkPass: [{validator:validatecheckpassword,trigger:'blur'}],
        phone: [{validator:validatephone,trigger:'blur'}],
        address: [{validator:validateaddress,trigger:'blur'}]
      }
    }
  },
  methods:{
    handleClick(tab, event) {
      if (this.activeName === 'first') this.$router.push("Home")
      console.log(tab, event);
    },
    submitForm(formName){
      this.$refs[formName].validate((valid) => {
        if (valid) {//打包数据传送
          var data = {}
          data.phone = this.ruleForm.phone
          data.address = this.ruleForm.address
          console.log(data)
          this.$axios({
            headers:{
              'Content-Type':'application/json;charset=UTF-8',
            },
            dataType:'JSON',
            method:"post",
            url:"/v2.0/users/"+this.$store.getters.getUser.uid,
            data:data
          }).then(res=>{
            console.log("sss")
            console.log(res)
            this.$message({
              type:"success",
              message:res.data.msg
            })
            this.$refs[formName].resetFields();
          })
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
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
  }
}
</script>

<style scoped>
.form1 {
  margin: 0 auto;
  /*margin-top: 0;*/
  /*margin-left: 200px;*/
  max-width: 500px;
}

.item {
  width: 400px;
  margin: 40px auto;
}
.div1 {
  float: left;
  /*width: auto;*/
  width: 40%;
  height: 60px;

}

.div2 {
  margin-left: 70px;
  float: left;
  width: 35%;
  margin-top: 10px;
  height: 60px;
}

.div3 {
  margin-left: 30px;
  float: right;
  align-content: center;
  /*width: auto;*/
  width: 10%;
  height: 60px;
}
</style>
