package ru.hzerr.controller.project;

import com.jfoenix.controls.JFXToggleButton;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import ru.hzerr.HElias;
import ru.hzerr.config.PropertyNames;
import ru.hzerr.config.listener.BooleanEventListener;
import ru.hzerr.controller.actions.projects.mc.skill.McSkillDebugEnabledChangeListener;
import ru.hzerr.controller.popup.ChoiceFileController;
import ru.hzerr.exception.ErrorSupport;
import ru.hzerr.file.HFile;
import ru.hzerr.loaders.FXMLLoader;
import ru.hzerr.log.LogManager;
import ru.hzerr.modification.state.impl.McSkillState;
import ru.hzerr.modification.state.strategy.StateManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class McSkillController {

    @FXML private URL location;
    @FXML private ResourceBundle resources;
    @FXML private AnchorPane root;
    @FXML private JFXToggleButton debugEnabledMcSkill;
    @FXML private JFXToggleButton deleteSecurityMcSkill;
    @FXML private JFXToggleButton changeBackgroundMcSkill;
    @FXML private JFXToggleButton addJFoenixMcSkill;
    @FXML private JFXToggleButton deleteJFoenixMcSkill;
    @FXML private JFXToggleButton rebuildMcSkill;
    @FXML private JFXToggleButton unpackMcSkill;
    @FXML private JFXToggleButton deleteBuildFileMcSkill;
    @FXML private JFXToggleButton cleanProjectFolderMcSkill;
    @FXML private JFXToggleButton buildMcSkill;
    @FXML private JFXToggleButton updateRuntimeFolderMcSkill;

    @FXML
    void initialize() {
        debugEnabledMcSkill.selectedProperty().addListener(new McSkillDebugEnabledChangeListener());
        deleteSecurityMcSkill.selectedProperty().addListener((observable, o, n) -> StateManager.getInstance().changeState(McSkillState.class, state -> state.setDeleteSecurity(n)));
        addJFoenixMcSkill.selectedProperty().addListener((observable, o, n) -> StateManager.getInstance().changeState(McSkillState.class, state -> state.setPrependJFoenix(n)));
        deleteJFoenixMcSkill.selectedProperty().addListener((observable, o, n) -> StateManager.getInstance().changeState(McSkillState.class, state -> state.setRemoveJFoenix(n)));
        rebuildMcSkill.selectedProperty().addListener((observable, o, n) -> StateManager.getInstance().changeState(McSkillState.class, state -> state.setRebuild(n)));
        unpackMcSkill.selectedProperty().addListener((observable, o, n) -> StateManager.getInstance().changeState(McSkillState.class, state -> state.setDecompress(n)));
        deleteBuildFileMcSkill.selectedProperty().addListener((observable, o, n) -> StateManager.getInstance().changeState(McSkillState.class, state -> state.setDeleteBuildFile(n)));
        cleanProjectFolderMcSkill.selectedProperty().addListener((observable, o, n) -> StateManager.getInstance().changeState(McSkillState.class, state -> state.setDeleteProjectFolder(n)));
        buildMcSkill.selectedProperty().addListener((observable, o, n) -> StateManager.getInstance().changeState(McSkillState.class, state -> state.setConstruct(n)));
        updateRuntimeFolderMcSkill.selectedProperty().addListener((observable, o, n) -> StateManager.getInstance().changeState(McSkillState.class, state -> state.setReplaceRuntimeFolder(n)));
        changeBackgroundMcSkill.selectedProperty().addListener((observable, o, n) -> {
            if (n) {
                try {
                    FileChooser projectChooser = new FileChooser();
                    projectChooser.setTitle(resources.getString("file.chooser.title.background"));
                    ChoiceFileController choiceFileController = new ChoiceFileController();
                    choiceFileController.setExplorer(projectChooser);
                    if (HElias.getProperties().getMcSkillBackground().exists()) {
                        choiceFileController.setValue(HElias.getProperties().getMcSkillBackground().getLocation());
                    } else choiceFileController.setValue("");
                    choiceFileController.setOnFinished(selectedValue -> {
                        try {
                            HElias.getProperties().setMcSkillBackground(new HFile(selectedValue));
                            StateManager.getInstance().changeState(McSkillState.class, state -> state.setChangeBackground(true));
                        } catch (IOException io) { ErrorSupport.showErrorPopup(io); }
                    });
                    choiceFileController.setRoot(root);
                    FXMLLoader.showPopup("choice-file", resources, choiceFileController);
                } catch (IOException io) { ErrorSupport.showErrorPopup(io); }
            } else
                StateManager.getInstance().changeState(McSkillState.class, state -> state.setChangeBackground(false));
        });
        HElias.getProperties().addListener(new BooleanEventListener(PropertyNames.EXPERT_MODE) {
            @Override
            public void onRun(Boolean newValue) {
                revalidateButtons(newValue,
                        addJFoenixMcSkill,
                        deleteJFoenixMcSkill,
                        unpackMcSkill,
                        deleteBuildFileMcSkill,
                        cleanProjectFolderMcSkill,
                        buildMcSkill,
                        updateRuntimeFolderMcSkill,
                        rebuildMcSkill);
                LogManager.getLogger().debug("Buttons have been successfully revalidated");
            }
        });
        LogManager.getLogger().info("McSkill tab was initialized");
    }

    private void revalidateButtons(boolean newValue, JFXToggleButton... buttons) {
        for (JFXToggleButton button: buttons) {
            button.setDisable(!newValue);
            button.setVisible(newValue);
        }
    }
}
