package com.eogo.item.service;


import com.eogo.page.PageResult;
import com.eogo.pojo.Spu;

/**
 * 商品Service
 */
public interface GoodsService {
    PageResult<Spu> querySpuBypage (Integer page, Integer rows, Boolean saleable, String key);
}
