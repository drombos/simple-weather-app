package org.example.handler;

import org.example.persistence.Dao;
import org.example.persistence.model.DbLocation;
import org.example.ui.ErrorUI;
import org.example.ui.submenu.DisplayLocationsUI;

import java.util.List;

public class DisplayLocationsHandler extends AbstractCommandHandler<DisplayLocationsUI> {
    public DisplayLocationsHandler(DisplayLocationsUI ui, ErrorUI errorUI, Dao dao) {
        super(ui, errorUI, dao);
    }

    @Override
    public boolean perform() {
        List<DbLocation> locations = dao.readAll();
        ui.displayAll(locations);

        return true;
    }
}
