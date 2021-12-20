<template>
  <el-container>
    <el-header>
      <Userdetail></Userdetail>
    </el-header>
    <el-container>
      <el-aside width="230px" >
        <Side></Side>
      </el-aside>
      <el-main>
        <el-table :data="user" stripe border style="width:100%" highlight-current-row>
          <el-table-column prop="username" label="用户名" align="center" min-width="120"></el-table-column>
          <el-table-column prop="phone" label="电话" align="center" min-width="100"></el-table-column>
          <el-table-column prop="address" label="默认交易地点" align="center" min-width="120"></el-table-column>
          <el-table-column label="用户下单记录" align="center">
            <template slot-scope="scope">
              <el-button type="primary" @click="check(scope.row)">查看</el-button>
              </template>
          </el-table-column>
        </el-table>
      </el-main>




      <el-dialog
          title="用户下单记录"
          :visible.sync="dialogVisible"
          width="60%"
          >
        <el-table
            :data="userrecords"
            style="width: 100%">
          <el-table-column
              prop="username"
              label="用户名"
              width="120">
          </el-table-column>
          <el-table-column
              prop="gid"
              label="商品编号"
              width="150">
          </el-table-column>
          <el-table-column
              prop="number"
              label="购买数量"
          >
          </el-table-column>
          <el-table-column
              prop="address"
              label="交易地点"
              width="180">
          </el-table-column>
          <el-table-column
              prop="date"
              label="交易时间"
              width="180">
          </el-table-column>
          <el-table-column
              prop="status"
              label="交易结果">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.status === 2">交易成功</el-tag>
              <el-tag v-if="scope.row.status === 3" type="success">交易失败</el-tag>
            </template>
          </el-table-column>
        </el-table>



        <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
        </span>


        <el-footer>
          <div class="block">
              <el-pagination
                  @current-change="handcurrentchange"
                  :current-page.sync="currentPage"
                  :page-size="this.size"
                  layout="prev, pager, next, jumper"
                  :total="total">
              </el-pagination>
          </div>
        </el-footer>


      </el-dialog>
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
      dialogVisible:false,
      user:[],
      currentPage: 1,
      size: 5,
      total: 0,
      userrecords:[],
      uid:''
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
    check(data){
      this.dialogVisible=true
      this.uid = data.uid
      let url = "/v2.0/orders?currentpage=" + this.currentPage + "&size=" + this.size
      url += '&uid=' + this.uid
      this.$axios.get(url).then(res => {
        this.userrecords = res.data.data.data
        this.currentPage = res.data.data.current
        this.total = res.data.data.total
        this.size = res.data.data.size
        console.log(res)
      })
    },
    handcurrentchange(val){
      this.dialogVisible=true
      let url = "/v2.0/orders?currentpage=" + val + "&size=" + this.size
      url += '&uid=' + this.uid
      this.$axios.get(url).then(res => {
        this.userrecords = res.data.data.data
        this.currentPage = res.data.data.current
        this.total = res.data.data.total
        this.size = res.data.data.size
        console.log(res)
      })
    }
  }
}

</script>

<style scoped>

</style>
