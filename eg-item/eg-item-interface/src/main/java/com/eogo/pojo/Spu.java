package com.eogo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Table(name = "tb_spu")
@Data
public class Spu {
    //自增主键
    @Id
    @KeySql(useGeneratedKeys = true)
    private  Long id ;
    private Long brandId;
    /**
     * 一级类目
     */
    private Long cid1;
    /**
     * 二级类目
     */
    private Long cid2;
    /**
     * 三级类目
     */
    private Long cid3;
    private String title;
    /**
     * 子标题
     */
    private String subTitle;
    /**
     * 是否下架
     */
    private Boolean saleable;
    /**
     * 逻辑删除字段
     */
    private Boolean valid ;
    private Date createTime;
    @JsonIgnore
    private Date lastUpdateTime;
    @Transient
    private String cname;
    @Transient
    private String bname;
}
