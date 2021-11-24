package ru.hzerr.controller.popup;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXTextArea;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import ru.hzerr.log.LogManager;
import ru.hzerr.util.Fx;
import ru.hzerr.util.SystemInfo;

import java.net.URL;
import java.util.ResourceBundle;

public class ErrorController implements Showable {

    @FXML private URL location;
    @FXML private ResourceBundle resources;
    @FXML private AnchorPane root;
    @FXML private Label title;
    @FXML private JFXButton ok;
    @FXML private JFXButton saveToLogFile;
    @FXML private JFXTextArea message;

    private final ObjectProperty<Throwable> exceptionProperty = new SimpleObjectProperty<>();

    private final JFXPopup popup = new JFXPopup();

    public void initialize() {
        title.setText(exceptionProperty.getValue().getClass().getSimpleName());
        message.setText(exceptionProperty.getValue().getMessage());
        popup.setAutoHide(false);
        popup.setPopupContent(root);
    }

    @FXML
    void onAccept(ActionEvent event) {
        popup.hide();
    }

    @FXML
    void onLog(ActionEvent event) {
        LogManager.getLogger().error("ErrorPopupHandler", exceptionProperty.getValue());
        ok.fire();
    }

    public void setException(Throwable throwable) { this.exceptionProperty.setValue(throwable); }

    @Override
    public void show() {
        int initOffsetX = SystemInfo.isWindows() ? 150 : 50;
        int initOffsetY = SystemInfo.isWindows() ? 100 : 25;
        popup.show(Fx.getScene().getRoot(), JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, initOffsetX, initOffsetY);
    }
}
