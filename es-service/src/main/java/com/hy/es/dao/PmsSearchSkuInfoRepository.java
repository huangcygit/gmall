package com.hy.es.dao;

import com.hy.es.bean.PmsSearchSkuInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PmsSearchSkuInfoRepository extends ElasticsearchRepository<PmsSearchSkuInfo,Long> {
}
