<template>
  <el-container>
    <el-header>
      <Userdetail></Userdetail>
    </el-header>
    <el-container>
      <el-aside width="230px">
        <Side></Side>
      </el-aside>

      <el-main>
        <el-tabs type="card" @tab-click="handleClick">
          <el-tab-pane label="新增目录" name="new"></el-tab-pane>
          <el-tab-pane label="修改目录" name="change"></el-tab-pane>
          <el-tab-pane label="删除目录" name="delete"></el-tab-pane>
        </el-tabs>
        <el-form style="margin-top: 60px" label-width="100px" class="ruleForm">
          <el-form-item  label="选择类别:">
            一级分类:&nbsp;<el-select v-model="pricata" placeholder="请选择" @change="ListChange">
            <el-option
                v-for="item in options"
                :key="item.id"
                :label="item.name"
                :value="item.id">
            </el-option>
          </el-select>
            二级分类:&nbsp;<el-select v-model="seccata" clearable placeholder="请选择" >
            <el-option
                v-for="item in options2"
                :key="item.id"
                :label="item.name"
                :value="item.id">
            </el-option>
          </el-select>
            <el-form-item>
              <el-button type="danger" @click="del">删除</el-button>
            </el-form-item>
          </el-form-item>
        </el-form>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import Userdetail from "@/components/Userdetail";
import Side from "@/components/Side";
export default {
  name: "DelClassif",
  components:{Userdetail,Side},
  data(){
    return{
      pricata:'',
      seccata:'',
      options:[],
      options2:[]
    }
  },
  methods:{
    ListChange(){
      this.seccata=''
      // console.log(this.pricata)
      this.$axios({
        url:'/v2.0/sec/' + this.pricata,
        method:'get'
      }).then(res=>{
        console.log(res)
        this.options2=res.data.data
      })
    },
    del(){
      console.log(this.pricata )
      console.log(this.seccata)
      this.$axios({
        url:'/v2.0/' + this.pricata + '/' + this.seccata,
        method:'delete'
      }).then(res=>{
        console.log(res)
        if (res.data.code===200){
          this.$message({
            type:'success',
            message:'删除成功'
          })
          this.init()
        }else {
          this.$message({
            type:'warning',
            message:res.data.msg
          })
        }
      })
    },
    init(){
      this.$axios({
        url:'/v2.0/pri',
        method:'get'
      }).then(res=>{
        this.options=res.data.data
      })
    },
    handleClick(tab) {
      if (tab.name==='new'){
        this.$router.push({name:'Classif'})
      }
      if (tab.name==='change'){
        this.$router.push({name:'ChangeClassif'})
      }
      if (tab.name==='delete'){
        this.$router.push({name:'DelClassif'})
      }
    }
  },
  created() {//初始化界面时候就读取数据库目录表中的数据增加到下拉框
    this.init()
  }
}
</script>

<style scoped>

</style>
