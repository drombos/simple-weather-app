package org.example.ui.console;

import org.example.ui.submenu.AddLocationUI;
import org.example.ui.MainMenu;
import org.example.ui.UI;
import org.example.ui.submenu.DisplayLocationsUI;
import org.example.ui.submenu.DownloadForecastsUI;
import org.example.ui.submenu.EndProgramUI;

public class ConsoleUI implements UI {
    public ConsoleUI(MainMenu mainMenu, AddLocationMenu addLocationMenu, DisplayLocationsMenu displayLocationsMenu,
                     DownloadForecastsMenu downloadForecastsMenu, EndProgramMenu endProgramMenu) {
        this.mainMenu = mainMenu;
        this.addLocationMenu = addLocationMenu;
        this.displayLocationsMenu = displayLocationsMenu;
        this.downloadForecastsMenu = downloadForecastsMenu;
        this.endProgramMenu = endProgramMenu;
    }
    private final MainMenu mainMenu;
    private final AddLocationMenu addLocationMenu;
    private final DisplayLocationsMenu displayLocationsMenu;
    private final DownloadForecastsMenu downloadForecastsMenu;
    private final EndProgramMenu endProgramMenu;

    @Override
    public AddLocationUI getAddLocationMenu() {
        return addLocationMenu;
    }

    @Override
    public DisplayLocationsUI getDisplayLocationsMenu() {
        return displayLocationsMenu;
    }

    @Override
    public DownloadForecastsUI getDownloadForecastsMenu() {
        return downloadForecastsMenu;
    }

    @Override
    public EndProgramUI getEndProgramMenu() {
        return endProgramMenu;
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
