package org.example.ui;

import org.example.AppCallback;
import org.example.http.dtos.LocationDto;
import org.example.http.query.ApiLocationQuery;

import java.util.Collection;

public interface UIAddLocation {
    ApiLocationQuery askForLocation();

    LocationDto specifyLocationFromMultiple(Collection<? extends LocationDto> locations);
}
