package com.hy.gmall.service;

import com.hy.gmall.bean.manage.PmsSearchSkuInfo;
import com.hy.gmall.bean.search.PmsSearchParam;

import java.util.List;

public interface SearchNewService {
    List<PmsSearchSkuInfo> list(PmsSearchParam pmsSearchParam);
}
