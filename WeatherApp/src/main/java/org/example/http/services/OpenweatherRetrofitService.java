package org.example.http.services;

import org.example.http.dtos.OpenweatherForecastsDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

non-sealed public interface OpenweatherRetrofitService extends Service {
    @GET("/data/3.0/onecall")
    Call<OpenweatherForecastsDto> getForecasts(
            @Query("lat") Double latitude,
            @Query("lon") Double longitude,
            @Query("appid") String apiKey,
            @Query("units") String units,
            @Query("lang") String lang,
            @Query("exclude") String exclude
    );
}

//https://api.openweathermap.org/data/3.0/onecall?lat=33.44&lon=-94.04&appid={APIKEY}&units=metric&lang=pl&exclude=current,minutely,hourly,alerts