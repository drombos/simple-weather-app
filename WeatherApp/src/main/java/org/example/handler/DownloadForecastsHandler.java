package org.example.handler;

import org.example.http.ApiClientPool;
import org.example.http.dto.ForecastsDto;
import org.example.http.query.ApiForecastQuery;
import org.example.persistence.Dao;
import org.example.util.FormatMapper;
import org.example.persistence.model.DbLocation;
import org.example.ui.ErrorUI;
import org.example.ui.submenu.DownloadForecastsUI;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DownloadForecastsHandler extends AbstractCommandHandler<DownloadForecastsUI> {
    public DownloadForecastsHandler(DownloadForecastsUI ui, ErrorUI errorUI, Dao dao) {
        super(ui, errorUI, dao);
    }

    @Override
    public boolean perform() {
        List<DbLocation> locationsFromDb = dao.readAll();

        if (locationsFromDb.isEmpty()) {
            ui.noLocations();
            return false;
        }

        Set<ApiForecastQuery> locationQueries = convertDbEntitiesToApiQueries(locationsFromDb);

        Map<ApiForecastQuery, Set<? extends ForecastsDto>> locationsWithForecasts =
                ApiClientPool.queryForecasts(locationQueries);

        if (locationsWithForecasts.isEmpty()) {
            errorUI.printError("Nie udało się pobrać prognoz. Prawdopodobnie problem z API.");
            return false;
        }

        try {
            ui.displayForecasts(locationsWithForecasts);
        } catch (FormatMapper.ParsingException e) {
            errorUI.printError("Błąd przetwarzania prognozy pogody z API: " + e.getMessage());
        }

        return true;
    }

    private Set<ApiForecastQuery> convertDbEntitiesToApiQueries(Collection<DbLocation> locations) {
        FormatMapper mapper = new FormatMapper();
        return locations.stream()
                .map(mapper::dbToApi)
                .collect(Collectors.toSet());
    }
}
