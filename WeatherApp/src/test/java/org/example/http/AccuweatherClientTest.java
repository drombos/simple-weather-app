package org.example.http;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.example.http.dtos.AccuweatherLocationDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.*;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class AccuweatherClientTest {
    MockWebServer server = new MockWebServer();

    @AfterEach
    void tearDown() throws IOException {
        server.shutdown();
    }

    @ParameterizedTest
    @ValueSource(ints = { 401, 404, 500 })
    void givenFailedMockResponse_queryLocations_returnsEmptySet(int responseCode) throws Exception {
        server.enqueue(new MockResponse().setResponseCode(responseCode));

        server.start();

        HttpUrl baseUrl = server.url("/locations/v1/cities/search/");
        AccuweatherClient client = new AccuweatherClient(baseUrl.toString());

        Set<AccuweatherLocationDto> response = client.queryLocations(() -> "fail query");
        assertTrue(response.isEmpty());
    }

    @Test
    void givenEmptyResponse_queryLocations_returnsEmptySet() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("[]"));

        server.start();
        HttpUrl baseUrl = server.url("/locations/v1/cities/search/");
        AccuweatherClient client = new AccuweatherClient(baseUrl.toString());

        Set<AccuweatherLocationDto> response = client.queryLocations(() -> "empty query");
        assertTrue(response.isEmpty());
    }

    @Test
    void givenExampleResponse_queryLocations_returnSet() throws Exception {
        server.enqueue(getResponseFromFile(new File("src/test/resources/exampleLocationResponse.json")));

        server.start();
        HttpUrl baseUrl = server.url("/locations/v1/cities/search/");
        AccuweatherClient client = new AccuweatherClient(baseUrl.toString());

        Set<AccuweatherLocationDto> response = client.queryLocations(() -> "valid query");
        assertEquals(4, response.size());
    }

    private MockResponse getResponseFromFile(File file) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String json = reader.lines().collect(Collectors.joining());

        return new MockResponse()
                .setResponseCode(200)
                .setBody(json);
    }
}