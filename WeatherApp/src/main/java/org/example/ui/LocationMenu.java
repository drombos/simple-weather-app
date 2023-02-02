package org.example.ui;

import org.example.App;
import org.example.AppComponent;
import org.example.http.dtos.LocationDto;
import org.example.http.query.ApiLocationQuery;
import org.example.http.query.CityQuery;

import java.util.*;

public class LocationMenu implements AppComponent {
    private App app = null;

    public ApiLocationQuery buildLocationQueryFromInput() {
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

        return new CityQuery(userCity, userState, userCountry);
    }

    public LocationDto specifyLocationFromMultiple(Collection<? extends LocationDto> locations) {
        List<LocationDto> list = new ArrayList<>(locations);
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i));
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Wpisz numer (ID) wybranego miasta ===");
        System.out.println("\tZakres: 1 - " + list.size());
        try {
            int id = scanner.nextInt() - 1;
            if (id >= 0 && id < list.size()) {
                return list.get(id);
            } else {
                System.out.println("ID poza zakresem.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Niewłaściwy format ID.");
        }

        System.out.println("Operacja anulowana.");
        return null;
    }

    @Override
    public void register(App app) {
        this.app = app;
    }
}





