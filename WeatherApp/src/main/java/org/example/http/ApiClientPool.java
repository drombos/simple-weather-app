package org.example.http;

import org.example.http.client.Api;
import org.example.http.client.ApiClient;
import org.example.http.dtos.ForecastsDto;
import org.example.http.dtos.LocationDto;
import org.example.http.query.ApiLocationQuery;

import java.util.*;
import java.util.stream.Collectors;

public class ApiClientPool {
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


    public static Set<? extends LocationDto> queryLocations(ApiLocationQuery query) {
        return getInstance().locationSources.stream()
                .flatMap(client -> client.queryLocations(query).stream())
                .collect(Collectors.toSet());
    }

    public static Set<? extends ForecastsDto> queryForecasts(LocationDto location) {
        return getInstance().forecastsSources.stream()
                .flatMap(client -> client.queryForecasts(location).stream())
                .collect(Collectors.toSet());
    }

    public static Map<LocationDto, Set<? extends ForecastsDto>> queryForecasts(Collection<LocationDto> locations) {
        Map<LocationDto, Set<? extends ForecastsDto>> locationToForecasts = new HashMap<>();
        for (LocationDto location : locations) {
            Set<? extends ForecastsDto> forecasts = queryForecasts(location);
            locationToForecasts.put(location, forecasts);
        }
        return locationToForecasts;
    }
}
