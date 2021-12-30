package ru.hzerr.modification.chain.stage.impl.mcskill;

import ru.hzerr.log.LogManager;
import ru.hzerr.modification.chain.stage.combine.RebuildStage;

public class McSkillRebuildStage extends RebuildStage {

    @Override
    public void onStart() {
        LogManager.getLogger().debug("Starting the rebuilding phase of the McSkill project");
    }

    @Override
    public void onExit(Object result) {
        LogManager.getLogger().debug("Completing the rebuilding phase of the McSkill project");
    }
}
