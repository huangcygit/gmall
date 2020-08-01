package com.hy.gmall.controller.manage;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hy.gmall.bean.manage.PmsBaseCatalog1;
import com.hy.gmall.bean.manage.PmsBaseCatalog2;
import com.hy.gmall.service.CatagorylogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(("/catagory"))
public class CatagorylogController {

    @Reference
    private CatagorylogService catagorylogService;

    @GetMapping("/getAllCatalog1")
    public List<PmsBaseCatalog1> getAllCatalog1(){
        List<PmsBaseCatalog1> catalog1s = catagorylogService.getCatalog1();
        return catalog1s;
    }

    @RequestMapping("getCatalog2")
    @ResponseBody
    public List<PmsBaseCatalog2> getCatalog2(String catalog1Id){

        List<PmsBaseCatalog2> catalog2s = catagorylogService.getCatalog2(catalog1Id);
        return catalog2s;
    }
}
