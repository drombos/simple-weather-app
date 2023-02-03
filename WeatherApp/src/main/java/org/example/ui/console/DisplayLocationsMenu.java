package org.example.ui.console;

import org.example.persistence.model.DbLocation;
import org.example.ui.submenu.DisplayLocationsUI;

import java.util.List;

//klasa odpowiedzialna za wyświetlanie lokalizacji
//punkt 2 submenu
public class DisplayLocationsMenu implements DisplayLocationsUI {
    @Override
    public void displayAll(List<DbLocation> locations) {
        if (locations.isEmpty()) {
            System.out.println("Brak zapisanych lokalizacji.");
        } else {
            locations.forEach(System.out::println);
        }

    }
}
