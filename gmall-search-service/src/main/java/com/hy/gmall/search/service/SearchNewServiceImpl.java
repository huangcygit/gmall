package com.hy.gmall.search.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.hy.gmall.bean.manage.PmsSearchSkuInfo;
import com.hy.gmall.bean.search.PmsSearchParam;
import com.hy.gmall.service.SearchNewService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.List;

@Service
public class SearchNewServiceImpl implements SearchNewService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public List<PmsSearchSkuInfo> list(PmsSearchParam pmsSearchParam) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = getQUeryBuilder(pmsSearchParam);
        nativeSearchQueryBuilder.withIndices("gmall").withQuery(boolQueryBuilder);
//        AggregatedPage<PmsSearchSkuInfo> page = elasticsearchRestTemplate.queryForPage(nativeSearchQueryBuilder.build(), PmsSearchSkuInfo.class, IndexCoordinates.of("gmall"));
        AggregatedPage<PmsSearchSkuInfo> page = elasticsearchRestTemplate.queryForPage(nativeSearchQueryBuilder.build(), PmsSearchSkuInfo.class);
        List<PmsSearchSkuInfo> content = page.getContent();
        if (page.getAggregations() != null){
            for (Aggregation aggregation : page.getAggregations()){
                ParsedStringTerms terms = (ParsedStringTerms) aggregation;
            }
        }
        return content;
    }

    private BoolQueryBuilder getQUeryBuilder(PmsSearchParam pmsSearchParam) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (StringUtils.isNotBlank(pmsSearchParam.getKeyword())){
            boolQueryBuilder.must(QueryBuilders.termQuery("skuName",pmsSearchParam.getKeyword()));
        }
        if (StringUtils.isNotBlank(pmsSearchParam.getCatalog3Id())){
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
