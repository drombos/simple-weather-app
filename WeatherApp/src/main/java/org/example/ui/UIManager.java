package org.example.ui;

public class UIManager {
    private UI ui;
    private UIManager() {}
    private static class Holder {
        private static final UIManager INSTANCE = new UIManager();
    }
    public static UIManager getInstance() {
        return Holder.INSTANCE;
    }

    public static void init(UI uiSolution) {
        getInstance().ui = uiSolution;
    }
    public void startMainMenu() {
        ui.startMainMenu();
    }

    public void invalidCommand() {
        ui.invalidCommand();
    }

    public UIAddLocation getAddLocationHandler() {
        return ui.getAddLocationHandler();
    }
}
