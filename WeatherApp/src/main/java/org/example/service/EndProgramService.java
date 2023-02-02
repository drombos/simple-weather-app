package org.example.service;

import org.example.App;
import org.example.ui.submenu.EndProgramUI;

public class EndProgramService {
    public boolean perform() {
        EndProgramUI ui = App.getUI().getEndProgramMenu();
        ui.displayEnd();
        return true;
    }
}
