package org.example.http;

import org.example.http.dtos.AccuweatherLocationDto;
import org.example.http.services.AccuweatherRetrofitService;
import org.example.http.services.RetrofitServiceFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class AccuweatherClient implements HttpClient {
    private final static String CREDENTIALS_PATH = "api";
    private final static String ACCUWEATHER_KEY = ResourceBundle.getBundle(CREDENTIALS_PATH)
            .getString("accuweather_key");
    private final AccuweatherRetrofitService service;

    public AccuweatherClient(String baseUrl) {
        this.service = new RetrofitServiceFactory(baseUrl).createService(AccuweatherRetrofitService.class);
    }

    public AccuweatherClient() {
        this("http://dataservice.accuweather.com/");
    }

    @Override
    public Set<AccuweatherLocationDto> queryLocations(ApiQuery query) {
        Set<AccuweatherLocationDto> locations;
        try {
            locations = service.getLocations(ACCUWEATHER_KEY, query.toQuery(), "pl-pl")
                    .execute()
                    .body();
        } catch (IOException e) {
            System.out.println("Nie udało się połączyć z API.");
            throw new RuntimeException(e);
        }

        if (locations == null) {
            locations = new HashSet<>();
        }

        return locations;
    }
}