package ru.hzerr.modification.chain.stage;

import ru.hzerr.config.profile.Profile;
import ru.hzerr.exception.ErrorSupport;
import ru.hzerr.log.LogManager;
import ru.hzerr.modification.chain.BaseStage;
import ru.hzerr.modification.chain.Level;
import ru.hzerr.modification.util.Builder;

/**
 * Build stage of the project
 */
public abstract class BuildStage extends BaseStage<Void> {

    public BuildStage() {
        super();
    }

    @Override
    public Void run(Profile profile) throws Throwable {
        Builder builder = Builder.create();
        builder.setNewJarFile(profile.getStructureProperty().get().getBuildFile());
        builder.apply(profile.getStructureProperty().getValue().getDecompressionDir().getAllFiles(false));
        return null;
    }

    @Override
    public <X extends Throwable> void onError(X throwable) {
        LogManager.getLogger().error("An error occurred during the building of the project");
        ErrorSupport.showErrorPopup(throwable);
        LogManager.getLogger().error("Stopping further processing...");
    }

    @Override
    public Integer level() {
        return Level.BUILD.getLevel();
    }

    @Override
    public String description() {
        return "Build stage of the project";
    }
}
