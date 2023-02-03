package org.example.handler;

import org.example.persistence.Dao;
import org.example.ui.ErrorUI;
import org.example.ui.submenu.DisplayLocationsUI;

public class DisplayLocationsHandler extends AbstractCommandHandler<DisplayLocationsUI> {
    public DisplayLocationsHandler(DisplayLocationsUI ui, ErrorUI errorUI, Dao dao) {
        super(ui, errorUI, dao);
    }

    @Override
    public boolean perform() {
        return true;
    }
}
