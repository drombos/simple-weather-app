package org.example.ui.submenu;

import org.example.persistence.model.DbLocation;

import java.util.List;

public interface DisplayLocationsUI {
    void displayAll(List<DbLocation> locations);
}
