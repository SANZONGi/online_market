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
                fit="contain"></el-image>
          </div>
      </el-aside>
      <el-main v-if="rel">
        <el-form class="des">
          <el-form-item label="商品名:">{{this.good['gname']}}</el-form-item>
          <el-form-item label="商品价格:">{{this.good['price']}}</el-form-item>
          <el-form-item label="商品库存:">{{this.good['stock']}}</el-form-item>
          <el-form-item label="商品描述:">{{this.good['description']}}</el-form-item>
          <el-button type="primary" @click="Buy">购买</el-button>
          <el-button v-if="good.stock < 1" type="info">商品已售空</el-button>
<!--          <el-button v-if="good.status===2" type="info">商品已下架</el-button>-->
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
      img:[],
      good : {}
    }
  },methods: {
    reload(){
      this.rel = false
      this.$nextTick(function (){
        this.rel = true;
      })
    },
    Buy(){
      this.$router.push({
        name: 'Buy',
        params : {
          good: this.good
        }
      })
    }
  },
  created() {
    let gid = this.$route.params.gid
    if (gid === undefined)
      gid = localStorage.getItem('gid')
    else
      localStorage.setItem('gid',gid)

    this.$axios({
      method:'get',
      url: '/v2.0/good/'+gid
    }).then(res=>{
      this.good["uid"]=this.$store.getters.getUser.uid
      this.good["gid"]=res.data.data.gid
      this.good["gname"]=res.data.data.gname
      this.good["price"]=res.data.data.price
      this.good["stock"]=res.data.data.stock
      this.good["status"]=res.data.data.status
      this.good["description"]=res.data.data.description
      this.$axios({
        method:'get',
        url:'/v2.0/img/' + gid
      }).then(res=>{
        this.img = res.data
        if (this.img[1] !== undefined) {
          this.url = this.img[1]
        }
        else this.url = this.img[0]
        console.log(this.url)
      })
    })

    this.reload()
  }

}


</script>

<style scoped>
.block{
  margin: 0px 60px;
}
.des{
  margin: 20px 0;
}


</style>
