package org.example.handler;

import org.example.http.ApiClientPool;
import org.example.http.dto.LocationDto;
import org.example.http.query.ApiLocationQuery;
import org.example.persistence.Dao;
import org.example.util.FormatMapper;
import org.example.persistence.model.DbLocation;
import org.example.ui.ErrorUI;
import org.example.ui.submenu.AddLocationUI;

import java.util.Set;

public class AddLocationHandler extends AbstractCommandHandler<AddLocationUI> {
    public AddLocationHandler(AddLocationUI ui, ErrorUI errorUI, Dao dao) {
        super(ui, errorUI, dao);
    }

    @Override
    public boolean perform() {
        ApiLocationQuery query = ui.buildLocationQueryFromInput();

        LocationDto locationFromApi = getLocationFromApi(query);

        if (locationFromApi == null) {
            errorUI.printError("Operacja anulowana.");
            return false;
        }

        DbLocation locationToAdd;
        try {
            locationToAdd = mapLocationToDbEntity(locationFromApi);
        } catch (FormatMapper.ParsingException e) {
            errorUI.printError("Błędnie sformatowana / wybrakowana lokalizacja zwrócona z API, może padło?");
            return false;
        }

        return persistIfNotDuplicate(locationToAdd);
    }

    private LocationDto getLocationFromApi(ApiLocationQuery query) {
        Set<? extends LocationDto> matchingLocations = ApiClientPool.queryLocations(query);

        if (matchingLocations.isEmpty()) {
            ui.noLocationsFound(query);
            return null;
        }

        LocationDto location;
        if (matchingLocations.size() > 1) {
            location = ui.specifyLocationFromMultiple(matchingLocations);
        } else {
            location = matchingLocations.stream().toList().get(0);
        }

        return location;
    }

    private DbLocation mapLocationToDbEntity(LocationDto locationFromApi) throws FormatMapper.ParsingException {
        FormatMapper mapper = new FormatMapper();
        return mapper.apiToDb(locationFromApi);
    }

    private boolean persistIfNotDuplicate(DbLocation locationToAdd) {
        if (dao.doesAlreadyContain(locationToAdd)) {
            ui.locationAlreadyAdded(locationToAdd);
            return false;
        }

        return dao.create(locationToAdd);
    }
}
