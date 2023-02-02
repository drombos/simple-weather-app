package main.java.org.example.menu;

import java.util.Scanner;

//klasa zarządzająca menu
public class Menu {

    public void startmenu() {

        Scanner scanner = new Scanner(System.in);
        String userOptions = "";
        LocationDisplay locationDisplay = new LocationDisplay();
        LocationMenu locationMenu = new LocationMenu();
        ForecastDownload forecastDownload = new ForecastDownload();
        ProgramEnd programEnd = new ProgramEnd();
        ErrorMenu errorMenu = new ErrorMenu();

        while (!userOptions.equals("d")) {
            System.out.println("""
                    Wybierz opcję:
                    a. Dodaj lokalizację.
                    b. Wyświetl wszystkie lokalizacje.
                    c. Pobierz prognozę pogody.
                    d. Zakończ program.""");
            userOptions = scanner.nextLine();

            switch (userOptions) {
                case "a":
                    locationMenu.addLocation();
                    break;
                case "b":
                    locationDisplay.displayLocation();
                    break;
                case "c":
                    forecastDownload.downloadForecast();
                    break;
                case "d":
                    programEnd.displayEnd();
                    break;
                default:
                    errorMenu.displayError();
            }
            System.out.println();
        }
    }
}






