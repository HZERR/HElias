package ru.hzerr.modification.chain.stage.impl.mcskill;

import ru.hzerr.log.LogManager;
import ru.hzerr.modification.chain.stage.ChangeManifestStage;

public class McSkillChangeManifestStage extends ChangeManifestStage {

    @Override
    public void onStart() {
        LogManager.getLogger().debug("Start of the manifest modification phase of the McSkill project");
    }

    @Override
    public void onExit(Object result) {
        LogManager.getLogger().debug("Completion of the McSkill project manifest modification phase");
    }
}
