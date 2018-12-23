package com.eogo.item.mapper;

import com.eogo.pojo.Category;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * 继承IdListMapper就是为了批量查询  泛型为实体类的类型和主键的类型
 */
public interface CategoryMapper extends Mapper<Category> , IdListMapper <Category, Long >{
}
