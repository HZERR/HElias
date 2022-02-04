package ru.hzerr.modification.chain.stage.impl.mythical.world;

import ru.hzerr.log.LogManager;
import ru.hzerr.modification.chain.stage.ChangeManifestStage;

public class MythicalWorldChangeManifestStage extends ChangeManifestStage {

    @Override
    public void onStart() {
        LogManager.getLogger().debug("Start of the manifest modification phase of the MythicalWorld project");
    }

    @Override
    public void onExit(Object result) {
        LogManager.getLogger().debug("Completion of the MythicalWorld project manifest modification phase");
    }
}
