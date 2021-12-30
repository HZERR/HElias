package ru.hzerr.modification.chain.stage.impl.mcskill;

import ru.hzerr.log.LogManager;
import ru.hzerr.modification.chain.stage.PrependJFoenixStage;

public class McSkillPrependJFoenixStage extends PrependJFoenixStage {

    @Override
    public void onStart() {
        LogManager.getLogger().debug("Starting the removal phase of the JFoenix library from the McSkill project");
    }

    @Override
    public void onExit(Object result) {
        LogManager.getLogger().debug("Completion the removal phase of the JFoenix library from the McSkill project");
    }
}
