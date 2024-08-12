package com.frogkim93.stationsystemapi.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDto {
    private double temperature;
    private double windSpeed;
    private double windDirection;
    private double humidity;
    private int skyCode; //1: 맑음, 3: 구름많음, 4:흐림
    private String rainStatus;
}
