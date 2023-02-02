package org.example.service;

import org.example.App;
import org.example.http.ApiClientPool;
import org.example.http.dto.LocationDto;
import org.example.http.query.ApiLocationQuery;
import org.example.persistence.Dao;
import org.example.persistence.ObjectMapper;
import org.example.persistence.model.DbLocation;
import org.example.ui.ErrorUI;
import org.example.ui.submenu.AddLocationUI;

import java.util.Set;

public class AddLocationService {
    public boolean perform() {
        AddLocationUI ui = App.getUI().getAddLocationMenu();
        ErrorUI errorUI = App.getUI().getErrorUI();

        ApiLocationQuery query = ui.buildLocationQueryFromInput();
        Set<? extends LocationDto> matchingLocations = ApiClientPool.queryLocations(query);

        if (matchingLocations.isEmpty()) {
            ui.noLocationsFound(query);
            return false;
        }

        LocationDto location;
        if (matchingLocations.size() > 1) {
            location = ui.specifyLocationFromMultiple(matchingLocations);
        } else {
            location = matchingLocations.stream().toList().get(0);
        }

        if (location == null) {
            errorUI.print("Pusta lokalizacja zwrócona z API, może padło?");
            return false;
        }

        ObjectMapper mapper = new ObjectMapper();
        DbLocation locationToAdd;
        try {
            locationToAdd = mapper.apiToDb(location);
        } catch (ObjectMapper.ParsingException e) {
            errorUI.print("Błędnie sformatowana / wybrakowana lokalizacja zwrócona z API, może padło?");
            return false;
        }

        Dao dao = App.getDao();
        if (dao.doesAlreadyContain(locationToAdd)) {
            ui.locationAlreadyAdded(locationToAdd);
            return false;
        }

        return dao.create(locationToAdd);
    }
}
