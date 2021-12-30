package ru.hzerr.modification.chain.stage.impl.mcskill;

import ru.hzerr.log.LogManager;
import ru.hzerr.modification.chain.stage.combine.SashokRemoveSecurityStage;

public class McSkillRemoveSecurityStage extends SashokRemoveSecurityStage {

    @Override
    public void onStart() {
        LogManager.getLogger().debug("Starting the removal phase of the McSkill project's defense");
    }

    @Override
    public void onExit(Object result) {
        LogManager.getLogger().debug("Completion of the McSkill project removal phase");
    }
}
