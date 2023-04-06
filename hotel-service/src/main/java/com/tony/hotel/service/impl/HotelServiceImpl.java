package com.tony.hotel.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tony.hotel.domain.Hotel;
import com.tony.hotel.domain.TableResultBean;
import com.tony.hotel.domain.param.QueryParam;
import com.tony.hotel.domain.vo.HotelDoc;
import com.tony.hotel.mapper.HotelMapper;
import com.tony.hotel.service.HotelService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author TonyHu
 * @description 针对表【tb_hotel】的数据库操作Service实现
 * @createDate 2023-03-31 15:28:49
 */
@Service
public class HotelServiceImpl extends ServiceImpl<HotelMapper, Hotel>
        implements HotelService {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Override
    public TableResultBean listPage(QueryParam param) throws IOException {

        SearchRequest request = new SearchRequest("hotel");

        handleBasicQueue(param, request);


        if (ObjectUtil.isNotEmpty(param.getLocation())) {
            request.source().sort(SortBuilders
                    .geoDistanceSort("location", new GeoPoint(param.getLocation()))
                    .order(SortOrder.ASC)
                    .unit(DistanceUnit.KILOMETERS));
        }

        request.source().from((param.getPage() - 1) * param.getSize()).size(param.getSize());

        SearchResponse search = restHighLevelClient.search(request, RequestOptions.DEFAULT);

        SearchHits searchHits = search.getHits();

        long total = searchHits.getTotalHits().value;

        SearchHit[] hits = searchHits.getHits();

        List<HotelDoc> list = new ArrayList<>();

        for (SearchHit hit : hits) {
            String source = hit.getSourceAsString();
            HotelDoc hotelDoc = JSON.parseObject(source, HotelDoc.class);

            Object[] sortValues = hit.getSortValues();

            if (ObjectUtil.isNotEmpty(sortValues)) {
                hotelDoc.setDistance(sortValues[0]);
            }

            list.add(hotelDoc);
        }

        TableResultBean resultBean = new TableResultBean(total, list);

        return resultBean;
    }

    @Override
    public Map<String, List<String>> filters(QueryParam param) throws IOException {
        SearchRequest request = new SearchRequest("hotel");

        handleBasicQueue(param, request);

        request.source().size(0);

        request.source().aggregation(AggregationBuilders.terms("brandAgg").field("brand").size(100));
        request.source().aggregation(AggregationBuilders.terms("cityAgg").field("city").size(100));
        request.source().aggregation(AggregationBuilders.terms("starNameAgg").field("starName").size(100));

        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);

        Aggregations aggregations = response.getAggregations();

        List<String> brandAgg = getAggByName(aggregations, "brandAgg");
        List<String> cityAgg = getAggByName(aggregations, "cityAgg");
        List<String> starNameAgg = getAggByName(aggregations, "starNameAgg");

        Map<String, List<String>> map = new HashMap<>();
        map.put("brand", brandAgg);
        map.put("city", cityAgg);
        map.put("starName", starNameAgg);

        return map;
    }

    private List<String> getAggByName(Aggregations aggregations, String aggName) {
        List<String> list = new ArrayList<>();

        Terms terms = aggregations.get(aggName);

        List<? extends Terms.Bucket> buckets = terms.getBuckets();

        for (Terms.Bucket bucket : buckets) {
            String key = bucket.getKeyAsString();
            list.add(key);
        }

        return list;
    }

    private void handleBasicQueue(QueryParam param, SearchRequest request) {
        BoolQueryBuilder boolQuery = new BoolQueryBuilder();

        if (ObjectUtil.isNotEmpty(param.getKey())) {
            boolQuery.must(QueryBuilders.matchQuery("all", param.getKey()));
        } else {
            boolQuery.must(QueryBuilders.matchAllQuery());
        }

        if (ObjectUtil.isNotEmpty(param.getCity())) {
            boolQuery.filter(QueryBuilders.termQuery("city", param.getCity()));
        }

        if (ObjectUtil.isNotEmpty(param.getBrand())) {
            boolQuery.filter(QueryBuilders.termQuery("brand", param.getBrand()));
        }

        if (ObjectUtil.isNotEmpty(param.getStarName())) {
            boolQuery.filter(QueryBuilders.termQuery("starName", param.getStarName()));
        }

        if (ObjectUtil.isNotEmpty(param.getMinPrice()) && ObjectUtil.isNotEmpty(param.getMaxPrice())) {
            boolQuery.filter(QueryBuilders.rangeQuery("price").lte(param.getMaxPrice()).gte(param.getMinPrice()));
        }


        FunctionScoreQueryBuilder functionScoreQuery =
                QueryBuilders.functionScoreQuery(
                        boolQuery,
                        new FunctionScoreQueryBuilder.FilterFunctionBuilder[]{
                                new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                                        QueryBuilders.termQuery("isAD", true),
                                        ScoreFunctionBuilders.weightFactorFunction(10))
                        });

        request.source().query(functionScoreQuery);
    }
}




