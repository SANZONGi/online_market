<template>
  <el-container>
    <el-header>
      <Userdetail></Userdetail>
    </el-header>
    <el-container>
      <el-aside width="180px">
        <Side></Side>
      </el-aside>

      <el-main>
        <el-form ref="ruleForm" :model="ruleForm" status-icon :rules="rules" label-width="80px" class="pub-ruleForm">
          <el-form-item label="商品名称" prop="gname">
            <el-input v-model="ruleForm.gname" style="width: 200px"></el-input><!--prop用于进行传值验证,即要验证的字段名-->
          </el-form-item>
          <el-form-item label="商品价格" prop="price">
            <el-input v-model="ruleForm.price" style="width: 200px"></el-input>
          </el-form-item>
          <el-form-item label="商品数量" prop="stock">
            <el-input v-model="ruleForm.stock" style="width: 100px"></el-input>
          </el-form-item>
<!--          <el-form-item label="商品数量">-->
<!--            <el-input v-model="form.number"></el-input>-->
<!--          </el-form-item>-->
          <el-form-item label="商品描述" prop="description">
            <el-input type="textarea" v-model="ruleForm.description" style="width: 400px"></el-input>
          </el-form-item>
          <el-form-item label="商品分类">
            一级分类:&nbsp;<el-input v-model="pricata" prop="pricate" style="width: 200px"></el-input><br>
            二级分类:&nbsp;<el-input v-model="seccata" prop="pricate" style="width: 200px;margin-top: 20px"></el-input>
          </el-form-item>
          <el-form-item label="商品图片">
            <el-upload
                :on-preview="handlePreview"
                :on-change="onUploadChange"
                action=""
                :multiple="true"
                :auto-upload="false"
                :file-list="fileList"
                list-type="picture">
              <el-button size="small" type="primary">点击上传</el-button>
              <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
            </el-upload>

          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">发布</el-button>
            <el-button>取消</el-button>
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
  name: "Publish",
  components: {Userdetail, Side},
  data() {
      var validategname = (rule, value, callback) => {
        if (value === '') {
          callback(new Error("请输入商品名称"))
        } else callback();
      };
     var validateprice = (rule, value, callback) => {
       if (!isNaN(value) && value !== '') {
         callback();
       } else
         callback(new Error("请输入正确价格（数字）"))
     };
    var validatedescription = (rule, value, callback) => {
      if (value === '') {
        callback(new Error("请输入商品描述"))
      } else
        callback();
    };

    return {
      pricata: '',
      seccata:'',
      BASE64: '',
      fileList:[],
      ruleForm: {
        gname: '',
        description: '',
        price: '',
        image: '',
        stock:''
      },
      rules:{
        gname: [{validator: validategname, trigger: 'blur'}
        ],
        description:[
          {validator: validatedescription, trigger: 'blur'}
        ],
        price: [
          {validator: validateprice, trigger: 'blur'}
        ]
      },
    };
  },
  methods: {
    onSubmit() {
      let data = new FormData;
      data.append('uid',this.$store.getters.getUser.uid)
      data.append('gname', this.ruleForm.gname)
      data.append('description', this.ruleForm.description)
      data.append('price', this.ruleForm.price)
      data.append('stock', this.ruleForm.stock)
      data.append('priCata',this.pricata)
      data.append('secCata',this.seccata)
      data.append('status',1)
      this.fileList.forEach(file => {
        data.append('files',file.raw)
      })
      console.log()
      this.$axios({
        method: "put",
        url: '/v2.0/good',
        data: data
      }).then(res => {
        console.log(res.data)
        if (res.data.code === 200){
          this.$message({
            message: '发布成功',
            type: 'success'
          });
          this.$router.push({name: 'Home'})
        }
      })

    },
    onUploadChange(file,fileList) {
      const isImage = (file.raw.type === 'image/jpeg' || file.raw.type === 'image/png' || file.raw.type === 'image/gif');
      const isLimit = file.size / 1024 / 512 < 1;
      if (!isImage) {
        this.$message.error('上传文件只能是图片格式!')
        return false;
      }
      if (!isLimit)
      {
        this.$message.error('上传文件大小不能超过 500 KB!')
        return false;
      }

      this.fileList = fileList;
      // var reader = new FileReader()
      // reader.readAsDataURL(file.raw)
      // reader.onload = () => {
      //   this.BASE64 = reader.result
      // };
    },
    handlePreview(file) {
      console.log(file);
    }
  },
  created() {//初始化界面时候就读取数据库目录表中的数据增加到下拉框
  }
}
</script>

<style>


</style>
