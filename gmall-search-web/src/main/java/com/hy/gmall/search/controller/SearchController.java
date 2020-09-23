package com.hy.gmall.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hy.gmall.bean.manage.PmsSearchSkuInfo;
import com.hy.gmall.bean.search.PmsSearchParam;
import com.hy.gmall.service.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SearchController {

    @Reference
    private SearchService searchService;

    @RequestMapping(value = "search")
    @ResponseBody
    public List<PmsSearchSkuInfo> search(PmsSearchParam param){
        return searchService.list(param);
    }
}
