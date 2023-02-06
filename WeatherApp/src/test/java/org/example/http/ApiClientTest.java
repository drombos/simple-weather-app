package org.example.http;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.example.http.client.Api;
import org.example.http.client.ApiClient;
import org.example.http.dto.ForecastsDto;
import org.example.http.dto.LocationDto;
import org.example.http.query.CityQuery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ApiClientTest {
    private static final String RESOURCE_BASE_PATH = "src/test/resources/";
    MockWebServer server = new MockWebServer();

    @AfterEach
    void tearDown() throws IOException {
        server.shutdown();
    }

    @ParameterizedTest
    @ValueSource(ints = { 401, 404, 500 })
    void givenFailedResponse_queryForecasts_returnsEmptySet(int responseCode) throws Exception {
        server.start();
        LocationDto location = fakeLocation("", 10000.0, 10000.0);

        Arrays.stream(Api.values()).forEach(api -> {
            server.enqueue(new MockResponse().setResponseCode(responseCode));

            HttpUrl baseUrl = forecastUrlFromApi(api);
            ApiClient client = new ApiClient(api, baseUrl.toString());

            Set<? extends ForecastsDto> response = client.queryForecasts(location);
            assertTrue(response.isEmpty());
        });
    }

    @Test
    void givenMalformedResponse_queryForecasts_returnsEmptySet() throws Exception {
        server.start();
        LocationDto location = fakeLocation("", 10000.0, 10000.0);

        Arrays.stream(Api.values()).forEach(api -> {
            server.enqueue(new MockResponse()
                    .setResponseCode(200)
                    .setBody("{}"));
            HttpUrl baseUrl = locationUrlFromApi(api);
            ApiClient client = new ApiClient(api, baseUrl.toString());

            Set<? extends ForecastsDto> response = client.queryForecasts(location);
            assertTrue(response.isEmpty());
        });
    }

    @Test
    void givenExampleResponse_accuweatherQueryForecasts_returnsForecasts() throws Exception {
        server.start();
        server.enqueue(getResponseFromFile(new File(RESOURCE_BASE_PATH + "exampleAccuweatherForecasts.json")));

        HttpUrl baseUrl = locationUrlFromApi(Api.ACCUWEATHER);
        ApiClient client = new ApiClient(Api.ACCUWEATHER, baseUrl.toString());

        LocationDto location = fakeLocation("", 10000.0, 10000.0);

        Set<? extends ForecastsDto> response = client.queryForecasts(location);
        System.out.println(response);
        assertEquals(1, response.size());
        assertTrue(response.toString().contains("ACCUWEATHER"));
        assertEquals(7, response.toString().lines().count());
        assertEquals(5, response.toString().lines().filter(line -> line.contains("°C")).count());
    }

    @Test
    void givenExampleResponse_openweatherQueryForecasts_returnsForecasts() throws Exception {
        server.start();
        server.enqueue(getResponseFromFile(new File(RESOURCE_BASE_PATH + "exampleOpenweatherForecasts.json")));

        HttpUrl baseUrl = locationUrlFromApi(Api.OPENWEATHER);
        ApiClient client = new ApiClient(Api.OPENWEATHER, baseUrl.toString());

        LocationDto location = fakeLocation("", 10000.0, 10000.0);

        Set<? extends ForecastsDto> response = client.queryForecasts(location);
        System.out.println(response);
        assertEquals(1, response.size());
        assertTrue(response.toString().contains("OPENWEATHER"));
        assertEquals(10, response.toString().lines().count());
        assertEquals(8, response.toString().lines().filter(line -> line.contains("°C")).count());
    }

    @ParameterizedTest
    @ValueSource(ints = { 401, 404, 500 })
    void givenFailedResponse_queryLocations_returnsEmptySet(int responseCode) throws Exception {
        server.start();

        Arrays.stream(Api.values()).forEach(api -> {
            server.enqueue(new MockResponse().setResponseCode(responseCode));
            HttpUrl baseUrl = locationUrlFromApi(api);
            ApiClient client = new ApiClient(api, baseUrl.toString());

            Set<? extends LocationDto> response = client.queryLocations(fakeCityQuery());
            assertTrue(response.isEmpty());
        });
    }

    @Test
    void givenEmptyResponse_queryLocations_returnsEmptySet() throws Exception {
        server.start();

        Arrays.stream(Api.values()).forEach(api -> {
            server.enqueue(new MockResponse()
                    .setResponseCode(200)
                    .setBody("[]"));
            HttpUrl baseUrl = locationUrlFromApi(api);
            ApiClient client = new ApiClient(api, baseUrl.toString());

            Set<? extends LocationDto> response = client.queryLocations(fakeCityQuery());
            assertTrue(response.isEmpty());
        });
    }

    @Test
    void givenMalformedResponse_queryLocations_returnsEmptySet() throws Exception {
        server.start();

        Arrays.stream(Api.values()).forEach(api -> {
            server.enqueue(new MockResponse()
                    .setResponseCode(200)
                    .setBody("[{}]"));
            HttpUrl baseUrl = locationUrlFromApi(api);
            ApiClient client = new ApiClient(api, baseUrl.toString());

            Set<? extends LocationDto> response = client.queryLocations(fakeCityQuery());
            assertTrue(response.isEmpty());
        });
    }

    @Test
    void givenExampleResponse_accuweatherQueryLocations_returnsLocations() throws Exception {
        server.start();
        server.enqueue(getResponseFromFile(new File(RESOURCE_BASE_PATH + "exampleAccuweatherLocation.json")));

        HttpUrl baseUrl = locationUrlFromApi(Api.ACCUWEATHER);
        ApiClient client = new ApiClient(Api.ACCUWEATHER, baseUrl.toString());

        Set<? extends LocationDto> response = client.queryLocations(fakeCityQuery());
        assertEquals(4, response.size());
        assertEquals(4, response.stream().map(Object::toString).filter(str -> str.contains("Jarocin")).count());
    }

    private MockResponse getResponseFromFile(File file) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String json = reader.lines().collect(Collectors.joining());

        return new MockResponse()
                .setResponseCode(200)
                .setBody(json);
    }

    private LocationDto fakeLocation(String accuweatherLocation, Double latitude, Double longitude) {
        return new LocationDto() {
            @Override
            public String accuweatherLocationKey() {
                return accuweatherLocation;
            }
            @Override
            public Double openweatherLatitude() {
                return latitude;
            }
            @Override
            public Double openweatherLongitude() {
                return longitude;
            }
            @Override
            public boolean isProperlyFormed() {
                return true;
            }
        };
    }

    private CityQuery fakeCityQuery() {
        return new CityQuery("asdasd", null, null);
    }

    private HttpUrl locationUrlFromApi(Api api) {
        return switch (api) {
            case ACCUWEATHER -> server.url("/locations/v1/cities/search/");
            case OPENWEATHER -> server.url("/geo/1.0/direct/");
        };
    }

    private HttpUrl forecastUrlFromApi(Api api) {
        return switch (api) {
            case ACCUWEATHER -> server.url("/forecasts/v1/daily/5day/TEST/");
            case OPENWEATHER -> server.url("/data/3.0/onecall/");
        };
    }


}