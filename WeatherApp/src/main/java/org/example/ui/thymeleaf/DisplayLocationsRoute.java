package org.example.ui.thymeleaf;

import org.example.persistence.model.DbLocation;
import org.example.ui.submenu.DisplayLocationsUI;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisplayLocationsRoute implements ContextParametersSource, DisplayLocationsUI {
    private List<DbLocation> locations = null;
    @Override
    public Map<String, Object> getContextVariables(WebContext context) {
        Map<String, Object> contextVariables = new HashMap<>();
        if (locations == null) {
            contextVariables.put("locations", new ArrayList<DbLocation>());
        } else {
            contextVariables.put("locations", locations);
        }

        return contextVariables;
    }

    @Override
    public void displayAll(List<DbLocation> locations) {
        this.locations = locations;
    }
}
