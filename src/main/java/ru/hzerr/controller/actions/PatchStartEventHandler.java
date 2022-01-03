package ru.hzerr.controller.actions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;
import ru.hzerr.HElias;
import ru.hzerr.config.profile.Profile;
import ru.hzerr.exception.ErrorSupport;
import ru.hzerr.file.BaseFile;
import ru.hzerr.modification.chain.BaseLayeredProjectChangerChain;
import ru.hzerr.modification.chain.impl.McSkillLayeredProjectChangerChain;
import ru.hzerr.modification.state.strategy.ProjectType;
import ru.hzerr.util.SystemInfo;

import java.util.Optional;
import java.util.ResourceBundle;

public class PatchStartEventHandler implements EventHandler<ActionEvent> {

    private VBox vBoxInfo;
    private final ResourceBundle resources = HElias.getProperties().getLanguage().getBundle();

    public PatchStartEventHandler(VBox vBoxInfo) {
        this.vBoxInfo = vBoxInfo;
    }

    @Override
    public void handle(ActionEvent event) {
        if (isInvalidUserData()) return;

        BaseLayeredProjectChangerChain chain = new McSkillLayeredProjectChangerChain(HElias.getProperties().getDefaultProfile().get(), vBoxInfo);
        chain.init(HElias.getProperties().getDefaultProfile().get().getSettingsProperty().get().getState());
        chain.apply();
    }

    private boolean isInvalidUserData() {
        Optional<Profile> defaultProfile = HElias.getProperties().getDefaultProfile();
        if (!defaultProfile.isPresent()) {
            ErrorSupport.showWarningPopup(resources.getString("tab.patcher.warning.popup.no.define.default.profile.title"), resources.getString("tab.patcher.warning.popup.no.define.default.profile.message"));
            return true;
        }
        BaseFile commercial = defaultProfile.get().getStructureProperty().getValue().getCommercialProjectJarFile();
        if (commercial.notExists()) {
            ErrorSupport.showWarningPopup(resources.getString("tab.patcher.warning.popup.no.such.commercial.jar.file.title"), resources.getString("tab.patcher.warning.popup.no.such.commercial.jar.file.message"));
            return true;
        }

        // TODO: 29.11.2021 REWRITE
        if (defaultProfile.get().getSettingsProperty().getValue().getState().equals(ProjectType.MC_SKILL)) {
            if (SystemInfo.isWindows()) {
                final String title = resources.getString("tab.projects.mc.skill.for.windows.warning.popup.title");
                final String message = resources.getString("tab.projects.mc.skill.for.windows.warning.popup.message");
                ErrorSupport.showWarningPopup(title, message);
                return true;
            }
        }

        return false;
    }
}
