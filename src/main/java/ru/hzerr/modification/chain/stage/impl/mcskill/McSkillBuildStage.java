package ru.hzerr.modification.chain.stage.impl.mcskill;

import javafx.application.Platform;
import javafx.scene.layout.VBox;
import ru.hzerr.log.LogManager;
import ru.hzerr.modification.chain.advanced.VBoxModificationEditable;
import ru.hzerr.modification.chain.stage.BuildStage;

public class McSkillBuildStage extends BuildStage implements VBoxModificationEditable {

    private VBox vBox;

    public McSkillBuildStage(VBox vBox) {
        super();
        this.vBox = vBox;
    }

    @Override
    public void onStart() {
        LogManager.getLogger().debug("Start of the construction phase of the McSkill project");
        Platform.runLater(() -> {
            append(vBox, "Start of the construction phase of the McSkill project...");
            switchStateToProcessing(vBox);
        });
    }

    @Override
    public <X extends Throwable> void onError(X throwable) {
        super.onError(throwable);
        Platform.runLater(() -> switchStateToIncorrect(vBox));
    }

    @Override
    public void onExit(Object result) {
        LogManager.getLogger().debug("Completion of the construction phase of the McSkill project");
        Platform.runLater(() -> switchStateToCompleted(vBox));
    }
}
