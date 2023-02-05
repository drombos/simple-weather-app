package org.example.ui.console;

import org.example.ui.ErrorUI;

public class ConsoleErrorUI implements ErrorUI {
    @Override
    public void printError(String msg) {
        System.err.println(msg);
    }
}
