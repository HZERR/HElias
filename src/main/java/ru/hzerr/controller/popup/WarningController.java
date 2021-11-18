package ru.hzerr.controller.popup;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXTextArea;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class WarningController implements Showable {

    @FXML private URL location;
    @FXML private ResourceBundle resources;
    @FXML private AnchorPane root;
    @FXML private Label title;
    @FXML private JFXButton ok;
    @FXML private JFXTextArea message;

    private final ObjectProperty<Node> rootProperty = new SimpleObjectProperty<>();
    private final StringProperty titleProperty = new SimpleStringProperty();
    private final StringProperty messageProperty = new SimpleStringProperty();

    private final JFXPopup popup = new JFXPopup();

    public void initialize() {
        title.setText(titleProperty.getValue());
        message.setText(messageProperty.getValue());
        ok.setOnAction(event -> popup.hide());
        popup.setAutoHide(false);
        popup.setPopupContent(root);
    }

    public void setMessage(String message) { this.messageProperty.setValue(message); }
    public void setTitle(String title) { this.titleProperty.setValue(title); }
    public void setRoot(Node root) { this.rootProperty.setValue(root); }

    @Override
    public void show() {
        popup.show(rootProperty.getValue(), JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, 175, 125);
    }
}