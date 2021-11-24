<template>
  <el-container>
    <el-header>
      <Userdetail></Userdetail>
    </el-header>
    <el-container>
      <el-aside width="180px" >
        <Side></Side>
      </el-aside>
    </el-container>
  </el-container>
</template>

<script>
import Userdetail from "@/components/Userdetail";
import Side from "@/components/Side";
export default {
  components: {Userdetail,Side},
  name: "Userrecords"
}
</script>

<style scoped>

</style>
