package ru.hzerr.controller.actions;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.util.Duration;
import ru.hzerr.HElias;
import ru.hzerr.config.listener.content.Content;
import ru.hzerr.config.listener.content.ContentInstaller;
import ru.hzerr.config.profile.Profile;
import ru.hzerr.exception.ErrorSupport;
import ru.hzerr.file.BaseFile;
import ru.hzerr.modification.chain.BaseLayeredProjectChangerChain;
import ru.hzerr.modification.state.strategy.ProjectType;
import ru.hzerr.modification.state.strategy.State;
import ru.hzerr.util.SystemInfo;

import java.util.Optional;
import java.util.ResourceBundle;

public class PatchStartEventHandler implements EventHandler<ActionEvent>, ContentInstaller<PatchStartEventHandler> {

    private JFXButton startButton;
    private final ResourceBundle resources = HElias.getProperties().getLanguage().getBundle();

    @Override
    public PatchStartEventHandler installContent(Content userData) {
        checkArguments(userData, "startButton");
        this.startButton = userData.get("startButton", JFXButton.class);
        return this;
    }

    @Override
    public void handle(ActionEvent event) {
        if (noValidUserData()) return;

        BaseLayeredProjectChangerChain chain = ;
        Content content = new Content();
        content.put("state", state);
        content.put("countEnabled", selectedState.count(Count.State.ENABLED));
        if (HElias.getProperties().getDefaultProfile().get().getSettingsProperty().getValue().getState()) {
            case :
                builder = new MythicalWorldChainBuilderImpl(project);
                break;
            case MC_SKILL:
                builder = new McSkillChainBuilderImpl(project);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + selectedType);
        }
        // START ANIMATION
        state.setText("0%");
        RotateTransition rt = new RotateTransition(Duration.millis(2000), startButton);
        rt.setByAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setAutoReverse(true);
        rt.play();

        // INITIALIZATION OF THE BUILDER
//        builder.installContent(content);
//        builder.init(selectedState);
//        builder.setOnFinishedIsComplete(() -> {
//            Platform.runLater(() -> state.setText("100%"));
//            Timer finisher = new Timer("Timer Finisher", true);
//            finisher.schedule(new TimerTask() {
//
//                @Override
//                public void run() {
//                    Platform.runLater(() -> {
//                        state.setText(resources.getString("tab.patcher.start.button.text"));
//                        rt.stop();
//                    });
//                }
//            }, 1000L);
//        });
//        builder.setOnFinishedIsError(() -> {
//            Platform.runLater(() -> state.setText(resources.getString("tab.patcher.start.button.text")));
//            rt.stop();
//        });
//        builder.setOnError(ErrorSupport::showErrorPopup);
//        builder.apply();
    }

    private boolean noValidUserData() {
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
