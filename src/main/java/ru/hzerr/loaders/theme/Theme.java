package ru.hzerr.loaders.theme;

import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import ru.hzerr.collections.list.ArrayHList;
import ru.hzerr.collections.list.HList;

public abstract class Theme {

    protected HList<Entity> entities;

    public Theme() { this.entities = new ArrayHList<>(); }
    public Theme(HList<Entity> entities) { this.entities = entities; }

    public void applyTheme(Scene sceneToBeApplied) {
        TabPane mainTab = (TabPane) sceneToBeApplied.getRoot();
        for (Tab tab: mainTab.getTabs()) {
            if (tab.getId() != null) {
                this.entities.find(entity -> entity.getId().equals(tab.getId()))
                        .ifPresent(entity -> {
                            AnchorPane root = ((AnchorPane) tab.getContent().lookup(".content"));
                            root.getStylesheets().clear();
                            root.getStylesheets().add(entity.getStylesheet());
                        });
            }
        }
        entities.find(entity -> entity.getId().equals("root")).ifPresent(entity -> {
            mainTab.getStylesheets().clear();
            mainTab.getStylesheets().add(entity.getStylesheet());
        });
    }
}
