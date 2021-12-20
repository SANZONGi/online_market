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
            <span style="font-family: Algerian">尊敬的{{ this.$store.getters.getUser.username }}，欢迎</span>
        </div>

        <!--登录按钮-->
        <div class="div3">

          <el-button @click="logout" type="danger" round>退出登录</el-button>
        </div>
      </div>
    </el-header>
    <el-container>
      <el-aside width="200px" >
        <Custside></Custside>
      </el-aside>
      <el-container>
      <el-main>
        <el-table :data="orders" stripe border style="width:100%" highlight-current-row>
          <el-table-column prop="gid" label="商品编号" align="center" min-width="120"></el-table-column>
          <el-table-column prop="number" label="购买数量" align="center" min-width="100"></el-table-column>
          <el-table-column prop="date" label="交易时间" align="center" min-width="120"></el-table-column>
          <el-table-column prop="address" label="交易地点" align="center" min-width="100"></el-table-column>
          <el-table-column prop="status" label="交易结果" align="center" min-width="100">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status === 2">交易成功</el-tag>
            <el-tag v-if="scope.row.status === 3" type="success">交易失败</el-tag>
            <el-tag v-if="scope.row.status === 0" type="info">正在交易</el-tag>
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
      activeName:'first',
      size:5//页面最大数据容量
    }
  },
  created() {
    this.handleCurrentChange()
  },
  methods:{
    handleClick(tab, event) {
      if (this.activeName === 'first') this.$router.push("Home")
      console.log(tab, event);
    },
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
    },
    logout(){
      this.$axios({
        method:"post",
        url:"/v2.0/users/logout"
      }).then(res=>{
        if (res.data.data === 0 ){
          this.$message({
            type:"success",
            message:"成功退出登录"
          })
          this.$store.commit("REMOVE_INFO")
          this.$router.push("login")
        }else {
          this.$message({
            type:"warning",
            message:res.data.msg
          })
          this.$store.commit("REMOVE_INFO")
          this.$router.push("login")
        }
      })
    }
  }
}
</script>

<style scoped>
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
  margin-top: 10px;
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
