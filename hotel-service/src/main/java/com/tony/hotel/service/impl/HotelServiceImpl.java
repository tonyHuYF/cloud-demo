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
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

        if (ObjectUtil.isNotEmpty(param.getKey())) {
            request.source().query(QueryBuilders.matchQuery("all", param.getKey()));
        } else {
            request.source().query(QueryBuilders.matchAllQuery());
        }

        request.source().from((param.getPage() - 1) * param.getSize()).size(param.getSize());

//        if (!StrUtil.equals(param.getSortBy(), "default")) {
//            request.source().sort(param.getSortBy());
//        }

        SearchResponse search = restHighLevelClient.search(request, RequestOptions.DEFAULT);

        SearchHits searchHits = search.getHits();

        long total = searchHits.getTotalHits().value;

        SearchHit[] hits = searchHits.getHits();

        List<HotelDoc> list = new ArrayList<>();

        for (SearchHit hit : hits) {
            String source = hit.getSourceAsString();
            HotelDoc hotelDoc = JSON.parseObject(source, HotelDoc.class);
            list.add(hotelDoc);
        }

        TableResultBean resultBean = new TableResultBean(total, list);

        return resultBean;
    }
}




