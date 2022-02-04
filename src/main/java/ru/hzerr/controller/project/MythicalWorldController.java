package ru.hzerr.controller.project;

import com.jfoenix.controls.JFXToggleButton;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import ru.hzerr.HElias;
import ru.hzerr.config.PropertyNames;
import ru.hzerr.config.listener.BooleanEventListener;
import ru.hzerr.config.profile.Profile;
import ru.hzerr.config.profile.settings.Background;
import ru.hzerr.controller.popup.ChoiceFileController;
import ru.hzerr.exception.ErrorSupport;
import ru.hzerr.file.HFile;
import ru.hzerr.loaders.FXMLLoader;
import ru.hzerr.log.LogManager;
import ru.hzerr.modification.state.impl.MythicalWorldState;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class MythicalWorldController {

    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private AnchorPane root;
    @FXML private JFXToggleButton debugEnabledMythicalWorld;
    @FXML private JFXToggleButton changeBackgroundMythicalWorld;
    @FXML private JFXToggleButton deleteSecurityMythicalWorld;
    @FXML private JFXToggleButton addJFoenixMythicalWorld;
    @FXML private JFXToggleButton deleteJFoenixMythicalWorld;
    @FXML private JFXToggleButton rebuildMythicalWorld;
    @FXML private JFXToggleButton unpackMythicalWorld;
    @FXML private JFXToggleButton deleteBuildFileMythicalWorld;
    @FXML private JFXToggleButton cleanProjectFolderMythicalWorld;
    @FXML private JFXToggleButton buildMythicalWorld;
    @FXML private JFXToggleButton updateRuntimeFolderMythicalWorld;

    @FXML
    void initialize() {
        debugEnabledMythicalWorld.selectedProperty().addListener((observable, o, n) -> onChangeState(state -> state.setDebugMode(n)));
        deleteSecurityMythicalWorld.selectedProperty().addListener((observable, o, n) -> onChangeState(state -> state.setDeleteSecurity(n)));
        addJFoenixMythicalWorld.selectedProperty().addListener((observable, o, n) -> onChangeState(state -> state.setPrependJFoenix(n)));
        deleteJFoenixMythicalWorld.selectedProperty().addListener((observable, o, n) -> onChangeState(state -> state.setRemoveJFoenix(n)));
        rebuildMythicalWorld.selectedProperty().addListener((observable, o, n) -> onChangeState(state -> state.setRebuild(n)));
        unpackMythicalWorld.selectedProperty().addListener((observable, o, n) -> onChangeState(state -> state.setDecompress(n)));
        deleteBuildFileMythicalWorld.selectedProperty().addListener((observable, o, n) -> onChangeState(state -> state.setDeleteBuildFile(n)));
        cleanProjectFolderMythicalWorld.selectedProperty().addListener((observable, o, n) -> onChangeState(state -> state.setCleanupProjectFolder(n)));
        buildMythicalWorld.selectedProperty().addListener((observable, o, n) -> onChangeState(state -> state.setConstruct(n)));
        updateRuntimeFolderMythicalWorld.selectedProperty().addListener((observable, o, n) -> onChangeState(state -> state.setReplaceRuntimeFolder(n)));
        changeBackgroundMythicalWorld.setOnAction(event -> {
            if (changeBackgroundMythicalWorld.isSelected()) {
                changeBackgroundMythicalWorld.setSelected(false);
                FileChooser projectChooser = new FileChooser();
                projectChooser.setTitle(resources.getString("file.chooser.title.background"));
                projectChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));
                ChoiceFileController choiceFileController = new ChoiceFileController();
                choiceFileController.setExplorer(projectChooser);
                choiceFileController.disallowIdentity();
                HElias.getProperties().getDefaultProfile().ifPresent(profile -> {
                    if (profile.getSettingsProperty().getValue().getBackground().exists()) {
                        choiceFileController.setValue(profile.getSettingsProperty().getValue().getBackground().getBackgroundFile().getLocation());
                    } else choiceFileController.setValue("");
                });
                choiceFileController.setOnFinished(selectedValue -> {
                    if (!selectedValue.isEmpty()) {
                        HElias.getProperties().getDefaultProfile().ifPresent(profile -> {
                            Background background = new Background.MythicalWorldBackground();
                            background.init(profile.getStructureProperty().getValue());
                            try {
                                background.setBackgroundFile(new HFile(selectedValue));
                            } catch (IOException io) { ErrorSupport.showErrorPopup(io); }
                            profile.getSettingsProperty().getValue().setBackground(background);
                            onChangeState(state -> state.setChangeBackground(true));
                            changeBackgroundMythicalWorld.setSelected(true);
                        });
                    }
                });
                FXMLLoader.showSafePopup("choice-file", resources, choiceFileController);
            } else onChangeState(state -> state.setChangeBackground(false));
        });

        HElias.getProperties().addListener(new BooleanEventListener(PropertyNames.EXPERT_MODE) {

            @Override
            public void onRun(Boolean newValue) {
                revalidateButtons(newValue,
                        addJFoenixMythicalWorld,
                        deleteJFoenixMythicalWorld,
                        unpackMythicalWorld,
                        deleteBuildFileMythicalWorld,
                        cleanProjectFolderMythicalWorld,
                        buildMythicalWorld,
                        updateRuntimeFolderMythicalWorld,
                        rebuildMythicalWorld);
                LogManager.getLogger().debug("Buttons have been successfully revalidated");
            }
        });
        LogManager.getLogger().info("MythicalWorld tab was initialized");
    }

    private void onChangeState(Consumer<MythicalWorldState> onChangeAction) {
        Optional<Profile> defaultProfile = HElias.getProperties().getDefaultProfile();
        // А нужно ли проверять?
        defaultProfile.ifPresent(profile -> profile.getSettingsProperty().getValue().changeState(MythicalWorldState.class, onChangeAction));
    }

    private void revalidateButtons(boolean newValue, JFXToggleButton... buttons) {
        for (JFXToggleButton button: buttons) {
            button.setDisable(!newValue);
            button.setVisible(newValue);
        }
    }
}
