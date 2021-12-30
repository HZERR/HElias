package ru.hzerr.modification.chain.stage.impl.mcskill;

import ru.hzerr.log.LogManager;
import ru.hzerr.modification.chain.stage.LaunchStage;

public class McSkillLaunchStage extends LaunchStage {

    @Override
    public void onStart() {
        LogManager.getLogger().debug("Starting the pre-launch phase of the McSkill project");
    }

    @Override
    public void onExit(Object result) {
        LogManager.getLogger().debug("Completion of the pre-launch phase of the McSkill project");
    }
}
