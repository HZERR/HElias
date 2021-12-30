package ru.hzerr.modification.chain.stage;

import ru.hzerr.config.profile.Profile;
import ru.hzerr.exception.ErrorSupport;
import ru.hzerr.log.LogManager;
import ru.hzerr.modification.chain.BaseStage;
import ru.hzerr.modification.chain.Level;

/**
 * The build file deletion stage
 */
public abstract class RemoveBuildFileStage extends BaseStage<Boolean> {

    public RemoveBuildFileStage() {
        super();
    }

    @Override
    public final Boolean run(Profile profile) throws Throwable {
        profile.getStructureProperty().getValue().deleteBuildFile();
        return profile.getStructureProperty().getValue().getBuildFile().notExists();
    }

    @Override
    public <X extends Throwable> void onError(X throwable) {
        LogManager.getLogger().error("An error occurred while deleting the build file");
        ErrorSupport.showErrorPopup(throwable);
        LogManager.getLogger().error("Stopping further processing...");
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
