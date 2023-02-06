package org.example;

import org.example.ui.ErrorUI;
import org.example.ui.UI;
import org.example.ui.submenu.AddLocationUI;
import org.example.ui.submenu.DisplayLocationsUI;
import org.example.ui.submenu.DownloadForecastsUI;
import org.example.ui.submenu.EndProgramUI;

public class MockUI implements UI {
    @Override
    public void startMainMenu() {

    }

    @Override
    public void invalidCommand() {

    }

    @Override
    public AddLocationUI getAddLocationMenu() {
        return null;
    }

    @Override
    public DisplayLocationsUI getDisplayLocationsMenu() {
        return null;
    }

    @Override
    public DownloadForecastsUI getDownloadForecastsMenu() {
        return null;
    }

    @Override
    public EndProgramUI getEndProgramMenu() {
        return null;
    }

    @Override
    public ErrorUI getErrorUI() {
        return null;
    }
}
