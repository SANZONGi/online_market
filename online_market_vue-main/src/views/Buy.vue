<template>
  <el-container>
    <el-header>
      <Userdetail></Userdetail>
    </el-header>
<!--    <el-container>-->
<!--      <el-aside width="400px">-->
<!--        <div class="block">-->
<!--          <el-image-->
<!--              style="width: 300px; height: 300px"-->
<!--              :src="this.good['image']"-->
<!--              fit="fill"></el-image>-->
<!--        </div>-->
<!--      </el-aside>-->
<!--      <el-main>-->

<!--        <el-form ref="ruleForm" :model="ruleForm" status-icon :rules="rules" label-width="100px" class="des">-->
<!--          <el-form-item label="商品名称: ">-->
<!--            <el-label-wrap>{{ this.good['gname'] }}</el-label-wrap>-->
<!--          </el-form-item>-->
<!--          <el-form-item label="商品价格: ">-->
<!--            <el-label-wrap>{{ this.good['price'] }}</el-label-wrap>-->
<!--          </el-form-item>-->
<!--          <el-form-item label="收件人姓名: " prop="name">-->
<!--            <el-input v-model="ruleForm.name" auto-complete="off"></el-input>-->
<!--          </el-form-item>-->
<!--          <el-form-item label="收件人电话: " prop="phone">-->
<!--            <el-input v-model="ruleForm.phone" auto-complete="off"></el-input>-->
<!--          </el-form-item>-->
<!--          <el-form-item label="收件人地址: " prop="address">-->
<!--            <el-input v-model="ruleForm.address" auto-complete="off"></el-input>-->
<!--          </el-form-item>-->
<!--          <el-form-item>-->
<!--            <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>-->
<!--            <el-button>取消</el-button>-->
<!--          </el-form-item>-->
<!--        </el-form>-->
<!--      </el-main>-->
<!--    </el-container>-->
      <h2>确认订单信息</h2>
      <span>默认交易地址:</span>
    <el-container>
      <el-main>
        <div class="label" style="margin-top: 10px">
        <div style="display: inline;width: 150px;text-align: center">商品名称</div>
        <div style="display: inline;width: 150px;text-align: center;margin-left: 150px">单价</div>
        <div style="display: inline;width: 150px;text-align: center;margin-left: 150px">商品库存</div>
        <div style="display: inline;width: 150px;text-align: center;margin-left: 150px">购买数量</div>
        <div style="display: inline;width: 150px;text-align: center;margin-left: 150px">总价</div>
          <el-divider></el-divider>
        </div>
        <div class="label2" style="margin-top: 10px">
          <div style="display: inline;text-align: center;">{{ this.good['gname'] }}</div>
          <div style="display: inline;text-align: center;margin-left: 220px">{{ this.good['price'] }}</div>
          <div style="display: inline;text-align: center;margin-left: 160px">{{ this.good['stock'] }}</div>
          <div style="display: inline;text-align: center;margin-left: 160px">
            <el-input-number v-model="num" :min="1"  :max="this.good['stock']"  label="描述文字" size="small">12</el-input-number>
          </div>
          <div style="display: inline;text-align: center;margin-left: 110px">{{ this.num * this.good['price'] }}</div>
        </div>
        <div style="float: right;margin-top: 150px">
          <div style="margin-right: 0px">实付款:</div>
          <h2>{{ this.num * this.good['price'] }} ￥</h2>
          <br>
          <el-button type="primary" style="width: 200px;background-color: red" @click="submitForm">提交订单</el-button>
        </div>
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
    return {
      num:1,
      good: {},
      // total:this.num * this.good['price']
    }
  }, methods: {
    submitForm() {
      let data = new FormData
      var total = this.num * this.good['price']
      data.append('amount',total)
      data.append('gid',this.good['gid'])
      data.append('number',this.num)
      data.append('status',0)
      data.append('uid',this.$store.getters.getUser.uid)
      this.$axios({
        method:'put',
        url:'/v2.0/orders',
        data:data
      }).then(res=>{
        console.log(res)
      })
      this.$message({
        type:"success",
        message:"下单成功"
      })
      this.$router.push('home')
       // console.log(total)


      // this.$refs[forName].validate(valid => {
      //   if (valid) {
      //     let data = new FormData
      //     data.append('username', this.ruleForm.name)
      //     data.append('userphone', this.ruleForm.phone)
      //     data.append('useraddress', this.ruleForm.address)
      //     data.append('oid', 0)
      //     data.append('gid', this.good.gid)
      //     data.append('number', 1)
      //     data.append('amount', this.good.price)
      //     data.append('status', 0)
      //     this.$axios({
      //       method: "post",
      //       data: data,
      //       url: "/orders/insert"
      //     }).then(res => {
      //       if (res.data.code === 200) {
      //         this.$message({
      //           type: "success",
      //           message: "下单成功"
      //         })
      //         this.$router.push({//下单成功页面进行跳转
      //           name: "Gooddetail"
      //         })
      //       } else {
      //         this.$message({
      //           type: "info",
      //           message: res.data.msg
      //         })
      //       }
      //       // console.log(res.data)
      //     })
      //   } else {
      //     this.$message({
      //       type: "info",
      //       message: "格式错误"
      //     })
      //   }
      // })
    },

  },created() {
    this.good = this.$route.params['good']
    if (this.good === undefined)
      this.good = localStorage.getItem('good')
    else
      localStorage.setItem('good', this.good)
    // console.log(this.good)
    console.log(this.$store.getters.getUser.uid)
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
span{
  float: left;
}
</style>
