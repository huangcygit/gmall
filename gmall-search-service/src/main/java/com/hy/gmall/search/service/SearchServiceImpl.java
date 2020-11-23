package com.hy.gmall.search.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.hy.gmall.bean.manage.PmsSearchSkuInfo;
import com.hy.gmall.bean.manage.PmsSkuAttrValue;
import com.hy.gmall.bean.search.PmsSearchParam;
import com.hy.gmall.service.SearchService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private JestClient jestClient;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public List<PmsSearchSkuInfo> list(PmsSearchParam pmsSearchParam) {
        List<PmsSearchSkuInfo> pmsSearchSkuInfos = new ArrayList<>();
        String dsl = getSearchDsl(pmsSearchParam);
        System.out.println("-------------- " + dsl);
        //.addType("PmsSkuInfo")
        Search search = new Search.Builder(dsl).addIndex("gmall").build();
        SearchResult searchResult = null;
        try {
//            NativeSearchQueryBuilder dsl1 = dsl(pmsSearchParam);
//            elasticsearchRestTemplate.query(dsl1.build(), SearchResponse::);
            searchResult = jestClient.execute(search);
            System.out.println(searchResult);
            List<SearchResult.Hit<PmsSearchSkuInfo, Void>> hits = searchResult.getHits(PmsSearchSkuInfo.class);
            if (CollectionUtils.isEmpty(hits)){
                return pmsSearchSkuInfos;
            }
            for (SearchResult.Hit<PmsSearchSkuInfo, Void> hit : hits){
                PmsSearchSkuInfo info = hit.source;
                pmsSearchSkuInfos.add(info);
            }
            System.out.println(searchResult.getTotal());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pmsSearchSkuInfos;
    }


    private NativeSearchQueryBuilder dsl(PmsSearchParam pmsSearchParam){
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        if (StringUtils.isNotBlank(pmsSearchParam.getCatalog3Id())){
            TermQueryBuilder termQueryBuilder = new TermQueryBuilder("catalog3Id",pmsSearchParam.getCatalog3Id());
            boolQueryBuilder.filter(termQueryBuilder);
        }
        if (!CollectionUtils.isEmpty(pmsSearchParam.getSkuAttrValueList())){
            for (PmsSkuAttrValue value : pmsSearchParam.getSkuAttrValueList()){
                TermQueryBuilder termQueryBuilder = new TermQueryBuilder("skuAttrValueList.valueId",value);
                boolQueryBuilder.filter(termQueryBuilder);
            }
        }
        if (StringUtils.isNotBlank(pmsSearchParam.getKeyword())){
            TermQueryBuilder builder = new TermQueryBuilder("skuName",pmsSearchParam.getKeyword());
            boolQueryBuilder.must(builder);
        }

        TermsAggregationBuilder aaa = AggregationBuilders.terms("aaa").field("skuAttrValueList.valueId").size(100);

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withIndices("gmall");
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        nativeSearchQueryBuilder.addAggregation(aaa);
        return nativeSearchQueryBuilder;
    }






    private String getSearchDsl(PmsSearchParam pmsSearchParam){
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        //filter
        if (StringUtils.isNotBlank(pmsSearchParam.getCatalog3Id())){
            TermQueryBuilder termQueryBuilder = new TermQueryBuilder("catalog3Id",pmsSearchParam.getCatalog3Id());
            boolQueryBuilder.filter(termQueryBuilder);
        }

        if (!CollectionUtils.isEmpty(pmsSearchParam.getSkuAttrValueList())){
            for (PmsSkuAttrValue skuAttrValue : pmsSearchParam.getSkuAttrValueList()){
                TermQueryBuilder termQueryBuilder = new TermQueryBuilder("skuAttrValueList.valueId",skuAttrValue.getValueId());
                boolQueryBuilder.filter(termQueryBuilder);
            }
        }

        //must
        if (StringUtils.isNotBlank(pmsSearchParam.getKeyword())){
            MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("skuName", pmsSearchParam.getKeyword());
            boolQueryBuilder.must(matchQueryBuilder);
        }

        searchSourceBuilder.sort("id", SortOrder.DESC);
        searchSourceBuilder.from(pmsSearchParam.getPage());
        searchSourceBuilder.size(pmsSearchParam.getPageSize());
        searchSourceBuilder.query(boolQueryBuilder);
        return searchSourceBuilder.toString();
    }
}
