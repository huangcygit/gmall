package com.hy.gmall.controller.manage;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hy.gmall.bean.manage.PmsProductInfo;
import com.hy.gmall.bean.manage.PmsProductSaleAttr;
import com.hy.gmall.service.SpuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class SpuController {

    @Reference
    private SpuService spuService;

    @RequestMapping("spuList")
    @ResponseBody
    public List<PmsProductInfo> spuList(String catalog3Id){
        List<PmsProductInfo> productInfos = spuService.spuList(catalog3Id);
        return productInfos;
    }

    @RequestMapping("spuSaleAttrList")
    @ResponseBody
    public List<PmsProductSaleAttr> spuSaleAttrList(String spuId){

        List<PmsProductSaleAttr> pmsProductSaleAttrs = spuService.spuSaleAttrList(spuId);
        return pmsProductSaleAttrs;
    }

    @RequestMapping(value = "saveSpuInfo", method = {RequestMethod.POST})
    @ResponseBody
    public String saveSpuInfo(@RequestBody PmsProductInfo pmsProductInfo){
        spuService.saveSpuInfo(pmsProductInfo);
        return "success";
    }
}
