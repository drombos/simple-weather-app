package org.example.ui;

import org.example.App;
import org.example.ui.submenu.*;


import java.util.Collection;

public class ConsoleUI implements UI {
    public ConsoleUI(MainMenu mainMenu, AddLocationMenu addLocationMenu, LocationDisplay locationDisplay,
                     ForecastDownload forecastDownload, ProgramEnd programEnd) {
        this.mainMenu = mainMenu;
        this.addLocationMenu = addLocationMenu;
        this.locationDisplay = locationDisplay;
        this.forecastDownload = forecastDownload;
        this.programEnd = programEnd;
    }
    private final MainMenu mainMenu;
    private final AddLocationMenu addLocationMenu;
    private final LocationDisplay locationDisplay;
    private final ForecastDownload forecastDownload;
    private final ProgramEnd programEnd;

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
