package main.java.org.example;

import java.util.Scanner;

public class LocationMenu {

    public static void location() {

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
        System.out.println(userCityQuery);
    }
}





