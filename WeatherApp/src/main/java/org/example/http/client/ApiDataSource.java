package org.example.http.client;

import org.example.http.query.ApiLocationQuery;
import org.example.http.dtos.ForecastsDto;
import org.example.http.dtos.LocationDto;

import java.util.Set;

public interface ApiDataSource {
    Set<? extends LocationDto> queryLocations(ApiLocationQuery query);
    Set<? extends ForecastsDto> queryForecasts(LocationDto location);
}
