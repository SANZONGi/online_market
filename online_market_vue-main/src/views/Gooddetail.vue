<template>
  <el-container>
<!--    顶栏-->
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
        <!--登录按钮-->
        <div class="div3">
          <el-button type="primary" @click="login" icon="el-icon-user" circle></el-button>
          <el-button type="success" icon="el-icon-refresh" @click="refresh" circle></el-button>
        </div>
      </div>
    </el-header>
    <el-container>
      <el-aside width="600px">
          <div class="block">
<!--            走马灯展示图片-->
            <el-carousel indicator-position="outside" loop="true" :height="imgHeight + 'px'" >
              <el-carousel-item v-for="item in this.img" :key="item">
                <img
                    ref="imgHeight"
                    :src="item"
                    style="width: 100%;"
                    @load="imgLoad"
                    >
              </el-carousel-item>
            </el-carousel>
          </div>
      </el-aside>
<!--      使用el-from展示商品详情-->
      <el-main v-if="rel">
        <el-form class="des">
          <el-form-item label="商品名称:">{{this.good['gname']}}</el-form-item>
          <el-form-item label="商品价格:">{{this.good['price']}}</el-form-item>
          <el-form-item label="商品库存:">{{this.good['stock']}}</el-form-item>
          <el-form-item label="商品描述:"><br>
<!--           富文本展示商品详情-->
            <div class="ql-container">
              <div class="ql-editor" v-html="this.good['description']" />
            </div>
          </el-form-item>
          <el-button v-if="good.status === 1" type="danger">商品已冻结</el-button>
          <el-button v-else-if="good.stock >=1 " type="primary" @click="Buy">购买</el-button>
          <el-button v-if="good.stock < 1 " type="info">商品已售空</el-button>
        </el-form>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: "Buy",
  inject: ['reload'],
  provide() {
    return {
      reload: this.reload
    }
  },
  data() {
    return {
      activeName: 'first',
      rel:true,
      url: 'https://i.loli.net/2021/11/02/xhGdp2qk31SEyMt.jpg',
      img:[],
      good : {},
      imgHeight:"",
      nowrole:'',

    }
  },
  methods: {
    reload(){
      this.rel = false
      this.$nextTick(function (){
        this.rel = true;
      })
    },
    Buy() {
      this.$router.push({
        name: 'Buy',
        params : {
          good: this.good
        }
      })
    },
    imgLoad(){
      this.$nextTick(()=>{
        this.imgHeight=this.$refs.imgHeight[0].height
        console.log(this.$refs.imgHeight[0].height)
        var testH=document.getElementById("test")
        testH.style.height=this.imgHeight + "px"
      })
    },
    refresh(){
      this.$router.go(0)
    },
    handleClick(tab, event) {
      if (this.activeName === 'first') this.$router.push("Home")
      console.log(tab, event);

    },
    login(){
      if (this.nowrole === '0' ){
        this.$router.push("Userhome")
      }else{
        this.$router.push("Custhistory")
      }
      console.log(this.nowrole)
    },
  },
  created() {
    let gid = this.$route.params.gid
    if (gid === undefined){
      gid = localStorage.getItem('gid')
    } else{
      localStorage.setItem('gid',gid)
    }
    this.$axios({
      method:'get',
      url: '/v2.0/good/'+gid
    }).then(res=>{
      // this.good["uid"]=this.$store.getters.getUser.uid
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
      })
      console.log(this.good)
      console.log("s")
    })
    this.imgLoad()
    window.addEventListener('resize',()=>{//设置图片适应走马灯大小
      this.imgHeight=this.$refs.imgHeight[0].height
      this.imgLoad()
    },false)
    this.nowrole = localStorage.getItem("role")
    this.reload()
  },
}


</script>

<style scoped>
.block{
  margin: 50px 60px;
}
.des{
  margin: 20px 0;
}
.div1 {
  float: left;
  /*width: auto;*/
  width: 40%;
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
