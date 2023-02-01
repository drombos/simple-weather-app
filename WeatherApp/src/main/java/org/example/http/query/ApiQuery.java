package org.example.http.query;

public sealed interface ApiQuery permits CityQuery, GeoQuery {
     String toQuery();

     static ApiQuery from(String input) throws IllegalArgumentException {
          String[] splitInput = input.trim().split(" *[,;/] *", 3);

          if (splitInput.length == 1) {
               return new CityQuery(splitInput[0], "", "");
          }

          //try to parse it as a GeoQuery
          if (splitInput.length == 2) {
               try {
                    double[] longitudeLatitude = new double[2];

                    for (int i = 0; i < 2; i++) {
                         longitudeLatitude[i] = Double.parseDouble(splitInput[i]);
                    }

                    return new GeoQuery(longitudeLatitude[0], longitudeLatitude[1]);
               } catch (NumberFormatException e) {
                    //no parseable longitude / latitude
                    return new CityQuery(splitInput[0], splitInput[1], "");
               }
          }

          if (splitInput.length == 3) {
               return new CityQuery(splitInput[0], splitInput[1], splitInput[2]);
          }

          //could not parse
          throw new IllegalArgumentException("Nie udało się przeparsować wprowadzonego tekstu:" +
                  " nieprawidłowy format.");
     }
}