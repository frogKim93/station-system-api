package com.frogkim93.stationsystemapi.weather.dto.forecast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Header {
    private String resultCode;
    private String resultMsg;
}
