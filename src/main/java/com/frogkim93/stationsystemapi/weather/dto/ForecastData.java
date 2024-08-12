package com.frogkim93.stationsystemapi.weather.dto;

import com.frogkim93.stationsystemapi.weather.dto.forecast.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForecastData {
    private Response response;
}
