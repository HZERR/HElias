package ru.hzerr.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import ru.hzerr.controller.actions.PatchStartEventHandler;
import ru.hzerr.log.LogManager;

import java.net.URL;
import java.util.ResourceBundle;

public class PatcherController {

    @FXML private AnchorPane root;
    @FXML private JFXButton startButton;
    @FXML private VBox vBoxInfo;
    @FXML private URL location;
    @FXML private ResourceBundle resources;

    public void initialize() {
        LogManager.getLogger().info("Patcher tab was initialized");
    }

    @FXML
    void onStart(ActionEvent event) {
        new PatchStartEventHandler(vBoxInfo).handle(event);
    }
}
