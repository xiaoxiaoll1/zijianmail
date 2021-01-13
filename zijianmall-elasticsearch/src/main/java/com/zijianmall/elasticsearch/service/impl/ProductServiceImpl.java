package com.zijianmall.elasticsearch.service.impl;

import com.alibaba.fastjson.JSON;
import com.zijianmall.common.to.es.SkuEsModelTo;
import com.zijianmall.elasticsearch.config.ElasticSearchConfig;
import com.zijianmall.elasticsearch.constant.EsConstant;
import com.zijianmall.elasticsearch.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiaozj
 */
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public boolean productUp(List<SkuEsModelTo> skuEsModelTos) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        skuEsModelTos.stream().forEach(skuEsModelTo -> {
            IndexRequest indexRequest = new IndexRequest();
            indexRequest.index(EsConstant.PRODUCT_INDEX);
            indexRequest.id(skuEsModelTo.getSkuId().toString());
            String s = JSON.toJSONString(skuEsModelTo);
            indexRequest.source(s, XContentType.JSON);
            bulkRequest.add(indexRequest);
        });
        BulkResponse response = null;
        response = restHighLevelClient.bulk(bulkRequest, ElasticSearchConfig.COMMON_OPTIONS);
        boolean b = response.hasFailures();
        BulkItemResponse[] items = response.getItems();
        // 数组也是可以链式编程的
        List<String> ids = Arrays.stream(items).map(item -> {
            return item.getId();
        }).collect(Collectors.toList());
        log.info("上架完成的id为{}", ids);
        return b;


    }
}
