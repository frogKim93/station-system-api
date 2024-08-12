package com.frogkim93.stationsystemapi.weather.dto.forecast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Body {
    private String dataType;
    private Items items;
    private int pageNo;
    private int numOfRows;
    private int totalCount;
}
