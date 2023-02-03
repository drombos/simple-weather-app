package org.example.persistence;

import org.example.http.dto.AccuweatherLocationDto;
import org.example.http.dto.LocationDto;
import org.example.http.query.ApiForecastQuery;
import org.example.persistence.model.DbLocation;


public class ObjectMapper {
    public DbLocation apiToDb(LocationDto apiObj) throws ParsingException {
        if (AccuweatherLocationDto.class != apiObj.getClass()) {
            throw new ParsingException("Na razie tylko AccuweatherLocationDto jest obsługiwane przez Mapper");
        }

        AccuweatherLocationDto source = (AccuweatherLocationDto) apiObj;
        return new DbLocation(
                source.getCityName(),
                source.getArea(),
                source.getCountry(),
                source.getLongitude(),
                source.getLatitude(),
                source.getLocationKey()
        );
    }

    public ApiForecastQuery dbToApi(DbLocation dbLocation) {
        return new ApiForecastQuery() {
            @Override
            public String accuweatherLocationKey() {
                return dbLocation.getAccuweatherKey();
            }

            @Override
            public Double openweatherLatitude() {
                return dbLocation.getLatitude();
            }

            @Override
            public Double openweatherLongitude() {
                return dbLocation.getLongitude();
            }
        };
    }

    public static class ParsingException extends Exception {
        public ParsingException(String message) {
            super(message);
        }
    }
}
