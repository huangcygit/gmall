package com.hy.gmall.manage.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.hy.gmall.bean.manage.PmsProductInfo;
import com.hy.gmall.bean.manage.PmsProductSaleAttr;
import com.hy.gmall.manage.mapper.PmsProductInfoMapper;
import com.hy.gmall.manage.mapper.PmsProductSaleAttrMapper;
import com.hy.gmall.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    private PmsProductInfoMapper pmsProductInfoMapper;
    @Autowired
    private PmsProductSaleAttrMapper pmsProductSaleAttrMapper;

    @Override
    public List<PmsProductInfo> spuList(String catalog3Id) {
        PmsProductInfo pmsProductInfo = new PmsProductInfo();
        pmsProductInfo.setCatalog3Id(catalog3Id);
        List<PmsProductInfo> pmsProductInfos = pmsProductInfoMapper.select(pmsProductInfo);
        return pmsProductInfos;
    }

    @Override
    public List<PmsProductSaleAttr> spuSaleAttrList(String spuId) {
        PmsProductSaleAttr pmsProductSaleAttr = new PmsProductSaleAttr();
        pmsProductSaleAttr.setProductId(spuId);
        List<PmsProductSaleAttr> pmsProductSaleAttrs = pmsProductSaleAttrMapper.select(pmsProductSaleAttr);
        return pmsProductSaleAttrs;
    }
}
