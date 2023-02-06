package org.example.ui.thymeleaf;

import org.example.http.dto.LocationDto;
import org.example.http.query.ApiLocationQuery;
import org.example.http.query.CityQuery;
import org.example.persistence.model.DbLocation;
import org.example.ui.submenu.AddLocationUI;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebRequest;

import java.util.*;

public class AddLocationRoute implements AddLocationUI, ContextParametersSource, GETParametersProcessor {
    private String city;
    private String area;
    private String country;
    private boolean noLocations;
    private String emptyResultQuery;
    private boolean alreadyAdded;
    private String alreadyAddedLocation;
    private boolean hasMultipleResults;
    private List<String> matchDisplayList;
    @Override
    public ApiLocationQuery buildLocationQueryFromInput() {
        if (city == null) {
            city = "";
        }
        if (area == null) {
            area = "";
        }
        if (country == null) {
            country = "";
        }
        return new CityQuery(city, area, country);
    }

    @Override
    public LocationDto specifyLocationFromMultiple(Collection<? extends LocationDto> locations) {
        this.noLocations = false;
        this.alreadyAdded = false;

        this.hasMultipleResults = true;
        this.matchDisplayList = new ArrayList<>();
        locations.forEach(l -> matchDisplayList.add(l.toString()));

        return null;
    }

    @Override
    public void noLocationsFound(ApiLocationQuery query) {
        this.noLocations = true;
        this.emptyResultQuery = query.toString();

        this.alreadyAdded = false;
        this.hasMultipleResults = false;
    }

    @Override
    public void locationAlreadyAdded(DbLocation location) {
        this.noLocations = false;

        this.alreadyAdded = true;
        this.alreadyAddedLocation = location.toString();

        this.hasMultipleResults = false;
    }

    @Override
    public Map<String, Object> getContextVariables(WebContext context) {
        Map<String, Object> contextVariables = new HashMap<>();

        if (noLocations) {
            contextVariables.put("noLocationsDisplay", "block");
            contextVariables.put("alreadyAddedDisplay", "none");
            contextVariables.put("multipleResultsDisplay", "none");
            contextVariables.put("doneDisplay", "none");
        } else if (alreadyAdded) {
            contextVariables.put("noLocationsDisplay", "none");
            contextVariables.put("alreadyAddedDisplay", "block");
            contextVariables.put("multipleResultsDisplay", "none");
            contextVariables.put("doneDisplay", "none");
        } else if (hasMultipleResults) {
            contextVariables.put("noLocationsDisplay", "none");
            contextVariables.put("alreadyAddedDisplay", "none");
            contextVariables.put("multipleResultsDisplay", "block");
            contextVariables.put("doneDisplay", "none");
        } else {
            contextVariables.put("noLocationsDisplay", "none");
            contextVariables.put("alreadyAddedDisplay", "none");
            contextVariables.put("multipleResultsDisplay", "none");
            contextVariables.put("doneDisplay", "block");
        }

        if (emptyResultQuery == null) {
            emptyResultQuery = "";
        }
        contextVariables.put("emptyResultQuery", emptyResultQuery);

        if (alreadyAddedLocation == null) {
            alreadyAddedLocation = "";
        }
        contextVariables.put("alreadyAddedLocation", alreadyAddedLocation);

        contextVariables.put(
                "matchDisplayList",
                Objects.requireNonNullElseGet(matchDisplayList, HashMap::new)
        );

        //reset flags
        noLocations = false;
        alreadyAdded = false;
        hasMultipleResults = false;

        return contextVariables;
    }

    @Override
    public void takeGETParameters(IWebRequest request) {
        this.city = request.getParameterValue("city");
        this.area = request.getParameterValue("area");
        this.country = request.getParameterValue("country");
    }
}
