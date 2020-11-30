package com.hy.es.service.impl;

import com.hy.es.bean.PmsSearchSkuInfo;
import com.hy.es.dao.PmsSearchSkuInfoRepository;
import com.hy.es.model.PmsSearchParam;
import com.hy.es.service.PmsSearchSkuInfoService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
//import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class PmsSearchSkuInfoServiceImpl implements PmsSearchSkuInfoService {

    @Autowired
    PmsSearchSkuInfoRepository pmsSearchSkuInfoRepository;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;

    @Override
    public List<PmsSearchSkuInfo> find() {
        List<PmsSearchSkuInfo> all = (List<PmsSearchSkuInfo>) pmsSearchSkuInfoRepository.findAll();
        return all;
    }

    @Override
    public List<PmsSearchSkuInfo> list(PmsSearchParam pmsSearchParam) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = getQUeryBuilder(pmsSearchParam);
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        nativeSearchQueryBuilder.withIndices("gmall");
//        AggregatedPage<PmsSearchSkuInfo> page = elasticsearchTemplate.queryForPage(nativeSearchQueryBuilder.build(), PmsSearchSkuInfo.class, IndexCoordinates.of("gmall"));
        AggregatedPage<PmsSearchSkuInfo> page = elasticsearchTemplate.queryForPage(nativeSearchQueryBuilder.build(), PmsSearchSkuInfo.class);
        List<PmsSearchSkuInfo> pmsSearchSkuInfos = elasticsearchTemplate.queryForList(nativeSearchQueryBuilder.build(), PmsSearchSkuInfo.class);
        List<PmsSearchSkuInfo> content = page.getContent();
        if (page.getAggregations() != null){
            for (Aggregation aggregation : page.getAggregations()){
                ParsedStringTerms terms = (ParsedStringTerms) aggregation;
            }
        }
        return content;
    }

    @Override
    public void save() {
        PmsSearchSkuInfo info = new PmsSearchSkuInfo();
        PmsSearchSkuInfo save = pmsSearchSkuInfoRepository.save(info);
        System.out.println();
    }

    private BoolQueryBuilder getQUeryBuilder(PmsSearchParam pmsSearchParam) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (StringUtils.hasLength(pmsSearchParam.getKeyword())){
            boolQueryBuilder.must(QueryBuilders.termQuery("skuName",pmsSearchParam.getKeyword()));
        }
        if (StringUtils.hasLength(pmsSearchParam.getCatalog3Id())){
            boolQueryBuilder.must(QueryBuilders.termQuery("catalog3Id",pmsSearchParam.getCatalog3Id()));
        }
        if (pmsSearchParam.getValueId() != null){
            for (String valueId : pmsSearchParam.getValueId()){
                boolQueryBuilder.must(QueryBuilders.termQuery("skuAttrValueList.valueId.keyword",valueId));
            }
        }
        return boolQueryBuilder;
    }
}
