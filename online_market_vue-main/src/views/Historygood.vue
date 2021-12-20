<template>
  <el-container>
    <el-header>
      <Userdetail>
      </Userdetail>
    </el-header>
    <el-container>
      <el-aside width="230px">
        <Side>
        </Side>
      </el-aside>
      <el-container>
        <el-main>
          <el-table :data="tableData" height="300" class="table">
            <el-table-column label="商品编号" prop="gid" width="100px">
            </el-table-column>
            <el-table-column label="商品名称" prop="gname" width="120px">
            </el-table-column>
            <el-table-column label="商品价格" prop="price" width="120px">
            </el-table-column>
            <el-table-column label="商品类别" prop="catagory" width="150px">
            </el-table-column>
            <el-table-column label="商品描述" prop="description" width="250px">
            </el-table-column>
          </el-table>

        </el-main>
        <el-footer>
          <div class="block">
            <el-pagination
                @current-change="handleCurrentChange"
                :current-page.sync="currentPage"
                :page-size="this.size"
                layout="prev, pager, next, jumper"
                :total="total">
            </el-pagination>
          </div>
        </el-footer>
      </el-container>
    </el-container>
  </el-container>
</template>

<script>
import Userdetail from "@/components/Userdetail";
import Side from "@/components/Side";

export default {
  name: "Historygood",
  components: {Side, Userdetail},
  data() {
    return {
      tableData: [],
      currentPage: 1,
      size: 5,
      total: 0
    }
  },
  created() {
    this.handleCurrentChange(1)
  }, methods: {
    handleCurrentChange(val) {
      this.$axios.get("/v2.0/good/listofhis?currentpage=" + val + "&size=" + this.size).then(res => {
        this.tableData = res.data.data.records
        this.currentPage = res.data.data.current
        this.total = res.data.data.total
        this.size = res.data.data.size
        for (var i =0;i<this.tableData.length;i++){//获取商品分类
          if (this.tableData[i].priCata === 1){
            if (this.tableData[i].secCata === 1){
              this.tableData[i].catagory = '食品-零食'
            }
          }
          if (this.tableData[i].priCata === 1){
            if (this.tableData[i].secCata === 2){
              this.tableData[i].catagory = '食品-生鲜'
            }
          }
          if (this.tableData[i].priCata === 1){
            if (this.tableData[i].secCata === 3){
              this.tableData[i].catagory = '食品-茶酒'
            }
          }
          if (this.tableData[i].priCata === 2){
            if (this.tableData[i].secCata === 4){
              this.tableData[i].catagory = '家用-厨具'
            }
          }
          if (this.tableData[i].priCata === 2){
            if (this.tableData[i].secCata === 5){
              this.tableData[i].catagory = '家用-收纳'
            }
          }
          if (this.tableData[i].priCata === 2){
            if (this.tableData[i].secCata === 6){
              this.tableData[i].catagory = '家用-清洁'
            }
          }
          if (this.tableData[i].priCata === 3){
            if (this.tableData[i].secCata === 7){
              this.tableData[i].catagory = '服饰-男装'
            }
          }
          if (this.tableData[i].priCata === 3){
            if (this.tableData[i].secCata === 8){
              this.tableData[i].catagory = '服饰-女装'
            }
          }
          if (this.tableData[i].priCata === 3){
            if (this.tableData[i].secCata === 9){
              this.tableData[i].catagory = '服饰-童装'
            }
          }
          if (this.tableData[i].priCata === 4){
            if (this.tableData[i].secCata === 10){
              this.tableData[i].catagory = '运动-健身瑜伽'
            }
          }
          if (this.tableData[i].priCata === 4){
            if (this.tableData[i].secCata === 11){
              this.tableData[i].catagory = '运动-户外'
            }
          }
          if (this.tableData[i].priCata === 4){
            if (this.tableData[i].secCata === 12){
              this.tableData[i].catagory = '运动-游泳'
            }
          }
          if (this.tableData[i].priCata === 5){
            if (this.tableData[i].secCata === 13){
              this.tableData[i].catagory = '数码-手机'
            }
          }
          if (this.tableData[i].priCata === 5){
            if (this.tableData[i].secCata === 14){
              this.tableData[i].catagory = '数码-电视'
            }
          }
          if (this.tableData[i].priCata === 5){
            if (this.tableData[i].secCata === 15){
              this.tableData[i].catagory = '数码-电脑'
            }
          }
        }//定义商品分类
        console.log(this.tableData)
      })
    },
  }
}
</script>

<style scoped>
.table {
  width: available;
}
</style>
