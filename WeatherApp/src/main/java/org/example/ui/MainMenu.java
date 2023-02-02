package org.example.ui;

import org.example.App;

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
                case "a" -> app.addLocationOption();
                case "b" -> app.displayLocationsOption();
                case "c" -> app.downloadForecastsOption();
                case "d" -> app.endProgramOption();
                default -> app.invalidCommand();
            }
            System.out.println();
        }
    }
}
