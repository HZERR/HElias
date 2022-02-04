package ru.hzerr.controller.popup;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import ru.hzerr.stream.HStream;
import ru.hzerr.util.Fx;
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
    private final BooleanProperty disallowIdentityProperty = new SimpleBooleanProperty(false);
    private final StringProperty oldValueProperty = new SimpleStringProperty();
    private final ObjectProperty<FileChooser> explorerProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<Consumer<String>> onFinishedProperty = new SimpleObjectProperty<>();

    public void initialize() {
        description.setOpacity(0);
        if (disallowIdentityProperty.get()) {
            pathTextField.setFocusColor(Color.RED);
        } else
            pathTextField.setFocusColor(Color.rgb(64, 89, 169));
        pathTextField.setText(oldValueProperty.getValue());
        pathTextField.textProperty().addListener(((observableValue, o, n) -> {
            File file = new File(n);
            boolean matches = extensions.count() == 0 || extensions.anyMatch((ext) -> file.getAbsolutePath().endsWith(ext));
            // lol. maybe rewrite???
            if (file.exists() && matches) {
                if (disallowIdentityProperty.get()) {
                    if (!o.equals(n)) pathTextField.setFocusColor(Color.rgb(64, 89, 169));
                    else pathTextField.setFocusColor(Color.RED);
                } else pathTextField.setFocusColor(Color.rgb(64, 89, 169));
            } else
                pathTextField.setFocusColor(Color.RED);
        }));
        popup.setAutoFix(true);
        popup.setOpacity(1);
        popup.setPopupContent(root);
    }

    public void disallowIdentity() {
        disallowIdentityProperty.setValue(true);
    }

    @FXML
    public void onChange(ActionEvent event) {
        if (pathTextField.getFocusColor().equals(Color.rgb(64, 89, 169))) {
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
    }

    @FXML
    public void onCancel(ActionEvent event) {
        popup.hide();
    }

    @FXML
    public void onOpenFolder(ActionEvent event) {
        popup.hide();
        File selectedFile = explorerProperty.get().showOpenDialog(description.getScene().getWindow());
        show();
        if (selectedFile != null) {
            pathTextField.setText(selectedFile.getAbsolutePath());
        }
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

    @Override
    public void show() {
        int initOffsetX = SystemInfo.isWindows() ? 175 : 75;
        int initOffsetY = SystemInfo.isWindows() ? 125 : 25;
        popup.show(Fx.getScene().getRoot(), JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, initOffsetX, initOffsetY);
    }
}
