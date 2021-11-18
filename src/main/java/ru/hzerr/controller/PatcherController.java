package ru.hzerr.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import ru.hzerr.HElias;
import ru.hzerr.config.listener.content.Content;
import ru.hzerr.controller.actions.PatchStartEventHandler;
import ru.hzerr.controller.converter.ProjectBoxConverter;
import ru.hzerr.log.SessionLogManager;
import ru.hzerr.modification.state.strategy.ProjectType;
import ru.hzerr.util.Fx;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PatcherController {

    @FXML private AnchorPane root;
    @FXML private Label title;
    @FXML private Label state;
    @FXML private JFXButton startButton;
    @FXML private ChoiceBox<JFXButton> projectsBox;
    @FXML private JFXButton mcSkill;
    @FXML private JFXButton mythicalWorld;
    @FXML private JFXButton borealis;
    @FXML private URL location;
    @FXML private ResourceBundle resources;

    private final Content userData = new Content();

    public void initialize() throws IOException, FontFormatException {
//        title.setFont(FontLoader.load(Fonts.ZEN_TOKYO_ZOO, 40D));
        Fx.DropShadowBackgroundEffect.create(title).run();
        // TODO: 03.08.2021 be sure to install borealis class names
        borealis.setOnAction(e -> userData.put("selectedType", ProjectType.BOREALIS));
        mcSkill.setOnAction(event -> {
            userData.put("selectedType", ProjectType.MC_SKILL);
            HElias.getProperties().installMcSkillClassNames();
        });
        mythicalWorld.setOnAction(event -> {
            userData.put("selectedType", ProjectType.MYTHICAL_WORLD);
            HElias.getProperties().installMythicalWorldClassNames();
        });
        projectsBox.getSelectionModel().selectedItemProperty().addListener((observable, o, n) -> n.fire());
        ProjectBoxConverter converter = ProjectBoxConverter.newConverter();
        converter.installContent(Content.single("projects", projectsBox));
        projectsBox.setConverter(converter);
        PatchStartEventHandler onStartEventHandler = new PatchStartEventHandler();
        userData.put("state", state);
        userData.put("startButton", startButton);
        // lazy handle
        startButton.setOnAction(event -> onStartEventHandler.installContent(userData).handle(event));
        SessionLogManager.getManager().getLogger().info("Patcher tab was initialized");
    }
}
