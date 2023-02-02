package org.example;

import org.example.ui.ConsoleUI;
import org.example.ui.LocationMenu;
import org.example.ui.MainMenu;
import org.example.ui.UI;

public class Main {
    public static void main(String[] args) {
        UI ui = new ConsoleUI(new MainMenu(), new LocationMenu());

        App app = new App(ui);
        app.run();
    }
}