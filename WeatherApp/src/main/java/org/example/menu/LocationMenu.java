package main.java.org.example.menu;

import main.java.org.example.CityQuery;

import java.util.Scanner;

//klasa odpowiedzialna za uzyskanie od użytkownika danych lokalizacji
//punkt 1 menu
public class LocationMenu {

    public void addLocation() {

        Scanner scanner = new Scanner(System.in);
        String userCity;
        String userState;
        String userCountry;

        do {
            System.out.print("Wpisz nazwę miasta: ");
            userCity = scanner.nextLine();
        } while (userCity.equals(""));

        System.out.print("Wpisz nazwę województwa: ");
        userState = scanner.nextLine();


        System.out.print("Wpisz nazwę kraju: ");
        userCountry = scanner.nextLine();
        if (userCountry.equals("")) {
            userCountry = "Poland";
        }


        CityQuery userCityQuery = new CityQuery(userCity, userState, userCountry);
    }
}





