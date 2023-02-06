package org.example.ui.console;

import org.example.App;
import org.example.handler.AddLocationHandler;
import org.example.handler.DisplayLocationsHandler;
import org.example.handler.DownloadForecastsHandler;
import org.example.handler.EndProgramHandler;

import java.util.HashMap;
import java.util.Map;
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
                default -> {
                    boolean validCommand = handleParameterizedCommand(usersChoice);
                    if (!validCommand) {
                        app.invalidCommand();
                    }
                }
            }
            System.out.println();
        }
    }

    private boolean handleParameterizedCommand(String input) {
        if (input.matches("c \\d")) {
            String[] splitInput = input.split(" ");
            try {
                int forecastOffset = Integer.parseInt(splitInput[1]);

                Map<String, Object> contextVariables = new HashMap<>();
                contextVariables.put(DownloadForecastsHandler.FORECAST_OFFSET_KEY, forecastOffset);

                app.performAction(DownloadForecastsHandler.class, contextVariables);

                return true;
            } catch (NumberFormatException ignore) {
                //ignore
            }
        }
        return false;
    }
}
