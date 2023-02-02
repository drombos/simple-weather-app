package org.example;

import org.example.service.AddLocationService;
import org.example.ui.ConsoleUI;
import org.example.ui.MainMenu;
import org.example.ui.UIManager;
import org.example.ui.submenu.*;

public class Main {
    public static void main(String[] args) {
        UIManager uiManager = new ConsoleUI(
                new MainMenu(),
                new AddLocationMenu(),
                new LocationDisplay(),
                new ForecastDownload(),
                new ProgramEnd()
        );

        App app = new App(
                uiManager,
                new AddLocationService()
        );
        app.run();
    }
}