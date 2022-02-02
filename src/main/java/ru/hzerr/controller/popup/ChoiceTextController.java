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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import ru.hzerr.util.Fx;
import ru.hzerr.util.SystemInfo;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class ChoiceTextController implements Showable {

    @FXML private URL location;
    @FXML private ResourceBundle resources;
    @FXML private AnchorPane root;
    @FXML private Label title;
    @FXML private Label variableLabel;
    @FXML private JFXTextField variableTextField;
    @FXML private JFXButton ok;
    @FXML private JFXButton cancel;
    @FXML private Label description;

    private final JFXPopup popup = new JFXPopup();
    private final ObjectProperty<Pattern> patternProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<Consumer<String>> onFinishedProperty = new SimpleObjectProperty<>();
    private final StringProperty titleProperty = new SimpleStringProperty();
    private final StringProperty oldValueProperty = new SimpleStringProperty();
    private final StringProperty errorProperty = new SimpleStringProperty();

    public void initialize() {
        variableTextField.setFocusColor(Color.rgb(64, 89, 169));
        variableTextField.setText(oldValueProperty.getValue());
        if (patternProperty.getValue() != null) {
            variableTextField.textProperty().addListener(((observableValue, o, n) -> {
                if (patternProperty.getValue().matcher(n).matches()) {
                    variableTextField.setFocusColor(Color.rgb(64, 89, 169));
                } else variableTextField.setFocusColor(Color.RED);
            }));
        }
        ok.setOnAction(event -> {
            if (patternProperty.isNull().get() ||
                    (patternProperty.isNotNull().get() &&
                            patternProperty.getValue().matcher(variableTextField.getText()).matches())) {
                onFinishedProperty.getValue().accept(variableTextField.getText());
                KeyValue value = new KeyValue(popup.opacityProperty(), 0);
                KeyFrame frame = new KeyFrame(Duration.seconds(0.5), value);
                Timeline timeline = new Timeline(frame);
                timeline.setOnFinished(e -> popup.hide());
                timeline.play();
            } else {
                variableTextField.setFocusColor(Color.RED);
                description.setText(errorProperty.getValue());
                description.setOpacity(1);
                KeyValue value = new KeyValue(description.opacityProperty(), 0);
                KeyFrame frame = new KeyFrame(Duration.seconds(2), value);
                new Timeline(frame).play();
            }
        });
        title.setText(titleProperty.getValue());
        cancel.setOnAction(event -> popup.hide());
        description.setOpacity(0);
        popup.setPopupContent(root);
        popup.setAutoHide(false);
    }

    public void setTitle(String title) { this.titleProperty.setValue(title); }
    public void setPattern(Pattern validator) { this.patternProperty.setValue(validator); }
    public void setOldValue(String oldValue) { this.oldValueProperty.setValue(oldValue); }
    public void setError(String errorMessage) { this.errorProperty.setValue(errorMessage); }
    public void setOnFinished(Consumer<String> onFinished) { this.onFinishedProperty.setValue(onFinished); }

    @Override
    public void show() {
        int initOffsetX = SystemInfo.isWindows() ? 175 : 75;
        int initOffsetY = SystemInfo.isWindows() ? 125 : 25;
        popup.show(Fx.getScene().getRoot(), JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, initOffsetX, initOffsetY);
    }
}
