package org.example;

import org.example.handler.AddLocationHandler;
import org.example.handler.DisplayLocationsHandler;
import org.example.handler.DownloadForecastsHandler;
import org.example.handler.EndProgramHandler;
import org.example.persistence.Dao;
import org.example.persistence.HibernateRepository;
import org.example.persistence.model.DbLocation;
import org.example.ui.UI;
import org.example.ui.console.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class AppTest {
    private App app = App.getInstance();
    @BeforeAll
    void init() {
        UI ui = new MockUI();

        Dao dao = new MockDao();

        App.runWith(
                ui,
                new AddLocationHandler(
                        ui.getAddLocationMenu(),
                        ui.getErrorUI(),
                        dao
                ),
                new DisplayLocationsHandler(
                        ui.getDisplayLocationsMenu(),
                        ui.getErrorUI(),
                        dao
                ),
                new DownloadForecastsHandler(
                        ui.getDownloadForecastsMenu(),
                        ui.getErrorUI(),
                        dao
                ),
                new EndProgramHandler(
                        ui.getEndProgramMenu(),
                        ui.getErrorUI(),
                        dao)
        );
    }

    @Test
    void isSingleton() {
        App instance1 = App.getInstance();
        App instance2 = App.getInstance();
        assertSame(instance1, instance2);
    }

    @Test
    void whenInvoked_invalidCommand_returnsTrue() {
        boolean actual = app.invalidCommand();
        assertTrue(actual);
    }
}
