package org.example.service;

import org.example.App;
import org.example.http.ApiClientPool;
import org.example.http.dtos.LocationDto;
import org.example.http.query.ApiLocationQuery;
import org.example.ui.submenu.AddLocationUI;

import java.util.Set;

public class AddLocationService {
    public boolean perform() {
        AddLocationUI ui = App.getUI().getAddLocationMenu();

        ApiLocationQuery query = ui.buildLocationQueryFromInput();
        Set<? extends LocationDto> matchingLocations = ApiClientPool.queryLocations(query);

        if (matchingLocations.isEmpty()) {
            return false;
        }

        LocationDto location;
        if (matchingLocations.size() > 1) {
            location = ui.specifyLocationFromMultiple(matchingLocations);
        } else {
            location = matchingLocations.stream().toList().get(0);
        }

        return location != null;
    }
}
