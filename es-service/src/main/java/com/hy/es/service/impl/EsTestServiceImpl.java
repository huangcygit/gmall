package com.hy.es.service.impl;

import com.hy.es.bean.BeanTest;
import com.hy.es.service.EsTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

@Service
public class EsTestServiceImpl implements EsTestService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;

    @Override
    public void createIndex() {
        elasticsearchTemplate.createIndex(BeanTest.class);
    }
}
