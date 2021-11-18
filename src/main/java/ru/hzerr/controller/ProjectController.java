package ru.hzerr.controller;

import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import ru.hzerr.HElias;
import ru.hzerr.modification.state.impl.BorealisStateBuilder;
import ru.hzerr.modification.state.impl.McSkillStateBuilder;
import ru.hzerr.modification.state.impl.MythicalWorldStateBuilder;
import ru.hzerr.modification.state.strategy.StateManager;
import ru.hzerr.loaders.FXMLLoader;
import ru.hzerr.log.SessionLogManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProjectController {

    @FXML private URL location;
    @FXML private ResourceBundle resources;
    @FXML private AnchorPane root;
    @FXML private JFXTabPane projectsPane;

    public void initialize() throws IOException {
        StateManager.getInstance().addIfAbsent(McSkillStateBuilder.defaultBuilder());
        StateManager.getInstance().addIfAbsent(MythicalWorldStateBuilder.defaultBuilder());
        StateManager.getInstance().addIfAbsent(BorealisStateBuilder.defaultBuilder());
        final Parent mwParent = FXMLLoader.getParent("mythical-world", FXMLLoader.FXMLType.PROJECTS, HElias.getProperties().getLanguage());
        projectsPane.getTabs().add(new Tab("MythicalWorld", mwParent));
        final Parent mcSkillParent = FXMLLoader.getParent("mcskill", FXMLLoader.FXMLType.PROJECTS, HElias.getProperties().getLanguage());
        projectsPane.getTabs().add(new Tab("McSkill", mcSkillParent));
        SessionLogManager.getManager().getLogger().info("Project tab was initialized");
    }
}
