<template>
  <el-container>
    <el-header>
      <Userdetail></Userdetail>
    </el-header>
    <el-container>
      <el-aside width="180px" >
        <Side></Side>
      </el-aside>
      <el-main>
        <el-table :data="user" stripe border style="width:100%" highlight-current-row>
          <el-table-column prop="username" label="用户名" align="center" min-width="120"></el-table-column>
          <el-table-column prop="phone" label="电话" align="center" min-width="100"></el-table-column>
          <el-table-column prop="address" label="默认交易地点" align="center" min-width="120"></el-table-column>
        </el-table>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import Userdetail from "@/components/Userdetail";
import Side from "@/components/Side";
export default {
  components: {Userdetail,Side},
  name: "Checkuser",
  data(){
    return{
      user:[]
    }
  },
  created() {
    this.$axios({
      method:"get",
      url: "/v2.0/users"
    }).then(res=>{
      this.user = res.data.data
      console.log(this.user)
    })
    // this.handleCurrentChange(1)
  },
  methods:{
    // handleCurrentChange(val){
    //
    // }
  }
}

</script>

<style scoped>

</style>
