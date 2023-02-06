package org.example.ui.console;

import org.example.App;
import org.example.handler.AddLocationHandler;
import org.example.handler.DisplayLocationsHandler;
import org.example.handler.DownloadForecastsHandler;
import org.example.handler.EndProgramHandler;

import java.util.Scanner;

public class MainMenu {
    private final App app = App.getInstance();

    public void loop() {
        Scanner scanner = new Scanner(System.in);
        String usersChoice = "";

        while (!usersChoice.equals("d")) {
            System.out.println("""
                    Wybierz opcję:
                    a. Dodaj lokalizację.
                    b. Wyświetl wszystkie lokalizacje.
                    c. Pobierz prognozę pogody.
                    d. Zakończ program.
                    """);
            usersChoice = scanner.nextLine();

            switch (usersChoice) {
                case "a" -> app.performAction(AddLocationHandler.class);
                case "b" -> app.performAction(DisplayLocationsHandler.class);
                case "c" -> app.performAction(DownloadForecastsHandler.class);
                case "d" -> app.performAction(EndProgramHandler.class);
                default -> app.invalidCommand();
            }
            System.out.println();
        }
    }
}
