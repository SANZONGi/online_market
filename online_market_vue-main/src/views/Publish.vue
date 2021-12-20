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
<!--          <el-form-item label="商品描述" prop="description">-->
<!--            <el-input type="textarea" v-model="ruleForm.description" style="width: 400px"></el-input>-->
<!--          </el-form-item>-->
          <el-form-item label="商品描述">
            <div class="edit_container">
              <quill-editor
                  v-model="ruleForm.description"
                  ref="myQuillEditor"
                  class="editor"
                  :options="editorOption"
                  @blur="onEditorBlur($event)"
                  @focus="onEditorFocus($event)"
                  @change="onEditorChange($event)">
              </quill-editor>
            </div>
          </el-form-item>
          <el-form-item  label="商品分类">
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
import { quillEditor } from "vue-quill-editor"; //调用编辑器
import 'quill/dist/quill.core.css';
import 'quill/dist/quill.snow.css';
import 'quill/dist/quill.bubble.css';

const toolbarOptions = [
  ['bold', 'italic', 'underline', 'strike'], // 加粗 斜体 下划线 删除线 -----['bold', 'italic', 'underline', 'strike']
  ['blockquote', 'code-block'], // 引用  代码块-----['blockquote', 'code-block']
  [{ header: 1 }, { header: 2 }], // 1、2 级标题-----[{ header: 1 }, { header: 2 }]
  [{ list: 'ordered' }, { list: 'bullet' }], // 有序、无序列表-----[{ list: 'ordered' }, { list: 'bullet' }]
  [{ script: 'sub' }, { script: 'super' }], // 上标/下标-----[{ script: 'sub' }, { script: 'super' }]
  [{ indent: '-1' }, { indent: '+1' }], // 缩进-----[{ indent: '-1' }, { indent: '+1' }]
  [{'direction': 'rtl'}], // 文本方向-----[{'direction': 'rtl'}]
  [{ size: ['small', false, 'large', 'huge'] }], // 字体大小-----[{ size: ['small', false, 'large', 'huge'] }]
  [{ header: [1, 2, 3, 4, 5, 6, false] }], // 标题-----[{ header: [1, 2, 3, 4, 5, 6, false] }]
  [{ color: [] }, { background: [] }], // 字体颜色、字体背景颜色-----[{ color: [] }, { background: [] }]
  [{ font: [] }], // 字体种类-----[{ font: [] }]
  [{ align: [] }], // 对齐方式-----[{ align: [] }]
  ['clean'], // 清除文本格式-----['clean']
  // ['image'] // 链接、图片、视频-----['link', 'image', 'video']
  ['link']
]
export default {
  name: "Publish",
  components: {Userdetail, Side,quillEditor},
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
      options: [],
      options2:[],
      pricata: '',
      seccata:'',
      BASE64: '',
      fileList:[],

      content:'',
      editorOption: {
        placeholder: '请输入商品描述',
        theme: 'snow',
        modules: {
          toolbar: {
            container: toolbarOptions
          }
        }
      },

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
      console.log(this.ruleForm.description)
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
      console.log(this.pricata)
      console.log(this.seccata)
    },//按钮事件，发布商品
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
    },//上传图片事件
    handlePreview(file) {
      console.log(file);
    },
    ListChange(){
      this.seccata=''
      console.log(this.pricata)
      this.$axios({
        url:'/v2.0/sec/' + this.pricata,
        method:'get'
      }).then(res=>{
        console.log(res)
        this.options2=res.data.data
      })
    },
    onEditorBlur(){}, // 失去焦点事件
    onEditorFocus(){}, // 获得焦点事件
    onEditorChange(){
      console.log(this.ruleForm.description)
    }, // 内容改变事件

  },
  computed: {
    editor() {
      return this.$refs.myQuillEditor.quill;
    },
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

<style>
.editor {
  line-height: normal !important;

}
.ql-snow .ql-tooltip[data-mode=link]::before {
  content: "请输入链接地址:";
}
.ql-snow .ql-tooltip.ql-editing a.ql-action::after {
  border-right: 0px;
  content: '保存';
  padding-right: 0px;
}

.ql-snow .ql-tooltip[data-mode=video]::before {
  content: "请输入视频地址:";
}

.ql-snow .ql-picker.ql-size .ql-picker-label::before,
.ql-snow .ql-picker.ql-size .ql-picker-item::before {
  content: '14px';
}
.ql-snow .ql-picker.ql-size .ql-picker-label[data-value=small]::before,
.ql-snow .ql-picker.ql-size .ql-picker-item[data-value=small]::before {
  content: '10px';
}
.ql-snow .ql-picker.ql-size .ql-picker-label[data-value=large]::before,
.ql-snow .ql-picker.ql-size .ql-picker-item[data-value=large]::before {
  content: '18px';
}
.ql-snow .ql-picker.ql-size .ql-picker-label[data-value=huge]::before,
.ql-snow .ql-picker.ql-size .ql-picker-item[data-value=huge]::before {
  content: '32px';
}

.ql-snow .ql-picker.ql-header .ql-picker-label::before,
.ql-snow .ql-picker.ql-header .ql-picker-item::before {
  content: '文本';
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="1"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="1"]::before {
  content: '标题1';
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="2"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="2"]::before {
  content: '标题2';
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="3"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="3"]::before {
  content: '标题3';
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="4"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="4"]::before {
  content: '标题4';
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="5"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="5"]::before {
  content: '标题5';
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="6"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="6"]::before {
  content: '标题6';
}

.ql-snow .ql-picker.ql-font .ql-picker-label::before,
.ql-snow .ql-picker.ql-font .ql-picker-item::before {
  content: '标准字体';
}
.ql-snow .ql-picker.ql-font .ql-picker-label[data-value=serif]::before,
.ql-snow .ql-picker.ql-font .ql-picker-item[data-value=serif]::before {
  content: '衬线字体';
}
.ql-snow .ql-picker.ql-font .ql-picker-label[data-value=monospace]::before,
.ql-snow .ql-picker.ql-font .ql-picker-item[data-value=monospace]::before {
  content: '等宽字体';
}
</style>
