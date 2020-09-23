package com.hy.gmall.manage.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.hy.gmall.bean.manage.PmsBaseAttrInfo;
import com.hy.gmall.bean.manage.PmsBaseAttrValue;
import com.hy.gmall.bean.manage.PmsBaseSaleAttr;
import com.hy.gmall.manage.mapper.PmsBaseAttrInfoMapper;
import com.hy.gmall.manage.mapper.PmsBaseAttrValueMapper;
import com.hy.gmall.manage.mapper.PmsBaseSaleAttrMapper;
import com.hy.gmall.service.AttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Component
public class AttrServiceImpl implements AttrService {

    @Autowired
    private PmsBaseAttrInfoMapper pmsBaseAttrInfoMapper;
    @Autowired
    private PmsBaseAttrValueMapper pmsBaseAttrValueMapper;
    @Autowired
    private PmsBaseSaleAttrMapper pmsBaseSaleAttrMapper;

    @Override
    public List<PmsBaseAttrInfo> attrInfoList(String catalog3Id) {
        PmsBaseAttrInfo info = new PmsBaseAttrInfo();
        info.setCatalog3Id(catalog3Id);
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = pmsBaseAttrInfoMapper.select(info);

        for (PmsBaseAttrInfo attrInfo : pmsBaseAttrInfos){
            PmsBaseAttrValue attrValue = new PmsBaseAttrValue();
            attrValue.setAttrId(attrInfo.getId());
            List<PmsBaseAttrValue> pmsBaseAttrValues = pmsBaseAttrValueMapper.select(attrValue);
            attrInfo.setAttrValueList(pmsBaseAttrValues);
        }

        return pmsBaseAttrInfos;
    }

    @Override
    public String saveAttrInfo(PmsBaseAttrInfo info) {
        String id = info.getId();
        //新增
        if (StringUtils.isEmpty(id)){
            //保存属性
            pmsBaseAttrInfoMapper.insertSelective(info);

            //保存属性值
                List<PmsBaseAttrValue> attrValueList = info.getAttrValueList();
            if (!CollectionUtils.isEmpty(attrValueList)){
                for (PmsBaseAttrValue attrValue : attrValueList){
                    attrValue.setAttrId(info.getId());
                    pmsBaseAttrValueMapper.insertSelective(attrValue);
                }
            }
        }else {
            //修改
            Example example = new Example(PmsBaseAttrInfo.class);
            example.createCriteria().andEqualTo("id", info.getId());
            pmsBaseAttrInfoMapper.updateByExampleSelective(info,example);

            PmsBaseAttrValue delAttrValue = new PmsBaseAttrValue();
            delAttrValue.setAttrId(info.getId());
            pmsBaseAttrValueMapper.delete(delAttrValue);

            List<PmsBaseAttrValue> attrValueList = info.getAttrValueList();
            for (PmsBaseAttrValue pmsBaseAttrValue : attrValueList) {
                pmsBaseAttrValueMapper.insertSelective(pmsBaseAttrValue);
            }
        }
        return "success";
    }

    @Override
    public List<PmsBaseAttrValue> getAttrValueList(String attrId) {
        PmsBaseAttrValue attrValue = new PmsBaseAttrValue();
        attrValue.setAttrId(attrId);
        List<PmsBaseAttrValue> result = pmsBaseAttrValueMapper.select(attrValue);
        return result;
    }

    @Override
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
        List<PmsBaseSaleAttr> pmsBaseSaleAttrs = pmsBaseSaleAttrMapper.selectAll();
        return pmsBaseSaleAttrs;
    }
}
