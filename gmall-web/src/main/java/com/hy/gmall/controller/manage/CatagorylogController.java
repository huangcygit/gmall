package com.hy.gmall.controller.manage;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hy.gmall.bean.manage.PmsBaseCatalog1;
import com.hy.gmall.bean.manage.PmsBaseCatalog2;
import com.hy.gmall.bean.manage.PmsBaseCatalog3;
import com.hy.gmall.service.CatagorylogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
//@RequestMapping(("/catagory"))
public class CatagorylogController {

    @Reference
    private CatagorylogService catagorylogService;

    @PostMapping("/getCatalog1")
    @ResponseBody
    public List<PmsBaseCatalog1> getAllCatalog1(){
        List<PmsBaseCatalog1> catalog1s = catagorylogService.getCatalog1();
        return catalog1s;
    }

    @PostMapping("getCatalog2")
    @ResponseBody
    public List<PmsBaseCatalog2> getCatalog2(String catalog1Id){

        List<PmsBaseCatalog2> catalog2s = catagorylogService.getCatalog2(catalog1Id);
        return catalog2s;
    }

    @PostMapping("getCatalog3")
    @ResponseBody
    public List<PmsBaseCatalog3> getCatalog3(String catalog2Id){

        List<PmsBaseCatalog3> catalog3s = catagorylogService.getCatalog3(catalog2Id);
        return catalog3s;
    }
}
