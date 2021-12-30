package ru.hzerr.modification.chain.stage;

import ru.hzerr.config.profile.Profile;
import ru.hzerr.exception.ErrorSupport;
import ru.hzerr.modification.chain.BaseStage;
import ru.hzerr.modification.chain.Level;

/**
 * The project directory cleanup stage
 */
public abstract class CleanupProjectDirectoryStage extends BaseStage<Boolean> {

    public CleanupProjectDirectoryStage() {
        super();
    }

    @Override
    public final Boolean run(Profile profile) throws Throwable {
        profile.getStructureProperty().getValue().cleanupDecompressDirectory();
        profile.getStructureProperty().getValue().deleteBuildFile();
        return profile.getStructureProperty().getValue().getBuildFile().notExists();
    }

    @Override
    public <X extends Throwable> void onError(X throwable) {
        ErrorSupport.showErrorPopup(throwable);
    }

    @Override
    public final Integer level() {
        return Level.CLEANUP.getLevel();
    }

    @Override
    public String description() {
        return "The build file deletion stage";
    }
}
