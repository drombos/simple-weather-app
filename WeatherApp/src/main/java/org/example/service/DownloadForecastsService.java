package org.example.service;

import org.example.App;
import org.example.ui.submenu.DownloadForecastsUI;

public class DownloadForecastsService {
    public boolean perform() {
        DownloadForecastsUI ui = App.getUI().getDownloadForecastsMenu();
        return true;
    }
}
