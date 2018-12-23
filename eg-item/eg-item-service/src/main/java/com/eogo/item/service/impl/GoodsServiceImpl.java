package com.eogo.item.service.impl;

import com.eogo.egexception.EgException;
import com.eogo.egexception.status.EgExceptionStatus;
import com.eogo.item.mapper.SpuDetailMapper;
import com.eogo.item.mapper.SpuMapper;
import com.eogo.item.service.BrandService;
import com.eogo.item.service.CategoryService;
import com.eogo.item.service.GoodsService;
import com.eogo.page.PageResult;
import com.eogo.pojo.Brand;
import com.eogo.pojo.Spu;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private SpuDetailMapper spuDetailMapper;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;


    @Override
    public PageResult<Spu> querySpuBypage(Integer page, Integer rows, Boolean saleable, String key) {
        //分页
        PageHelper.startPage(page,rows);
        //过滤
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(key)){
            criteria.andLike("title","%"+key+"%");
            if (saleable != null){
                criteria.andEqualTo("saleable",saleable);
            }
        }
        //默认以商品的更新时间排序
        example.setOrderByClause("last_update_time Desc");

        List<Spu> spus = spuMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(spus)){
            throw new EgException(EgExceptionStatus.RESOURCE_NOT_FOUND);
        }
        //解析分类和品牌的查询
        loadCategoryAndBrandName(spus);
        //解析分页结果
        PageInfo<Spu>  info = new PageInfo<>(spus);

        return new PageResult<>(info.getTotal(),spus);
    }
    private void loadCategoryAndBrandName (List<Spu> spus){
        for (Spu spu : spus) {
            //处理分类名称
            List<String> names = categoryService.queryByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()))
                    .stream().map(category -> category.getName()).collect(Collectors.toList());
            //把集合拼成字符串
            spu.setCname(StringUtils.join(names,"/"));

            Brand brand = brandService.queryById(spu.getBrandId());
            spu.setBname(brand.getName());
        }
    }
}
