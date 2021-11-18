package ru.hzerr.loaders.theme;

import javafx.scene.Scene;

public class Dark extends Theme {

    public Dark() { super(); }

    public void addStylesheet(Entity entity) { super.entities.add(entity); }

    @Override
    public void applyTheme(Scene sceneToBeApplied) { super.applyTheme(sceneToBeApplied); }
}
