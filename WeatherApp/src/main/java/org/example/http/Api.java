package org.example.http;

import org.example.http.services.AccuweatherRetrofitService;
import org.example.http.services.OpenweatherRetrofitService;
import org.example.http.services.RetrofitServiceFactory;
import org.example.http.services.Service;

public enum Api {
    ACCUWEATHER("AccuWeather.com", "accuweather_key", "http://dataservice.accuweather.com/"),
    OPENWEATHER("OpenWeatherMap.org", "openweather_key", "https://api.openweathermap.org/");
    final String name;
    final String credentialsKey;
    private final String baseUrl;

    Api(String name, String credentialsKey, String baseUrl) {
        this.name = name;
        this.credentialsKey = credentialsKey;
        this.baseUrl = baseUrl;
    }

    Service getService() {
        RetrofitServiceFactory factory = new RetrofitServiceFactory(baseUrl);
        return switch (this) {
            case ACCUWEATHER -> factory.createService(AccuweatherRetrofitService.class);
            case OPENWEATHER -> factory.createService(OpenweatherRetrofitService.class);
        };
    }

    Service getService(String overrideUrl) {
        RetrofitServiceFactory factory = new RetrofitServiceFactory(overrideUrl);
        return switch (this) {
            case ACCUWEATHER -> factory.createService(AccuweatherRetrofitService.class);
            case OPENWEATHER -> factory.createService(OpenweatherRetrofitService.class);
        };
    }
}
