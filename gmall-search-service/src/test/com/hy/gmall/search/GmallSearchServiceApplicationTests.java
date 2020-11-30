package com.hy.gmall.search;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hy.gmall.bean.manage.PmsSearchSkuInfo;
import com.hy.gmall.bean.manage.PmsSkuInfo;
import com.hy.gmall.bean.search.PmsSearchParam;
import com.hy.gmall.search.dao.PmsSearchSkuInfoRepository;
import com.hy.gmall.service.SearchNewService;
import com.hy.gmall.service.SkuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GmallSearchServiceApplicationTests {

	@Reference
	SkuService skuService;// 查询mysql

//	@Autowired
//	JestClient jestClient;

	@Autowired
	private SearchNewService searchNewService;
	@Autowired
	private PmsSearchSkuInfoRepository pmsSearchSkuInfoRepository;

	@Test
	public void contextLoads() throws IOException {
		get();
	}

	@Test
	public void search(){
		PmsSearchParam param = PmsSearchParam.builder()
				.page(1)
				.pageSize(10).build();
		List<PmsSearchSkuInfo> list = searchNewService.list(param);
		System.out.println(list);
	}
	@Test
	public void save(){

		// 查询mysql数据
		List<PmsSkuInfo> pmsSkuInfoList = skuService.getAllSku();
		// 转化为es的数据结构
		List<PmsSearchSkuInfo> pmsSearchSkuInfos = new ArrayList<>();

		for (PmsSkuInfo pmsSkuInfo : pmsSkuInfoList) {
			PmsSearchSkuInfo pmsSearchSkuInfo = new PmsSearchSkuInfo();

			BeanUtils.copyProperties(pmsSkuInfo,pmsSearchSkuInfo);

			pmsSearchSkuInfo.setId(Long.parseLong(pmsSkuInfo.getId()));

			pmsSearchSkuInfos.add(pmsSearchSkuInfo);
			PmsSearchSkuInfo save = pmsSearchSkuInfoRepository.save(pmsSearchSkuInfo);
		}

	}

	public void get() throws IOException {

		// jest的dsl工具
//		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//		// bool
//		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
//		// filter
//		TermQueryBuilder termQueryBuilder = new TermQueryBuilder("skuAttrValueList.valueId","43");
//		boolQueryBuilder.filter(termQueryBuilder);
//		// must
//		MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("skuName","华为");
//		boolQueryBuilder.must(matchQueryBuilder);
//		// query
//		searchSourceBuilder.query(boolQueryBuilder);
//		// from
//		searchSourceBuilder.from(0);
//		// size
//		searchSourceBuilder.size(20);
//		// highlight
//		searchSourceBuilder.highlight();
//
//		String dslStr = searchSourceBuilder.toString();
//
//		System.err.println(dslStr);
//
//
//		// 用api执行复杂查询
//		List<PmsSearchSkuInfo> pmsSearchSkuInfos = new ArrayList<>();
//
//		Search search = new Search.Builder(dslStr).addIndex("gmall").build();
//
//		SearchResult execute = jestClient.execute(search);
//
//		List<SearchResult.Hit<PmsSearchSkuInfo, Void>> hits = execute.getHits(PmsSearchSkuInfo.class);
//
//		for (SearchResult.Hit<PmsSearchSkuInfo, Void> hit : hits) {
//			PmsSearchSkuInfo source = hit.source;
//
//			pmsSearchSkuInfos.add(source);
//		}
//
//		System.out.println(pmsSearchSkuInfos.size());
	}



}
