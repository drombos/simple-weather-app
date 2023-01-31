package org.example.http.services;

import org.example.http.dtos.AccuweatherLocationDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.Set;

non-sealed public interface AccuweatherRetrofitService extends Service {
    @GET("/locations/v1/cities/search")
    Call<Set<AccuweatherLocationDto>> getLocations(
            @Query("apikey") String apiKey,
            @Query("q") String query,
            @Query("language") String locale
    );
}