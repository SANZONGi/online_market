<template>
  <el-container>
    <el-header>
      <Userdetail></Userdetail>
    </el-header>
    <el-container>
      <el-aside width="600px">
        <div class="block">
          <el-image
              style=" width: 500px; height: 500px"
              :src="url"
              fit="contain  "></el-image>
        </div>
      </el-aside>
      <el-main v-if="rel">
        <el-form class="des">
          <el-form-item label="商品名:">{{ this.good['gname'] }}</el-form-item>
          <el-form-item label="商品价格:">{{ this.good['price'] }}</el-form-item>
          <el-form-item label="商品描述:">{{ this.good['description'] }}</el-form-item>
          <el-button v-if="good.status===0" type="primary" @click="Buy">购买</el-button>
          <el-button v-if="good.status===1" type="info">商品已冻结</el-button>
          <el-button v-if="good.status===2" type="info">商品已下架</el-button>
        </el-form>
      </el-main>
    </el-container>
  </el-container>


</template>

<script>
import Userdetail from "@/components/Userdetail";

export default {
  name: "Buy",
  components: {Userdetail},
  provide() {
    return {
      reload: this.reload
    }
  },
  data() {
    return {

      rel:true,
      url: 'https://i.loli.net/2021/11/02/xhGdp2qk31SEyMt.jpg',
      good: {},
    }
  }, methods: {
    reload(){
      this.rel = false
      this.$nextTick(function (){
        this.rel = true;
      })
    },
    Buy() {
      this.$router.push({
        name: 'Buy',
        params: {
          good: this.good
        }
      })
    }
  },
  created() {
    this.$axios({
      method: 'get',
      url: '/home'
    }).then(res => {
      let obj = res.data.data[0];
      this.good["uid"] = obj.uid
      this.good["gid"] = obj.gid
      this.good["gname"] = obj.gname
      this.good["image"] = obj.image
      this.good["price"] = obj.price
      this.good["stock"] = obj.stock
      this.good["status"] = obj.status
      this.good["description"] = obj.description
      this.url = obj.image
    })

    this.reload()

  }

}


</script>

<style scoped>
.block {
  margin: 0px 60px;
}

.des {
  margin: 20px 0;
}


</style>
