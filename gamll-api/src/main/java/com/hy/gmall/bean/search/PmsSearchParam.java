package com.hy.gmall.bean.search;

import com.hy.gmall.bean.manage.PmsSkuAttrValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PmsSearchParam implements Serializable {
    private String catalog3Id;

    private String keyword;

    private int page = 1;

    private int pageSize = 100;

    private List<PmsSkuAttrValue> skuAttrValueList;
}
