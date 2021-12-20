<template>
<!--  <el-container>-->
<!--    <el-header>-->
<!--      <div class="div1">-->
<!--        <el-tabs v-model="activeName" @tab-click="handleClick">-->
<!--          <el-tab-pane label="商品主页" name="first"></el-tab-pane>-->
<!--          &lt;!&ndash;          <el-tab-pane label="&#45;&#45;&#45;&#45;&#45;&#45;" name="second"></el-tab-pane>&ndash;&gt;-->
<!--          &lt;!&ndash;          <el-tab-pane label="&#45;&#45;&#45;&#45;&#45;&#45;" name="third"></el-tab-pane>&ndash;&gt;-->
<!--          &lt;!&ndash;          <el-tab-pane label="&#45;&#45;&#45;&#45;&#45;&#45;" name="fourth"></el-tab-pane>&ndash;&gt;-->
<!--        </el-tabs>-->
<!--      </div>-->
<!--      <div class="div2">-->
<!--        <el-input-->
<!--            v-model="input4">-->
<!--          <i slot="suffix" class="el-input__icon el-icon-search"></i>-->
<!--        </el-input>-->
<!--      </div>-->

<!--    </el-header>-->
<!--    <div class="link">-->
<!--      <router-link :to="{name: 'Login'}">-->
<!--        登录-->
<!--      </router-link>-->
<!--      <el-divider direction="vertical"></el-divider>-->
<!--      <router-link :to="{name: 'Register'}">-->
<!--        注册-->
<!--      </router-link>-->
<!--    </div>-->
<!--    <el-container>-->
<!--      <el-main>-->
<!--        <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="form1">-->
<!--          <el-form-item label="用户名" class="item">-->
<!--            <el-input v-model="ruleForm.username" placeholder="请输入用户名"></el-input>-->
<!--          </el-form-item>-->
<!--          <el-form-item label="电话" prop="phone" class="item">-->
<!--            <el-input v-model="ruleForm.phone" auto-complete="off" placeholder="请输入联系方式"></el-input>-->
<!--          </el-form-item>-->
<!--          <el-form-item label="默认交易地点" class="item">-->
<!--            <el-input v-model="ruleForm.address" placeholder="请输入交易地点"></el-input>-->
<!--          </el-form-item>-->
<!--          <el-form-item label="密码" prop="password" class="item">-->
<!--            <el-input type="password" v-model="ruleForm.password" autocomplete="off" placeholder="请输入密码">-->
<!--            </el-input>-->
<!--          </el-form-item>-->
<!--          <el-form-item label="确认密码" prop="checkPass" class="item">-->
<!--            <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off" placeholder="请确认密码"></el-input>-->
<!--          </el-form-item>-->
<!--          <el-form-item style="margin-left: 100px">-->
<!--            <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>-->
<!--            <el-button @click="resetForm('ruleForm')">重置</el-button>-->
<!--          </el-form-item>-->
<!--        </el-form>-->
<!--      </el-main>-->
<!--    </el-container>-->
<!--  </el-container>-->
  <div class="back">
    <div class="form">
      <h2>注册</h2>
      <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm"  class="form1">
        <el-form-item class="item">
          <el-input v-model="ruleForm.username" placeholder="请输入用户名" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item  prop="phone" class="item">
          <el-input v-model="ruleForm.phone" auto-complete="off" placeholder="请输入联系方式" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item  class="item">
          <el-input v-model="ruleForm.address" placeholder="请输入交易地点" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item  prop="password" class="item">
          <el-input type="password" v-model="ruleForm.password" autocomplete="off" placeholder="请输入密码" style="width: 300px;">
          </el-input>
        </el-form-item>
        <el-form-item  prop="checkPass" class="item">
          <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off" placeholder="请确认密码" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')" style="">提交</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
        <router-link :to="{name: 'Login'}" style="color:black;float: right;margin-right: 20px;margin-top: 10px">
          登录
        </router-link>
      </el-form>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    var validatephone = (rule, value, callback) => {
      if (value.length !== 11) {
        callback(new Error("电话必须为11位数字"));
      } else {
        callback();
      }
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
        callback(new Error('请再次输入密码'));
      } else if (value !== this.ruleForm.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      activeName: '',
      input4: '',
      circleUrl: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
      ruleForm: {
        username: '',
        password: '',
        checkPass: '',
        phone: '',
        address: ''
      },
      rules: {
        phone: [{validator: validatephone, trigger: 'blur'}
        ],
        password: [
          {validator: validatePass, trigger: 'blur'}
        ],
        checkPass: [
          {validator: validatePass2, trigger: 'blur'}
        ],
      }
    };
  },
  methods: {
    handleClick(tab, event) {
      if (this.activeName === 'first') this.$router.push("Home")
      console.log(tab, event);
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          var data={}
          data.username = this.ruleForm.username
          data.password = this.ruleForm.password
          data.phone = this.ruleForm.phone
          data.address = this.ruleForm.address

          this.$axios({
            headers:{
              'Content-Type':'application/json;charset=UTF-8',
            },
            dataType:'JSON',
            method: 'put',
            url: '/v2.0/users',
            data:data
            //data: data

          }).then(res => {
            console.log(res)
            if (res.data.code===400){
              this.$message({
                type:'warning',
                message:'密码长度必须大于9位且至少包含一个字母'
              })
            } else if (res.data.code === 200) {
              console.log(res.data)
              this.$message({
                type: "success",
                message: "注册成功"
              })
              this.$router.push({name:'Login'})
            }
          }).catch()
          {
            console.log(JSON.stringify(data))
            console.log("register err")
          }
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
};
</script>


<style scoped>
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
  float: left;
  align-content: center;
}

.div4 {
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
  max-width: 900px;
  margin: 0 auto;
  max-height: 600px;
}

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

.el-button {
  margin: 0 auto;
}

.link {
  margin: 60px auto;
  align-content: center;
  max-width: max-content;
}
.back{
  background: url("../assets/v.jpg");
  background-size: 100% 100%;
  height: 100%;
  position: fixed;
  width: 100%;
  top: 0px;
  left: 0px;
  text-align: center;
}
.form{
  width: 450px;
  height: 600px;
  background-color:rgba(255,255,255,0.3);
  float: right;
  margin-top: 100px;
  margin-right: 550px;
  border-radius: 15px;
}
.el-form-item >>> .el-form-item__error {
  color: #000000;
  font-size: 12px;
  line-height: 1;
  padding-top: 4px;
  position: absolute;
  top: 100%;
  left: 0;
  margin-left: 60px;
}
</style>
