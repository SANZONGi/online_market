<template>
  <el-container>
    <el-header>
      <Userdetail>
      </Userdetail>
    </el-header>
    <el-container>
      <el-aside width="180px">
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
            <el-table-column label="商品状态" prop="status" width="120px">
            </el-table-column>
            <el-table-column label="商品描述" prop="description" width="200px">
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
      this.$axios.get("/good/listofhis?currentpage=" + val + "&size=" + this.size
      ).then(res => {
        this.tableData = res.data.data.records
        this.currentPage = res.data.data.current
        this.total = res.data.data.total
        this.size = res.data.data.size
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