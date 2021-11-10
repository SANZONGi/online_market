<template>
  <el-container>
    <el-header>
      <Userdetail></Userdetail>
    </el-header>
    <el-container>
      <el-aside width="400px">
        <div class="block">
          <el-image
              style="width: 350px; height: 350px"
              :src="this.good['image']"
              fit="contain"></el-image>
        </div>
      </el-aside>
      <el-main>

        <el-form ref="ruleForm" :model="ruleForm" status-icon :rules="rules" label-width="100px" class="des">
          <el-form-item label="商品名称: ">
            <el-label-wrap>{{ this.good['gname'] }}</el-label-wrap>
          </el-form-item>
          <el-form-item label="商品价格: ">
            <el-label-wrap>{{ this.good['price'] }}</el-label-wrap>
          </el-form-item>
          <el-form-item label="收件人姓名: " prop="name">
            <el-input v-model="ruleForm.name" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="收件人电话: " prop="phone">
            <el-input v-model="ruleForm.phone" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="收件人地址: " prop="address">
            <el-input v-model="ruleForm.address" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
            <el-button>取消</el-button>
          </el-form-item>
        </el-form>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import Userdetail from "@/components/Userdetail";
export default {
  name: "Buy",
  components: {Userdetail},
  data() {
    var validatename = (rule, value, callback) => {
      if (value === '') {
        callback(new Error("请输入姓名"))
      } else callback();
    };
    var validatephone = (rule, value, callback) => {
      var a = 0;
      for(var i in value){
        if ( value[i]<'0' || value[i]>'9'){
          a = 1;
        }
      }
      if (value.length === 11 && a === 0) {
        callback();
      } else
        callback(new Error("请输入正确电话（11位数字）"))
    };
    var validateaddress = (rule, value, callback) => {
      if (value === '') {
        callback(new Error("请输入地址"))  ``
      } else
        callback();
    };
    return {
      ruleForm: {
        name: '',
        phone: '',
        address: ''
      },
      good: {},
      rules: {
        phone: [{validator: validatephone, trigger: 'blur'}
        ],
        name: [
          {validator: validatename, trigger: 'blur'}
        ],
        address: [
          {validator: validateaddress, trigger: 'blur'}
        ],
      }
    }
  }, methods: {
    submitForm(forName) {
      this.$refs[forName].validate(valid => {
        if (valid) {
          let data = new FormData
          data.append('username', this.ruleForm.name)
          data.append('userphone', this.ruleForm.phone)
          data.append('useraddress', this.ruleForm.address)
          data.append('oid', 0)
          data.append('gid', this.good.gid)
          data.append('number', 1)
          data.append('amount', this.good.price)
          data.append('status', 0)
          this.$axios({
            method: "post",
            data: data,
            url: "/orders/insert"
          }).then(res => {
            if (res.data.code === 200) {
              this.$message({
                type: "success",
                message: "下单成功"
              })
              this.$router.push({//下单成功页面进行跳转
                name: "Gooddetail"
              })
            } else {
              this.$message({
                type: "info",
                message: res.data.msg
              })
            }
            // console.log(res.data)
          })
        } else {
          this.$message({
            type: "info",
            message: "格式错误"
          })
        }
      })
    },

  },created() {
    this.good = this.$route.params['good']
    if (this.good === undefined)
      this.good = localStorage.getItem('good')
    else
      localStorage.setItem('good', this.good)
  }
}
</script>

<style scoped>
.block {
  margin: 60px 20px;
}

.des {
  margin: 20px 0;
}

.label {
  margin: 60px 0;
}
</style>
