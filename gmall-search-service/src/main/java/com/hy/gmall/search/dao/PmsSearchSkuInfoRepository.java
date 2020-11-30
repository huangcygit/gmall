package com.hy.gmall.search.dao;

import com.hy.gmall.bean.manage.PmsSearchSkuInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PmsSearchSkuInfoRepository extends ElasticsearchRepository<PmsSearchSkuInfo,Long> {
}
