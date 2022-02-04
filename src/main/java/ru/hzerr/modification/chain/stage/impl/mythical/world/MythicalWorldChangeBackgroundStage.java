package ru.hzerr.modification.chain.stage.impl.mythical.world;

import javafx.application.Platform;
import javafx.scene.layout.VBox;
import ru.hzerr.HElias;
import ru.hzerr.collections.list.ArrayHList;
import ru.hzerr.collections.list.HList;
import ru.hzerr.config.profile.Profile;
import ru.hzerr.exception.modification.BackgroundNotFoundException;
import ru.hzerr.exception.modification.ExtensionNotEqualsException;
import ru.hzerr.exception.modification.ResourceNotFoundException;
import ru.hzerr.file.BaseFile;
import ru.hzerr.log.LogManager;
import ru.hzerr.modification.chain.advanced.VBoxModificationEditable;
import ru.hzerr.modification.chain.stage.ChangeBackgroundStage;

import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.util.stream.Collectors;

public class MythicalWorldChangeBackgroundStage extends ChangeBackgroundStage implements VBoxModificationEditable {

    private final VBox vBox;

    public MythicalWorldChangeBackgroundStage(VBox vBox) {
        super();
        this.vBox = vBox;
    }
    // TODO: 03.01.2022 SWITCH TO ENGLISH/RUSSIAN
    @Override
    public void onStart() {
        LogManager.getLogger().debug("Starting the wallpaper modification phase of the MythicalWorld project");
        Platform.runLater(() -> {
            append(vBox, "Starting the wallpaper modification phase of the MythicalWorld project...");
            switchStateToProcessing(vBox);
        });
    }

    @Override
    public Void run(Profile profile) throws Throwable {
        BaseFile replaceable = profile.getStructureProperty().getValue().getDecompressionDir()
                .getSubDirectory("runtime")
                .getSubDirectory("dialog")
                .getSubFile("dialog.png");
        BaseFile substitute = profile.getStructureProperty().getValue()
                .getAssetsDir().getFiles().findFirst()
                .orElseThrow(FileNotFoundException::new);
        if (replaceable.notExists())
            throw new BackgroundNotFoundException(HElias.getProperties().getLanguage().getBundle().getString("mc.skill.background.not.found.exception"));
        if (substitute.notExists())
            throw new ResourceNotFoundException(HElias.getProperties().getLanguage().getBundle().getString("background.resource.not.found.exception"));
        if (replaceable.notEqualsExtension(substitute))
            throw new ExtensionNotEqualsException(HElias.getProperties().getLanguage().getBundle().getString("mc.skill.background.extension.not.equals.exception"));
        BaseFile dialogFXML = profile.getStructureProperty().getValue().getDecompressionDir()
                .getSubDirectory("runtime")
                .getSubDirectory("dialog")
                .getSubFile("dialog.fxml");
        HList<String> lines = dialogFXML.readLines(Charset.defaultCharset()).collect(Collectors.toCollection(ArrayHList::new));
        lines.replaceIf(line -> line.contains("fx:id=\"background\" fitHeight=\"450.0\" fitWidth=\"346.0\" pickOnBounds=\"true\" preserveRatio=\"true\""), oldLine -> {
            String lineAfterReplacement = oldLine.replace("true", "false");
            LogManager.getLogger().debug("Replaceable line: " + oldLine);
            LogManager.getLogger().debug("Line after replacement: " + lineAfterReplacement);
            return lineAfterReplacement;
        });
        dialogFXML.writeLines(lines);
        substitute.copyToFile(replaceable);
        return null;
    }

    @Override
    public <X extends Throwable> void onError(X throwable) {
        super.onError(throwable);
        Platform.runLater(() -> switchStateToIncorrect(vBox));
    }

    @Override
    public void onExit(Object result) {
        LogManager.getLogger().debug("Completion the wallpaper modification phase of the MythicalWorld project");
        Platform.runLater(() -> switchStateToCompleted(vBox));
    }
}
