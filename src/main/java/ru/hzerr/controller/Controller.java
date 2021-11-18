package ru.hzerr.controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import ru.hzerr.HElias;
import ru.hzerr.loaders.FXMLLoader;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller {

    @FXML private URL location;
    @FXML private ResourceBundle resources;
    @FXML private TabPane mainTab;

    public void initialize() throws IOException {
        final Parent PATCHER_PARENT = FXMLLoader.getParent("patcher", FXMLLoader.FXMLType.TAB, HElias.getProperties().getLanguage());
        final Parent PROJECTS_PARENT = FXMLLoader.getParent("projects", FXMLLoader.FXMLType.TAB, HElias.getProperties().getLanguage());
        final Parent MOD_WIZARD_PARENT = FXMLLoader.getParent("modwizard", FXMLLoader.FXMLType.TAB, HElias.getProperties().getLanguage());
        final Parent PROFILES_PARENT = FXMLLoader.getParent("profile", FXMLLoader.FXMLType.TAB, HElias.getProperties().getLanguage());
        final Parent SETTINGS_PARENT = FXMLLoader.getParent("settings", FXMLLoader.FXMLType.TAB, HElias.getProperties().getLanguage());
        final Tab PATCHER_TAB = new Tab(resources.getString("patcher.tab.name"), PATCHER_PARENT);
        final Tab PROJECTS_TAB = new Tab(resources.getString("projects.tab.name"), PROJECTS_PARENT);
        final Tab MOD_WIZARD_TAB = new Tab(resources.getString("modwizard.tab.name"), MOD_WIZARD_PARENT);
        final Tab PROFILES_TAB = new Tab(resources.getString("profiles.tab.name"), PROFILES_PARENT);
        final Tab SETTINGS_TAB = new Tab(resources.getString("settings.tab.name"), SETTINGS_PARENT);
        PATCHER_TAB.setId("patcher");
        PROJECTS_TAB.setId("projects");
        MOD_WIZARD_TAB.setId("modWizard");
        PROFILES_TAB.setId("profiles");
        SETTINGS_TAB.setId("settings");
        mainTab.getTabs().add(PATCHER_TAB);
        mainTab.getTabs().add(PROJECTS_TAB);
        mainTab.getTabs().add(MOD_WIZARD_TAB);
        mainTab.getTabs().add(PROFILES_TAB);
        mainTab.getTabs().add(SETTINGS_TAB);
    }
}
