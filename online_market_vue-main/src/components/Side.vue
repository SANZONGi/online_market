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
            <el-menu-item index="3-1" @click="publish">商品发布</el-menu-item>
            <el-menu-item index="3-2" @click="historygood">历史商品</el-menu-item>
            <el-menu-item index="3-3" @click="frozengood">冻结商品</el-menu-item>
          </el-menu-item-group>
        </el-submenu>
      </el-menu>
    </el-col>
</template>

<script>
export default {
  name: "Side",
  // data(){
  //   var validatename = (rule, value, callback) => {
  //     if (value === '') {
  //       callback(new Error("请输入正确的名称(中文)"))
  //     } else callback();
  //   };
  // },
  //  var validatephone = (rule, value, callback) => {
  //   if (value.length !== 11) {
  //     callback(new Error("请输入正确的价格(仅限数字)"))
  //   } else{
  //     callback();}

  //
  //   var tel = "123456789";
  //   if(!/^\d+$/.test(tel)){
  //   alert("请输入正确的电话号码");
  // }
  //
  //
  //
  //


  // }
  // var validateaddress = (rule, value, callback) => {
  //   if (value === '') {
  //     callback(new Error("请输入地址"))
  //   } else
  //     callback();
  // };
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
      this.$axios({
        method:"get",
        url:"/good/alive"//从good/alive接受数据
      }).then(res => {
        if (res.data.data === true)       //有商品激活
        {
          this.$router.push({name: 'Publish'})
        }else {
          this.$message({
            type: "info",
            message: "已有商品,无法上架新商品"
          })
        }
      })

    },historygood(){
      this.$router.push({
        name: "Historygood"
      })
    },frozengood(){
      this.$router.push({
        name: "Frozen"
      })
    }
  }
}
</script>

<style scoped>

</style>
