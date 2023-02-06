package org.example.ui.thymeleaf;

import org.example.http.dto.LocationDto;
import org.example.http.query.ApiLocationQuery;
import org.example.persistence.model.DbLocation;
import org.example.ui.submenu.AddLocationUI;
import org.thymeleaf.context.WebContext;

import java.util.Collection;
import java.util.Map;

public class AddLocationRoute implements AddLocationUI, ContextParametersSource {
    @Override
    public ApiLocationQuery buildLocationQueryFromInput() {
        return null;
    }

    @Override
    public LocationDto specifyLocationFromMultiple(Collection<? extends LocationDto> locations) {
        return null;
    }

    @Override
    public void noLocationsFound(ApiLocationQuery query) {

    }

    @Override
    public void locationAlreadyAdded(DbLocation location) {

    }

    @Override
    public Map<String, Object> getContextVariables(WebContext context) {
        return null;
    }
}
