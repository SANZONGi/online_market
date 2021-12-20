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
        <el-form style="margin-top: 60px" :model="dynamicValidateForm" ref="dynamicValidateForm" label-width="100px" class="demo-dynamic">
          <el-form-item
              prop="pricata"
              label="一级目录"
          >
            <el-input v-model="dynamicValidateForm.priName" style="width: 300px"></el-input>
          </el-form-item>
          <el-form-item
              style="width: 400px;"
              v-for="(domain, index) in dynamicValidateForm.secName"
              :label="'二级目录' + index"
              :key="domain.key"
              :prop="'domains.' + index + '.value'"
          >
            <el-input v-model="domain.value" style="display: inline;width: 300px;"></el-input>
            <el-button @click.prevent="removeDomain(domain)" style="display: inline;" size="small">删除</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm">提交</el-button>
            <el-button @click="addDomain">新增二级目录</el-button>
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
  name: "Classif",
  components:{Userdetail,Side},
  data(){
    return{
      dynamicValidateForm: {
        secName: [{
          value: ''
        }],
        priName: ''
      },
      sec:[]
    }
  },
  methods: {
    submitForm() {
      for (let i =0;i<this.dynamicValidateForm.secName.length;i++){
        this.sec[i] = this.dynamicValidateForm.secName[i].value
      }
      let data = new FormData
      data.append('priName',this.dynamicValidateForm.priName)
      data.append('name',this.sec)
      this.$axios({
        url:'/v2.0/seclist',
        method:'put',
        data:data
      }).then(res=>{
        console.log(res)
        if (res.data.code===200){
          this.$message({
            type:'success',
            message:'插入目录成功'
          })
        }else {
          this.$message({
            type:'warning',
            message:res.data.msg
          })
        }
      })


      // for (let i =0;i<this.dynamicValidateForm.secName.length;i++){
      //   this.sec[i] = this.dynamicValidateForm.secName[i].value
      // }
      // console.log(this.sec)

      // console.log(this.dynamicValidateForm.secName[0].value)
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    removeDomain(item) {
      var index = this.dynamicValidateForm.secName.indexOf(item)
      if (index !== -1) {
        this.dynamicValidateForm.secName.splice(index, 1)
      }
    },
    addDomain() {
      this.dynamicValidateForm.secName.push({
        value: '',

      });
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
  }
}
</script>

<style scoped>

</style>
