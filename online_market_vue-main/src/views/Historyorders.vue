<template>
  <el-container>
    <el-header>
      <Userdetail></Userdetail>
    </el-header>
    <el-container>
      <el-aside width="230px">
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
              {{ order.phone }}
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
              {{ order.address }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-office-building"></i>
                状态
              </template>
              <el-tag v-if="order.status===1" >正在交易</el-tag>
              <el-tag v-if="order.status===2" >交易成功</el-tag>
              <el-tag v-if="order.status===3" type="success">交易失败</el-tag>
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
    handleCurrentChange(val) {
      this.$axios.get("/v2.0/orders?currentpage="+val+"&size="+this.size).then(res=>{
        this.orderslist = res.data.data.data
        this.currentPage = res.data.data.current
        this.total = res.data.data.total
        this.size = res.data.data.size
        console.log(this.orderslist)
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
