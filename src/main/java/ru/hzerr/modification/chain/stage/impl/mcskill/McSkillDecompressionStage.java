package ru.hzerr.modification.chain.stage.impl.mcskill;

import ru.hzerr.log.LogManager;
import ru.hzerr.modification.chain.stage.DecompressionStage;

public class McSkillDecompressionStage extends DecompressionStage {

    @Override
    public void onStart() {
        LogManager.getLogger().debug("Start of the unpacking phase of the McSkill project");
    }

    @Override
    public void onExit(Object result) {
        LogManager.getLogger().debug("Completion the unpacking phase of the McSkill project");
    }
}
