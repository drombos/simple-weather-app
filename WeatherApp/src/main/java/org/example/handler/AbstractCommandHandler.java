package org.example.handler;

import org.example.persistence.Dao;
import org.example.ui.ErrorUI;

import java.util.Map;

public abstract class AbstractCommandHandler<T> {
    protected final T ui;
    protected final ErrorUI errorUI;
    protected final Dao dao;

    protected AbstractCommandHandler(T ui, ErrorUI errorUI, Dao dao) {
        if (ui == null || errorUI == null || dao == null) {
            throw new IllegalArgumentException("Nieprawidłowa inicjalizacja serwisu: podano null-owy komponent.");
        }

        this.ui = ui;
        this.errorUI = errorUI;
        this.dao = dao;
    }

    public abstract boolean perform();

    public boolean perform(Map<String, Object> contextVariables) {
        return perform();
    }
}
