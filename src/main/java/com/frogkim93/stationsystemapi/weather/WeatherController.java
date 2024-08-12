package com.frogkim93.stationsystemapi.weather;

import com.frogkim93.stationsystemapi.weather.dto.WeatherDto;
import com.frogkim93.stationsystemapi.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/weather")
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping
    private ResponseEntity<WeatherDto> getWeatherByCoordinate(@RequestParam double latitude, @RequestParam double longitude) {
        return weatherService.getWeather(latitude, longitude);
    }
}
