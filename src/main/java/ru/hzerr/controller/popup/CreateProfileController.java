package ru.hzerr.controller.popup;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import ru.hzerr.HElias;
import ru.hzerr.config.profile.Profile;
import ru.hzerr.config.profile.Structure;
import ru.hzerr.config.profile.settings.Settings;
import ru.hzerr.exception.ErrorSupport;
import ru.hzerr.exception.ProfileCreationException;
import ru.hzerr.file.BaseDirectory;
import ru.hzerr.file.BaseFile;
import ru.hzerr.file.HFile;
import ru.hzerr.modification.state.strategy.ProjectType;
import ru.hzerr.util.Fx;
import ru.hzerr.util.SystemInfo;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class CreateProfileController implements Showable {

    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private AnchorPane root;
    @FXML private JFXTextField pathToProjectJarFileField;
    @FXML private JFXTextField profileNameField;
    @FXML private ChoiceBox<ProjectType> projectTypes;
    @FXML private JFXButton cancel;
    @FXML private JFXButton addProfile;
    @FXML private JFXButton explorer;
    @FXML private Label description;

    private final JFXPopup popup = new JFXPopup();
    private final ObjectProperty<Consumer<Profile>> onFinishedProperty = new SimpleObjectProperty<>();
    private static final FileChooser JAR_CHOOSER = new FileChooser();
    private static final String JAR_EXTENSION = ".jar";

    @FXML
    void initialize() {
        projectTypes.getItems().addAll(ProjectType.MYTHICAL_WORLD, ProjectType.MC_SKILL);
        pathToProjectJarFileField.setFocusColor(Color.rgb(64, 89, 169));
        pathToProjectJarFileField.textProperty().addListener(((observableValue, o, n) -> {
            if (n.endsWith(JAR_EXTENSION)) {
                pathToProjectJarFileField.setFocusColor(Color.rgb(64, 89, 169));
            } else pathToProjectJarFileField.setFocusColor(Color.RED);
        }));
        profileNameField.setFocusColor(Color.rgb(64, 89, 169));
        profileNameField.textProperty().addListener(((observableValue, o, n) -> {
            if (HElias.getProperties().checkExistsProfileWithName(n)) {
                pathToProjectJarFileField.setFocusColor(Color.RED);
            } else pathToProjectJarFileField.setFocusColor(Color.rgb(64, 89, 169));
        }));
        initJarChooser();
        addProfile.setOnAction(event -> {
            if (isValid()) {
                // create profile
                BaseFile projectJar = new HFile(pathToProjectJarFileField.getText());
                try {
                    BaseDirectory newRoot = HElias.getProperties().getProjectsDir().getSubDirectory(profileNameField.getText());
                    if (newRoot.exists()) {
                        ErrorSupport.showErrorPopup(new IllegalStateException("The folder structure for project " + profileNameField.getText() + " already exists!"));
                    }
                    newRoot.create();
                    Structure structure = Structure.createStructure(newRoot, projectJar);
                    Settings settings = Settings.makeCopy(projectTypes.getValue());
                    settings.getBackground().init(structure);
                    Profile profile = new Profile(profileNameField.getText(), projectTypes.getSelectionModel().getSelectedItem(), structure, settings);
                    onFinishedProperty.get().accept(profile);
                } catch (IOException io) {
                    ErrorSupport.showErrorPopup(new ProfileCreationException(io));
                }
                // end create profile
                KeyValue value = new KeyValue(popup.opacityProperty(), 0);
                KeyFrame frame = new KeyFrame(Duration.seconds(0.5), value);
                Timeline timeline = new Timeline(frame);
                timeline.setOnFinished(e -> popup.hide());
                timeline.play();
                return;
            }
            pathToProjectJarFileField.setFocusColor(Color.RED);
            description.setOpacity(1);
            KeyValue value = new KeyValue(description.opacityProperty(), 0);
            KeyFrame frame = new KeyFrame(Duration.seconds(2), value);
            new Timeline(frame).play();
        });
        explorer.setOnAction(event -> {
            popup.hide();
            File selectedFile = JAR_CHOOSER.showOpenDialog(Fx.getScene().getWindow());
            show();
            if (selectedFile != null) {
                pathToProjectJarFileField.setText(selectedFile.getAbsolutePath());
            }
        });
        cancel.setOnAction(event -> popup.hide());
        description.setOpacity(0);
        popup.setPopupContent(root);
        popup.setAutoHide(false);
    }

    public ObjectProperty<Consumer<Profile>> onFinishedProperty() { return onFinishedProperty; }
    public void setOnFinished(Consumer<Profile> onFinished) { this.onFinishedProperty.setValue(onFinished); }

    @Override
    public void show() {
        int initOffsetX = SystemInfo.isWindows() ? 175 : 75;
        int initOffsetY = SystemInfo.isWindows() ? 125 : 25;
        popup.show(Fx.getScene().getRoot(), JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, initOffsetX, initOffsetY);
    }

    private void initJarChooser() {
        JAR_CHOOSER.setTitle(resources.getString("file.chooser.title.path.to.project"));
        JAR_CHOOSER.getExtensionFilters().add(new FileChooser.ExtensionFilter("Project Jar", "*.jar"));
    }

    private boolean isValid() {
        String path = pathToProjectJarFileField.getText();
        if (path.isEmpty() || new HFile(path).notExists()) {
            description.setText(resources.getString("popup.create.profile.path.entered.incorrectly.description"));
            return false;
        }
        String name = profileNameField.getText();
        if (name.isEmpty() || HElias.getProperties().checkExistsProfileWithName(name)) {
            description.setText(resources.getString("popup.create.profile.name.such.description"));
            return false;
        }
        if (projectTypes.getSelectionModel().isEmpty()) {
            description.setText(resources.getString("popup.create.profile.project.type.not.selected.description"));
            return false;
        }
        return true;
    }
}
