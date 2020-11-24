package com.hy.gmall.service;

import com.hy.gmall.bean.manage.PmsBaseAttrInfo;
import com.hy.gmall.bean.manage.PmsBaseAttrValue;
import com.hy.gmall.bean.manage.PmsBaseSaleAttr;

import java.util.List;
import java.util.Set;

public interface AttrService {
    List<PmsBaseAttrInfo> attrInfoList(String catalog3Id);

    String saveAttrInfo(PmsBaseAttrInfo info);

    List<PmsBaseAttrValue> getAttrValueList(String attrId);

    List<PmsBaseSaleAttr> baseSaleAttrList();

    List<PmsBaseAttrInfo> getAttrValueListByValueId(Set<String> valueIdSet);
}
