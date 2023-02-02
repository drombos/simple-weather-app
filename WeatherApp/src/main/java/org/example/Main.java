package org.example;

import org.example.service.AddLocationService;
import org.example.service.DisplayLocationsService;
import org.example.service.DownloadForecastsService;
import org.example.service.EndProgramService;
import org.example.ui.console.*;
import org.example.ui.MainMenu;

public class Main {
    public static void main(String[] args) {

        ConsoleUI consoleUI = new ConsoleUI(
                new MainMenu(),
                new AddLocationMenu(),
                new DisplayLocationsMenu(),
                new DownloadForecastsMenu(),
                new EndProgramMenu()
        );

        App.init(
                consoleUI,
                new AddLocationService(),
                new DisplayLocationsService(),
                new DownloadForecastsService(),
                new EndProgramService()
        );

        App.getInstance().run();
    }
}