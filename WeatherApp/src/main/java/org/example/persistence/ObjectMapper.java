package org.example.persistence;

import org.example.http.dto.AccuweatherLocationDto;
import org.example.http.dto.LocationDto;
import org.example.persistence.model.DbLocation;

public class ObjectMapper {
    public DbLocation apiToDb(LocationDto apiObj) throws ParsingException {
        if (AccuweatherLocationDto.class != apiObj.getClass()) {
            throw new IllegalArgumentException("Na razie tylko AccuweatherLocationDto jest obsługiwane przez Mapper");
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

    public static class ParsingException extends Exception {
        public ParsingException(String message) {
            super(message);
        }
    }
}
