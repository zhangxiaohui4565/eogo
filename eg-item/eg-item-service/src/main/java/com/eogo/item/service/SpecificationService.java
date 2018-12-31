package com.eogo.item.service;

import com.eogo.pojo.SpecGroup;
import com.eogo.pojo.SpecParam;

import java.util.List;

public interface SpecificationService {
    //根据商品分类id查询商品规格组
    List<SpecGroup> queryGroupByCid(Long cid);

    /**
     * 根据商品规格组查询商品规格信息
     * @param groupId
     * @param cid
     * @param searching
     * @return
     */
    List<SpecParam> querySpecParamByGid(Long groupId, Long cid, Boolean searching);
}
