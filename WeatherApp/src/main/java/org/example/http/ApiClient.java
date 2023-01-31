package org.example.http;

import org.example.http.dtos.ForecastDto;
import org.example.http.dtos.LocationDto;

import java.util.Set;

public interface ApiClient {
    Set<? extends LocationDto> queryLocations(ApiQuery query);
    Set<? extends ForecastDto> queryForecasts(LocationDto location);
}
