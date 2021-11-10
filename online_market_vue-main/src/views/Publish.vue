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
<!--          <el-form-item label="商品数量">-->
<!--            <el-input v-model="form.number"></el-input>-->
<!--          </el-form-item>-->
          <el-form-item label="商品描述" prop="description">
            <el-input type="textarea" v-model="ruleForm.description" style="width: 400px"></el-input>
          </el-form-item>
          <el-form-item label="商品图片">
<!--            <el-upload v-if="flag === 0"-->
<!--                       accept="image/jpeg,image/gif,image/png"-->
<!--                       class="upload-demo"-->
<!--                       drag-->
<!--                       :on-change="onUploadChange"-->
<!--                       :auto-upload="false"-->
<!--                       action=""-->
<!--                       multiple-->
<!--                       :limit="1"-->
<!--                       :on-exceed="handleExceed"-->
<!--                       :disabled="false"-->
<!--            >-->
<!--              <i class="el-icon-upload"></i>-->
<!--              <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>-->
<!--              <div class="el-upload__tip" slot="tip">只能上传1张jpg/png文件， 且不超过500 KB</div>-->
<!--            </el-upload>-->
<!--         已有文件，禁用上传  -->
            <el-upload
                :on-preview="handlePreview"
                :on-remove="handleRemove"
                :on-change="onUploadChange"
                :limit="1"
                action=""
                :multiple="false"
                :auto-upload="false"
                :file-list="fileList"
                list-type="picture">
              <el-button size="small" type="primary" v-if="flag===0">点击上传</el-button>
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
      flag:0,
      BASE64: '',
      ruleForm: {
        gname: '',
        description: '',
        price: '',
        image: '',

      },fileList:[],
      rules:{
        gname: [{validator: validategname, trigger: 'blur'}
        ],
        description:[
          {validator: validatedescription, trigger: 'blur'}
        ],
        price: [
          {validator: validateprice, trigger: 'blur'}
        ]
      }
    };
  },
  methods: {
    onSubmit() {
      let data = new FormData;
      data.append('uid',this.$store.getters.getUser.uid)
      data.append('gname', this.ruleForm.gname)
      data.append('description', this.ruleForm.description)
      data.append('price', this.ruleForm.price)
      // data.append('stock', this.form.number)
      data.append('stock', 1)
      data.append('image', this.BASE64)
      this.$axios({
        method: "post",
        url: '/publishGood',
        data: data
      }).then(res => {
        console.log(res.data)
        if (res.data.code === 200){
          this.$message({
            message: '发布成功',
            type: 'success'
          });
          this.$router.push({name: 'Gooddetail'})
        }
      })
    },
    onUploadChange(file) {
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

      var reader = new FileReader()
      reader.readAsDataURL(file.raw)
      reader.onload = () => {
        this.BASE64 = reader.result
      };

      this.flag = 1
    },
    handleRemove() {
      this.flag = 0
    },
    handlePreview(file) {
      console.log(file);
    }
  }
}
</script>

<style>


</style>
