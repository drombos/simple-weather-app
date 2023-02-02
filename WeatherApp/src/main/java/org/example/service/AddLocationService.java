package org.example.service;

import org.example.http.ApiClientPool;
import org.example.http.dtos.LocationDto;
import org.example.http.query.ApiLocationQuery;
import org.example.ui.UIAddLocation;
import org.example.ui.UIManager;

import java.util.Set;

public class AddLocationService {
    public boolean addLocationOption() {
        UIAddLocation addLocationUI = UIManager.getInstance().getAddLocationHandler();

        ApiLocationQuery query = addLocationUI.askForLocation();
        Set<? extends LocationDto> locations = ApiClientPool.queryLocations(query);

        //System.out.println("QUERY: " + query.toQuery());

        if (locations.isEmpty()) {
            return false;
        }

        LocationDto location;
        if (locations.size() > 1) {
            location = addLocationUI.specifyLocationFromMultiple(locations);
        } else {
            location = locations.stream().toList().get(0);
        }

        //System.out.println(location);

        return location != null;
    }
}
