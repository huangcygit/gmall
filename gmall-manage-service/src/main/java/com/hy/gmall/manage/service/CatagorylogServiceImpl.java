package com.hy.gmall.manage.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.hy.gmall.bean.manage.PmsBaseCatalog1;
import com.hy.gmall.bean.manage.PmsBaseCatalog2;
import com.hy.gmall.bean.manage.PmsBaseCatalog3;
import com.hy.gmall.manage.mapper.PmsBaseCatalog1Mapper;
import com.hy.gmall.manage.mapper.PmsBaseCatalog2Mapper;
import com.hy.gmall.manage.mapper.PmsBaseCatalog3Mapper;
import com.hy.gmall.service.CatagorylogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Service
@Component
public class CatagorylogServiceImpl implements CatagorylogService {

    @Autowired
    private PmsBaseCatalog1Mapper pmsBaseCatalog1Mapper;

    @Autowired
    private PmsBaseCatalog2Mapper pmsBaseCatalog2Mapper;

    @Autowired
    private PmsBaseCatalog3Mapper pmsBaseCatalog3Mapper;

    @Override
    public List<PmsBaseCatalog1> getCatalog1() {
        return pmsBaseCatalog1Mapper.selectAll();
    }

    @Override
    public List<PmsBaseCatalog2> getCatalog2(String catalog1Id) {
        PmsBaseCatalog2 pmsBaseCatalog2 = new PmsBaseCatalog2();
        pmsBaseCatalog2.setCatalog1Id(catalog1Id);
        return pmsBaseCatalog2Mapper.select(pmsBaseCatalog2);
    }

    @Override
    public List<PmsBaseCatalog3> getCatalog3(String catalog2Id) {
        PmsBaseCatalog3 pmsBaseCatalog3 = new PmsBaseCatalog3();
        pmsBaseCatalog3.setCatalog2Id(catalog2Id);
        return pmsBaseCatalog3Mapper.select(pmsBaseCatalog3);
    }
}
