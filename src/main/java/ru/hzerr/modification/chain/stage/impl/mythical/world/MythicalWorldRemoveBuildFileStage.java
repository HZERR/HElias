package ru.hzerr.modification.chain.stage.impl.mythical.world;

import javafx.application.Platform;
import javafx.scene.layout.VBox;
import ru.hzerr.log.LogManager;
import ru.hzerr.modification.chain.advanced.VBoxModificationEditable;
import ru.hzerr.modification.chain.stage.RemoveBuildFileStage;

public class MythicalWorldRemoveBuildFileStage extends RemoveBuildFileStage implements VBoxModificationEditable {

    private VBox vBox;

    public MythicalWorldRemoveBuildFileStage(VBox vBox) {
        super();
        this.vBox = vBox;
    }

    @Override
    public void onStart() {
        LogManager.getLogger().debug("Starting the build-file cleanup phase of the MythicalWorld project");
        Platform.runLater(() -> {
            append(vBox, "Starting the build-file cleanup phase of the MythicalWorld project...");
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
        LogManager.getLogger().debug("Completion the MythicalWorld project build-file cleanup phase");
        Platform.runLater(() -> switchStateToCompleted(vBox));
    }
}
