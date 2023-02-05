package org.example.handler;

import org.example.persistence.Dao;
import org.example.ui.ErrorUI;
import org.example.ui.submenu.EndProgramUI;

public class EndProgramHandler extends AbstractCommandHandler<EndProgramUI> {

    public EndProgramHandler(EndProgramUI ui, ErrorUI errorUI, Dao dao) {
        super(ui, errorUI, dao);
    }

    @Override
    public boolean perform() {
        ui.displayEnd();
        return true;
    }
}
