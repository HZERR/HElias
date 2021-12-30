package ru.hzerr.modification.chain.stage.impl.mcskill;

import ru.hzerr.HElias;
import ru.hzerr.config.profile.Profile;
import ru.hzerr.exception.modification.BackgroundNotFoundException;
import ru.hzerr.exception.modification.ExtensionNotEqualsException;
import ru.hzerr.exception.modification.ResourceNotFoundException;
import ru.hzerr.file.BaseFile;
import ru.hzerr.log.LogManager;
import ru.hzerr.modification.chain.stage.ChangeBackgroundStage;

import java.io.FileNotFoundException;

public class McSkillChangeBackgroundStage extends ChangeBackgroundStage {

    public McSkillChangeBackgroundStage() {
        super();
    }

    @Override
    public void onStart() {
        LogManager.getLogger().debug("Starting the wallpaper modification phase of the McSkill project");
    }

    @Override
    public Void run(Profile profile) throws Throwable {
        BaseFile replaceable = profile.getStructureProperty().getValue().getDecompressionDir()
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
        return null;
    }

    @Override
    public void onExit(Object result) {
        LogManager.getLogger().debug("Completion the wallpaper modification phase of the McSkill project");
    }
}
