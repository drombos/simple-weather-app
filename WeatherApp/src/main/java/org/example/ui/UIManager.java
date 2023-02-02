package org.example.ui;

import org.example.AppCallback;

public interface UIManager extends AppCallback {
    void startMainMenu();

    void invalidCommand();

    UIAddLocation getAddLocationHandler();
}
