package org.example;

import org.example.persistence.HibernateRepository;
import org.example.handler.AddLocationHandler;
import org.example.handler.DisplayLocationsHandler;
import org.example.handler.DownloadForecastsHandler;
import org.example.handler.EndProgramHandler;
import org.example.ui.console.*;
import org.example.ui.MainMenu;

public class Main {
    public static void main(String[] args) {

        ConsoleUI consoleUI = new ConsoleUI(
                new ConsoleErrorUI(),
                new MainMenu(),
                new AddLocationMenu(),
                new DisplayLocationsMenu(),
                new DownloadForecastsMenu(),
                new EndProgramMenu()
        );

        HibernateRepository dao = new HibernateRepository();

        App.runWith(
                consoleUI,
                new AddLocationHandler(
                        consoleUI.getAddLocationMenu(),
                        consoleUI.getErrorUI(),
                        dao
                ),
                new DisplayLocationsHandler(
                        consoleUI.getDisplayLocationsMenu(),
                        consoleUI.getErrorUI(),
                        dao
                ),
                new DownloadForecastsHandler(
                        consoleUI.getDownloadForecastsMenu(),
                        consoleUI.getErrorUI(),
                        dao
                ),
                new EndProgramHandler(
                        consoleUI.getEndProgramMenu(),
                        consoleUI.getErrorUI(),
                        dao)
        );
    }
}