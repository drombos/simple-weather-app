package org.example.ui.console;

import org.example.http.dto.LocationDto;
import org.example.http.query.ApiLocationQuery;
import org.example.http.query.CityQuery;
import org.example.persistence.model.DbLocation;
import org.example.ui.submenu.AddLocationUI;

import java.util.*;

//klasa odpowiedzialna za uzyskanie od użytkownika danych lokalizacji
//punkt 1 submenu
public class AddLocationMenu implements AddLocationUI {
    @Override
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

    @Override
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

        return null;
    }

    @Override
    public void noLocationsFound(ApiLocationQuery query) {
        System.out.println("Nie znaleziono wyników dla zapytania:");
        System.out.println(query);
    }

    @Override
    public void locationAlreadyAdded(DbLocation location) {
        System.out.println(location + " już jest w bazie danych.");
    }
}





