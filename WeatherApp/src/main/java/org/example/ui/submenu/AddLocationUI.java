package org.example.ui.submenu;

import org.example.http.dto.LocationDto;
import org.example.http.query.ApiLocationQuery;
import org.example.persistence.model.DbLocation;

import java.util.Collection;

public interface AddLocationUI {
    ApiLocationQuery buildLocationQueryFromInput();

    LocationDto specifyLocationFromMultiple(Collection<? extends LocationDto> locations);

    void noLocationsFound(ApiLocationQuery query);

    void locationAlreadyAdded(DbLocation location);
}