package com.eogo.item.mapper;

import com.eogo.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 通用mapper提供简单的与实体类对应的单表的增删改查
 * 除上述简单的之外需要进行单独的编写sql
 */
public interface BrandMapper extends Mapper<Brand> {
    @Insert("INSERT INTO tb_category_brand (category_id ,brand_id) VALUES (#{cid},#{bid})")
    int insertCategoryBrand(@Param("cid")Long cid,@Param("bid") Long bid);

    @Select("select b.*\n" +
            "from tb_brand b inner join tb_category_brand tb\n" +
            "on b.id=tb.brand_id\n" +
            "where tb.category_id = #{cid};")
    List<Brand> getBrandsByCid(@Param ("cid")Long cid);

}
