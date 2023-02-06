package org.example.handler;

import org.example.http.ApiClientPool;
import org.example.http.dto.ForecastsDto;
import org.example.http.query.ApiForecastQuery;
import org.example.persistence.Dao;
import org.example.persistence.model.DbForecast;
import org.example.util.FormatMapper;
import org.example.persistence.model.DbLocation;
import org.example.ui.ErrorUI;
import org.example.ui.submenu.DownloadForecastsUI;

import java.util.*;
import java.util.stream.Collectors;

public class DownloadForecastsHandler extends AbstractCommandHandler<DownloadForecastsUI> {
    public DownloadForecastsHandler(DownloadForecastsUI ui, ErrorUI errorUI, Dao dao) {
        super(ui, errorUI, dao);
    }

    @Override
    public boolean perform() {
        Set<DbLocation> dbLocations = new HashSet<>(dao.readAll());

        if (dbLocations.isEmpty()) {
            ui.noLocations();
            return false;
        }

        Map<ApiForecastQuery, DbLocation> queriesToLocations = assignDbEntitiesToApiQueries(dbLocations);

        Map<ApiForecastQuery, Set<? extends ForecastsDto>> queriesToForecastsResults =
                ApiClientPool.queryForecasts(queriesToLocations.keySet());

        if (queriesToForecastsResults.isEmpty()) {
            errorUI.printError("Nie udało się pobrać prognoz. Prawdopodobnie problem z API.");
            return false;
        }

        Map<DbLocation, Set<DbForecast>> forecastsToLocation = new HashMap<>();
        queriesToForecastsResults.forEach((location, forecasts) -> {
            DbLocation dbLocation = queriesToLocations.get(location);
            Set<DbForecast> dbForecasts = convertApiResultsToDbEntities(forecasts);
            if (dbLocation != null && !dbForecasts.isEmpty()) {
                dbForecasts.forEach(dbLocation::addForecast);
                forecastsToLocation.put(dbLocation, dbForecasts);
            }
            boolean updateSuccessful = dao.update(dbLocation);
            if (!updateSuccessful) {
                throw new RuntimeException("Błąd aktualizacji prognoz w bazie danych.");
            }
        });

        ui.displayForecasts(forecastsToLocation);

        return true;
    }

    private Set<DbForecast> convertApiResultsToDbEntities(Collection<? extends ForecastsDto> apiForecasts) {
        FormatMapper mapper = new FormatMapper();
        Set<DbForecast> dbForecasts = new HashSet<>();
        for (ForecastsDto f : apiForecasts) {
            try {
                DbForecast dbForecast = mapper.apiToDb(f, 0);
                dbForecasts.add(dbForecast);
            } catch (FormatMapper.ParsingException e) {
                errorUI.printError("Pominięto dodawanie prognozy do bazy danych.");
            }
        }
        return dbForecasts;
    }

    private Map<ApiForecastQuery, DbLocation> assignDbEntitiesToApiQueries(Collection<DbLocation> dbLocations) {
        FormatMapper mapper = new FormatMapper();
        return dbLocations.stream()
                .collect(Collectors.toMap(
                        mapper::dbToApi,
                        location -> location
                ));
    }
}
