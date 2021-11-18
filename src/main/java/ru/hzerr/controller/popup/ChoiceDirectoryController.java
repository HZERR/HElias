package ru.hzerr.controller.popup;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import ru.hzerr.stream.HStream;
import ru.hzerr.util.Fx;
import ru.hzerr.util.SystemInfo;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class ChoiceDirectoryController implements Showable {

    @FXML private URL location;
    @FXML private ResourceBundle resources;
    @FXML private Label title;
    @FXML private AnchorPane root;
    @FXML private JFXTextField pathTextField;
    @FXML private JFXButton openFileChooser;
    @FXML private JFXButton cancel;
    @FXML private JFXButton ok;
    @FXML private Label description;

    private final JFXPopup popup = new JFXPopup();
    private final StringProperty oldValueProperty = new SimpleStringProperty();
    private final ObjectProperty<DirectoryChooser> explorerProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<Consumer<String>> onFinishedProperty = new SimpleObjectProperty<>();

    public void initialize() {
        description.setOpacity(0);
        pathTextField.setFocusColor(Color.rgb(64, 89, 169));
        pathTextField.setText(oldValueProperty.getValue());
        pathTextField.textProperty().addListener(((observableValue, o, n) -> {
            if (new File(n).exists()) {
                pathTextField.setFocusColor(Color.rgb(64, 89, 169));
            } else
                pathTextField.setFocusColor(Color.RED);
        }));
        ok.setOnAction(event -> {
            File file = new File(pathTextField.getText());
            if (file.exists()) {
                onFinishedProperty.get().accept(pathTextField.getText());
                KeyValue value = new KeyValue(popup.opacityProperty(), 0);
                KeyFrame frame = new KeyFrame(Duration.seconds(0.5), value);
                Timeline timeline = new Timeline(frame);
                timeline.setOnFinished(e -> popup.hide());
                timeline.play();
            } else {
                description.setOpacity(1);
                KeyValue value = new KeyValue(description.opacityProperty(), 0);
                KeyFrame frame = new KeyFrame(Duration.seconds(2), value);
                new Timeline(frame).play();
            }
        });
        openFileChooser.setOnAction(event -> {
            popup.hide();
            File selectedFile = explorerProperty.get().showDialog(description.getScene().getWindow());
            show();
            if (selectedFile != null) {
                pathTextField.setText(selectedFile.getAbsolutePath());
            }
        });
        cancel.setOnAction(event -> popup.hide());
        popup.setAutoFix(true);
        popup.setOpacity(1);
        popup.setPopupContent(root);
    }

    public void setValue(String oldValue) { this.oldValueProperty.setValue(oldValue); }
    public void setExplorer(DirectoryChooser explorer) { this.explorerProperty.setValue(explorer); }
    public void setOnFinished(Consumer<String> onFinished) { this.onFinishedProperty.setValue(onFinished); }

    @Override
    public void show() {
        int initOffsetX = SystemInfo.isWindows() ? 175 : 75;
        int initOffsetY = SystemInfo.isWindows() ? 125 : 25;
        popup.show(Fx.getScene().getRoot(), JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, initOffsetX, initOffsetY);
    }
}
