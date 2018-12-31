package com.eogo.item.service.impl;

import com.eogo.egexception.EgException;
import com.eogo.egexception.status.EgExceptionStatus;
import com.eogo.item.mapper.SkuMapper;
import com.eogo.item.mapper.SpuDetailMapper;
import com.eogo.item.mapper.SpuMapper;
import com.eogo.item.mapper.StockMapper;
import com.eogo.item.service.BrandService;
import com.eogo.item.service.CategoryService;
import com.eogo.item.service.GoodsService;
import com.eogo.page.PageResult;
import com.eogo.pojo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GoodsServicedsServiceImpl implements GoodsService {
    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private SpuDetailMapper spuDetailMapper;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private StockMapper stockMapper;


    @Override
    public PageResult<Spu> querySpuBypage(Integer page, Integer rows, Boolean saleable, String key) {
        //分页
        PageHelper.startPage(page, rows);
        //过滤
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("title", "%" + key + "%");
            if (saleable != null) {
                criteria.andEqualTo("saleable", saleable);
            }
        }
        //默认以商品的更新时间排序
        example.setOrderByClause("last_update_time Desc");

        List<Spu> spus = spuMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(spus)) {
            throw new EgException(EgExceptionStatus.RESOURCE_NOT_FOUND);
        }
        //解析分类和品牌的查询
        loadCategoryAndBrandName(spus);
        //解析分页结果
        PageInfo<Spu> info = new PageInfo<>(spus);

        return new PageResult<>(info.getTotal(), spus);
    }

    @Override
    @Transactional
    public void saveGoods(Spu spu) {
        //新增Spu
        spu.setId(null);
        spu.setCreateTime(new Date());
        spu.setLastUpdateTime(spu.getCreateTime());
        spu.setSaleable(true);
        spu.setValid(false);
        int count = spuMapper.insert(spu);
        if (count != 1) {
            throw new EgException(EgExceptionStatus.RESOURCE_INSERT_ERROR);
        }
        //新增detail
        SpuDetail spuDetail = spu.getSpuDetail();
        spuDetail.setSpuId(spu.getId());
        int insert = spuDetailMapper.insert(spuDetail);
        if (insert != 1) {
            throw new EgException(EgExceptionStatus.RESOURCE_INSERT_ERROR);
        }
        saveSkuAndStock(spu);
    }

    @Override
    public SpuDetail queryDetailById(Long spuId) {
        SpuDetail spuDetail = spuDetailMapper.selectByPrimaryKey(spuId);
        if (spuDetail == null) {
            throw new EgException(EgExceptionStatus.RESOURCE_NOT_FOUND);
        }
        return spuDetail;
    }

    /**
     * 根据spuid查询所属的sku商品
     *
     * @param spuId
     * @return
     */
    @Override
    public List<Sku> querySkusBySpuId(Long spuId) {
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        List<Sku> skus = skuMapper.select(sku);
        if (CollectionUtils.isEmpty(skus)) {
            throw new EgException(EgExceptionStatus.RESOURCE_NOT_FOUND);
        }
//        for (Sku s : skus) {
//            Stock stock = stockMapper.selectByPrimaryKey(s.getId());
//            if (stock == null){
//                throw new EgException(EgExceptionStatus.RESOURCE_NOT_FOUND);
//            }
//            s.setStock(stock.getStock());
//        }

        List<Long> skuIds = skus.stream().map(Sku::getId).collect(Collectors.toList());
        List<Stock> stocks = stockMapper.selectByIdList(skuIds);
        if (CollectionUtils.isEmpty(stocks)) {
            throw new EgException(EgExceptionStatus.RESOURCE_NOT_FOUND);
        }
        Map<Long, Integer> stockMap = stocks.stream().
                collect(Collectors.toMap(Stock::getSkuId, Stock::getStock));
        skus.forEach(s -> s.setStock(stockMap.get(s.getId())));
        return skus;
    }

    @Override
    public void updateGoods(Spu spu) {
        /**
         *  删除sku和stock
         */
        Sku sku = new Sku();
        sku.setSpuId(spu.getId());
        //查询sku
        List<Sku> skuList = skuMapper.select(sku);
        if (!CollectionUtils.isEmpty(skuList)){
            skuMapper.delete(sku);
            //由于库存是和sku关联的 进行关联删除
            List<Long> ids = skuList.stream().map(Sku::getId).collect(Collectors.toList());
            stockMapper.deleteByIdList(ids);
        }
        //修改spu
        spu.setLastUpdateTime(new Date());
        int i = spuMapper.updateByPrimaryKeySelective(spu);
        if (i != 1){
            throw new EgException(EgExceptionStatus.RESOURCE_UPDATE_ERROR);
        }
        //修改spudetail
        i = spuDetailMapper.updateByPrimaryKeySelective(spu.getSpuDetail());
        if (i != 1){
            throw new EgException(EgExceptionStatus.RESOURCE_UPDATE_ERROR);
        }
        //新增sku和stock
        saveSkuAndStock(spu);
    }

    /**
     * 分类和品牌的查询
     *
     * @param spus
     */
    private void loadCategoryAndBrandName(List<Spu> spus) {
        for (Spu spu : spus) {
            //处理分类名称
            List<String> names = categoryService.queryByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()))
                    .stream().map(category -> category.getName()).collect(Collectors.toList());
            //把集合拼成字符串
            spu.setCname(StringUtils.join(names, "/"));

            Brand brand = brandService.queryById(spu.getBrandId());
            spu.setBname(brand.getName());
        }
    }

    /**
     * 对新增的sku和库存数据进行存储
     * @param spu
     */
    private void saveSkuAndStock(Spu spu){
        int count ;
        //新增sku
        List<Sku> skus = spu.getSkus();
        List<Stock> stocks = new ArrayList<>();
        for (Sku sku : skus) {
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            sku.setSpuId(spu.getId());
            count = skuMapper.insert(sku);
            if (count != 1) {
                throw new EgException(EgExceptionStatus.RESOURCE_INSERT_ERROR);
            }
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stocks.add(stock);
        }
        //批量增加库存信息
        count= stockMapper.insertList(stocks);
        if (count != stocks.size()) {
            throw new EgException(EgExceptionStatus.RESOURCE_INSERT_ERROR);
        }
    }
}
