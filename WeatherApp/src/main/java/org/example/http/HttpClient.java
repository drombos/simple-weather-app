package org.example.http;

import org.example.http.dtos.LocationDto;

import java.util.Set;

public interface HttpClient {
    Set<? extends LocationDto> queryLocations(ApiQuery query);
}
