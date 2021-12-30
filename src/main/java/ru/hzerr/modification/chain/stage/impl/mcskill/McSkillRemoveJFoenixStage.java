package ru.hzerr.modification.chain.stage.impl.mcskill;

import ru.hzerr.log.LogManager;
import ru.hzerr.modification.chain.stage.RemoveJFoenixStage;

public class McSkillRemoveJFoenixStage extends RemoveJFoenixStage {

    @Override
    public void onStart() {
        LogManager.getLogger().debug("Starting to remove the JFoenix library from the McSkill project");
    }

    @Override
    public void onExit(Object result) {
        LogManager.getLogger().debug("Completing the removal of the JFoenix library from the McSkill project");
    }
}
