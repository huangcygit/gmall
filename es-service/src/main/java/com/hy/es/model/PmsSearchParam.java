package com.hy.es.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PmsSearchParam implements Serializable {
    private String catalog3Id;

    private String keyword;

    private int page = 1;

    private int pageSize = 100;

    private String[] valueId;
}
