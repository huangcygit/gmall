package com.hy.gmall.manage.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.hy.gmall.bean.manage.PmsProductInfo;
import com.hy.gmall.bean.manage.PmsProductSaleAttr;
import com.hy.gmall.bean.manage.PmsProductSaleAttrValue;
import com.hy.gmall.manage.mapper.PmsProductInfoMapper;
import com.hy.gmall.manage.mapper.PmsProductSaleAttrMapper;
import com.hy.gmall.manage.mapper.PmsProductSaleAttrValueMapper;
import com.hy.gmall.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    private PmsProductInfoMapper pmsProductInfoMapper;
    @Autowired
    private PmsProductSaleAttrMapper pmsProductSaleAttrMapper;
    @Autowired
    PmsProductSaleAttrValueMapper pmsProductSaleAttrValueMapper;

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
        for (PmsProductSaleAttr saleAttr : pmsProductSaleAttrs){
            PmsProductSaleAttrValue attrValue = new PmsProductSaleAttrValue();
            attrValue.setProductId(saleAttr.getProductId());
            attrValue.setSaleAttrId(saleAttr.getSaleAttrId());
            List<PmsProductSaleAttrValue> pmsProductSaleAttrValues = pmsProductSaleAttrValueMapper.select(attrValue);
            saleAttr.setSpuSaleAttrValueList(pmsProductSaleAttrValues);
        }
        return pmsProductSaleAttrs;
    }

    @Override
    @Transactional
    public void saveSpuInfo(PmsProductInfo pmsProductInfo) {
        pmsProductInfoMapper.insertSelective(pmsProductInfo);

        String productId = pmsProductInfo.getId();

        //保存销售属性
        List<PmsProductSaleAttr> spuSaleAttrList = pmsProductInfo.getSpuSaleAttrList();
        if (!CollectionUtils.isEmpty(spuSaleAttrList)){
            spuSaleAttrList.forEach(pmsProductSaleAttr -> {
                pmsProductSaleAttr.setProductId(productId);
                pmsProductSaleAttrMapper.insertSelective(pmsProductSaleAttr);

                //保存销售属性值
                List<PmsProductSaleAttrValue> saleAttrValueList = pmsProductSaleAttr.getSpuSaleAttrValueList();
                if (!CollectionUtils.isEmpty(saleAttrValueList)){
                    saleAttrValueList.forEach(saleValue -> {
                        saleValue.setProductId(productId);
                        pmsProductSaleAttrValueMapper.insertSelective(saleValue);
                    });
                }
            });
        }
    }

    @Override
    public List<PmsProductSaleAttr> spuSaleAttrListCheckBySku(String productId, String skuId) {
        List<PmsProductSaleAttr> result = pmsProductSaleAttrMapper.selectSpuSaleAttrListCheckBySku(productId,skuId);
        return result;
    }
}
