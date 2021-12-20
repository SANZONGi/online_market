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

        </div>

        <!--登录按钮-->
        <div class="div3">
          <el-button type="primary" @click="login" icon="el-icon-user" circle></el-button>
          <el-button type="success" icon="el-icon-refresh" @click="refresh" circle></el-button>
        </div>
      </div>
    </el-header>
      <h2>确认订单信息</h2>
<!--      <span>默认交易地址:</span>-->
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

        <div class="label4" style="display: inline-block;">
          <div style="width: 150px;display: inline;">{{ this.good['gname'] }}</div>
          <div style="width: 150px;margin-left: 170px;display: inline;">{{ this.good['price'] }}</div>
          <div style="width: 150px;margin-left: 170px;display: inline;">{{ this.good['stock'] }}</div>
          <div style="width: 150px;margin-left: 170px;display: inline;">
            <el-input-number v-model="num" :min="1"  :max="this.good['stock']"  size="small"></el-input-number>
          </div>
          <div style="width: 150px;margin-left: 100px;display: inline;">{{ this.num * this.good['price'] }}</div>
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
      activeName:'first',
      num:1,
      good: {},
      nowrole:'',
      gooddetail:[{
        gname:'',
        price:0,
        stock:0,
        total:0
      }]
      // gooddetail:{
      //   gname:'',
      //   price:'',
      //   stock: 0,
      //   number:0,
      //   total:0
      // }
    }
  }, methods: {
    handleClick(){
      if (this.activeName==='first')
        this.$router.push({name:'Home'})
    },
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
    login(){
      if (this.nowrole === '0' ){
        this.$router.push("Userhome")
      }else{
        this.$router.push("Custhistory")
      }
      console.log(this.nowrole)
    },
  },created() {
    this.good = this.$route.params['good']
    if (this.good === undefined)
      this.good = localStorage.getItem('good')
    else
      localStorage.setItem('good', this.good)
    this.nowrole = localStorage.getItem("role")
    this.gooddetail[0].gname = this.good['gname']
    this.gooddetail[0].price = this.good['price']
    this.gooddetail[0].stock = this.good['stock']
    console.log(this.good['stock'])
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
.detail{
  margin-left: 150px;
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
