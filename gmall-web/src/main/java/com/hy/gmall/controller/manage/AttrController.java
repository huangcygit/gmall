package com.hy.gmall.controller.manage;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hy.gmall.bean.manage.PmsBaseAttrInfo;
import com.hy.gmall.bean.manage.PmsBaseAttrValue;
import com.hy.gmall.bean.manage.PmsBaseSaleAttr;
import com.hy.gmall.service.AttrService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class AttrController {

    @Reference
    private AttrService attrService;

    @GetMapping("attrInfoList")
    @ResponseBody
    public List<PmsBaseAttrInfo> attrInfoList(String catalog3Id){
        List<PmsBaseAttrInfo> attrInfoList = attrService.attrInfoList(catalog3Id);
        return attrInfoList;
    }

    @RequestMapping("saveAttrInfo")
    @ResponseBody
    public String saveAttrInfo(@RequestBody PmsBaseAttrInfo info){
        String result = attrService.saveAttrInfo(info);
        return result;
    }

    @RequestMapping("getAttrValueList")
    @ResponseBody
    public List<PmsBaseAttrValue> getAttrValueList(String attrId){
        List<PmsBaseAttrValue> attrValueList = attrService.getAttrValueList(attrId);
        return attrValueList;
    }

    @RequestMapping("baseSaleAttrList")
    @ResponseBody
    public List<PmsBaseSaleAttr> baseSaleAttrList(){

        List<PmsBaseSaleAttr> pmsBaseSaleAttrs = attrService.baseSaleAttrList();
        return pmsBaseSaleAttrs;
    }
}
