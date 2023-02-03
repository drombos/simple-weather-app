package org.example.handler;

import org.example.persistence.Dao;
import org.example.ui.ErrorUI;
import org.example.ui.submenu.DownloadForecastsUI;

public class DownloadForecastsHandler extends AbstractCommandHandler<DownloadForecastsUI> {

    public DownloadForecastsHandler(DownloadForecastsUI ui, ErrorUI errorUI, Dao dao) {
        super(ui, errorUI, dao);
    }

    @Override
    public boolean perform() {
        return true;
    }
}
