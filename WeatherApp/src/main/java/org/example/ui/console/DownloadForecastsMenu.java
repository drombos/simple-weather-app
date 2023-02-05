package org.example.ui.console;

import org.example.persistence.model.DbForecast;
import org.example.persistence.model.DbLocation;
import org.example.ui.ForecastDisplayFormat;
import org.example.ui.submenu.DownloadForecastsUI;
import org.example.util.FormatMapper;

import java.util.Map;
import java.util.Set;

//klasa odpowiedzialna za wyświetlanie pogody
//punkt 3 menu
public class DownloadForecastsMenu implements DownloadForecastsUI {
    @Override
    public void noLocations() {
        System.out.println("W bazie danych nie ma żadnych zapisanych lokalizacji.");
    }

    @Override
    public void displayForecasts(Map<DbLocation, Set<DbForecast>> locationsWithForecasts) {
        FormatMapper mapper = new FormatMapper();
        for (Map.Entry<DbLocation, Set<DbForecast>> entry : locationsWithForecasts.entrySet()) {
            DbLocation location = entry.getKey();
            Set<DbForecast> forecasts = entry.getValue();

            System.out.printf("\n\t\t\t####   %s   ####\n\n", location);

            for (DbForecast forecast : forecasts) {
                displayFormattedForecast(mapper, forecast);
            }
        }
    }

    private void displayFormattedForecast(FormatMapper mapper, DbForecast forecast) {
        ForecastDisplayFormat formattedForecast = mapper.dbToDisplay(forecast);
        System.out.println(formattedForecast);
    }
}
