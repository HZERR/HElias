package ru.hzerr.modification.chain.stage.combine;

import ru.hzerr.config.profile.Profile;
import ru.hzerr.exception.modification.ManifestNotFoundException;
import ru.hzerr.file.BaseDirectory;
import ru.hzerr.modification.annotation.OnlyProject;
import ru.hzerr.modification.util.ManifestChanger;
import ru.hzerr.modification.util.SashokTransformator;

@OnlyProject("Sashok724")
public abstract class SashokRemoveSecurityStage extends RemoveSecurityStage {

    @Override
    public Void run(Profile profile) throws Throwable {
        final BaseDirectory META_INF = profile.getStructureProperty().getValue().getDecompressionDir()
                .findDirectoriesByNames("META-INF")
                .findFirst().orElseThrow(ManifestNotFoundException::new);
        ManifestChanger.create().apply(META_INF);
        SashokTransformator.create().apply(profile);
        return null;
    }
}
