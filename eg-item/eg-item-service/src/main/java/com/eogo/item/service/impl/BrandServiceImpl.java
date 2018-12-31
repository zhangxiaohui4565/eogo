package com.eogo.item.service.impl;

import com.eogo.egexception.EgException;
import com.eogo.egexception.status.EgExceptionStatus;
import com.eogo.item.mapper.BrandMapper;
import com.eogo.item.service.BrandService;
import com.eogo.page.PageResult;
import com.eogo.pojo.Brand;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public PageResult<Brand> queryBrandByPageAndSort(
            Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        // 开始分页
        PageHelper.startPage(page, rows);
        // 过滤
        Example example = new Example(Brand.class);
        if (StringUtils.isNotBlank(key)) {
            example.createCriteria().andLike("name", "%" + key + "%")
                    .orEqualTo("letter", key);
        }
        if (StringUtils.isNotBlank(sortBy)) {
            // 排序
            String orderByClause = sortBy + (desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }
        // 查询
        List<Brand> lists = brandMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(lists)) {
            throw new EgException(EgExceptionStatus.RESOURCE_NOT_FOUND);
        }

        PageInfo<Brand> pageInfo = new PageInfo<>(lists);
        // 返回结果 pageResult为自己定义的分页包装类
        return new PageResult<>(pageInfo.getTotal(), lists);
    }

    @Override
    @Transactional
    public Integer saveBrand(Brand brand, List<Long> cids) {
        //此处供选择的方法有insertSelective()---选择非空字段进行插入
        int count = brandMapper.insert(brand);
        if (count != 1) {
            throw new EgException(EgExceptionStatus.RESOURCE_INSERT_ERROR);
        }
        //将关联信息新增到中间表
        for (Long cid : cids) {
            int result = brandMapper.insertCategoryBrand(cid, brand.getId());
            if (result != 1) {
                throw new EgException(EgExceptionStatus.RESOURCE_INSERT_ERROR);
            }
        }
        return 1;
    }
    @Override
    public Brand queryById (Long id){
        Brand brand = brandMapper.selectByPrimaryKey(id);
        if (brand == null){
            throw new EgException(EgExceptionStatus.RESOURCE_NOT_FOUND);
        }
        return  brand;
    }

    @Override
    public List<Brand> getBrandsByCid(Long cid) {
        List<Brand> brands = brandMapper.getBrandsByCid(cid);
        if (CollectionUtils.isEmpty(brands)){
            throw  new EgException(EgExceptionStatus.RESOURCE_NOT_FOUND);
        }
        return brands;
    }
}
