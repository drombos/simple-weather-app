package org.example;

import org.example.handler.*;
import org.example.ui.UI;

import java.util.HashMap;
import java.util.Map;

public class App {
    private static final App INSTANCE = new App();
    private static boolean initialized = false;
    private UI ui;
    private Map<Class<? extends AbstractCommandHandler<?>>, AbstractCommandHandler<?>> handlers;

    private App() { }

    public static App getInstance() {
        return INSTANCE;
    }


    static void runWith(
            UI uiSolution,
            AddLocationHandler addLocationService,
            DisplayLocationsHandler displayLocationsService,
            DownloadForecastsHandler downloadForecastsService,
            EndProgramHandler endProgramService
    ) {
        App app = getInstance();
        app.init(
                uiSolution,
                addLocationService,
                displayLocationsService,
                downloadForecastsService,
                endProgramService
        );

        app.ui.startMainMenu();
    }

    private void init(
            UI uiSolution,
            AddLocationHandler addLocationHandler,
            DisplayLocationsHandler displayLocationsHandler,
            DownloadForecastsHandler downloadForecastsHandler,
            EndProgramHandler endProgramHandler
    ) {
        App app = getInstance();
        app.ui = uiSolution;

        handlers = new HashMap<>();
        handlers.put(AddLocationHandler.class, addLocationHandler);
        handlers.put(DisplayLocationsHandler.class, displayLocationsHandler);
        handlers.put(DownloadForecastsHandler.class, downloadForecastsHandler);
        handlers.put(EndProgramHandler.class, endProgramHandler);

        initialized = true;
    }

    public boolean invalidCommand() {
        ui.invalidCommand();
        return true;
    }

    public <T extends AbstractCommandHandler<?>> boolean performAction(Class<T> handlerClass) {
        if (!initialized) {
            throw new IllegalStateException("Appka nie jest prawidłowo zainicjalizowana.");
        }

        AbstractCommandHandler<?> handler = handlers.get(handlerClass);
        if (handler == null) {
            return false;
        }
        return handler.perform();
    }

    public <T extends AbstractCommandHandler<?>> boolean performAction(Class<T> handlerClass,
                                                                       Map<String, Object> contextVariables) {
        if (!initialized) {
            throw new IllegalStateException("Appka nie jest prawidłowo zainicjalizowana.");
        }

        AbstractCommandHandler<?> handler = handlers.get(handlerClass);
        if (handler == null) {
            return false;
        }

        if (contextVariables == null) {
            return handler.perform();
        }
        return handler.perform(contextVariables);
    }

    public UI getUI() {
        return ui;
    }
}
