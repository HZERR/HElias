package ru.hzerr.controller.popup;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import ru.hzerr.util.Fx;

import java.net.URL;
import java.util.ResourceBundle;

public class WarningController implements Showable {

    @FXML private URL location;
    @FXML private ResourceBundle resources;
    @FXML private AnchorPane root;
    @FXML private Label title;
    @FXML private JFXButton ok;
    @FXML private Text message;

    private final StringProperty titleProperty = new SimpleStringProperty();
    private final StringProperty messageProperty = new SimpleStringProperty();

    private final JFXPopup popup = new JFXPopup();

    public void initialize() {
        title.setText(titleProperty.getValue());
        message.setText(messageProperty.getValue());
        popup.setAutoHide(false);
        popup.setPopupContent(root);
//        ok.setOnAction(this::onAccept);
    }

    @FXML
    void onAccept(ActionEvent event) {
        popup.hide();
    }

    public void setMessage(String message) { this.messageProperty.setValue(message); }
    public void setTitle(String title) { this.titleProperty.setValue(title); }

    @Override
    public void show() {
        popup.show(Fx.getScene().getRoot(), JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, 175, 125);
    }
}