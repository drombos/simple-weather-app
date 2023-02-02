package org.example;

import org.example.persistence.Dao;
import org.example.service.*;
import org.example.ui.UI;

public class App {
    private static final App INSTANCE = new App();
    private static boolean initialized = false;
    private UI ui;
    private Dao dao;
    private AddLocationService addLocationService;
    private DisplayLocationsService displayLocationsService;
    private DownloadForecastsService downloadForecastsService;
    private EndProgramService endProgramService;

    private App() {}

    public static App getInstance() {
        return INSTANCE;
    }

    public static UI getUI() {
        return getInstance().ui;
    }

    public static Dao getDao() {
        return getInstance().dao;
    }

    public static void init(
            UI uiSolution,
            Dao dao,
            AddLocationService addLocationService,
            DisplayLocationsService displayLocationsService,
            DownloadForecastsService downloadForecastsService,
            EndProgramService endProgramService
    ) {
        App app = getInstance();
        app.ui = uiSolution;
        app.dao = dao;
        app.addLocationService = addLocationService;
        app.displayLocationsService = displayLocationsService;
        app.downloadForecastsService = downloadForecastsService;
        app.endProgramService = endProgramService;

        initialized = true;
    }

    void run() {
        if (!initialized) {
            throw new IllegalStateException("Najpierw zainicjalizuj App wywołując App::init");
        }
        ui.startMainMenu();
    }

    public boolean addLocationOption() {
        return addLocationService.perform();
    }

    public boolean displayLocationsOption() {
        return displayLocationsService.perform();
    }

    public boolean downloadForecastsOption() {
        return downloadForecastsService.perform();
    }

    public boolean endProgramOption() {
        return endProgramService.perform();
    }

    public boolean invalidCommand() {
        ui.invalidCommand();
        return true;
    }
}
