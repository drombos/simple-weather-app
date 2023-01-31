package main.java.org.example;

import java.util.Scanner;

public class CityQuery {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        City userCity = new City();


        System.out.print("Enter the name of the city: ");
        userCity.setCity(scanner.nextLine());
        System.out.print("Enter the name of the state: ");
        userCity.setState(scanner.nextLine());
        System.out.print("Enter the name of the country: ");
        userCity.setCountry(scanner.nextLine());

        System.out.println(userCity);
    }



}

