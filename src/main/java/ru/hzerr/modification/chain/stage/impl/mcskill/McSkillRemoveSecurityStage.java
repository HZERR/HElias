package ru.hzerr.modification.chain.stage.impl.mcskill;

import javafx.application.Platform;
import javafx.scene.layout.VBox;
import ru.hzerr.log.LogManager;
import ru.hzerr.modification.chain.advanced.VBoxModificationEditable;
import ru.hzerr.modification.chain.stage.combine.SashokRemoveSecurityStage;

public class McSkillRemoveSecurityStage extends SashokRemoveSecurityStage implements VBoxModificationEditable {

    private VBox vBox;

    public McSkillRemoveSecurityStage(VBox vBox) {
        super();
        this.vBox = vBox;
    }

    @Override
    public void onStart() {
        LogManager.getLogger().debug("Starting the removal phase of the McSkill project's defense");
        Platform.runLater(() -> {
            append(vBox, "Starting the removal phase of the McSkill project's defense...");
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
        LogManager.getLogger().debug("Completion of the McSkill project removal phase");
        Platform.runLater(() -> switchStateToCompleted(vBox));
    }
}
