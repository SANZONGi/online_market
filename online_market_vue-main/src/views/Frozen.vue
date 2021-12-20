<template>
  <el-container>
    <el-header>
      <Userdetail></Userdetail>
    </el-header>
    <el-container>
      <el-aside width="230px">
        <Side></Side>
      </el-aside>
      <el-main>
        <el-table :data="goods" stripe border style="width:100%" highlight-current-row>
          <el-table-column prop="gid" label="商品号" align="center" min-width="120"></el-table-column>
          <el-table-column prop="gname" label="商品名" align="center" min-width="100"></el-table-column>
          <el-table-column prop="price" label="商品价格" align="center" min-width="120"></el-table-column>
          <el-table-column prop="stock" label="商品库存" align="center" min-width="120"></el-table-column>
          <el-table-column prop="status" label="商品状态" align="center" min-width="100">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.status===1" type="danger">冻结中</el-tag>
              <el-tag v-if="scope.row.status!==1" type="success">出售中</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" min-width="100">
            <template slot-scope="scope">
              <el-button v-if="scope.row.status!==1" type="info" @click="frozen(scope)">冻结</el-button>
              <el-button v-if="scope.row.status===1" type="success" @click="unfrozen(scope)">解冻</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
            @current-change="handleCurrentChange"
            :current-page.sync="currentPage"
            :page-size="this.size"
            layout="prev, pager, next, jumper"
            :total="total">
        </el-pagination>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import Userdetail from "@/components/Userdetail";
import Side from "@/components/Side";
export default {
  inject: ["reload"],
  name: "Frozen",
  components: {Userdetail,Side},
  data(){
    return{
      goods: [],
      size:8,
      currentPage:1,
      total:0
    }
  },methods:{
    frozen(val){
      this.$axios({
        method: "post",
        url: "/v2.0/good/frozen/"+ val.row.gid
      }).then(res => {
        this.$message({
          message: res.data.msg,
          type: "success"
        })
        this.reload()
      })
    },
    unfrozen(val){
      this.$axios({
        method: "post",
        url: "/v2.0/good/unfrozen/"+ val.row.gid
      }).then(res => {
        this.$message({
          message: res.data.msg,
          type: "success"
        })
        this.reload()
      })
    },
    handleCurrentChange(val){
      this.$axios.get("/v2.0/good/dto?currentpage=" + val + "&size=" + this.size + "&status=0&status=1").then(res=>{
        this.goods=res.data.data.data
        this.currentPage=res.data.data.current
        this.size=res.data.data.size
        this.total=res.data.data.total
        console.log(res)
      })
    }
  },
  created() {
    // this.$axios({
    //   method:"get",
    //   url: "/v2.0/good"
    // }).then(res => {
    //   this.goods = res.data.data
    //   console.log(res)
    // })
    this.handleCurrentChange(1)
  }
}
</script>

<style scoped>

</style>
