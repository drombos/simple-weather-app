package org.example.http.services;

import okhttp3.Request;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import static org.junit.jupiter.api.Assertions.*;

class RetrofitServiceFactoryTest {

    @ParameterizedTest
    @ValueSource(strings = { "http://test/", "https://test/", "http://api.test.com/" })
    void givenValidUrl_createdRetrofitInstance_hasValidUrl(String actualUrl) {
        RetrofitServiceFactory factory = new RetrofitServiceFactory(actualUrl);

        String retrofitUrl = factory.retrofit.baseUrl().url().toString();
        assertEquals(actualUrl, retrofitUrl);
    }

    @ParameterizedTest
    @ValueSource(strings = { "http//test", "//test/", "org.test.com" })
    void givenMalformedUrl_createdRetrofitInstance_throws(String malformedUrl) {
        assertThrows(IllegalArgumentException.class, () -> new RetrofitServiceFactory(malformedUrl));
    }

    @Test
    void givenAccuweather_createResponse_createsValidRequest() {
        RetrofitServiceFactory factory = new RetrofitServiceFactory("http://some.url.com/");

        AccuweatherRetrofitService service = factory.createService(AccuweatherRetrofitService.class);
        assertInstanceOf(AccuweatherRetrofitService.class, service);

        String apiQuery = "api";
        String qQuery = "q";
        String localeQuery = "locale";
        Request request = service.getLocations(apiQuery, qQuery, localeQuery).request();

        assertEquals("GET", request.method());
        assertEquals(apiQuery, request.url().queryParameterValue(0));
        assertEquals(qQuery, request.url().queryParameterValue(1));
        assertEquals(localeQuery, request.url().queryParameterValue(2));
    }
}