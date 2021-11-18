package ru.hzerr.modification.chain.builder.impl;

import org.jetbrains.annotations.NotNull;
import ru.hzerr.HElias;
import ru.hzerr.collections.list.ArrayHList;
import ru.hzerr.collections.list.HList;
import ru.hzerr.modification.state.impl.MythicalWorldState;
import ru.hzerr.modification.state.strategy.State;
import ru.hzerr.exception.modification.BackgroundNotFoundException;
import ru.hzerr.exception.modification.ExtensionNotEqualsException;
import ru.hzerr.exception.modification.ResourceNotFoundException;
import ru.hzerr.file.BaseFile;
import ru.hzerr.log.SessionLogManager;
import ru.hzerr.modification.Project;
import ru.hzerr.modification.chain.builder.strategy.SashokChainBuilder;

import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.util.stream.Collectors;

public abstract class MythicalWorldChainBuilder extends SashokChainBuilder {

    public MythicalWorldChainBuilder(Project project) { super(project); }

    @Override
    public <T extends State> void init(@NotNull T state) {
        if (state instanceof MythicalWorldState) {
            MythicalWorldState mw = (MythicalWorldState) state;
            if (mw.isDeleteBuildFile()) onCleanupBuildFile();
            if (mw.isDeleteProjectFolder()) onCleanupProjectFolder();
            if (mw.isChangeBackground()) onChangeBackground();
            if (mw.isConstruct()) onBuild();
            if (mw.isDecompress()) onDecompress();
            if (mw.isDeleteSecurity()) onDeleteSecurity();
            if (mw.isPrependJFoenix()) onPrependJFoenix();
            if (mw.isRemoveJFoenix()) onDeleteJFoenix();
            if (mw.isRebuild()) onRebuild();
            if (mw.isReplaceRuntimeFolder()) onUpdateRuntimeFolder();
            HElias.getProperties().setForceDebug(mw.isDebugModeEnabled());
        }
    }

    public void onChangeBackground() {
        addOnChangeBackground(innerProject -> {
            BaseFile replaceable = innerProject.getUnpack()
                    .getSubDirectory("runtime")
                    .getSubDirectory("dialog")
                    .getSubFile("dialog.png");
            BaseFile substitute = HElias.getProperties().getBackgroundDir()
                    .findFilesByNames("mythical-world")
                    .findFirst()
                    .orElseThrow(FileNotFoundException::new);
            if (replaceable.notExists()) throw new BackgroundNotFoundException(HElias.getProperties().getLanguage().getBundle().getString("mythical.world.background.not.found.exception"));
            if (substitute.notExists()) throw new ResourceNotFoundException(HElias.getProperties().getLanguage().getBundle().getString("background.resource.not.found.exception"));
            if (replaceable.notEqualsExtension(substitute)) throw new ExtensionNotEqualsException(HElias.getProperties().getLanguage().getBundle().getString("mythical.world.background.extension.not.equals.exception"));
            BaseFile dialogFXML = innerProject.getUnpack()
                    .getSubDirectory("runtime")
                    .getSubDirectory("dialog")
                    .getSubFile("dialog.fxml");
            HList<String> lines = dialogFXML.readLines(Charset.defaultCharset()).collect(Collectors.toCollection(ArrayHList::new));
            lines.replaceIf(line -> line.contains("fx:id=\"background\" fitHeight=\"450.0\" fitWidth=\"346.0\" pickOnBounds=\"true\" preserveRatio=\"true\""), oldLine -> {
                String lineAfterReplacement = oldLine.replace("true", "false");
                SessionLogManager.getManager().getLogger().info("Replaceable line: " + oldLine);
                SessionLogManager.getManager().getLogger().info("Line after replacement: " + lineAfterReplacement);
                return lineAfterReplacement;
            });
            dialogFXML.writeLines(lines);
            substitute.copyToFile(replaceable);
        }, this::onSuccessChangeBackground);
    }
}
