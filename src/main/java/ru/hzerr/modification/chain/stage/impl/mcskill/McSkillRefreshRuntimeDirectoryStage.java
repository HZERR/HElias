package ru.hzerr.modification.chain.stage.impl.mcskill;

import ru.hzerr.log.LogManager;
import ru.hzerr.modification.chain.stage.RefreshRuntimeDirectoryStage;

public class McSkillRefreshRuntimeDirectoryStage extends RefreshRuntimeDirectoryStage {

    @Override
    public void onStart() {
        LogManager.getLogger().debug("Starting the update phase of the \"runtime\" folder in the McSkill project");
    }

    @Override
    public void onExit(Object result) {
        LogManager.getLogger().debug("Completion the update phase of the \"runtime\" folder in the McSkill project");
    }
}
