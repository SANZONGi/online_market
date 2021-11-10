<template>
  <el-container>
    <el-header>
      <div>
        <div class="div1">
          <el-tabs v-model="activeName" @tab-click="handleClick" stretch="false">
            <el-tab-pane label="在售商品" name="first"></el-tab-pane>
            <el-tab-pane label="" name="second"></el-tab-pane>
            <el-tab-pane label="" name="third"></el-tab-pane>
            <el-tab-pane label="" name="fourth"></el-tab-pane>
          </el-tabs>
        </div>
<!--        <div class="div2">-->
<!--          <el-input-->
<!--            placeholder=""-->
<!--            v-model="input4">-->
<!--            <i slot="suffix" class="el-input__icon el-icon-search"></i>-->
<!--          </el-input>-->
<!--        </div>-->
        <div class="div3">
          <router-link :to="{name: 'Login'}">
            <el-avatar :size="40" :src="circleUrl">
            </el-avatar>
          </router-link>
        </div>
      </div>
    </el-header>
    <el-container>
<!--      <el-aside class="div4">-->
<!--        <el-menu-->
<!--            default-active="1"-->
<!--            class="el-menu-vertical-demo"-->
<!--            background-color="#545c64"-->
<!--            text-color="#fff"-->
<!--            active-text-color="#ffd04b">-->
<!--          <el-menu-item index="1">-->
<!--            <i class="el-icon-location"></i>-->
<!--            <span slot="title">数码</span>-->
<!--          </el-menu-item>-->
<!--          <el-menu-item index="2">-->
<!--            <i class="el-icon-menu"></i>-->
<!--            <span slot="title">衣物</span>-->
<!--          </el-menu-item>-->
<!--          <el-menu-item index="3">-->
<!--            <i class="el-icon-document"></i>-->
<!--            <span slot="title">洗护</span>-->
<!--          </el-menu-item>-->
<!--          <el-menu-item index="4">-->
<!--            <i class="el-icon-setting"></i>-->
<!--            <span slot="title">食品</span>-->
<!--          </el-menu-item>-->
<!--          <el-menu-item index="5">-->
<!--            <i class="el-icon-setting"></i>-->
<!--            <span slot="title">其他</span>-->
<!--          </el-menu-item>-->
<!--        </el-menu>-->
<!--      </el-aside>-->
<!--      <el-main class="div5">-->
<!--        <template>-->
<!--          <el-carousel :interval="4000" type="card" height="200px">-->
<!--            <el-carousel-item v-for="item in 3" :key="item">-->
<!--              <img src="https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/b30177d629bfbe2fb42251c1b8538f7b.jpg?w=632&h=340"-->
<!--                   alt="" class="img_adapter">-->
<!--            </el-carousel-item>-->
<!--          </el-carousel>-->
<!--        </template>-->
<!--      </el-main>-->
    </el-container>
    <el-divider></el-divider>
    <div align="center">
      <div v-for="(good,i) in goodlist" :key="(good,i)" class="div6">
        <router-link :to="{name: 'Gooddetail',params: {gid :good.gid} }">
          <el-card class="block">
            <el-image :src="goodlist[i].image"></el-image>
            <span><p align="center">{{ goodlist[i].gname }}</p></span>
          </el-card>
        </router-link>
      </div>
    </div>
  </el-container>
</template>
<script>
export default {
  data() {
    return {
      goodlist: [],
      activeName: 'first',
      input4: '',
      circleUrl: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
    };
  },
  methods: {
    handleClick(tab, event) {
      if (this.activeName === 'first') this.$router.push("Home")
      console.log(tab, event);
    },
  },
  created() {
    this.$axios({
      method: 'get',
      url: '/home'
    }).then((res) => {
      this.goodlist = res.data.data
    }).catch((error) => {
      console.log(error)
    })
  }
};
</script>


<style>
.block {
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

/*.div2 {*/
/*  margin-left: 70px;*/
/*  float: left;*/
/*  width: 30%;*/
/*  height: 60px;*/
/*}*/

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
  width: 220px;
  height: auto;
  display: inline-block;
  /*margin-left: 50px;*/
}

</style>
