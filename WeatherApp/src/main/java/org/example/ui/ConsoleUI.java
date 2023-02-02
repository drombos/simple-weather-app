package org.example.ui;

import org.example.App;
import org.example.http.dtos.LocationDto;
import org.example.http.query.ApiLocationQuery;

import java.util.Collection;

public class ConsoleUI implements UI {
    private App app = null;
    private final MainMenu mainMenu;
    private final LocationMenu locationMenu;

    public ConsoleUI(MainMenu mainMenu, LocationMenu locationMenu) {
        this.mainMenu = mainMenu;
        this.locationMenu = locationMenu;
    }

    @Override
    public void register(App app) {
        this.app = app;
        this.mainMenu.register(app);
        this.locationMenu.register(app);
    }

    @Override
    public void startMainMenu() {
        mainMenu.loop();
    }

    @Override
    public ApiLocationQuery askForLocation() {
        return locationMenu.buildLocationQueryFromInput();
    }

    @Override
    public LocationDto specifyLocationFromMultiple(Collection<? extends LocationDto> locations) {
        return locationMenu.specifyLocationFromMultiple(locations);
    }
}
