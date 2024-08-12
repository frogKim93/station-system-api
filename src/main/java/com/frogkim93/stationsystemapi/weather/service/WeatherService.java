package com.frogkim93.stationsystemapi.weather.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frogkim93.stationsystemapi.utils.LambertConformalConic;
import com.frogkim93.stationsystemapi.weather.dto.ForecastData;
import com.frogkim93.stationsystemapi.weather.dto.WeatherDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class WeatherService {
    public ResponseEntity<WeatherDto> getWeather(double latitude, double longitude) {
        try {
            // HttpClient 객체 생성
            int[] nxny = LambertConformalConic.lamcproj(longitude, latitude);

            LocalDateTime now = LocalDateTime.now();

            if (now.getMinute() < 30) {
                now = now.minusHours(1);
            }

            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH");

            HttpClient client = HttpClient.newHttpClient();
            String uri = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst?";
            uri += "serviceKey=KDWs7igdaG6%2BCjOo%2BxZAUGL3AOc1ZG7NRB7rwvnauE0Av9NmvKfL7nHWSQfzC7ge13YvBvn1qszTaLTsc6VAsw%3D%3D";
            uri += "&base_date=" + now.format(dateFormat);
            uri += "&base_time=" + now.format(timeFormat) + "30";
            uri += "&nx=" + nxny[0];
            uri += "&ny=" + nxny[1];
            uri += "&dataType=JSON";
            uri += "&numOfRows=100";

            System.out.println(uri);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(uri))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            ForecastData forecastData = mapper.readValue(response.body(), ForecastData.class);

            now = LocalDateTime.now();
            WeatherDto weatherDto = new WeatherDto();

            for (int i = 0; i < forecastData.getResponse().getBody().getItems().getItem().size(); i++) {
                String forecastDate = forecastData.getResponse().getBody().getItems().getItem().get(i).getFcstDate();
                String forecastTime = forecastData.getResponse().getBody().getItems().getItem().get(i).getFcstTime();

                if (now.format(dateFormat).equals(forecastDate)) {
                    if ((now.format(timeFormat)+"00").equals(forecastTime)) {
                        String category = forecastData.getResponse().getBody().getItems().getItem().get(i).getCategory();
                        String value = forecastData.getResponse().getBody().getItems().getItem().get(i).getFcstValue();

                        switch (category) {
                            case "T1H":
                                weatherDto.setTemperature(Double.parseDouble(value));
                                break;
                            case "RN1":
                                weatherDto.setRainStatus(value);
                                break;
                            case "SKY":
                                weatherDto.setSkyCode(Integer.parseInt(value));
                                break;
                            case "VEC":
                                weatherDto.setWindDirection(Double.parseDouble(value));
                                break;
                            case "WSD":
                                weatherDto.setWindSpeed(Double.parseDouble(value));
                                break;
                        }
                    }
                }
            }

            return ResponseEntity.ok(weatherDto);
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.internalServerError().build();
        }
    }
}
