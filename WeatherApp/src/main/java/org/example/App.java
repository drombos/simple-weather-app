package org.example;

import org.example.http.ApiClientPool;
import org.example.http.dtos.LocationDto;
import org.example.http.query.ApiLocationQuery;
import org.example.service.AddLocationService;
import org.example.ui.UIManager;

import java.util.Set;

public class App {
    private final UIManager uiManager;
    private final AddLocationService addLocationService;
    App(UIManager uiManager, AddLocationService addLocationService) {
        this.uiManager = uiManager;
        uiManager.register(this);

        this.addLocationService = addLocationService;
    }

    void run() {
        uiManager.startMainMenu();
    }

    public boolean addLocationOption() {
        return addLocationService.addLocationOption(uiManager.getAddLocationHandler());
    }

    public boolean displayLocationsOption() {
        System.out.println("Wybrano opcję 2.");
        return false;
    }

    public boolean getForecastsOption() {
        System.out.println("Wybrano opcję 3.");
        return false;
    }

    public boolean invalidCommand() {
        uiManager.invalidCommand();
        return true;
    }
}
