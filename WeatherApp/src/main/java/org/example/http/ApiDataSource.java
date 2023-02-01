package org.example.http;

import org.example.http.dtos.ForecastsDto;
import org.example.http.dtos.LocationDto;

import java.util.Set;

public interface ApiDataSource {
    Set<? extends LocationDto> queryLocations(ApiQuery query);
    Set<? extends ForecastsDto> queryForecasts(LocationDto location);
}
