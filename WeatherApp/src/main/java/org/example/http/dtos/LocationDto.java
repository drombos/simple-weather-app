package org.example.http.dtos;

public interface LocationDto extends Dto {
    String accuweatherLocationKey();

    Double openweatherLatitude();

    Double openweatherLongitude();
}
