package ru.hzerr.loaders.theme;

import javafx.scene.Scene;

public class Purple extends Theme {

    public Purple() { super(); }

    public void addStylesheet(Entity entity) { super.entities.add(entity); }

    @Override
    public void applyTheme(Scene sceneToBeApplied) { super.applyTheme(sceneToBeApplied); }
}
