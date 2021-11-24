<template>
  <el-container>
    <el-header>
      <Userdetail></Userdetail>
    </el-header>
    <el-container>
      <el-aside width="180px" >
        <Custside></Custside>
      </el-aside>
      <el-container>
      <el-main>
        <el-table :data="orders" stripe border style="width:100%" highlight-current-row>
          <el-table-column prop="gid" label="商品名称" align="center" min-width="120"></el-table-column>
          <el-table-column prop="amount" label="商品数量" align="center" min-width="100"></el-table-column>
          <el-table-column prop="date" label="交易时间" align="center" min-width="120"></el-table-column>
          <el-table-column prop="address" label="交易地点" align="center" min-width="100"></el-table-column>
          <el-table-column prop="status" label="交易结果" align="center" min-width="100">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status === 2">交易成功</el-tag>
            <el-tag v-if="scope.row.status === 3" type="success">交易失败</el-tag>
          </template>
          </el-table-column>
        </el-table>
        <el-footer>
          <div class="block">
            <el-pagination
                @current-change="handleCurrentChange"
                :current-page.sync="currentPage"
                :page-size="this.size"
                layout="prev, pager, next, jumper"
                :total="total">
            </el-pagination>
          </div>
        </el-footer>
      </el-main>
      </el-container>
    </el-container>
  </el-container>
</template>

<script>
import Userdetail from "@/components/Userdetail";
import Custside from "@/components/Custside";
export default {
  name: "custhistory",
  components:{Userdetail,Custside},
  data(){
    return{
      currentPage:1,
      total:0,
      orders:[],

      size:5//页面最大数据容量
    }
  },
  created() {
    this.handleCurrentChange()
  },
  methods:{
    handleCurrentChange(){
      this.$axios.get("/v2.0/orders?currentpage=" + this.currentPage + "&size=" + this.size + "&uid=" + this.$store.getters.getUser.uid).then(res => {
        this.orders = res.data.data.data
        this.currentPage = res.data.data.current
        this.total = res.data.data.total
        this.size = res.data.data.size
        // for (let i = 0; i < this.orderslist.length; i++) {
        //   this.orderslist[i].date = this.orderslist[i].date.replace(/T/g," ")
        // }
        console.log(res)
      })
        console.log(this.$store.getters.getUser.uid)
    }
  }
}
</script>

<style scoped>

</style>
