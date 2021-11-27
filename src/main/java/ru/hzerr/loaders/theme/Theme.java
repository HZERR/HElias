package ru.hzerr.loaders.theme;

import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import ru.hzerr.collections.list.ArrayHList;
import ru.hzerr.collections.list.HList;

public abstract class Theme {

    protected HList<Stylesheet> stylesheets;

    public Theme() { this.stylesheets = new ArrayHList<>(); }
    public Theme(HList<Stylesheet> stylesheets) { this.stylesheets = stylesheets; }

    public void addStylesheet(Stylesheet entity) { stylesheets.add(entity); }

    public void applyTheme(Scene sceneToBeApplied) {
        TabPane mainTab = (TabPane) sceneToBeApplied.getRoot();
        for (Tab tab: mainTab.getTabs()) {
            if (tab.getId() != null) {
                stylesheets.find(stylesheet -> stylesheet.getTargetNodeId().equals(tab.getId()))
                        .ifPresent(stylesheet -> {
                            AnchorPane root = ((AnchorPane) tab.getContent().lookup(".content"));
                            root.getStylesheets().clear();
                            root.getStylesheets().add(stylesheet.getStylesheet());
                        });
            }
        }
        stylesheets.find(stylesheet -> stylesheet.getTargetNodeId().equals("root")).ifPresent(stylesheet -> {
            mainTab.getStylesheets().clear();
            mainTab.getStylesheets().add(stylesheet.getStylesheet());
        });
    }
}
