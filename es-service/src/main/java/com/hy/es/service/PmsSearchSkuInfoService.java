package com.hy.es.service;

import com.hy.es.bean.PmsSearchSkuInfo;
import com.hy.es.model.PmsSearchParam;

import java.util.List;

public interface PmsSearchSkuInfoService {

    List<PmsSearchSkuInfo> find();

    List<PmsSearchSkuInfo> list(PmsSearchParam pmsSearchParam);

    void save();
}
