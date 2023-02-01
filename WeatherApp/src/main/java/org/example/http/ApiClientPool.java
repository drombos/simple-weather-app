package org.example.http;

import org.example.http.client.Api;
import org.example.http.client.ApiClient;
import org.example.http.client.ApiDataSource;
import org.example.http.dtos.ForecastsDto;
import org.example.http.dtos.LocationDto;

import java.util.*;
import java.util.stream.Collectors;

public class ApiClientPool implements ApiDataSource {
    private final Set<ApiClient> locationSources = new HashSet<>();
    private final Set<ApiClient> forecastsSources = new HashSet<>();
    private ApiClientPool() {
        ApiClient accuweather = new ApiClient(Api.ACCUWEATHER);
        ApiClient openweather = new ApiClient(Api.OPENWEATHER);

        locationSources.add(accuweather);

        forecastsSources.add(accuweather);
        forecastsSources.add(openweather);
    }
    private static class Holder {
        private static final ApiClientPool INSTANCE = new ApiClientPool();
    }

    public static ApiClientPool getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public Set<? extends LocationDto> queryLocations(ApiQuery query) {
        return locationSources.stream()
                .flatMap(client -> client.queryLocations(query).stream())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<? extends ForecastsDto> queryForecasts(LocationDto location) {
        return forecastsSources.stream()
                .flatMap(client -> client.queryForecasts(location).stream())
                .collect(Collectors.toSet());
    }

    public Map<LocationDto, Set<? extends ForecastsDto>> queryForecasts(Collection<LocationDto> locations) {
        Map<LocationDto, Set<? extends ForecastsDto>> locationToForecasts = new HashMap<>();
        for (LocationDto location : locations) {
            Set<? extends ForecastsDto> forecasts = queryForecasts(location);
            locationToForecasts.put(location, forecasts);
        }
        return locationToForecasts;
    }
}
