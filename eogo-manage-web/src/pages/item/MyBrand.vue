<template>
  <div>
<!--header中的都是在页面显示的标题
  items是后台返回的数据即brands
-->
    <v-data-table
      :headers="headers"
      :items="brands"
      :pagination.sync="pagination"
      :total-items="totalBrands"
      :loading="loading"
      class="elevation-1"
    >
      <!--循环的显示数据-->
      <template slot="items" slot-scope="props">
        <td class="text-xs-right">{{ props.item.id }}</td>
        <td class="text-xs-right">{{ props.item.name }}</td>
        <td class="text-xs-right">{{ props.item.image }}</td>
        <td class="text-xs-right">{{ props.item.letter }}</td>
        <td>修改、删除</td>
      </template>
    </v-data-table>
  </div>
</template>

<script>
  export default {
    name: "MyBrand",
    //数据
    data() {
      return {
        headers: [
          {text: "品牌id", value: "id", align: "center", sortable: true},
          {text: "品牌名称", value: "name", align: "center", sortable: false},
          {text: "LOGO", value: "image", align: "center", sortable: false},
          {text: "品牌首字母", value: "letter", align: "center", sortable: true},
          {text:"操作" ,align:"center", sortable:false}
        ],
        brands: [],
        pagination: {},
        totalBrands: 0,
        loading: false,

      }

    },
    //create 页面一加载就进行创建（钩子函数）this.brands为后台返回的数据
    created() {
      //此处是相对路径 ，会在之前定义的baseUrl上进行拼接
      //this就是当前的vue对象，使用自定义的$http
      this.$http.get("/brand/page",{params:{
        page:1
        }}).then(resp=>{});
    }

  }
</script>

<style scoped>

</style>
