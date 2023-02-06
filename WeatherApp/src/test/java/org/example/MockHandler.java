package org.example;

import org.example.handler.AbstractCommandHandler;
import org.example.persistence.Dao;
import org.example.ui.ErrorUI;

public class MockHandler extends AbstractCommandHandler<MockActionUI> {
    protected MockHandler(MockActionUI ui, ErrorUI errorUI, Dao dao) {
        super(ui, errorUI, dao);
    }

    @Override
    public boolean perform() {
        return false;
    }
}
