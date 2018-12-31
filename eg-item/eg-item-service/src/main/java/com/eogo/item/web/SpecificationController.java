package com.eogo.item.web;

import com.eogo.item.service.SpecificationService;
import com.eogo.pojo.SpecGroup;
import com.eogo.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("spec")
public class SpecificationController {
    @Autowired
    SpecificationService specificationService;

    /**
     * 根据商品分类id进行查询商品规格组
     * @param cid
     * @return
     */
    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>>  queryGroup (@PathVariable("cid") Long cid){
        return ResponseEntity.ok(specificationService.queryGroupByCid(cid));
    }
    @GetMapping("params")
    public ResponseEntity<List<SpecParam>>  querySpecParam (
            @RequestParam(value = "gid",required = false)  Long gid,
            @RequestParam(value = "cid",required = false)  Long cid,
            @RequestParam(value = "searching",required = false)  Boolean searching){
        return ResponseEntity.ok(specificationService.querySpecParamByGid(gid,cid,searching));
    }
}
