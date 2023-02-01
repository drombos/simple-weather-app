package org.example.http;

import org.example.http.dtos.AccuweatherLocationDto;
import org.example.http.dtos.Dto;
import org.example.http.dtos.ForecastsDto;
import org.example.http.dtos.LocationDto;
import org.example.http.services.AccuweatherRetrofitService;
import org.example.http.services.OpenweatherRetrofitService;
import org.example.http.services.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class ApiClient implements ApiDataSource {
    private final static String CREDENTIALS_PATH = "api";
    private final static String API_ERROR_MSG = "\nNie udało się połączyć z API %s :(((\n";
    private final static String NO_RESULTS_MSG = "\nBrak wyników z API %s.\n";
    private final Api api;
    private final String apiKey;
    private final Service service;

    public ApiClient(Api api) {
        this.api = api;
        this.apiKey = ResourceBundle.getBundle(CREDENTIALS_PATH).getString(api.credentialsKey);
        this.service = api.getService();
    }

    ApiClient(Api api, String overrideUrl) {
        this.api = api;
        this.apiKey = ResourceBundle.getBundle(CREDENTIALS_PATH).getString(api.credentialsKey);
        this.service = api.getService(overrideUrl);
    }

    @Override
    public Set<? extends ForecastsDto> queryForecasts(LocationDto location) {
        Set<ForecastsDto> forecasts = new HashSet<>();
        ApiForecastFunction callMethod = switch (api) {
            case ACCUWEATHER -> this::getAccuweatherForecast;
            case OPENWEATHER -> this::getOpenweatherForecast;
        };

        try {
            ForecastsDto dto = (ForecastsDto) callMethod.call(location);
            if (dto != null && dto.isProperlyFormed()) forecasts.add(dto);

        } catch (IOException e) {
            System.out.printf(API_ERROR_MSG, api.name);
        }

        if (forecasts.isEmpty()) System.out.printf(NO_RESULTS_MSG, api.name);

        return forecasts;
    }

    @Override
    public Set<? extends LocationDto> queryLocations(ApiQuery query) {
        Set<? extends LocationDto> forecasts = null;
        ApiLocationFunction callMethod = switch (api) {
            case ACCUWEATHER -> this::getAccuweatherLocations;
            case OPENWEATHER -> this::getOpenweatherLocations;
        };

        try {
            forecasts = callMethod.call(query);
        } catch (IOException e) {
            System.out.printf(API_ERROR_MSG, api.name);
        }

        if (forecasts == null) forecasts = new HashSet<>();

        if (forecasts.isEmpty()) System.out.printf(NO_RESULTS_MSG, api.name);

        return forecasts;
    }

    private interface ApiForecastFunction {
        Dto call(LocationDto location) throws IOException;
    }

    private interface ApiLocationFunction {
        Set<? extends LocationDto> call(ApiQuery query) throws IOException;
    }

    private Set<AccuweatherLocationDto> getAccuweatherLocations(ApiQuery query) throws IOException {
        AccuweatherRetrofitService accuweather = (AccuweatherRetrofitService) service;
        return accuweather.getLocations(apiKey, query.toQuery(), "pl-pl")
                .execute()
                .body();
    }

    private Set<LocationDto> getOpenweatherLocations(ApiQuery query) throws IOException {
        System.out.println("Ten endpoint jeszcze nie jest obsługiwany");
        return null;
    }

    private ForecastsDto getAccuweatherForecast(LocationDto location) throws IOException {
        AccuweatherRetrofitService accuweather = (AccuweatherRetrofitService) service;
        return accuweather.getForecasts(
                location.accuweatherLocationKey(),
                apiKey,
                "pl-pl",
                true,
                true
        ).execute().body();
    }

    private ForecastsDto getOpenweatherForecast(LocationDto location) throws IOException {
        OpenweatherRetrofitService openweather = (OpenweatherRetrofitService) service;
        return openweather.getForecasts(
                location.openweatherLatitude(),
                location.openweatherLongitude(),
                apiKey,
                "metric",
                "pl",
                "current,minutely,hourly,alerts"
        ).execute().body();
    }
}

