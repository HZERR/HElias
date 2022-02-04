package ru.hzerr.modification.chain.stage.impl.mythical.world;

import ru.hzerr.log.LogManager;
import ru.hzerr.modification.chain.stage.LaunchStage;

public class MythicalWorldLaunchStage extends LaunchStage {

    @Override
    public void onStart() {
        LogManager.getLogger().debug("Starting the pre-launch phase of the MythicalWorld project");
    }

    @Override
    public void onExit(Object result) {
        LogManager.getLogger().debug("Completion of the pre-launch phase of the MythicalWorld project");
    }
}
