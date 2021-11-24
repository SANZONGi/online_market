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
          <el-input
              placeholder="请输入需要搜索的商品名"
              v-model="keyword">
            <el-button slot="append" icon="el-icon-search" @click="onSearch"></el-button>
          </el-input>
        </div>

        <!--登录按钮-->
        <div class="div3">
          <el-button type="primary" @click="login" icon="el-icon-user" circle></el-button>
          <el-button type="success" icon="el-icon-refresh" circle></el-button>
        </div>
      </div>
    </el-header>
    <el-container>
      <!--左侧分类目录菜单-->
      <el-aside class="div4">
        <el-menu
            default-active="1"
            class="el-menu-vertical-demo"
            background-color="#545c64"
            text-color="#fff"
            active-text-color="#ffd04b"
            unique-opened="true">
          <el-submenu index="1">

            <span slot="title">分类1</span>
            <el-menu-item index="1-1">二级分类1</el-menu-item>
            <el-menu-item index="1-2">二级分类2</el-menu-item>
          </el-submenu>
          <el-submenu index="2">
            <span slot="title">分类2</span>
            <el-menu-item index="2-1">二级分类1</el-menu-item>
            <el-menu-item index="2-2">二级分类2</el-menu-item>
          </el-submenu>
          <el-submenu index="3">
            <span slot="title">分类3</span>
            <el-menu-item index="3-1">二级分类1</el-menu-item>
            <el-menu-item index="3-2">二级分类2</el-menu-item>
          </el-submenu>
          <el-submenu index="4">
            <span slot="title">分类4</span>
            <el-menu-item index="4-1">二级分类1</el-menu-item>
            <el-menu-item index="4-2">二级分类2</el-menu-item>
          </el-submenu>
          <el-submenu index="5">
            <span slot="title">分类5</span>
            <el-menu-item index="5-1">二级分类1</el-menu-item>
            <el-menu-item index="5-2">二级分类2</el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>

      <!--商品列表-->
      <el-main class="div5">
        <template>
          <div align="center">
            <div v-for="(good,i) in goodlist" :key="(good,i)" class="div6">
              <router-link :to="{name: 'Gooddetail',params: {gid :good.gid} }">
                <el-card class="block">
                  <el-image fit="fill" class="image" :src="goodlist[i].imgUrl"></el-image>
                  <span><p align="center">{{ goodlist[i].gname }}</p></span>
                </el-card>
              </router-link>
            </div>
          </div>
        </template>
      </el-main>


    </el-container>


    <el-divider></el-divider><!-- 分割线,下半区放商品，上半区放筛选条件 -->


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
      rel: true,
      input1: '',
      goodlist: [],
      url: '',
      activeName: 'first',
      keyword: '',
      circleUrl: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
      nowrole:''
    };
  },
  methods: {
    reload() {
      this.rel = false
      this.$nextTick(function () {
        this.rel = true;
      })
    },
    handleClick(tab, event) {
      if (this.activeName === 'first') this.$router.push("Home")
      console.log(tab, event);
    },
    onSearch() {//搜索功能
      this.$axios({
        methods:"get",
        url: "/v2.0/good/search/?val=" + this.keyword,

      }).then(res => {
        console.log(res)
        this.goodlist=res.data.data
        this.reload()
      })
      console.log(this.keyword)
    },
    login(){

      if (this.nowrole === '0' ){
        this.$router.push("Userhome")
      }else{
        this.$router.push("Custhistory")
      }
    }
  },
  created() {
    this.$axios({
      method: 'get',
      url: '/v2.0/good/dto'
    }).then((res) => {
      this.goodlist = res.data.data
      console.log(res)
    }).catch((error) => {
      console.log(error)
    })
    this.reload()
    this.nowrole = localStorage.getItem("role")
    console.log(this.$store.getters.getUser.uid)
  }
};
</script>


<style>
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
  max-width: 200px;
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

</style>
