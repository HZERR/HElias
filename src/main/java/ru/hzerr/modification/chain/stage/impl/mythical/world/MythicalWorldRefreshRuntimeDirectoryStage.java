package ru.hzerr.modification.chain.stage.impl.mythical.world;

import javafx.application.Platform;
import javafx.scene.layout.VBox;
import ru.hzerr.log.LogManager;
import ru.hzerr.modification.chain.advanced.VBoxModificationEditable;
import ru.hzerr.modification.chain.stage.RefreshRuntimeDirectoryStage;

public class MythicalWorldRefreshRuntimeDirectoryStage extends RefreshRuntimeDirectoryStage implements VBoxModificationEditable {

    private VBox vBox;

    public MythicalWorldRefreshRuntimeDirectoryStage(VBox vBox) {
        super();
        this.vBox = vBox;
    }

    @Override
    public void onStart() {
        LogManager.getLogger().debug("Starting the update phase of the \"runtime\" folder in the MythicalWorld project");
        Platform.runLater(() -> {
            append(vBox, "Starting the update phase of the \"runtime\" folder in the MythicalWorld project...");
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
        LogManager.getLogger().debug("Completion the update phase of the \"runtime\" folder in the MythicalWorld project");
        Platform.runLater(() -> switchStateToCompleted(vBox));
    }
}
