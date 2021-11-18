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
import javafx.stage.FileChooser;
import javafx.util.Duration;
import ru.hzerr.stream.HStream;
import ru.hzerr.util.SystemInfo;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class ChoiceFileController implements Showable {

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
    private final HStream<String> extensions = HStream.empty();
    private final StringProperty oldValueProperty = new SimpleStringProperty();
    private final ObjectProperty<FileChooser> explorerProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<Consumer<String>> onFinishedProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<Node> rootProperty = new SimpleObjectProperty<>();

    public void initialize() {
        description.setOpacity(0);
        pathTextField.setFocusColor(Color.rgb(64, 89, 169));
        pathTextField.setText(oldValueProperty.getValue());
        pathTextField.textProperty().addListener(((observableValue, o, n) -> {
            File file = new File(n);
            boolean matches = extensions.count() == 0 || extensions.anyMatch((ext) -> file.getAbsolutePath().endsWith(ext));
            if (file.exists() && matches) {
                pathTextField.setFocusColor(Color.rgb(64, 89, 169));
            } else
                pathTextField.setFocusColor(Color.RED);
        }));
        ok.setOnAction(event -> {
            File file = new File(pathTextField.getText());
            boolean matches = extensions.count() == 0 || extensions.anyMatch((ext) -> file.getAbsolutePath().endsWith(ext));
            if (file.exists() && matches) {
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
            File selectedFile = explorerProperty.get().showOpenDialog(description.getScene().getWindow());
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
    public void setExplorer(FileChooser explorer) {
        this.explorerProperty.setValue(explorer);
        for (FileChooser.ExtensionFilter filter : explorer.getExtensionFilters()) {
            for (String extension : filter.getExtensions()) {
                extensions.put(extension.substring(1));
            }
        }
    }
    public void setOnFinished(Consumer<String> onFinished) { this.onFinishedProperty.setValue(onFinished); }
    public void setRoot(Node root) { this.rootProperty.setValue(root); }

    @Override
    public void show() {
        int initOffsetX = SystemInfo.isWindows() ? 175 : 75;
        int initOffsetY = SystemInfo.isWindows() ? 125 : 25;
        popup.show(rootProperty.getValue(), JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, initOffsetX, initOffsetY);
    }
}
