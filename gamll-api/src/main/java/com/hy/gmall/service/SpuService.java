package com.hy.gmall.service;

import com.hy.gmall.bean.manage.PmsProductInfo;
import com.hy.gmall.bean.manage.PmsProductSaleAttr;

import java.util.List;

public interface SpuService {
    List<PmsProductInfo> spuList(String catalog3Id);

    List<PmsProductSaleAttr> spuSaleAttrList(String spuId);
}
