package ru.hzerr.controller.actions;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.util.Duration;
import ru.hzerr.HElias;
import ru.hzerr.config.listener.content.Content;
import ru.hzerr.config.listener.content.ContentInstaller;
import ru.hzerr.exception.ErrorSupport;
import ru.hzerr.file.HDirectory;
import ru.hzerr.file.HFile;
import ru.hzerr.log.SessionLogManager;
import ru.hzerr.modification.Project;
import ru.hzerr.modification.chain.builder.impl.McSkillChainBuilderImpl;
import ru.hzerr.modification.chain.builder.impl.MythicalWorldChainBuilderImpl;
import ru.hzerr.modification.chain.builder.strategy.SashokChainBuilder;
import ru.hzerr.modification.state.strategy.Count;
import ru.hzerr.modification.state.strategy.ProjectType;
import ru.hzerr.modification.state.strategy.State;
import ru.hzerr.modification.state.strategy.StateManager;
import ru.hzerr.util.SystemInfo;

import java.io.IOException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class PatchStartEventHandler implements EventHandler<ActionEvent>, ContentInstaller<PatchStartEventHandler> {

    // USER DATA
    private ProjectType selectedType;
    private JFXButton startButton;
    private Label state;

    // OTHER DATA
    private final ResourceBundle resources = HElias.getProperties().getLanguage().getBundle();
    private State selectedState;

    @Override
    public PatchStartEventHandler installContent(Content userData) {
        checkArguments(userData, "startButton", "state");
        this.selectedType = userData.get("selectedType", ProjectType.class);
        this.startButton = userData.get("startButton", JFXButton.class);
        this.state = userData.get("state", Label.class);
        return this;
    }

    @Override
    public void handle(ActionEvent event) {
        if (noValidUserData()) return;

        final HFile originalProjectJarFile = new HFile(HElias.getProperties().getProjectFullName());
        HDirectory newRoot;
        Project project = null;
        try {
            newRoot = HElias.getProperties().getProjectsDir().getSubDirectory(String.valueOf(originalProjectJarFile.checksum()));
            if (newRoot.notExists()) {
                newRoot.create();
                project = Project.create(originalProjectJarFile, newRoot, HElias.getProperties().getProjectTestName());
            } else project = Project.getProject(originalProjectJarFile, newRoot, HElias.getProperties().getProjectTestName());
        } catch (IOException e) { ErrorSupport.showErrorPopup(e); }
        // Fetching a state
        StateManager.getInstance().findState(selectedType).ifPresent(state -> this.selectedState = state);
        if (Objects.isNull(selectedState)) {
            SessionLogManager.getManager().getLogger().info("Select type " + selectedType.getProjectType().getSimpleName() + " not found in state manager");
            return;
        }

        SashokChainBuilder builder;
        Content content = new Content();
        content.put("state", state);
        content.put("countEnabled", selectedState.count(Count.State.ENABLED));
        switch (selectedType) {
            case MYTHICAL_WORLD:
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
        builder.installContent(content);
        builder.init(selectedState);
        builder.setOnFinishedIsComplete(() -> {
            Platform.runLater(() -> state.setText("100%"));
            Timer finisher = new Timer("Timer Finisher", true);
            finisher.schedule(new TimerTask() {

                @Override
                public void run() {
                    Platform.runLater(() -> {
                        state.setText(resources.getString("tab.patcher.start.button.text"));
                        rt.stop();
                    });
                }
            }, 1000L);
        });
        builder.setOnFinishedIsError(() -> {
            Platform.runLater(() -> state.setText(resources.getString("tab.patcher.start.button.text")));
            rt.stop();
        });
        builder.setOnError(ErrorSupport::showErrorPopup);
        builder.apply();
    }

    private boolean noValidUserData() {
        String projectFullName = HElias.getProperties().getProjectFullName();
        if (projectFullName.isEmpty()) {
            ErrorSupport.showWarningPopup(resources.getString("tab.patcher.error.popup.no.define.project.full.name.title"), resources.getString("tab.patcher.error.popup.no.define.project.full.name.message"));
            return true;
        }

        if (new HFile(projectFullName).notExists()) {
            ErrorSupport.showWarningPopup(resources.getString("tab.patcher.error.popup.no.such.project.full.name.title"), resources.getString("tab.patcher.error.popup.no.such.project.full.name.message"));
            return true;
        }

        if (selectedType != null) {
            if (selectedType.equals(ProjectType.MC_SKILL)) {
                if (SystemInfo.isWindows()) {
                    final String title = resources.getString("tab.projects.mc.skill.for.windows.warning.popup.title");
                    final String message = resources.getString("tab.projects.mc.skill.for.windows.warning.popup.message");
                    ErrorSupport.showWarningPopup(title, message);
                    return true;
                }
            }
        } else {
            final String message = resources.getString("tab.patcher.error.not.selected.project.type");
            ErrorSupport.showWarningPopup("ProjectTypeNotSelectedException", message);
            return true;
        }

        return false;
    }
}
