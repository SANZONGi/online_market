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
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" style="margin-top: 60px" label-width="100px" class="pub-ruleForm">
          <el-form-item label="选择一级目录" style="margin-left: 100px" prop="pricata">
            <el-select v-model="ruleForm.pricata" placeholder="请选择" @change="ListChange">
            <el-option
                v-for="item in options"
                :key="item.id"
                :label="item.name"
                :value="item.id">
            </el-option>
          </el-select>
          </el-form-item>
          <el-form-item label="新增二级目录" style="margin-left: 100px" prop="newsec">
            <el-input v-model="ruleForm.newsec" style="width: 220px;"></el-input>
          </el-form-item>
          <el-form-item>
          <el-button style="margin-left: 120px" type="primary" @click="addsec">确认添加</el-button>
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
  name: "ChangeClassif",
  components:{Userdetail,Side},
  data(){
    var validatePri = (rule,value,callback) =>{
      if (!value){
        return callback(new Error('输入不能为空'))
      }
    }
    var validateNew = (rule,value,callback) =>{
      if (!value){
        return callback(new Error('输入不能为空'))
      }
    }
    return{
      options:[],
      options2:[],
      ruleForm: {
        pricata: '',
        newsec: ''
      },
      rules:{
        pricata: [
          {validator:validatePri,trigger:'blur'}
        ],
        newsec: [
          {validator:validateNew,trigger:'blur'}
        ]
      }
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
    addsec() {
      if (this.ruleForm.pricata === '') {
        this.$message({
          type: "warning",
          message: '请选择一级目录'
        })
      } else {
        let data = new FormData
        data.append('name', this.ruleForm.newsec)
        console.log(this.ruleForm.pricata)
        console.log(this.ruleForm.newsec)
        this.$axios({
          url: '/v2.0/sec/' + this.ruleForm.pricata,
          method: 'put',
          data: data
        }).then(res => {
          console.log(res)
          if (res.data.code===200){
            this.$message({
              type:'success',
              message:'插入成功'
            })
          }else if (res.data.code===400){
            this.$message({
              type:'warning',
              message:'二级目录名重复'
            })
          }
        })
      }
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
    this.$axios({
      url:'/v2.0/pri',
      method:'get'
    }).then(res=>{
      this.options=res.data.data
    })
  }
}
</script>

<style scoped>

</style>
