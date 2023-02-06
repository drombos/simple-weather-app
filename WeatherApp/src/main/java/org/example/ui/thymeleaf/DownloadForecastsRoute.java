package org.example.ui.thymeleaf;

import org.example.persistence.model.DbForecast;
import org.example.persistence.model.DbLocation;
import org.example.ui.ForecastDisplayFormat;
import org.example.ui.submenu.DownloadForecastsUI;
import org.example.util.FormatMapper;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebRequest;

import java.util.*;

public class DownloadForecastsRoute implements DownloadForecastsUI, ContextParametersSource {
    private boolean noLocations;
    private Map<DbLocation, Set<ForecastDisplayFormat>> locationsWithDisplay = null;

    @Override
    public void noLocations() {
        this.noLocations = true;
    }

    @Override
    public void displayForecasts(Map<DbLocation, Set<DbForecast>> locationsWithForecasts) {
        this.noLocations = false;

        FormatMapper mapper = new FormatMapper();

        locationsWithDisplay = new HashMap<>();
        for (Map.Entry<DbLocation, Set<DbForecast>> entry : locationsWithForecasts.entrySet()) {
            Set<ForecastDisplayFormat> displays = new HashSet<>();
            for (DbForecast forecast : entry.getValue()) {
                ForecastDisplayFormat display = mapper.dbToDisplay(forecast);
                displays.add(display);
            }
            locationsWithDisplay.put(entry.getKey(), displays);
        }
    }

    @Override
    public Map<String, Object> getContextVariables(WebContext context) {
        Map<String, Object> contextVariables = new HashMap<>();

        if (noLocations) {
            contextVariables.put("noLocationsDisplay", "block");
            contextVariables.put("forecastsDisplay", "none");
        } else {
            contextVariables.put("noLocationsDisplay", "none");
            contextVariables.put("forecastsDisplay", "block");
        }

        contextVariables.put(
                "locationsWithDisplay",
                Objects.requireNonNullElseGet(locationsWithDisplay, HashMap::new)
        );


        return contextVariables;
    }
}
