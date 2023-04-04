package com.tony.hotel.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.tony.hotel.constants.HotelConstants;
import com.tony.hotel.domain.Hotel;
import com.tony.hotel.domain.vo.HotelDoc;
import com.tony.hotel.service.HotelService;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Resource
    private HotelService hotelService;

    @GetMapping
    public List<Hotel> getAll() throws IOException {
        List<Hotel> list = hotelService.list();
        return list;
    }


    @GetMapping("/create")
    public String create() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(HttpHost.create("http://localhost:9200")));

        CreateIndexRequest request = new CreateIndexRequest("hotel");

        request.source(HotelConstants.MAPPING_TEMPLATE, XContentType.JSON);

        client.indices().create(request, RequestOptions.DEFAULT);

        return "success";
    }

    @GetMapping("/delete")
    public String delete() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(HttpHost.create("http://localhost:9200")));

        DeleteIndexRequest request = new DeleteIndexRequest("hotel");

        client.indices().delete(request, RequestOptions.DEFAULT);

        return "success";
    }

    @GetMapping("/exist")
    public String exist() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(HttpHost.create("http://localhost:9200")));

        GetIndexRequest request = new GetIndexRequest("hotel");

        boolean flag = client.indices().exists(request, RequestOptions.DEFAULT);

        return flag ? "success" : "error";
    }


    @GetMapping("/insert/doc")
    public String insertDoc() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(HttpHost.create("http://localhost:9200")));

        Hotel hotel = hotelService.getById(36934);
        HotelDoc hotelDoc = BeanUtil.copyProperties(hotel, HotelDoc.class);
        hotelDoc.setLocation(hotel.getLatitude() + "," + hotel.getLongitude());

        IndexRequest request = new IndexRequest("hotel").id(hotelDoc.getId().toString());

        request.source(JSON.toJSONString(hotelDoc), XContentType.JSON);

        client.index(request, RequestOptions.DEFAULT);

        return "success";
    }

    @GetMapping("/get/doc")
    public HotelDoc getDoc() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(HttpHost.create("http://localhost:9200")));

        GetRequest request = new GetRequest("hotel", "36934");

        GetResponse response = client.get(request, RequestOptions.DEFAULT);

        String json = response.getSourceAsString();

        HotelDoc hotelDoc = JSON.parseObject(json, HotelDoc.class);

        return hotelDoc;
    }

    @GetMapping("/update/doc")
    public String updateDoc() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(HttpHost.create("http://localhost:9200")));

        UpdateRequest request = new UpdateRequest("hotel", "36934");

        request.doc("price", "300", "starName", "一钻");

        client.update(request, RequestOptions.DEFAULT);

        return "success";
    }

    @GetMapping("/delete/doc")
    public String deleteDoc() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(HttpHost.create("http://localhost:9200")));

        DeleteRequest request = new DeleteRequest("hotel", "36934");

        client.delete(request, RequestOptions.DEFAULT);

        return "success";
    }

    @GetMapping("/batch/doc")
    public String batchDoc() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(HttpHost.create("http://localhost:9200")));

        BulkRequest request = new BulkRequest();

        List<Hotel> hotels = hotelService.list();

        hotels.forEach(p -> {
            HotelDoc hotelDoc = BeanUtil.copyProperties(p, HotelDoc.class);
            hotelDoc.setLocation(p.getLatitude() + "," + p.getLongitude());

            request.add(new IndexRequest("hotel").id(p.getId().toString())
                    .source(JSON.toJSONString(hotelDoc), XContentType.JSON));
        });

        client.bulk(request, RequestOptions.DEFAULT);

        return "success";
    }


    @GetMapping("/match/all")
    public String matchAll() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(HttpHost.create("http://localhost:9200")));

        SearchRequest request = new SearchRequest("hotel");

        request.source().query(QueryBuilders.matchAllQuery());

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        handleResponse(response);

        return "success";
    }

    @GetMapping("/match")
    public String match() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(HttpHost.create("http://localhost:9200")));

        SearchRequest request = new SearchRequest("hotel");

        request.source().query(QueryBuilders.matchQuery("all", "北京"));

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        handleResponse(response);

        return "success";
    }

    @GetMapping("/bool")
    public String bool() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(HttpHost.create("http://localhost:9200")));

        SearchRequest request = new SearchRequest("hotel");

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        boolQuery.must().add(QueryBuilders.termQuery("city", "上海"));

        boolQuery.filter().add(QueryBuilders.rangeQuery("price").lte("200"));

        request.source().query(boolQuery);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        handleResponse(response);

        return "success";
    }

    @GetMapping("/page")
    public String page() throws IOException {

        int pageNum = 2;
        int pageSize = 5;

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(HttpHost.create("http://localhost:9200")));

        SearchRequest request = new SearchRequest("hotel");

        request.source().query(QueryBuilders.matchAllQuery());

        request.source().sort("price", SortOrder.ASC);

        request.source().from((pageNum - 1) * pageSize).size(pageSize);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        handleResponse(response);

        return "success";
    }

    @GetMapping("/high/light")
    public String highLight() throws IOException {

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(HttpHost.create("http://localhost:9200")));

        SearchRequest request = new SearchRequest("hotel");

        request.source().query(QueryBuilders.matchQuery("all", "如家"));

        request.source().highlighter(new HighlightBuilder().field("name").requireFieldMatch(false));

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        handleResponse(response);

        return "success";
    }

    private void handleResponse(SearchResponse response) {
        SearchHits searchHits = response.getHits();

        long total = searchHits.getTotalHits().value;

        System.out.println("======共搜索到:" + total + "条======");

        SearchHit[] hits = searchHits.getHits();

        for (SearchHit hit : hits) {
            String json = hit.getSourceAsString();
            HotelDoc hotelDoc = JSON.parseObject(json, HotelDoc.class);

            //处理高亮
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();

            if (ObjectUtil.isNotEmpty(highlightFields)) {
                HighlightField field = highlightFields.get("name");

                if (ObjectUtil.isNotEmpty(field)) {
                    String name = field.getFragments()[0].string();
                    hotelDoc.setName(name);
                }
            }

            System.out.println(hotelDoc);
        }
    }

    @GetMapping("/one")
    public Hotel getOne() {
        List<Hotel> list = hotelService.list();
        Hotel hotel = list.get(0);
        return hotel;
    }
}
