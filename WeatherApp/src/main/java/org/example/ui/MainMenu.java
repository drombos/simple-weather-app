package org.example.ui;

import org.example.App;
import org.example.AppCallback;

import java.util.Scanner;
import java.util.function.Supplier;

public class MainMenu implements AppCallback {
    private App app = null;

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
                case "a" -> runIfInit(() -> app.addLocationOption());
                case "b" -> runIfInit(() -> app.displayLocationsOption());
                case "c" -> runIfInit(() -> app.getForecastsOption());
                case "d" -> System.out.println("Koniec programu.");
                default -> runIfInit(() -> app.invalidCommand());
            }
            System.out.println();
        }
    }

    @Override
    public void register(App app) {
        this.app = app;
    }

    private boolean runIfInit(Supplier<Boolean> command) {
        if (app == null) {
            throw new IllegalStateException("BŁĄD: Aplikacja nie została prawidłowa zainicjalizowana.");
        }
        return command.get();
    }
}
