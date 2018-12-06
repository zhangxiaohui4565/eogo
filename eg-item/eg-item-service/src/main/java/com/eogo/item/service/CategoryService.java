package com.eogo.item.service;

import com.eogo.egexception.EgException;
import com.eogo.egexception.status.EgExceptionStatus;
import com.eogo.item.mapper.CategoryMapper;
import com.eogo.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public List<Category> queryListByParent(Long pid) {
        Category category = new Category();
        category.setParentId(pid);
        List<Category> categories = categoryMapper.select(category);
        if (CollectionUtils.isEmpty(categories)){
            throw new EgException(EgExceptionStatus.RESOURCE_NOT_FOUND);
        }
        return  categories;
    }
}
