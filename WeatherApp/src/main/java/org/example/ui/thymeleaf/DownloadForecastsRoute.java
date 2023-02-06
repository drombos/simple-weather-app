package org.example.ui.thymeleaf;

import org.example.persistence.model.DbForecast;
import org.example.persistence.model.DbLocation;
import org.example.ui.submenu.DownloadForecastsUI;
import org.thymeleaf.context.WebContext;

import java.util.Map;
import java.util.Set;

public class DownloadForecastsRoute implements DownloadForecastsUI, ContextParametersSource {
    @Override
    public void noLocations() {

    }

    @Override
    public void displayForecasts(Map<DbLocation, Set<DbForecast>> locationsWithForecasts) {

    }

    @Override
    public Map<String, Object> getContextVariables(WebContext context) {
        return null;
    }
}
