<template>
  <el-container>
    <el-row class="tac">
      <el-col :span="25">
        <br>
        <br>
        <el-menu
            default-active="1"
            class="el-menu-vertical-demo">
          <el-menu-item index="1" @click="custhistory">
            <i class="el-icon-menu"></i>
            <span slot="title">历史下单记录</span>
          </el-menu-item>
          <el-menu-item index="2" @click="changecust">
            <i class="el-icon-document"></i>
            <span slot="title">修改信息</span>
          </el-menu-item>
        </el-menu>
      </el-col>

    </el-row>
  </el-container>
</template>

<script>
export default {
  name: "Custdetail",
  methods:{
    custhistory(){
      this.$router.push({name:'Custhistory'})
    },
    changecust(){
      this.$router.push({name:'Changecust'})
    }
  }
}
</script>

<style scoped>

</style>
