package ru.hzerr.modification.chain.builder.impl;

import org.jetbrains.annotations.NotNull;
import ru.hzerr.HElias;
import ru.hzerr.modification.state.impl.McSkillState;
import ru.hzerr.modification.state.strategy.State;
import ru.hzerr.exception.modification.BackgroundNotFoundException;
import ru.hzerr.exception.modification.ExtensionNotEqualsException;
import ru.hzerr.exception.modification.ResourceNotFoundException;
import ru.hzerr.file.BaseFile;
import ru.hzerr.modification.Project;
import ru.hzerr.modification.chain.builder.strategy.SashokChainBuilder;

import java.io.FileNotFoundException;

public abstract class McSkillChainBuilder extends SashokChainBuilder {

    public McSkillChainBuilder(Project project) {
        super(project);
    }

    @Override
    public <T extends State> void init(@NotNull T state) {
        if (state instanceof McSkillState) {
            McSkillState mw = (McSkillState) state;
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
                    .getSubDirectory("styles")
                    .getSubDirectory("dialog")
                    .getSubDirectory("img")
                    .getSubFile("BG.png");
            BaseFile substitute = HElias.getProperties().getBackgroundDir()
                    .findFilesByNames("mcskill")
                    .findFirst()
                    .orElseThrow(FileNotFoundException::new);
            if (replaceable.notExists()) throw new BackgroundNotFoundException(HElias.getProperties().getLanguage().getBundle().getString("mc.skill.background.not.found.exception"));
            if (substitute.notExists()) throw new ResourceNotFoundException(HElias.getProperties().getLanguage().getBundle().getString("background.resource.not.found.exception"));
            if (replaceable.notEqualsExtension(substitute)) throw new ExtensionNotEqualsException(HElias.getProperties().getLanguage().getBundle().getString("mc.skill.background.extension.not.equals.exception"));
            substitute.copyToFile(replaceable);
        }, this::onSuccessChangeBackground);
    }
}
