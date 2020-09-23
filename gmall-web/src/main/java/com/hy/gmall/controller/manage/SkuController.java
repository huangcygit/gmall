package com.hy.gmall.controller.manage;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hy.gmall.bean.manage.PmsProductInfo;
import com.hy.gmall.bean.manage.PmsSkuInfo;
import com.hy.gmall.service.SkuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class SkuController {

    @Reference
    private SkuService skuService;

    @RequestMapping(value = "saveSkuInfo", method = {RequestMethod.POST})
    @ResponseBody
    public String saveSkuInfo(@RequestBody PmsSkuInfo pmsSkuInfo){
        System.out.println("11111111111111111111111111111111111111111111");
        pmsSkuInfo.setProductId(pmsSkuInfo.getSpuId());
        // 处理默认图片
        String skuDefaultImg = pmsSkuInfo.getSkuDefaultImg();
        if(StringUtils.isBlank(skuDefaultImg) && !CollectionUtils.isEmpty(pmsSkuInfo.getSkuImageList())){
            pmsSkuInfo.setSkuDefaultImg(pmsSkuInfo.getSkuImageList().get(0).getImgUrl());
        }
        skuService.saveSkuInfo(pmsSkuInfo);
        return "success";
    }
}
