package com.zijianmall.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.zijianmall.elasticsearch.config.ElasticSearchConfig;
import lombok.Data;
import org.apache.lucene.index.Term;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JsonbTester;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticTest1 {

    @Autowired
    private RestHighLevelClient client;

    @Test
    public void test1() {
        System.out.println(client);
    }

    @Test
    public void test2() throws IOException {
        User user = new User();
        user.setAge(12);
        user.setGender("F");
        user.setName("dsoi");
        String s = JSON.toJSONString(user);
        IndexRequest indexRequest = new IndexRequest("users");
        indexRequest.source(s, XContentType.JSON);
        IndexResponse index = client.index(indexRequest, ElasticSearchConfig.COMMON_OPTIONS);
        System.out.println(index);
    }

    @Test
    public void test3() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("bank");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        MatchQueryBuilder query = QueryBuilders.matchQuery("address", "mill");
        builder.query(query);
        request.source(builder);
        SearchResponse response = client.search(request, ElasticSearchConfig.COMMON_OPTIONS);
        System.out.println(response);
    }

    @Test
    public void test4() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("bank");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        TermsAggregationBuilder aggBuilder = AggregationBuilders.terms("ageAggs").field("age").size(10);
        aggBuilder.subAggregation(AggregationBuilders.avg("balanceAggs").field("balance"));
        builder.aggregation(aggBuilder);
        request.source(builder);
        SearchResponse response = client.search(request, ElasticSearchConfig.COMMON_OPTIONS);
        System.out.println(response);
        Aggregations aggregations = response.getAggregations();

        Terms ageAgg1 = aggregations.get("ageAggs");

        for (Terms.Bucket bucket : ageAgg1.getBuckets()) {
            String keyAsString = bucket.getKeyAsString();
            System.out.println("年龄："+keyAsString+" ==> "+bucket.getDocCount());
            Aggregations aggregations1 = bucket.getAggregations();
            Avg balanceAggs = aggregations1.get("balanceAggs");

            System.out.println(balanceAggs.getValue());
        }

    }

    @Data
    class User {
        private Integer age;
        private String name;
        private String gender;
    }
}
