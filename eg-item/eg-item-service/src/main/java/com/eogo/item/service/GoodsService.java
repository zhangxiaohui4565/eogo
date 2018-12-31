package com.eogo.item.service;


import com.eogo.page.PageResult;
import com.eogo.pojo.Sku;
import com.eogo.pojo.Spu;
import com.eogo.pojo.SpuDetail;

import java.util.List;

/**
 * 商品Service
 */
public interface GoodsService {
    PageResult<Spu> querySpuBypage (Integer page, Integer rows, Boolean saleable, String key);

    void saveGoods(Spu spu);

    SpuDetail queryDetailById(Long spuId);

    List<Sku> querySkusBySpuId(Long spuId);

    /**
     * 修改商品属性
     * @param spu
     */
    void updateGoods(Spu spu);
}
