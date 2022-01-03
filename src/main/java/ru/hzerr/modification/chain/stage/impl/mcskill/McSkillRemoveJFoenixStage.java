package ru.hzerr.modification.chain.stage.impl.mcskill;

import javafx.application.Platform;
import javafx.scene.layout.VBox;
import ru.hzerr.log.LogManager;
import ru.hzerr.modification.chain.advanced.VBoxModificationEditable;
import ru.hzerr.modification.chain.stage.RemoveJFoenixStage;

public class McSkillRemoveJFoenixStage extends RemoveJFoenixStage implements VBoxModificationEditable {

    private VBox vBox;

    public McSkillRemoveJFoenixStage(VBox vBox) {
        super();
        this.vBox = vBox;
    }

    @Override
    public void onStart() {
        LogManager.getLogger().debug("Starting to remove the JFoenix library from the McSkill project");
        Platform.runLater(() -> {
            append(vBox, "Starting to remove the JFoenix library from the McSkill project...");
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
        LogManager.getLogger().debug("Completing the removal of the JFoenix library from the McSkill project");
        Platform.runLater(() -> switchStateToCompleted(vBox));
    }
}
