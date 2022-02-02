package ru.hzerr.controller.project;

import com.jfoenix.controls.JFXToggleButton;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import ru.hzerr.log.LogManager;

import java.net.URL;
import java.util.ResourceBundle;

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
//        debugEnabledMythicalWorld.selectedProperty().addListener((observable, o, n) -> StateManager.getInstance().changeState(MythicalWorldState.class, state -> state.setDebugMode(n)));
//        changeBackgroundMythicalWorld.selectedProperty().addListener((observable, o, n) -> StateManager.getInstance().changeState(MythicalWorldState.class, state -> state.setChangeBackground(n)));
//        deleteSecurityMythicalWorld.selectedProperty().addListener((observable, o, n) -> StateManager.getInstance().changeState(MythicalWorldState.class, state -> state.setDeleteSecurity(n)));
//        addJFoenixMythicalWorld.selectedProperty().addListener((observable, o, n) -> StateManager.getInstance().changeState(MythicalWorldState.class, state -> state.setPrependJFoenix(n)));
//        deleteJFoenixMythicalWorld.selectedProperty().addListener((observable, o, n) -> StateManager.getInstance().changeState(MythicalWorldState.class, state -> state.setRemoveJFoenix(n)));
//        rebuildMythicalWorld.selectedProperty().addListener((observable, o, n) -> StateManager.getInstance().changeState(MythicalWorldState.class, state -> state.setRebuild(n)));
//        unpackMythicalWorld.selectedProperty().addListener((observable, o, n) -> StateManager.getInstance().changeState(MythicalWorldState.class, state -> state.setDecompress(n)));
//        deleteBuildFileMythicalWorld.selectedProperty().addListener((observable, o, n) -> StateManager.getInstance().changeState(MythicalWorldState.class, state -> state.setDeleteBuildFile(n)));
//        cleanProjectFolderMythicalWorld.selectedProperty().addListener((observable, o, n) -> StateManager.getInstance().changeState(MythicalWorldState.class, state -> state.setDeleteProjectFolder(n)));
//        buildMythicalWorld.selectedProperty().addListener((observable, o, n) -> StateManager.getInstance().changeState(MythicalWorldState.class, state -> state.setConstruct(n)));
//        updateRuntimeFolderMythicalWorld.selectedProperty().addListener((observable, o, n) -> StateManager.getInstance().changeState(MythicalWorldState.class, state -> state.setReplaceRuntimeFolder(n)));
//        changeBackgroundMythicalWorld.setOnAction(event -> {
//            if (changeBackgroundMythicalWorld.isSelected()) {
//                changeBackgroundMythicalWorld.setSelected(false);
//                try {
//                    FileChooser projectChooser = new FileChooser();
//                    projectChooser.setTitle(resources.getString("file.chooser.title.background"));
//                    ChoiceFileController choiceFileController = new ChoiceFileController();
//                    choiceFileController.setExplorer(projectChooser);
//                    if (HElias.getProperties().getMythicalWorldBackground().exists()) {
//                        choiceFileController.setValue(HElias.getProperties().getMythicalWorldBackground().getLocation());
//                    } else choiceFileController.setValue("");
//                    choiceFileController.setOnFinished(selectedValue -> {
//                        try {
//                            if (!selectedValue.isEmpty()) {
//                                HElias.getProperties().setMythicalWorldBackground(new HFile(selectedValue));
//                                StateManager.getInstance().changeState(MythicalWorldState.class, state -> state.setChangeBackground(true));
//                                changeBackgroundMythicalWorld.setSelected(true);
//                            }
//                        } catch (IOException io) { ErrorSupport.showErrorPopup(io); }
//                    });
//                    choiceFileController.setRoot(root);
//                    FXMLLoader.showPopup("choice-file", resources, choiceFileController);
//                } catch (IOException io) { ErrorSupport.showErrorPopup(io); }
//            } else
//                StateManager.getInstance().changeState(MythicalWorldState.class, state -> state.setChangeBackground(false));
//        });
//
//        HElias.getProperties().addListener(new BooleanEventListener(PropertyNames.EXPERT_MODE) {
//
//            @Override
//            public void onRun(Boolean newValue) {
//                revalidateButtons(newValue,
//                        addJFoenixMythicalWorld,
//                        deleteJFoenixMythicalWorld,
//                        unpackMythicalWorld,
//                        deleteBuildFileMythicalWorld,
//                        cleanProjectFolderMythicalWorld,
//                        buildMythicalWorld,
//                        updateRuntimeFolderMythicalWorld,
//                        rebuildMythicalWorld);
//                LogManager.getLogger().debug("Buttons have been successfully revalidated");
//            }
//        });
        LogManager.getLogger().info("MythicalWorld tab was initialized");
    }

    private void revalidateButtons(boolean newValue, JFXToggleButton... buttons) {
        for (JFXToggleButton button: buttons) {
            button.setDisable(!newValue);
            button.setVisible(newValue);
        }
    }
}
