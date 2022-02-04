package ru.hzerr.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.commons.lang3.StringUtils;
import ru.hzerr.HElias;
import ru.hzerr.config.profile.Profile;
import ru.hzerr.controller.popup.CreateProfileController;
import ru.hzerr.exception.ErrorSupport;
import ru.hzerr.exception.modification.ImageNotFoundException;
import ru.hzerr.loaders.FXMLLoader;
import ru.hzerr.loaders.ImageLoader;
import ru.hzerr.loaders.theme.ThemeLoader;
import ru.hzerr.modification.state.strategy.ProjectType;
import ru.hzerr.util.Fx;

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
    @FXML private JFXTreeTableView<Profile> profiles;
    private static final TreeItem<Profile> rootItem = new TreeItem<>();

    public void initialize() throws IOException, ClassNotFoundException {
        byDefaultColumn.prefWidthProperty().bind(profiles.widthProperty().multiply(0.3));
        nameColumn.prefWidthProperty().bind(profiles.widthProperty().multiply(0.4));
        projectTypeColumn.prefWidthProperty().bind(profiles.widthProperty().multiply(0.3));
        profiles.setColumnResizePolicy(param -> true);
        nameColumn.setCellValueFactory(param -> param.getValue().getValue().getProfileNameProperty());
        projectTypeColumn.setCellValueFactory(param -> param.getValue().getValue().getProjectTypeProperty());
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
                if (HElias.getProperties().hasDefaultProfile(param.getValue().getValue())) {
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
    }

    @FXML
    void onAddProfile(ActionEvent event) {
        CreateProfileController profileController = new CreateProfileController();
        profileController.setOnFinished(newProfile -> {
            TreeItem<Profile> newTreeItem = new TreeItem<>(newProfile);
            rootItem.getChildren().add(newTreeItem);
            if (HElias.getProperties().isEmptyProfiles()) {
                HElias.getProperties().setDefaultProfile(newTreeItem.getValue());
                deleteProjectTab();
                try {
                    createTabByProfile(newTreeItem.getValue());
                    ThemeLoader.reApply(HElias.getProperties().getTheme());
                } catch (IOException io) { ErrorSupport.showErrorPopup(io); }
                // refresh items
                this.refresh(byDefaultColumn);
            }
            HElias.getProperties().addProfile(newProfile);
        });
        FXMLLoader.showSafePopup("create-profile", resources, profileController);
    }

    @FXML
    void onDeleteProfile(ActionEvent event) {
        if (profiles.getSelectionModel().getSelectedItem() != null) {
            // delete profile
            if (HElias.getProperties().hasDefaultProfile(profiles.getSelectionModel().getSelectedItem().getValue())) {
                HElias.getProperties().clearDefaultProfile();
                deleteProjectTab();
            }
            HElias.getProperties().removeProfile(profiles.getSelectionModel().getSelectedItem().getValue());
            rootItem.getChildren().remove(profiles.getSelectionModel().getSelectedItem());
            // reselect new default profile
            if (!rootItem.getChildren().isEmpty()) {
                int newSelectedIndex = Math.max(profiles.getSelectionModel().getSelectedIndex() - 1, 0);
                Profile newDefaultProfile = rootItem.getChildren().get(newSelectedIndex).getValue();
                HElias.getProperties().setDefaultProfile(newDefaultProfile);
                try {
                    createTabByProfile(newDefaultProfile);
                    ThemeLoader.reApply(HElias.getProperties().getTheme());
                } catch (IOException io) { ErrorSupport.showErrorPopup(io); }
                refresh(byDefaultColumn);
            }
        }
    }

    @FXML
    void onInstallByDefault(ActionEvent event) {
        if (profiles.getSelectionModel().getSelectedItem() != null) {
            HElias.getProperties().setDefaultProfile(profiles.getSelectionModel().getSelectedItem().getValue());
            try {
                deleteProjectTab();
                createTabByProfile(profiles.getSelectionModel().getSelectedItem().getValue());
                ThemeLoader.reApply(HElias.getProperties().getTheme());
            } catch (IOException io) { ErrorSupport.showErrorPopup(io); }
            // refresh items
            this.refresh(byDefaultColumn);
        }
    }

    private void createTabByProfile(Profile defaultProfile) throws IOException {
        JFXTabPane mainTab = ((JFXTabPane) Fx.getScene().getRoot());
        switch (defaultProfile.getProjectTypeProperty().getValue()) {
            case MYTHICAL_WORLD:
                final Parent mWorldParent = FXMLLoader.getParent("mythical-world", FXMLLoader.FXMLType.PROJECTS, HElias.getProperties().getLanguage());
                final Tab mWorld = new Tab("MythicalWorld", mWorldParent);
                mWorld.setId("mWorldProject");
                mainTab.getTabs().add(1, mWorld);
                break;
            case MC_SKILL:
                final Parent mcSkillParent = FXMLLoader.getParent("mcSkill", FXMLLoader.FXMLType.PROJECTS, HElias.getProperties().getLanguage());
                final Tab mcSkill = new Tab("McSkill", mcSkillParent);
                mcSkill.setId("mcSkillProject");
                mainTab.getTabs().add(1, mcSkill);
                break;
        }
    }

    private void deleteProjectTab() {
        JFXTabPane mainTab = ((JFXTabPane) Fx.getScene().getRoot());
        if (!mainTab.getTabs().filtered(tab -> tab.getId().contains("Project")).isEmpty()) {
            mainTab.getTabs().remove(1);
        }
    }

    private void refresh(TreeTableColumn<?, ?> column) {
        column.setVisible(false);
        column.setVisible(true);
    }
}
