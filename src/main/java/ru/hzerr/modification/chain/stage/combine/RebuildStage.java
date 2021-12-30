package ru.hzerr.modification.chain.stage.combine;

import ru.hzerr.config.profile.Profile;
import ru.hzerr.exception.ErrorSupport;
import ru.hzerr.log.LogManager;
import ru.hzerr.modification.chain.BaseStage;
import ru.hzerr.modification.chain.Level;
import ru.hzerr.modification.util.Builder;

/**
 * Project rebuild stage
 */
public abstract class RebuildStage extends BaseStage<Boolean> {

    public RebuildStage() {
        super();
    }

    @Override
    public Boolean run(Profile profile) throws Throwable {
        profile.getStructureProperty().getValue().deleteBuildFile();
        if (profile.getStructureProperty().getValue().getBuildFile().notExists()) {
            Builder builder = Builder.create();
            builder.setNewJarFile(profile.getStructureProperty().get().getBuildFile());
            builder.apply(profile.getStructureProperty().getValue().getDecompressionDir().getAllFiles(false));
            return true;
        }

        return false;
    }

    @Override
    public <X extends Throwable> void onError(X throwable) {
        LogManager.getLogger().error("An error occurred while rebuilding the project");
        ErrorSupport.showErrorPopup(throwable);
        LogManager.getLogger().error("Stopping further processing...");
    }

    @Override
    public Integer level() {
        return Level.BUILD.getLevel();
    }

    @Override
    public String description() {
        return "Project rebuild stage";
    }
}
