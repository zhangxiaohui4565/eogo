package com.eogo.item.web;

import com.eogo.item.service.BrandService;
import com.eogo.page.PageResult;
import com.eogo.pojo.Brand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("brand")
@Api(tags = "品牌信息的API")
public class BrandController {

    @Autowired
    private BrandService brandService;


    /**
     *
     * @param page 第几页
     * @param rows    每页的行数
     * @param sortBy 排序的列
     * @param desc   是否排序
     * @param key  搜索条件
     * @return
     */
    @GetMapping("page")
    @ApiOperation(value = "根据关键字ley查询品牌信息",notes = "查询品牌信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "key", value = "关键字", paramType = "query", required = false, dataType = "String"),
        @ApiImplicitParam(name = "sortBy", value = "排序字段", paramType = "query", required = false, dataType = "String"),
        @ApiImplicitParam(name = "desc",value = "是否倒序",paramType = "query",required = false,dataType = "Boolean")
    })
    public ResponseEntity<PageResult<Brand>> queryBrandByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
            @RequestParam(value = "key", required = false) String key) {
        PageResult<Brand> result = this.brandService.queryBrandByPageAndSort(page,rows,sortBy,desc, key);
        if (result == null || result.getItems().size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<Void> saveBrands (Brand brand ,@RequestParam("cids") List<Long> cids){
        brandService.saveBrand(brand,cids);
        //此处因为是新增无返回值，不需要使用body,build()即可。
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
