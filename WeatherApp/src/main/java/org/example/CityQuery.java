package main.java.org.example;

import java.util.Scanner;

public class CityQuery {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Location userLocation = new Location();
        String userCity;
        String userState;
        String userCountry;

        do {
            System.out.print("Enter the name of the city: ");
            userCity = scanner.nextLine();
            userLocation.setCity(userCity);
        } while (userCity.equals(""));

        System.out.print("Enter the name of the state: ");
        userState = scanner.nextLine();
        userLocation.setState(userState);


        System.out.print("Enter the name of the country: ");
        userCountry= scanner.nextLine();
        if (userCountry.equals("")) {
            userCountry = "Poland";
        }
        userLocation.setCountry(userCountry);

        System.out.println(userLocation);
    }
}





