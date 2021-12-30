package ru.hzerr.modification.chain.stage;

import ru.hzerr.config.profile.Profile;
import ru.hzerr.exception.ErrorSupport;
import ru.hzerr.log.LogManager;
import ru.hzerr.modification.annotation.OnlyProject;
import ru.hzerr.modification.chain.BaseStage;
import ru.hzerr.modification.chain.Level;

/**
 * The stage of refreshing the runtime folder of the project
 */
@OnlyProject("Sashok724")
public abstract class RefreshRuntimeDirectoryStage extends BaseStage<Void> {

    public RefreshRuntimeDirectoryStage() {
        super();
    }

    @Override
    public Void run(Profile profile) throws Throwable {
        return null;
    }

    @Override
    public <X extends Throwable> void onError(X throwable) {
        LogManager.getLogger().error("An error occurred while updating the runtime folder of the project");
        ErrorSupport.showErrorPopup(throwable);
        LogManager.getLogger().error("Stopping further processing...");
    }

    @Override
    public Integer level() {
        return Level.UPDATE.getLevel();
    }

    @Override
    public String description() {
        return "The stage of refreshing the runtime folder of the project";
    }
}
