package org.example.service;

import org.example.App;
import org.example.ui.submenu.DisplayLocationsUI;

public class DisplayLocationsService {
    public boolean perform() {
        DisplayLocationsUI ui = App.getUI().getDisplayLocationsMenu();
        return true;
    }
}
