package org.example.http;

import org.example.http.client.Api;
import org.example.http.client.ApiClient;
import org.example.http.dto.ForecastsDto;
import org.example.http.dto.LocationDto;
import org.example.http.query.ApiForecastQuery;
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

    public static Set<? extends ForecastsDto> queryForecasts(ApiForecastQuery location) {
        return getInstance().forecastsSources.stream()
                .flatMap(client -> client.queryForecasts(location).stream())
                .collect(Collectors.toSet());
    }

    public static Map<ApiForecastQuery, Set<? extends ForecastsDto>> queryForecasts(Collection<ApiForecastQuery> locations) {
        Map<ApiForecastQuery, Set<? extends ForecastsDto>> locationToForecasts = new HashMap<>();
        for (ApiForecastQuery location : locations) {
            Set<? extends ForecastsDto> forecasts = queryForecasts(location);
            if (!forecasts.isEmpty()) {
                locationToForecasts.put(location, forecasts);
            }
        }
        return locationToForecasts;
    }
}
