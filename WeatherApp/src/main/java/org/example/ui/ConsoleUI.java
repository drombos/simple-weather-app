package org.example.ui;

import org.example.App;
import org.example.http.dtos.LocationDto;
import org.example.http.query.ApiLocationQuery;
import org.example.ui.submenu.*;


import java.util.Collection;

public class ConsoleUI implements UIManager {
    private App app = null;
    private final MainMenu mainMenu;
    private final AddLocationMenu addLocationMenu;
    private final LocationDisplay locationDisplay;
    private final ForecastDownload forecastDownload;
    private final ProgramEnd programEnd;

    public ConsoleUI(MainMenu mainMenu,
                     AddLocationMenu addLocationMenu,
                     LocationDisplay locationDisplay,
                     ForecastDownload forecastDownload,
                     ProgramEnd programEnd
    ) {
        this.mainMenu = mainMenu;
        this.addLocationMenu = addLocationMenu;
        this.locationDisplay = locationDisplay;
        this.forecastDownload = forecastDownload;
        this.programEnd = programEnd;
    }

    @Override
    public void register(App app) {
        this.app = app;
        this.mainMenu.register(app);
        //this.addLocationMenu.register(app);
    }

    @Override
    public UIAddLocation getAddLocationHandler() {
        return addLocationMenu;
    }

    @Override
    public void startMainMenu() {
        mainMenu.loop();
    }

    @Override
    public void invalidCommand() {
        System.out.println("Wprowadź litery a, b, c lub d!");
    }
}
