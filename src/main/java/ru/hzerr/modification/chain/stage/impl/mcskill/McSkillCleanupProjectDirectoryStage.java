package ru.hzerr.modification.chain.stage.impl.mcskill;

import javafx.application.Platform;
import javafx.scene.layout.VBox;
import ru.hzerr.log.LogManager;
import ru.hzerr.modification.chain.advanced.VBoxModificationEditable;
import ru.hzerr.modification.chain.stage.CleanupProjectDirectoryStage;

public class McSkillCleanupProjectDirectoryStage extends CleanupProjectDirectoryStage implements VBoxModificationEditable {

    private VBox vBox;

    public McSkillCleanupProjectDirectoryStage(VBox vBox) {
        super();
        this.vBox = vBox;
    }

    @Override
    public void onStart() {
        LogManager.getLogger().debug("Starting the catalog cleanup phase of the McSkill project");
        Platform.runLater(() -> {
            append(vBox, "Starting the catalog cleanup phase of the McSkill project...");
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
        LogManager.getLogger().debug("Completing the McSkill project directory cleanup phase");
        Platform.runLater(() -> switchStateToCompleted(vBox));
    }
}
