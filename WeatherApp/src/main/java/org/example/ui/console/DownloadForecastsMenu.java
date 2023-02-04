package org.example.ui.console;

import org.example.http.dto.ForecastsDto;
import org.example.http.query.ApiForecastQuery;
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
    public void displayForecasts(Map<ApiForecastQuery, Set<? extends ForecastsDto>> locationsWithForecasts) throws FormatMapper.ParsingException {
        FormatMapper mapper = new FormatMapper();
        for (Map.Entry<ApiForecastQuery, Set<? extends ForecastsDto>> entry : locationsWithForecasts.entrySet()) {
            ApiForecastQuery location = entry.getKey();
            Set<? extends ForecastsDto> forecasts = entry.getValue();

            System.out.printf("####   %s   ####\n", location);

            for (ForecastsDto forecast : forecasts) {
                displayFormattedForecast(mapper, forecast);
            }
        }
    }

    private void displayFormattedForecast(FormatMapper mapper, ForecastsDto forecast) throws FormatMapper.ParsingException {
        ForecastDisplayFormat formattedForecast = mapper.apiToDisplay(forecast, 0);
        System.out.println(formattedForecast);
    }
}
