package org.example.ui.submenu;

import org.example.http.dto.ForecastsDto;
import org.example.http.query.ApiForecastQuery;
import org.example.util.FormatMapper;

import java.util.Map;
import java.util.Set;

public interface DownloadForecastsUI {
    void noLocations();
    void displayForecasts(Map<ApiForecastQuery, Set<? extends ForecastsDto>> locationsWithForecasts) throws FormatMapper.ParsingException;
}
