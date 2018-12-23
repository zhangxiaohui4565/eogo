package com.eogo.item.service;

import com.eogo.pojo.Category;

import java.util.List;

/**
 * 根据父节点查询商品分类信息
 */
public interface CategoryService {
    List<Category> queryListByParent(Long pid);
    List<Category> queryByIds (List<Long> ids );
}
