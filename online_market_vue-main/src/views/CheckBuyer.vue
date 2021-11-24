<template>
  <el-container>
    <el-header>
      <Userdetail></Userdetail>
    </el-header>
    <el-container>
      <el-aside width="180px">
        <Side></Side>
      </el-aside>
      <el-container v-if="this.orderslist.length!==0">
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
            <el-descriptions-item v-if="order.status===0">
              <template slot="label">
                操作
              </template>
              <div v-if="live === false">
              <el-button type="primary" size="mini" @click="accept(order)">确认交易</el-button>
              <el-button type="danger" size="mini" @click="reject(order)">取消交易</el-button>
              </div>
              <div v-else>
                <el-button type="primary" size="mini" @click="accept(order)" disabled>确认交易</el-button>
                <el-button type="danger" size="mini" @click="reject(order)" >取消交易</el-button>
              </div>
            </el-descriptions-item>
            <el-descriptions-item v-if="order.status===1">
              <template slot="label">
                交易结果
              </template>
              <el-button type="success" size="mini" @click="success(order)">交易成功</el-button>
              <el-button type="warning" size="mini" @click="fail(order)">交易失败</el-button>
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
<!--      <el-main>-->
<!--        <div>-->
<!--          暂时没有信息-->
<!--        </div>-->
<!--      </el-main>-->
    </el-container>
  </el-container>
</template>

<script>
import Userdetail from "@/components/Userdetail";
import Side from "@/components/Side";

export default {

  name: "CheckBuyer",
  components: {Side, Userdetail},
  inject: ["reload"],
  data() {
    return {
      orderslist: [],
      currentPage: 1,
      size: 5,
      total: 0,
      live:false
    }
  },
  created() {
    this.handleCurrentChange()
  },
  methods: {
    handleCurrentChange() {
      let url = "/v2.0/orders?currentpage=" + this.currentPage + "&size=" + this.size;
      // url += "&status=1&status=0"
      let status =[0,1]
      for (var i =0; i< status.length;i++){
        url += "&status="+status[i]
      }
      console.log(url)
      this.$axios.get(url).then(res => {
        this.orderslist = res.data.data.data
        this.currentPage = res.data.data.current
        this.total = res.data.data.total
        this.size = res.data.data.size
        console.log(res)
      })


      // this.$axios.get("/v2.0/good/alive").then(res=>{
      //   this.live = res.data.data
      // })
    },
    accept(order) {
      let data = new FormData;
      data.append("gid",order.gid)
      data.append("oid",order.oid)


      this.$axios({
        data:data,
        method:"post",
        url: "/v2.0/orders/accept/"  + order.oid,

      }).then((res) => {
        // this.$axios({
        //   method: "post",
        //   url: "good/frozen/"+ order.gid
        // })
        // if (res.data.code !== 200)
        //   console.log(res)
        //   this.$message.warning(res.data.msg);
        this.reload()//刷新
        console.log(res)
      })
    },
    reject(order) {
      let data = new FormData;
      data.append("gid",order.gid)
      data.append("oid",order.oid)
      this.$axios({
        data:data,
        method:"post",
        url: "/v2.0/orders/reject/"+order.oid
      }).then(() => {
        this.reload()
      })
    },
    success(order) {
      let data = new FormData;
      data.append("gid",order.gid)
      data.append("oid",order.oid)


      this.$axios({
        data: data,
        method:"post",
        url: "/v2.0/orders/success/" + order.oid
      }).then((res) => {
        console.log(res.data)
        if (res.data.code === 406 && res.data.data === 1){
          this.$message.warning(`当前商品已售空`);
        }else {
          this.$message.success("交易成功");
          this.reload()
        }
        this.reload()
      })
    },
    fail(order) {
      let data = new FormData;
      data.append("gid",order.gid)
      data.append("oid",order.oid)
      this.$axios({
        data:data,
        method:"post",
        url: "/v2.0/orders/reject/"+order.oid
      }).then(() => {
        this.reload()
      })
    }
  }
}
</script>

<style scoped>
.des {
  margin: 10px auto;
}
</style>
