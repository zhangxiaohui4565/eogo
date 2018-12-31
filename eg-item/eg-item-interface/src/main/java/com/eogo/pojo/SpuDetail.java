package com.eogo.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_spu_detail")
@Data
public class SpuDetail {
    @Id
    private Long spuId ;
    private String description ;
    /**
     * 商品特殊规格的名称及可选值魔板
     * {"4":["白色","金色","玫瑰金"],"12":["3GB"],"13":["16GB"]}
     */
    private String specialSpec ;
    /**
     * 商品的全局规格属性
     * {"1":"其它","2":"G9青春版（全网通版）","3":2016.0,"5":143,"6":"其它","7":"Android","8":"骁龙（Snapdragon)",
     * "9":"骁龙617（msm8952）","10":"八核","11":1.5,"14":5.2,"15":"1920*1080(FHD)","16":800.0,"17":1300.0,"18":3000.0}
     */
    private String genericSpec;
    /**
     * 包装清单
     * 手机（电池内置）*1，中式充电器*1，数据线*1，半入耳式线控耳机*1，华为手机凭证*1，快速指南*1，取卡针*1，屏幕保护膜（出厂已贴）*1
     */
    private String packingList;
    /**
     * 售后服务
     */
    private String afterService;


}
