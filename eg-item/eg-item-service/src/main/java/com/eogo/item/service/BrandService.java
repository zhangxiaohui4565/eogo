package com.eogo.item.service;

import com.eogo.page.PageResult;
import com.eogo.pojo.Brand;

public interface BrandService {
    /**
     * 分页查找品牌信息
     * @param page 页码
     * @param rows 每页显示的数量
     * @param sortBy 依据排序的列
     * @param desc 是否倒序
     * @param key 搜索的关键字
     * @return
     */
    PageResult<Brand> queryBrandByPageAndSort(
            Integer page, Integer rows, String sortBy, Boolean desc, String key);
}
