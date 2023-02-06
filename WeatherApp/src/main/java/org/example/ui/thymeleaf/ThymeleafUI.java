package org.example.ui.thymeleaf;

import org.example.ui.ErrorUI;
import org.example.ui.UI;
import org.example.ui.submenu.AddLocationUI;
import org.example.ui.submenu.DisplayLocationsUI;
import org.example.ui.submenu.DownloadForecastsUI;
import org.example.ui.submenu.EndProgramUI;

public class ThymeleafUI implements UI {
    private final ErrorRoute errorUI;
    private final AddLocationRoute addLocationRoute;
    private final DisplayLocationsRoute displayLocationsRoute;
    private final DownloadForecastsRoute downloadForecastsRoute;
    private final EndProgramRoute endProgramRoute;

    public ThymeleafUI(ErrorRoute errorUI, AddLocationRoute addLocationRoute,
                       DisplayLocationsRoute displayLocationsRoute, DownloadForecastsRoute downloadForecastsRoute,
                       EndProgramRoute endProgramRoute) {
        this.errorUI = errorUI;
        this.addLocationRoute = addLocationRoute;
        this.displayLocationsRoute = displayLocationsRoute;
        this.downloadForecastsRoute = downloadForecastsRoute;
        this.endProgramRoute = endProgramRoute;
    }

    @Override
    public void startMainMenu() {
//        Context context = new Context();
//        context.setVariable("name", "TEST NAME");
//        context.setVariable("date", "TEST DATE");
//
//        String index = ThymeleafEngine.getInstance().getContextVariables("index", context);
//        System.out.println(index);
    }

    @Override
    public void invalidCommand() {

    }

    @Override
    public AddLocationUI getAddLocationMenu() {
        return addLocationRoute;
    }

    @Override
    public DisplayLocationsUI getDisplayLocationsMenu() {
        return displayLocationsRoute;
    }

    @Override
    public DownloadForecastsUI getDownloadForecastsMenu() {
        return downloadForecastsRoute;
    }

    @Override
    public EndProgramUI getEndProgramMenu() {
        return endProgramRoute;
    }

    @Override
    public ErrorUI getErrorUI() {
        return errorUI;
    }
}
