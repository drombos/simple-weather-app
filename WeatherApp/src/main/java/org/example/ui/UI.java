package org.example.ui;

import org.example.AppComponent;
import org.example.http.dtos.LocationDto;
import org.example.http.query.ApiLocationQuery;

import java.util.Collection;

public interface UI extends AppComponent {
    void startMainMenu();
    ApiLocationQuery askForLocation();

    LocationDto specifyLocationFromMultiple(Collection<? extends LocationDto> locations);
}
