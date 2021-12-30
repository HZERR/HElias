package ru.hzerr.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import ru.hzerr.config.listener.content.Content;
import ru.hzerr.config.listener.content.ContentInstaller;
import ru.hzerr.controller.actions.PatchStartEventHandler;
import ru.hzerr.log.LogManager;
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
    @FXML private URL location;
    @FXML private ResourceBundle resources;

    private final Content userData = new Content();

    public void initialize() throws IOException, FontFormatException {
        Fx.DropShadowBackgroundEffect.create(title).run();
        // TODO: 03.08.2021 be sure to install borealis class names
        ContentInstaller<PatchStartEventHandler> onStartEventHandler = new PatchStartEventHandler();
        userData.put("state", state);
        userData.put("startButton", startButton);
        // lazy handle
        startButton.setOnAction(event -> onStartEventHandler.installContent(userData).handle(event));
        LogManager.getLogger().info("Patcher tab was initialized");
    }
}
