package org.example.http.dto;

public interface LocationDto extends Dto {
    String accuweatherLocationKey();

    Double openweatherLatitude();

    Double openweatherLongitude();
}
