package org.example;

import org.example.http.ApiClientPool;
import org.example.http.dtos.LocationDto;
import org.example.http.query.ApiLocationQuery;
import org.example.service.AddLocationService;
import org.example.ui.UIManager;

import java.util.Set;

public class App {
    private App() {}
    private static class Holder {
        private static final App INSTANCE = new App();
    }
    public static App getInstance() {
        return Holder.INSTANCE;
    }

    public static void init(AddLocationService addLocationService) {
        getInstance().addLocationService = addLocationService;
    }
    private AddLocationService addLocationService;

    void run() {
        UIManager.getInstance().startMainMenu();
    }

    public boolean addLocationOption() {
        return addLocationService.addLocationOption();
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
        UIManager.getInstance().invalidCommand();
        return true;
    }
}
