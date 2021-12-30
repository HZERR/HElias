package ru.hzerr.modification.chain.stage.impl.mcskill;

import ru.hzerr.log.LogManager;
import ru.hzerr.modification.chain.stage.BuildStage;

public class McSkillBuildStage extends BuildStage {

    @Override
    public void onStart() {
        LogManager.getLogger().debug("Start of the construction phase of the McSkill project");
    }

    @Override
    public void onExit(Object result) {
        LogManager.getLogger().debug("Completion of the construction phase of the McSkill project");
    }
}
