package main.java.org.example;

import java.util.Scanner;

public class LocationMenu {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Location userLocation = new Location();
        String userCity;
        String userState;
        String userCountry;

        do {
            System.out.print("Wpisz nazwę miasta: ");
            userCity = scanner.nextLine();
            userLocation.setCity(userCity);
        } while (userCity.equals(""));

        System.out.print("Wpisz nazwę województwa: ");
        userState = scanner.nextLine();
        userLocation.setState(userState);


        System.out.print("Wpisz nazwę kraju: ");
        userCountry = scanner.nextLine();
        if (userCountry.equals("")) {
            userCountry = "Poland";
        }
        userLocation.setCountry(userCountry);

        System.out.println(userLocation);
    }
}





