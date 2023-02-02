package org.example;

import org.example.http.ApiClientPool;
import org.example.http.dtos.LocationDto;
import org.example.http.query.ApiLocationQuery;
import org.example.ui.UI;

import java.util.Set;

public class App {
    private final UI ui;
    App(UI ui) {
        this.ui = ui;
        ui.register(this);
    }

    void run() {
        ui.startMainMenu();
    }

    public boolean addLocationOption() {
        ApiLocationQuery query = ui.askForLocation();
        Set<? extends LocationDto> locations = ApiClientPool.queryLocations(query);

        System.out.println("QUERY: " + query.toQuery());

        if (locations.isEmpty()) {
            return false;
        }

        LocationDto location;
        if (locations.size() > 1) {
            location = ui.specifyLocationFromMultiple(locations);
        } else {
            location = locations.stream().toList().get(0);
        }

        System.out.println(location);

        return location != null;
    }

    public boolean displayLocationsOption() {
        System.out.println("Wybrano opcję 2.");
        return false;
    }

    public boolean getForecastsOption() {
        System.out.println("Wybrano opcję 3.");
        return false;
    }
}
