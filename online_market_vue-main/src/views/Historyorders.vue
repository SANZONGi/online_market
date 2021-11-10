<template>
  <el-container>
    <el-header>
      <Userdetail></Userdetail>
    </el-header>
    <el-container>
      <el-aside width="180px">
        <Side></Side>
      </el-aside>
      <el-container>
        <el-main>
          <el-descriptions class="des" :column="3" size="mini" border v-for="(order,i) in this.orderslist" :key="i">
            <el-descriptions-item>
              <template slot="label">
                订单号
              </template>
              {{ order.oid }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-user"></i>
                买家姓名
              </template>
              {{ order.username }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-mobile-phone"></i>
                买家电话
              </template>
              {{ order.userphone }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-tickets"></i>
                商品号
              </template>
              {{ order.gid }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-timer"></i>
                交易时间
              </template>
              {{ order.date }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-office-building"></i>
                联系地址
              </template>
              {{ order.useraddress }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-office-building"></i>
                状态
              </template>
              <el-tag v-if="order.status===2" type="success">交易成功</el-tag>
              <el-tag v-if="order.status===3" typeof="danger">交易失败</el-tag>
            </el-descriptions-item>
          </el-descriptions>
        </el-main>
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
      </el-container>
    </el-container>
  </el-container>
</template>

<script>
import Userdetail from "@/components/Userdetail";
import Side from "@/components/Side";

export default {
  name: "Historyorders",
  components: {Side, Userdetail},
  data() {
    return {
      orderslist: [],
      currentPage: 1,
      size: 5,
      total: 0
    }
  },
  created() {
    this.handleCurrentChange(1)
  },
  methods: {
    // handleCurrentChange(val) {
    //
    //   this.$axios({
    //         url: "/orders/history",
    //         method: "get",
    //         data: JSON.stringify({
    //           "currentpage":val,
    //           "size": this.size
    //         })
    //       }
    //   ).then(res => {
    //     console.log(JSON.stringify({
    //       "currentpage":val,
    //       "size": this.size
    //     }))
    //     this.orderslist = res.data.data.records
    //     this.currentPage = res.data.data.current
    //     this.total = res.data.data.total
    //     this.size = res.data.data.size
    //     for (let i = 0; i < this.orderslist.length; i++) {
    //       //let arr = this.orderslist[i].date
    //       //this.orderslist[i].date = arr[0] + "/" + arr[1] + '/' + arr[2] + '  ' + arr[3] + ':' + arr[4] + ':' + arr[5];
    //
    //       this.orderslist[i].date = this.orderslist[i].date.replace(/T/g, " ")
    //     }
    //   })
    // }
    handleCurrentChange(val) {
      this.$axios.get("/orders/history?currentpage="+val+"&size="+this.size).then(res=>{
        this.orderslist = res.data.data.records
        this.currentPage = res.data.data.current
        this.total = res.data.data.total
        this.size = res.data.data.size
        for (let i = 0; i < this.orderslist.length; i++) {
          //let arr = this.orderslist[i].date
          this.orderslist[i].date = this.orderslist[i].date.replace(/T/g, " ")
        }
      })
    }
  }
}
</script>

<style scoped>
.des {
  margin: auto auto;
}
</style>
