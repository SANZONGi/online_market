<template>
  <div>
    <div>
      <div class="div1">
        <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="商品主页" name="first"></el-tab-pane>
          <el-tab-pane label="------" name="second"></el-tab-pane>
          <el-tab-pane label="------" name="third"></el-tab-pane>
          <el-tab-pane label="------" name="fourth"></el-tab-pane>
        </el-tabs>
      </div>
      <div class="div2">
        <el-input>
          placeholder="请输入内容"
          v-model="input4">
          <i slot="suffix" class="el-input__icon el-icon-search"></i>
        </el-input>
      </div>
      <div class="div3">
        <router-link :to="{name: 'Login'}">
          <el-avatar :size="40" :src="circleUrl">
          </el-avatar>
        </router-link>
      </div>
      <br>
      <br>
      <br>
      <br>
      <div class="link">
        <router-link :to="{name: 'Login'}">
          登录
        </router-link>
        <el-divider direction="vertical"></el-divider>
        <router-link :to="{name: 'Register'}">
          注册
        </router-link>
      </div>
      <el-main>
        <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="form1">
          <el-form-item label="账号" class="item">
            <el-input v-model="ruleForm.username"></el-input>
          </el-form-item>
          <el-form-item label="电话" prop="phone" class="item">
            <el-input v-model="ruleForm.phone" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="地址" class="item">
            <el-input v-model="ruleForm.address"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password" class="item">
            <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="checkPass" class="item">
            <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </el-main>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    var validatephone = (rule, value, callback) => {
      if (value.length !== 11) {
        callback(new Error("长度错误"));
      } else {
        callback();
      }
    };
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
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
          let data = new FormData;
          data.append('username', this.ruleForm.username)
          data.append('password', this.ruleForm.password)
          data.append('phone', this.ruleForm.phone)
          data.append('address', this.ruleForm.address)
          this.$axios({
            method: 'post',
            url: '/users/insertuser',
            data: data
          }).then(res => {
            console.log(res.data)
          }).catch()
          {
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


<style>
.div1 {
  float: left;
  width: 550px;
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