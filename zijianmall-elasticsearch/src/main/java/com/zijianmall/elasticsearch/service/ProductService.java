package com.zijianmall.elasticsearch.service;

import com.zijianmall.common.to.es.SkuEsModelTo;

import java.io.IOException;
import java.util.List;

/**
 * @author xiaozj
 */
public interface ProductService {


    boolean productUp(List<SkuEsModelTo> skuEsModelTos) throws IOException;
}
