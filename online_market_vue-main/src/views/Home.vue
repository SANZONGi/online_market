<template class="bk">
  <el-container>
    <!--顶部-->
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
          <el-input
              placeholder="请输入需要搜索的商品名或商品描述"
              v-model="keyword">
            <el-button slot="append" icon="el-icon-search" @click="onSearch"></el-button>
          </el-input>
        </div>
        <!--按钮模块-->
        <div class="div3">
          <el-button type="primary" @click="login" icon="el-icon-user" circle></el-button>
          <el-button type="success" icon="el-icon-refresh" @click="refresh" circle></el-button>
        </div>
      </div>
    </el-header>
    <el-container>
      <el-aside class="div4">
      <!--侧边菜单栏-->
        <el-menu
            default-active="2"
            class="el-menu-vertical-demo"
            background-color="#545c64"
            text-color="#fff"
            active-text-color="#ffd04b"
            unique-opened="true">

          <el-submenu
              :popper-append-to-body="false"
              popper-class="amenu"
              v-for="pri in pricata"
              :key="pri.key.id"
              :index="pri.key.name"
          >
            <template slot="title">{{ pri.key.name }}</template>
            <el-menu-item
                v-for="sec in pri.value"
                :key="sec.id"
                @click="chose(sec.priId,sec.id)"
                :index="sec.name"
            >
              {{sec.name}}
            </el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <!--商品列表-->
      <el-main class="div5">
        <div v-if="this.flag ===2 && this.flag1===1 && this.searchtotal!==0" style="font-family: 等线">为您找到以下商品:</div>
        <div v-if="this.flag ===2 && this.searchtotal === 0" style="font-family: 等线">没有找到您所需要的商品</div>
        <template>
          <div align="center">
            <div v-for="(good,i) in goodlist" :key="(good,i)" class="div6" v-if="good.status===0">
              <router-link :to="{name: 'Gooddetail',params: {gid :good.gid} }">
                <el-card class="block">
                  <el-image style="width: 400px;height: 120px" fit="contain" class="image" :src="goodlist[i].img_url"></el-image>
                  <span><p align="center">{{ goodlist[i].gname }}</p></span>
                </el-card>
              </router-link>
            </div>
          </div>
        </template>
      </el-main>
    </el-container>
      <!--分页-->
    <el-pagination
        style="margin-left: 300px"
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        :page-size="this.size"
        layout="prev, pager, next, jumper"
        :total="total">
    </el-pagination>
    <el-divider></el-divider>
  </el-container>
</template>
<script>
export default {
  provide() {
    return {
      reload: this.reload
    }
  },
  data() {
    return {
      currentPage:1,
      size:6,
      total:0,
      rel: true,
      input1: '',
      goodlist: [],
      url: '',
      activeName: 'first',
      keyword: '',
      circleUrl: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
      nowrole:'',
      flag:1,//判断是那种类型页面的分页
      pri:0,
      sec:0,
      pricata:[],
      flag1:0,//判断开始
      searchtotal:1//判断搜索到的是否为空
    };
  },
  methods: {
    chose(pri,sec){
      this.flag=0
      this.pri=pri
      this.sec=sec
      this.handleCurrentChange(1)
    },//选择菜单实现分类展示
    handleCurrentChange(val){//3种分页
      if (this.flag===1) {//主页分页
        this.$axios.get("/v2.0/good/dto?currentpage=" + val + "&size=" + this.size + "&status=0").then(res => {
          this.goodlist = res.data.data.data
          this.size = res.data.data.size
          this.total = res.data.data.total
          this.currentPage = res.data.data.current
          console.log(res)
        })
      }else if (this.flag===0){//分类分页
        this.$axios.get("/v2.0/good/catalogue/" + this.pri + "/" + this.sec + "?currentpage=" + val + "&size=" + this.size + "&status=0").then(res=>{
          this.goodlist = res.data.data.data
          this.size = res.data.data.size
          this.total = res.data.data.total
          this.currentPage = res.data.data.current
          console.log(res)
        })
      }else if (this.flag===2){//搜索栏分页
        this.$axios.get("/v2.0/good/search?currentpage=" + val + "&size=" + this.size + "&status=0&val=" + this.keyword).then(res=>{
          this.goodlist = res.data.data.data
          this.size = res.data.data.size
          this.total = res.data.data.total
          this.currentPage = res.data.data.current
          this.searchtotal = res.data.data.total
        })
      }
    },

    reload() {
      this.rel = false
      this.$nextTick(function () {
        this.rel = true;
      })
    },//reload函数
    refresh(){
      this.$router.go(0)
    },//刷新页面
    handleClick(tab, event) {
      if (this.activeName === 'first') this.$router.push("Home")
      console.log(tab, event);
      this.$router.go(0)
    },
    onSearch() {//搜索功能，搜索商品名或者描述包含关键词的商品
      this.flag=2
      if (this.keyword===''){
        this.$message({
          type:'warning',
          message:'输入不能为空'
        })
      }else {//输入不为空
        this.handleCurrentChange(1)
        this.flag1=1
        console.log(this.searchtotal)
      }
    },//搜索
    login(){
      if (this.nowrole === '0' ){
        this.$router.push("Userhome")
      }else{
        this.$router.push("Custhistory")
      }
    },//登录按钮
    handleOpen(key,keyPath){
      console.log(key,keyPath)
    },

  },
  created() {
    this.$axios({
      url:'/v2.0/cata/all',
      method:'get'
    }).then(res=>{
      this.pricata=res.data.data
    })
    this.flag = 1
    this.handleCurrentChange(1)
    this.reload()
    this.nowrole = localStorage.getItem("role")
    console.log(this.$store.getters.getUser.uid)
  }
};
</script>

<style scoped>
.block {
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
}
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
.div4 {
  float: left;
  max-width: 250px;
}


.img_adapter {
  max-width: 100%;
  max-height: 200px;
}

.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}

.div6 {
  margin: 10px 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
  width: 200px;
  height: auto;
  display: inline-block;
  /*margin-left: 50px;*/
}

.search {
  width: 400px;
  margin-left: 300px;
}

.category {
  width: 700px;
  height: 150px;
  border-style: solid;
  border-width: 1px;
  margin-left: 150px;
  margin-top: 30px;
}

.image {
  width: 100%;
}

.bk{background-color: #f5f5f5;}
</style>
