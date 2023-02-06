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
    public static final String FORECAST_OFFSET_KEY = "offset";
    private static final Integer DEFAULT_FORECAST_OFFSET = 0;
    public DownloadForecastsHandler(DownloadForecastsUI ui, ErrorUI errorUI, Dao dao) {
        super(ui, errorUI, dao);
    }

    @Override
    public boolean perform() {
        Map<String, Object> defaultContext = Map.of(FORECAST_OFFSET_KEY, DEFAULT_FORECAST_OFFSET);
        return perform(defaultContext);
    }

    @Override
    public boolean perform(Map<String, Object> contextVariables) {
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

        int daysFromNow;
        Object rawOffsetData = contextVariables.getOrDefault(FORECAST_OFFSET_KEY, 0);
        if (rawOffsetData.getClass() == Integer.class) {
            daysFromNow = (Integer) rawOffsetData;
        } else {
            errorUI.printError("Błędne zmienne kontekstowe, dla klucza %s oczekiwano Integer, otrzymano: %s"
                    .formatted(FORECAST_OFFSET_KEY, rawOffsetData.getClass().getName()));
            daysFromNow = 0;
        }

        Map<DbLocation, Set<DbForecast>> forecastsToLocation = new HashMap<>();
        queriesToForecastsResults.forEach((location, forecasts) -> {
            DbLocation dbLocation = queriesToLocations.get(location);
            Set<DbForecast> dbForecasts = convertApiResultsToDbEntities(forecasts, daysFromNow);
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

//    private Map<DbLocation, Set<DbForecast>> getAllForecastsMapped() {
//        List<DbLocation> allLocations = dao.readAll();
//        Map<DbLocation, Set<DbForecast>> allForecasts = new HashMap<>();
//        for (DbLocation dbLocation : allLocations) {
//            allForecasts.put(dbLocation, dbLocation.getForecasts());
//        }
//
//        return allForecasts;
//    }

    private Set<DbForecast> convertApiResultsToDbEntities(Collection<? extends ForecastsDto> apiForecasts,
                                                          Integer daysFromNow) {
        FormatMapper mapper = new FormatMapper();
        Set<DbForecast> dbForecasts = new HashSet<>();
        for (ForecastsDto f : apiForecasts) {
            try {
                DbForecast dbForecast = mapper.apiToDb(f, daysFromNow);
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
