package com.hy.gmall.manage.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.hy.gmall.bean.manage.PmsSkuAttrValue;
import com.hy.gmall.bean.manage.PmsSkuImage;
import com.hy.gmall.bean.manage.PmsSkuInfo;
import com.hy.gmall.bean.manage.PmsSkuSaleAttrValue;
import com.hy.gmall.manage.mapper.PmsSkuAttrValueMapper;
import com.hy.gmall.manage.mapper.PmsSkuImageMapper;
import com.hy.gmall.manage.mapper.PmsSkuInfoMapper;
import com.hy.gmall.manage.mapper.PmsSkuSaleAttrValueMapper;
import com.hy.gmall.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class SkuServiceImpl implements SkuService {
    @Autowired
    private PmsSkuInfoMapper pmsSkuInfoMapper;
    @Autowired
    private PmsSkuImageMapper pmsSkuImageMapper;
    @Autowired
    private PmsSkuAttrValueMapper pmsSkuAttrValueMapper;
    @Autowired
    private PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;

    @Override
    @Transactional
    public void saveSkuInfo(PmsSkuInfo pmsSkuInfo) {
        pmsSkuInfoMapper.insertSelective(pmsSkuInfo);
        String skuId = pmsSkuInfo.getId();

        //关联平台属性
        List<PmsSkuAttrValue> skuAttrValueList = pmsSkuInfo.getSkuAttrValueList();
        if (!CollectionUtils.isEmpty(skuAttrValueList)){
            skuAttrValueList.forEach(attrValue -> {
                attrValue.setSkuId(skuId);
                pmsSkuAttrValueMapper.insertSelective(attrValue);
            });
        }

        //关联销售属性
        List<PmsSkuSaleAttrValue> skuSaleAttrValueList = pmsSkuInfo.getSkuSaleAttrValueList();
        if (!CollectionUtils.isEmpty(skuSaleAttrValueList)){
            skuSaleAttrValueList.forEach(saleAttrValue -> {
                saleAttrValue.setSkuId(skuId);
                pmsSkuSaleAttrValueMapper.insertSelective(saleAttrValue);
            });
        }

        // 插入图片信息
        List<PmsSkuImage> skuImageList = pmsSkuInfo.getSkuImageList();
        for (PmsSkuImage pmsSkuImage : skuImageList) {
            pmsSkuImage.setSkuId(skuId);
            pmsSkuImageMapper.insertSelective(pmsSkuImage);
        }
    }

    @Override
    public PmsSkuInfo getSkuBySkuId(String skuId) {
        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
        pmsSkuInfo.setId(skuId);
        PmsSkuInfo result = pmsSkuInfoMapper.selectOne(pmsSkuInfo);
        return result;
    }

    @Override
    public List<PmsSkuInfo> getAllSku() {
        List<PmsSkuInfo> pmsSkuInfos = pmsSkuInfoMapper.selectAll();
        if (CollectionUtils.isEmpty(pmsSkuInfos)){
            return null;
        }
        for (PmsSkuInfo info : pmsSkuInfos){
            PmsSkuSaleAttrValue attrValue = new PmsSkuSaleAttrValue();
            attrValue.setSkuId(info.getId());
            List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValues = pmsSkuSaleAttrValueMapper.select(attrValue);
            info.setSkuSaleAttrValueList(pmsSkuSaleAttrValues);
        }
        return pmsSkuInfos;
    }
}
