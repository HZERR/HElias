package ru.hzerr.modification.chain.stage.impl.mcskill;

import ru.hzerr.log.LogManager;
import ru.hzerr.modification.chain.stage.CleanupProjectDirectoryStage;

public class McSkillCleanupProjectDirectoryStage extends CleanupProjectDirectoryStage {

    @Override
    public void onStart() {
        LogManager.getLogger().debug("Starting the catalog cleanup phase of the McSkill project");
    }

    @Override
    public void onExit(Object result) {
        LogManager.getLogger().debug("Completing the McSkill project directory cleanup phase");
    }
}
