package com.hy.es.controller;

import com.hy.es.bean.PmsSearchSkuInfo;
import com.hy.es.model.PmsSearchParam;
import com.hy.es.service.EsTestService;
import com.hy.es.service.PmsSearchSkuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchController {

    @Autowired
    private PmsSearchSkuInfoService pmsSearchSkuInfoService;
    @Autowired
    private EsTestService esTestService;

    @GetMapping("/search")
    public List<PmsSearchSkuInfo> search(){
        List<PmsSearchSkuInfo> pmsSearchSkuInfos = pmsSearchSkuInfoService.find();
        return pmsSearchSkuInfos;
    }

    @GetMapping("/save")
    public void save(){
        pmsSearchSkuInfoService.save();
    }

    @GetMapping("/search1")
    public List<PmsSearchSkuInfo> search1(PmsSearchParam pmsSearchParam){
        List<PmsSearchSkuInfo> pmsSearchSkuInfos = pmsSearchSkuInfoService.list(pmsSearchParam);
        return pmsSearchSkuInfos;
    }

    @GetMapping("/create")
    public void create(){
        esTestService.createIndex();
    }
}
