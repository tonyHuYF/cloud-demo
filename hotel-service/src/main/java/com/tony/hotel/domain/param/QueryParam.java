package com.tony.hotel.domain.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryParam {

    private String key;

    private Integer page;

    private Integer size;

    private String sortBy;

}
