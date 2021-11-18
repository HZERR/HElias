package ru.hzerr.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import org.apache.commons.lang3.StringUtils;
import ru.hzerr.HElias;
import ru.hzerr.config.profile.Profile;
import ru.hzerr.controller.popup.CreateProfileController;
import ru.hzerr.exception.ErrorSupport;
import ru.hzerr.exception.modification.ImageNotFoundException;
import ru.hzerr.file.exception.file.HFileReadException;
import ru.hzerr.loaders.FXMLLoader;
import ru.hzerr.loaders.ImageLoader;
import ru.hzerr.modification.state.strategy.ProjectType;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController {

    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private JFXButton setByDefault;
    @FXML private JFXButton deleteProfile;
    @FXML private JFXButton addProfile;
    @FXML private TreeTableColumn<Profile, Image> byDefaultColumn;
    @FXML private TreeTableColumn<Profile, String> nameColumn;
    @FXML private TreeTableColumn<Profile, ProjectType> projectTypeColumn;
    @FXML private TreeTableColumn<Profile, Long> checksumColumn;
    @FXML private TreeTableColumn<Profile, String> locationColumn;
    @FXML private JFXTreeTableView<Profile> profiles;
    private static final TreeItem<Profile> rootItem = new TreeItem<>();

    public void initialize() throws IOException, ClassNotFoundException {
        profiles.setColumnResizePolicy(param -> true);
        nameColumn.setCellValueFactory(param -> param.getValue().getValue().getProfileNameProperty());
        projectTypeColumn.setCellValueFactory(param -> param.getValue().getValue().getProjectTypeProperty());
        checksumColumn.setCellValueFactory(param -> {
            try {
                return new ReadOnlyObjectWrapper<>(param.getValue().getValue().getStructureProperty().getValue().getCommercialProjectJarFile().checksum());
            } catch (IOException io) {
                ErrorSupport.showErrorPopup(io.initCause(new HFileReadException("Checksum calculation error for checksumColumn")));
            }
            return null;
        });
        locationColumn.setCellValueFactory(param -> {
            String loc = param.getValue().getValue().getStructureProperty().getValue().getCommercialProjectJarFile().getLocation();
            int index = loc.lastIndexOf("projects");
            return new ReadOnlyObjectWrapper<>(loc.substring(index));
        });
        byDefaultColumn.setCellFactory(param -> {
            final ImageView view = new ImageView();
            view.setFitHeight(25D);
            view.setFitWidth(25D);
            return new TreeTableCell<Profile, Image>() {
                public void updateItem(Image item, boolean empty) {
                    if (item != null) {
                        view.setImage(item);
                        setGraphic(view);
                    } else setGraphic(null);
                }
            };
        });
        byDefaultColumn.setCellValueFactory(param -> {
            if (StringUtils.isNotEmpty(HElias.getProperties().getDefaultProfileName())) {
                if (HElias.getProperties().isDefaultProfile(param.getValue().getValue())) {
                    try {
                        return new ReadOnlyObjectWrapper<>(ImageLoader.loadImage("tick-by-hirschwolf-white.png", 50));
                    } catch (ImageNotFoundException infe) { ErrorSupport.showErrorPopup(infe); }
                }
            }

            return new ReadOnlyObjectWrapper<>();
        });
        profiles.setRoot(rootItem);
        rootItem.getChildren().clear();
        for (Profile profile: HElias.getProperties().getProfiles()) {
            rootItem.getChildren().add(new TreeItem<>(profile));
        }
        setByDefault.setOnAction(event -> {
            if (profiles.getSelectionModel().getSelectedItem() != null) {
                HElias.getProperties().setDefaultProfile(profiles.getSelectionModel().getSelectedItem().getValue());
                // refresh items
                this.refresh(byDefaultColumn);
            }
        });
        addProfile.setOnAction(event -> {
            CreateProfileController profileController = new CreateProfileController();
            profileController.setOnFinished(newProfile -> {
                TreeItem<Profile> newTreeItem = new TreeItem<>(newProfile);
                rootItem.getChildren().add(newTreeItem);
                if (HElias.getProperties().isEmptyProfiles()) {
                    HElias.getProperties().setDefaultProfile(newTreeItem.getValue());
                    // refresh items
                    this.refresh(byDefaultColumn);
                }
                HElias.getProperties().addProfile(newProfile);
            });
            FXMLLoader.showSafePopup("create-profile", resources, profileController);
        });
        deleteProfile.setOnAction(event -> {
            if (profiles.getSelectionModel().getSelectedItem() != null) {
                // delete profile
                if (HElias.getProperties().isDefaultProfile(profiles.getSelectionModel().getSelectedItem().getValue())) {
                    HElias.getProperties().clearDefaultProfile();
                }
                HElias.getProperties().removeProfile(profiles.getSelectionModel().getSelectedItem().getValue());
                rootItem.getChildren().remove(profiles.getSelectionModel().getSelectedItem());
                // reselect new default profile
                if (!rootItem.getChildren().isEmpty()) {
                    int newSelectedIndex = Math.max(profiles.getSelectionModel().getSelectedIndex() - 1, 0);
                    Profile newDefaultProfile = rootItem.getChildren().get(newSelectedIndex).getValue();
                    HElias.getProperties().setDefaultProfile(newDefaultProfile);
                    refresh(byDefaultColumn);
                }
            }
        });
    }

    private void refresh(TreeTableColumn<?, ?> column) {
        column.setVisible(false);
        column.setVisible(true);
    }
}
