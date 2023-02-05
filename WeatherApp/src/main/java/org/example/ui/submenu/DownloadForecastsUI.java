package org.example.ui.submenu;

import org.example.persistence.model.DbForecast;
import org.example.persistence.model.DbLocation;
import org.example.util.FormatMapper;

import java.util.Map;
import java.util.Set;

public interface DownloadForecastsUI {
    void noLocations();
    void displayForecasts(Map<DbLocation, Set<DbForecast>> locationsWithForecasts);
}
