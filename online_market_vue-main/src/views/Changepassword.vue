<template>
  <el-container>
    <el-header>
      <Userdetail></Userdetail>
    </el-header>
    <el-container>
      <el-aside width="230px">
        <Side>
        </Side>
      </el-aside>
      <el-main>
        <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="旧密码" prop="oldpassword">
          <el-input type="password" v-model="ruleForm.oldpassword" autocomplete="off" style="width: 300px"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input type="password" v-model="ruleForm.password" autocomplete="off" style="width: 300px"></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="checkPass">
            <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off" style="width: 300px"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import Userdetail from "@/components/Userdetail";
import Side from "@/components/Side";

export default {
  inject: ["reload"],//?
  name: "Changepassword",
  components: {Side, Userdetail},
  data() {

    var validateoldpwd = (rule,value,callback)=>{
        if (value === '') {
          callback(new Error("请输入旧密码"))
        } else
          callback();
    };

    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        var strength = 0
        if(value.length>=9) strength=1//密码强度必须大于9位且至少包含一种字母
        function hasLetter(str) {
          for (var i in str) {
            var asc = str.charCodeAt(i);
            if ((asc >= 65 && asc <= 90 || asc >= 97 && asc <= 122)) {
              return true;
            }
          }
          return false;
        }
        if (hasLetter(value)) strength = strength+1
        if (strength!==2)
          callback(new Error('密码长度必须大于9位且至少包含一个字母'))
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass');
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入新密码'));
      } else if (value !== this.ruleForm.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      ruleForm: {
        oldpassword: '',
        password: '',
        checkPass: '',
      },
      rules: {
        oldpassword: [
          {validator:validateoldpwd, trigger:'blur'}
        ],
        password: [
          {validator: validatePass, trigger: 'blur'}
        ],
        checkPass: [
          {validator: validatePass2, trigger: 'blur'}
        ]
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let data = new FormData;
          data.append("uid",this.$store.getters.getUser.uid)
          data.append("password",this.ruleForm.password)
          data.append("oldpassword",this.ruleForm.oldpassword)
          this.$axios({
            method:"post",
            url: "/v2.0/users",
            data: data
          }).then(res => {
            if (res.data.code === 200)
            {
              this.$message({
                type: "success",
                message: "修改成功"
              })
              this.$store.commit("REMOVE_INFO")
              this.$router.push({name:'Userhome'})
            }else if (res.data.code === 401){
              this.$message({
                type:"warning",
                message: "修改失败,旧密码输入错误"
              })
            }else if (res.data.code === 400){
              this.$message({
                type:"warning",
                message: "修改失败,密码强度过低"
              })
            }
            console.log(res)
          })
        } else {
          console.log('error submit!!');
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
.form_pwd {
  max-width: 500px;
  margin: 0 auto;
}
</style>
