package com.hy.gmall.service;

import com.hy.gmall.bean.manage.PmsSkuInfo;

import java.util.List;

public interface SkuService {
    void saveSkuInfo(PmsSkuInfo pmsSkuInfo);

    PmsSkuInfo getSkuBySkuId(String skuId);

    List<PmsSkuInfo> getAllSku();
}
