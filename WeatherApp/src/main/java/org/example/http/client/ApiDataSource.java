package org.example.http.client;

import org.example.http.query.ApiForecastQuery;
import org.example.http.query.ApiLocationQuery;
import org.example.http.dto.ForecastsDto;
import org.example.http.dto.LocationDto;

import java.util.Set;

public interface ApiDataSource {
    Set<? extends LocationDto> queryLocations(ApiLocationQuery query);
    Set<? extends ForecastsDto> queryForecasts(ApiForecastQuery location);
}
