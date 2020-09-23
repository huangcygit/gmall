package com.hy.gmall.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hy.gmall.bean.manage.PmsProductSaleAttr;
import com.hy.gmall.bean.manage.PmsSkuInfo;
import com.hy.gmall.service.SkuService;
import com.hy.gmall.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ItemController {

    @Reference
    private SkuService skuService;
    @Reference
    private SpuService spuService;

    @RequestMapping("{skuId}.html")
    public String item(@PathVariable String skuId, ModelMap map){
        //sku信息
        PmsSkuInfo sku = skuService.getSkuBySkuId(skuId);
        map.put("skuInfo",sku);

        //销售属性
        List<PmsProductSaleAttr> pmsProductSaleAttrs = spuService.spuSaleAttrListCheckBySku(sku.getProductId(),sku.getId());
        return "item";
    }

}
