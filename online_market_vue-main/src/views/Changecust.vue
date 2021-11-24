<template>

  <el-container>
    <el-header>
      <Userdetail></Userdetail>
    </el-header>
    <el-container>
      <el-aside width="180px" >
        <Custside></Custside>
      </el-aside>
    <el-main>
      <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="form1">
      <el-form-item label="用户名" prop="username" class="item">
        <el-input v-model="ruleForm.username"></el-input>
      </el-form-item>
      <el-form-item label="电话" prop="phone" class="item">
        <el-input v-model="ruleForm.phone" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item label="默认交易地点" prop="address" class="item">
        <el-input v-model="ruleForm.address"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password" class="item">
        <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="checkPass" class="item">
        <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
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
        address: ''
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
            url:"/users/"+this.$store.getters.getUser.uid,
            data:data
          }).then(res=>{
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
</style>
