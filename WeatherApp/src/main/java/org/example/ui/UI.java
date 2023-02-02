package org.example.ui;

import org.example.ui.submenu.AddLocationUI;
import org.example.ui.submenu.DisplayLocationsUI;
import org.example.ui.submenu.DownloadForecastsUI;
import org.example.ui.submenu.EndProgramUI;

public interface UI {
    void startMainMenu();

    void invalidCommand();

    AddLocationUI getAddLocationMenu();
    DisplayLocationsUI getDisplayLocationsMenu();
    DownloadForecastsUI getDownloadForecastsMenu();
    EndProgramUI getEndProgramMenu();
}
