<template>
    <el-col :span="20">
      <el-menu
          default-active="1"
          class="el-menu-vertical-demo"
          @open="handleOpen"
          @close="handleClose"><!--  handleopen是指事件展开指定的 sub-menu-->
        <!-- handleclose是指收起指定的submenu
             handleselect是菜单激活回调-->
        <el-submenu index="1">
          <template slot="title">
            <i class="el-icon-location"></i>
            <span>我的信息</span>
          </template>
          <el-menu-item-group>
            <el-menu-item @click="changepassword">修改密码</el-menu-item>
          </el-menu-item-group>
        </el-submenu>
        <el-submenu index="2">

          <template slot="title">
            <i class="el-icon-menu"></i>
            订单管理
          </template>
          <el-menu-item-group>
            <el-menu-item index="2-1" @click="checkbuyer">购买人信息</el-menu-item>
            <el-menu-item index="2-2" @click="historyorders">历史订单</el-menu-item>
          </el-menu-item-group>
        </el-submenu>
        <el-submenu index="3">

          <template slot="title">
            <i class="el-icon-document"></i>
            我的商品</template>
          <el-menu-item-group>
            <el-menu-item index="3-1" @click="publish" v-if="flag">商品发布</el-menu-item>
            <el-menu-item index="3-2" @click="historygood">历史商品</el-menu-item>
            <el-menu-item index="3-3" @click="frozengood">冻结商品</el-menu-item>
          </el-menu-item-group>
        </el-submenu>
        <el-submenu index="4">
          <template slot="title">
            <i class="el-icon-user"></i>
            用户信息
          </template>
          <el-menu-item-group>
            <el-menu-item index="4-1" @click="checkuser">查看用户信息</el-menu-item>
            <el-menu-item index="4-2" @click="userrecords">用户下单记录</el-menu-item>
          </el-menu-item-group>
        </el-submenu>
      </el-menu>
    </el-col>
</template>

<script>
export default {
  name: "Side",
  data(){
    return{
      flag : 1
    }
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath)
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath)
    },changepassword()//changepwd方法是指点击按钮之后路由跳转到修改密码界面，下面几个方法类似
    {
      this.$router.push({name: 'Changepassword'})
    },checkbuyer()
    {
      this.$router.push({name: 'CheckBuyer'})
    },historyorders()
    {
      this.$router.push({name: 'Historyorders'})
    },publish()
    {
      // this.$axios({
      //   method:"get",
      //   url:"/good/alive"//从good/alive接受数据
      // }).then(res => {
      //   if (res.data.data === true)       //有商品激活
      //   {
      //     this.flag = 1
      //     this.$router.push({name: 'Publish'})
      //   }else {
      //     this.flag = 0
      //     this.$message({
      //       type: "info",
      //       message: "已有商品,无法上架新商品"
      //     })
      //   }
      // })
      this.$router.push("Publish")

    },historygood(){
      this.$router.push({
        name: "Historygood"
      })
    },frozengood(){
      this.$router.push({
        name: "Frozen"
      })
    },checkuser(){
      this.$router.push({ name:"Checkuser"})
    },userrecords(){
      this.$router.push({ name:"Userrecords"})
    }
  },
  created() {
    // this.$axios({
    //   method:"get",
    //   url:"/good/alive"//从good/alive接受数据
    // }).then(res => {
    //   if (res.data.data === true)       //有商品激活
    //   {
    //     this.flag = 1
    //   }else {
    //     this.flag = 0
    //   }
    // })
  }
}
</script>

<style scoped>

</style>
