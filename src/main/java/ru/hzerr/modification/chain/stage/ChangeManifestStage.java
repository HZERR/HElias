package ru.hzerr.modification.chain.stage;

import ru.hzerr.config.profile.Profile;
import ru.hzerr.exception.ErrorSupport;
import ru.hzerr.exception.modification.ManifestNotFoundException;
import ru.hzerr.file.BaseDirectory;
import ru.hzerr.log.LogManager;
import ru.hzerr.modification.chain.BaseStage;
import ru.hzerr.modification.chain.Level;
import ru.hzerr.modification.util.ManifestChanger;

/**
 * The stage of changing the project manifest
 */
public abstract class ChangeManifestStage extends BaseStage<Void> {

    public ChangeManifestStage() {
        super();
    }

    @Override
    public Void run(Profile profile) throws Throwable {
        final BaseDirectory META_INF = profile.getStructureProperty().getValue().getDecompressionDir()
                .findDirectoriesByNames("META-INF")
                .findFirst().orElseThrow(ManifestNotFoundException::new);
        ManifestChanger.create().apply(META_INF);
        return null;
    }

    @Override
    public <X extends Throwable> void onError(X throwable) {
        LogManager.getLogger().error("An error occurred when modifying the project manifest");
        ErrorSupport.showErrorPopup(throwable);
        LogManager.getLogger().error("Stopping further processing...");
    }

    @Override
    public Integer level() {
        return Level.TRANSFORM.getLevel();
    }

    @Override
    public String description() {
        return "The stage of changing the project manifest";
    }
}
