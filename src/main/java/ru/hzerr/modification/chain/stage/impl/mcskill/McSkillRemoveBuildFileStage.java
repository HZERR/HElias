package ru.hzerr.modification.chain.stage.impl.mcskill;

import ru.hzerr.log.LogManager;
import ru.hzerr.modification.chain.stage.RemoveBuildFileStage;

public class McSkillRemoveBuildFileStage extends RemoveBuildFileStage {

    @Override
    public void onStart() {
        LogManager.getLogger().debug("Starting the build-file cleanup phase of the McSkill project");
    }

    @Override
    public void onExit(Object result) {
        LogManager.getLogger().debug("Completion the McSkill project build-file cleanup phase");
    }
}
