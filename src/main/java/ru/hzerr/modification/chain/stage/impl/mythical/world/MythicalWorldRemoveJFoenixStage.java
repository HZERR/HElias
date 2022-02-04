package ru.hzerr.modification.chain.stage.impl.mythical.world;

import javafx.application.Platform;
import javafx.scene.layout.VBox;
import ru.hzerr.log.LogManager;
import ru.hzerr.modification.chain.advanced.VBoxModificationEditable;
import ru.hzerr.modification.chain.stage.RemoveJFoenixStage;

public class MythicalWorldRemoveJFoenixStage extends RemoveJFoenixStage implements VBoxModificationEditable {

    private VBox vBox;

    public MythicalWorldRemoveJFoenixStage(VBox vBox) {
        super();
        this.vBox = vBox;
    }

    @Override
    public void onStart() {
        LogManager.getLogger().debug("Starting to remove the JFoenix library from the MythicalWorld project");
        Platform.runLater(() -> {
            append(vBox, "Starting to remove the JFoenix library from the MythicalWorld project...");
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
        LogManager.getLogger().debug("Completing the removal of the JFoenix library from the MythicalWorld project");
        Platform.runLater(() -> switchStateToCompleted(vBox));
    }
}
