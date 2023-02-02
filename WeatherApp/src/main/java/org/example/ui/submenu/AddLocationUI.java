package org.example.ui.submenu;

import org.example.http.dtos.LocationDto;
import org.example.http.query.ApiLocationQuery;

import java.util.Collection;

public interface AddLocationUI {
    ApiLocationQuery buildLocationQueryFromInput();

    LocationDto specifyLocationFromMultiple(Collection<? extends LocationDto> locations);
}